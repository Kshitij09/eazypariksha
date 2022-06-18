package com.kk.eazypariksha.android.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavBackStackEntry
import com.kk.eazypariksha.stateholder.StateHolderStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Retrieve or create a ViewModel.
 */
internal fun <T : Any> StateHolderStore.viewModelFlow(
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
internal inline fun <T : Any> StateHolderStore.viewModel(
    key: Any,
    noinline create: (scope: CoroutineScope) -> T,
): T = viewModelFlow(key, null, create).collectAsState().value

@Composable
internal inline fun <T : Any> StateHolderStore.viewModel(
    navBackStackEntry: NavBackStackEntry,
    noinline create: (scope: CoroutineScope) -> T,
): T = viewModelFlow(navBackStackEntry, create).collectAsState().value

