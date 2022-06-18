package com.kk.eazypariksha.android.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavBackStackEntry
import com.kk.eazypariksha.stateholder.StateHolderCancelTimeout
import io.github.aakira.napier.Napier
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock


@Stable
internal class ViewModelStore(
    coroutineScope: CoroutineScope,
) {
    private val viewModelMap = HashMap<Any, ViewModelStoreEntry>()
    private val mapLock = ReentrantLock()

    // Create a copy of the provided CoroutineScope, but replacing the Job with
    // a child SupervisorJob. This allows a ViewModel to be cancelled without affecting any
    // other ViewModels.
    private val coroutineScope = CoroutineScope(
        coroutineScope.coroutineContext +
                SupervisorJob(coroutineScope.coroutineContext[Job])
    )

    /**
     * Retrieve or create a ViewModel.
     */
    @SuppressLint("LogNotTimber")
    fun <T : Any> viewModelFlow(
        key: Any,
        cancellationSignal: (suspend () -> Unit)? = null,
        create: (scope: CoroutineScope) -> T,
    ): StateFlow<T> = mapLock.withLock {
        // First check if we have a 'cached' ViewModel which is still active
        val cached = viewModelMap[key]?.takeIf { it.isActive }
        if (cached != null) {
            @Suppress("UNCHECKED_CAST")
            return cached.flow as StateFlow<T>
        }

        // If not we'll create a new ViewModel
        val viewModelScope = createChildCoroutineScope()

        val flow = flow<T> {
            Log.d("ViewModelStore", "Creating CoroutineScope with key: $key")

            // This is a 'fake' flow which doesn't emit anything. The actual ViewModel is
            // created in the stateIn() below

            if (cancellationSignal != null) {
                // If we've been given a cancellation signal, invoke it
                cancellationSignal()
            } else {
                // Otherwise we this is a no-op suspend call, and we let the WhileSubscribed
                // behavior control the cancellation
                awaitCancellation()
            }
        }.onCompletion {
            // The flow has been cancelled, so we need to also cancel the
            // ViewModel's coroutine scope
            Log.d("ViewModelStore", "Cancelling CoroutineScope with key: $key")
            viewModelScope.cancel()
            // Remove the entry from the map
            mapLock.withLock {
                val viewModel = viewModelMap.remove(key)?.flow?.value ?: return@onCompletion
                Napier.d("${viewModel::class.java.simpleName} removed")
            }
        }.stateIn(
            scope = coroutineScope,
            started = when {
                // If we've been given a cancellation signal, we rely solely on that to tell us
                // when to cancel, so use Eagerly
                cancellationSignal != null -> SharingStarted.Eagerly
                // Otherwise we use the implicit behavior of cancelling the scope with a
                // 5 seconds timeout
                else -> SharingStarted.WhileSubscribed(StateHolderCancelTimeout)
            },
            initialValue = create(viewModelScope),
        )

        // Store the child scope & flow
        viewModelMap[key] = ViewModelStoreEntry(
            coroutineScope = viewModelScope,
            flow = flow,
        )

        return flow
    }

    /**
     * Create a [CoroutineScope] for each ViewModel. This is modelled after `viewModelScope`
     */
    private fun createChildCoroutineScope(): CoroutineScope = CoroutineScope(
        Dispatchers.Main.immediate +
                SupervisorJob(coroutineScope.coroutineContext[Job])
    )
}

/**
 * Retrieve or create a ViewModel.
 */
internal fun <T : Any> ViewModelStore.viewModelFlow(
    navBackStackEntry: NavBackStackEntry,
    create: (scope: CoroutineScope) -> T,
): StateFlow<T> = viewModelFlow(
    key = navBackStackEntry.id,
    cancellationSignal = {
        // With a NavBackStackEntry, we use it's lifecycle as the signal to
        // cancel the scope
        suspendCancellableCoroutine<Unit> { cont ->
            val observer = LifecycleEventObserver { _, event ->
                // Once the lifecycle is destroyed, cancel the coroutine
                if (event == Lifecycle.Event.ON_DESTROY) {
                    cont.cancel()
                }
            }
            navBackStackEntry.lifecycle.addObserver(observer)
            cont.invokeOnCancellation {
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        }
    },
    create = create,
)

@Composable
internal inline fun <T : Any> ViewModelStore.viewModel(
    key: Any,
    noinline create: (scope: CoroutineScope) -> T,
): T = viewModelFlow(key, null, create).collectAsState().value

@Composable
internal inline fun <T : Any> ViewModelStore.viewModel(
    navBackStackEntry: NavBackStackEntry,
    noinline create: (scope: CoroutineScope) -> T,
): T = viewModelFlow(navBackStackEntry, create).collectAsState().value

private data class ViewModelStoreEntry(
    val coroutineScope: CoroutineScope,
    val flow: StateFlow<Any>,
) {
    val isActive: Boolean get() = coroutineScope.isActive
}