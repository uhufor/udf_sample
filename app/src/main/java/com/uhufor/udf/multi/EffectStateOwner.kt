package com.uhufor.udf.multi

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

interface EffectStateOwner<E, S> {
    val effect: SharedFlow<E>
    val state: StateFlow<S>

    suspend fun setEffect(effect: E)
    suspend fun setState(state: S)
}

class DefaultEffectStateOwner<E, S>(initialState: S) : EffectStateOwner<E, S> {
    private val _effect: MutableSharedFlow<E> = MutableSharedFlow()
    override val effect: SharedFlow<E>
        get() = _effect.asSharedFlow()
    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    override val state: StateFlow<S>
        get() = _state.asStateFlow()

    override suspend fun setEffect(effect: E) {
        _effect.emit(effect)
    }

    override suspend fun setState(state: S) {
        _state.emit(state)
    }
}
