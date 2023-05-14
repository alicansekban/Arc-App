package com.alican.mvvm_starter.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.databinding.FragmentHomeBinding
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.ui.MainActivity
import com.alican.mvvm_starter.util.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        showToolbar()
    }

    private fun showToolbar() {
        val activity = requireActivity() as MainActivity
        activity.supportActionBar?.show()
    }

    private fun setListener() {
        binding.colorLightsButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToApplicationFragment(
                    Constant.COLORLIGHTS
                )
            )
        }
        binding.flashLightsButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToApplicationFragment(
                    Constant.FLASHLIGHTS
                )
            )

        }
        binding.sosAlerts.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToApplicationFragment(
                    Constant.SOSALERTS
                )
            )

        }
    }


}
