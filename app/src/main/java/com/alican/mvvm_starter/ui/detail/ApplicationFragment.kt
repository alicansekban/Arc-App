package com.alican.mvvm_starter.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.data.model.ResponseModel
import com.alican.mvvm_starter.databinding.FragmentApplicationBinding
import com.alican.mvvm_starter.ui.MainActivity
import com.alican.mvvm_starter.ui.detail.adapter.DataAdapter
import com.alican.mvvm_starter.util.Constant
import com.alican.mvvm_starter.util.utils.openAppOrPlayStore
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplicationFragment : BaseFragment<FragmentApplicationBinding>() {
    private val args by navArgs<ApplicationFragmentArgs>()
    private val viewModel: ApplicationViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.fragment_application

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
        initObserver()
        initViews()
        initSearch()
        hideToolbar()
    }

    private fun hideToolbar() {
        val activity = requireActivity() as MainActivity
        activity.supportActionBar?.hide()
    }

    private fun initViews() {
        binding.rvList.adapter = DataAdapter {
            it.packageName.let { it1 -> requireContext().openAppOrPlayStore(it1.toString()) }
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            binding.edtSearch.text?.clear()
            binding.edtSearch.clearFocus()
            fetchData()
        }
    }


    private fun initObserver() {
        viewModel.dataResponse.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                STATUS_LOADING -> {
                    showProgressDialog()
                }
                STATUS_ERROR -> {
                    hideProgressDialog()
                }
                STATUS_SUCCESS -> {
                    hideProgressDialog()
                    val result = response.responseObject
                    result?.let {
                        if (it.body()?.isNotEmpty() == true) {
                            initAdapter(it.body()!!)
                        }
                    }
                }
            }
        }
    }

    private fun initSearch() {
        binding.edtSearch.addTextChangedListener {
            (binding.rvList.adapter as DataAdapter).filter.filter(it.toString())
        }
    }

    private fun fetchData() {
        when (args.type) {
            Constant.COLORLIGHTS -> {
                viewModel.getColorLights()
            }
            Constant.FLASHLIGHTS -> {
                viewModel.getFlashLights()
            }
            Constant.SOSALERTS -> {
                viewModel.getSosAlerts()
            }
        }
    }

    private fun initAdapter(items: List<ResponseModel>) {
        (binding.rvList.adapter as? DataAdapter)?.submitList(items.map { it.copy() }.sortedByDescending { it.ratingValue }.toMutableList())
    }
}