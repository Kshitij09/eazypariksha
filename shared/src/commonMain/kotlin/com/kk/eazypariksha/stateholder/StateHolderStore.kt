package com.kk.eazypariksha.stateholder

import co.touchlab.stately.collections.IsoMutableMap
import io.github.aakira.napier.Napier
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class StateHolderStore(
    coroutineScope: CoroutineScope,
) {
    private val viewModelMap = IsoMutableMap<Any, ViewModelStoreEntry>()

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
    fun <T : Any> viewModelFlow(
        key: Any,
        cancellationSignal: (suspend () -> Unit)? = null,
        create: (scope: CoroutineScope) -> T,
    ): StateFlow<T> {
        // First check if we have a 'cached' ViewModel which is still active
        val cached = viewModelMap[key]?.takeIf { it.isActive }
        if (cached != null) {
            @Suppress("UNCHECKED_CAST")
            return cached.flow as StateFlow<T>
        }

        // If not we'll create a new ViewModel
        val viewModelScope = createChildCoroutineScope()

        val flow = flow<T> {
            Napier.d("Creating CoroutineScope with key: $key")

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
            Napier.d("Cancelling CoroutineScope with key: $key")
            viewModelScope.cancel()
            // Remove the entry from the map
            val viewModel = viewModelMap.remove(key)?.flow?.value ?: return@onCompletion
            Napier.d("${viewModel::class.simpleName} removed")
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

private data class ViewModelStoreEntry(
    val coroutineScope: CoroutineScope,
    val flow: StateFlow<Any>,
) {
    val isActive: Boolean get() = coroutineScope.isActive
}