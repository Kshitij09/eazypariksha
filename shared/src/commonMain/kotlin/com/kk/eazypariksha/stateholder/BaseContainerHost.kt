package com.kk.eazypariksha.stateholder

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

val StateHolderCancelTimeout: Long = 5_000L

abstract class BaseContainerHost<S : Any, E : Any>(coroutineScope: CoroutineScope) :
    ContainerHost<S, E> {

    fun stateFlow(): StateFlow<S> = container.stateFlow

    fun sideFlow(): Flow<E> = container.sideEffectFlow

    protected abstract val initialState: S

    protected open fun onCreate(initialState: S) {
        Napier.d("${this::class.simpleName} created")
    }

    override val container: Container<S, E> by lazy {
        coroutineScope.container<S, E>(
            initialState = initialState,
            settings = Container.Settings(repeatOnSubscribedStopTimeout = StateHolderCancelTimeout),
            onCreate = ::onCreate
        )
    }

}