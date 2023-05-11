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
import com.alican.mvvm_starter.ui.detail.adapter.ItemsAdapter
import com.alican.mvvm_starter.util.utils.handleError
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
    }

    private fun initViews() {
        binding.rvList.adapter = ItemsAdapter {
        }
    }


    private fun initObserver() {
        viewModel.flashLightsResponse.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                STATUS_LOADING -> {
                    showProgressDialog()
                }
                STATUS_ERROR -> {
                    hideProgressDialog()
                    response.handleError(requireActivity())
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
        viewModel.colorLightsResponse.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                STATUS_LOADING -> {
                    showProgressDialog()
                }
                STATUS_ERROR -> {
                    hideProgressDialog()
                    response.handleError(requireActivity())
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
        viewModel.sosAlertsResponse.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                STATUS_LOADING -> {
                    showProgressDialog()
                }
                STATUS_ERROR -> {
                    hideProgressDialog()
                    response.handleError(requireActivity())
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
            (binding.rvList.adapter as ItemsAdapter).filter(it.toString())

        }
    }

    private fun fetchData() {
        when (args.type) {
            "colors" -> {
                viewModel.getColorLights()
            }
            "flashes" -> {
                viewModel.getFlashLights()
            }
            "sosAlerts" -> {
                viewModel.getSosAlerts()
            }
        }
    }

    private fun initAdapter(items: List<ResponseModel>) {
        (binding.rvList.adapter as? ItemsAdapter)?.modifyList(items.map { it.copy() }
            .sortedBy { it.ratingValue?.toInt() })

    }
}