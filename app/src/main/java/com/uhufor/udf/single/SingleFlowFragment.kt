@file:Suppress("MemberVisibilityCanBePrivate")

package com.uhufor.udf.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class SingleFlowFragment<VM : SingleFlowViewModel<UiEvent, UiEffect, UiState>, VB : ViewBinding, UiEvent, UiEffect, UiState>(
) : Fragment() {
    abstract val viewModel: VM

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    protected fun UiEvent.emit() {
        viewModel.submit(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeEffect()
        observeState()
    }

    private fun observeEffect() {
        launch {
            viewModel.effect
                .flowWithLifecycle(lifecycle = lifecycle, minActiveState = Lifecycle.State.STARTED)
                .collect(::handleEffect)
        }
    }

    abstract fun handleEffect(effect: UiEffect)

    private fun observeState() {
        launch {
            viewModel.state
                .flowWithLifecycle(lifecycle = lifecycle, minActiveState = Lifecycle.State.STARTED)
                .collect(::handleState)
        }
    }

    abstract fun handleState(state: UiState)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflateViewBinding(inflater, container, savedInstanceState)
        ?.also { binding -> _binding = binding }
        ?.root

    abstract fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): VB?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewBinding(binding)
    }

    open fun initViewBinding(viewBinding: VB) {}

    override fun onDestroyView() {
        releaseViewBinding(binding)
        _binding = null
        super.onDestroyView()
    }

    open fun releaseViewBinding(viewBinding: VB) {}

    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch(block = block)
    }
}
