package com.uhufor.udf.single.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.uhufor.udf.databinding.FragmentSingleFlowCounterBinding
import com.uhufor.udf.single.SingleFlowFragment

class SingleFlowCounterFragment : SingleFlowFragment<
        SingleFlowCounterViewModel,
        FragmentSingleFlowCounterBinding,
        SingleFlowCounterEvent,
        SingleFlowCounterEffect,
        SingleFlowCounterState
        >() {
    override val viewModel: SingleFlowCounterViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentSingleFlowCounterBinding =
        FragmentSingleFlowCounterBinding.inflate(inflater, container, false)

    override fun initViewBinding(viewBinding: FragmentSingleFlowCounterBinding) {
        viewBinding.btnIncrement.setOnClickListener {
            SingleFlowCounterEvent.OnClickIncrement.emit()
        }

        viewBinding.btnDecrement.setOnClickListener {
            SingleFlowCounterEvent.OnClickDecrement.emit()
        }
    }

    override fun handleEffect(effect: SingleFlowCounterEffect) {
        when (effect) {
            is SingleFlowCounterEffect.ShowError -> {
                Toast
                    .makeText(
                        requireContext(),
                        "Cannot decrement below 0",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }
    }

    override fun handleState(state: SingleFlowCounterState) {
        binding.tvCount.text = "${state.count}"
    }
}
