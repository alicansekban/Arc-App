package com.alican.mvvm_starter.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.data.local.model.FlashLightsEntity
import com.alican.mvvm_starter.data.model.ResponseModel
import com.alican.mvvm_starter.databinding.FragmentApplicationBinding
import com.alican.mvvm_starter.ui.detail.adapter.ItemsAdapter
import com.alican.mvvm_starter.util.utils.handleError
import com.alican.mvvm_starter.util.utils.openGooglePlayStore
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApplicationFragment : BaseFragment<FragmentApplicationBinding>() {
    private val args by navArgs<ApplicationFragmentArgs>()
    private val viewModel: ApplicationViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.fragment_application

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //fetchData()
        initObserver()
        initViews()
        initSearch()
        fetchFlashLightsData()
    }

    private fun fetchFlashLightsData(searchQuery:String ="") {
        lifecycleScope.launch {
            viewModel.repository.fetchFlashLightsFromDb(searchQuery).collect{
                initAdapter(it)
            }
        }
    }

    private fun initViews() {
        binding.rvList.adapter = ItemsAdapter {
            it.packageName.let { it1 -> requireContext().openGooglePlayStore(it1) }
        }
     //   viewModel.getFlashLightsFromDb()
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            binding.edtSearch.text?.clear()
            binding.edtSearch.clearFocus()
            fetchData()
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
                      //      initAdapter(it.body()!!)
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
                          //  initAdapter(it.body()!!)
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
                      //      initAdapter(it.body()!!)
                        }
                    }
                }
            }
        }

    }

    private fun initSearch() {
        binding.edtSearch.addTextChangedListener {
            fetchFlashLightsData(it.toString())
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

    private fun initAdapter(items: List<FlashLightsEntity>) {
        (binding.rvList.adapter as? ItemsAdapter)?.modifyList(items.map { it.copy() }
            .sortedBy { it.ratingValue.toInt() })

    }
}