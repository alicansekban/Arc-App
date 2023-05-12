package com.alican.mvvm_starter.ui

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseActivity
import com.alican.mvvm_starter.data.local.model.FlashLightsEntity
import com.alican.mvvm_starter.databinding.ActivityMainBinding
import com.alican.mvvm_starter.util.utils.handleError
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(),
    NavController.OnDestinationChangedListener {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
        initObserver()
    }

    private fun initObserver() {
        viewModel.flashLightsResponse.observe(this) { response ->
            when (response.status) {
                STATUS_LOADING -> {
                    showProgressDialog()
                }
                STATUS_ERROR -> {
                    hideProgressDialog()
                    response.handleError(this)
                }
                STATUS_SUCCESS -> {
                    hideProgressDialog()
                    val result = response.responseObject
                    result?.let {
                        if (it.body()?.isNotEmpty() == true) {

                            val list = mutableListOf<FlashLightsEntity>()
                            it.body()?.forEach {
                                list.add(FlashLightsEntity(ratingCount = it.ratingCount!!, downloads = it.downloads.toString(), ratingValue = it.ratingValue!!,name = it.name.toString(), packageName = it.packageName.toString(), iconUrl = it.iconUrl.toString()))
                            }

                            viewModel.insertFlashLightsDataToDb(
                                list
                            )
                        }
                    }
                }
            }
        }
        viewModel.colorLightsResponse.observe(this) { response ->
            when (response.status) {
                STATUS_LOADING -> {
                    showProgressDialog()
                }
                STATUS_ERROR -> {
                    hideProgressDialog()
                    response.handleError(this)
                }
                STATUS_SUCCESS -> {
                    hideProgressDialog()
                    val result = response.responseObject
                    result?.let {
                        if (it.body()?.isNotEmpty() == true) {
                        }

                    }
                }
            }
        }
        viewModel.sosAlertsResponse.observe(this) { response ->
            when (response.status) {
                STATUS_LOADING -> {
                    showProgressDialog()
                }
                STATUS_ERROR -> {
                    hideProgressDialog()
                    response.handleError(this)
                }
                STATUS_SUCCESS -> {
                    hideProgressDialog()
                    val result = response.responseObject
                    result?.let {
                        if (it.body()?.isNotEmpty() == true) {
                        }
                    }
                }
            }
        }

    }

    private fun initNavigation() {


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navMainFragment) as NavHostFragment
        navController = navHostFragment.navController
        // Setup the bottom navigation view with navController

        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.itemIconTintList = null

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                //    R.id.searchFragment,
                //  R.id.shoppingCartFragment,
                //R.id.favoritesFragment,
                // R.id.profileFragment,
            )
        )
        navHostFragment.navController.addOnDestinationChangedListener(this)
        //binding.toolbar.materialToolbar.setNavigationOnClickListener { navController.popBackStack() }
        val menu = binding.bottomNavigation.menu

        binding.bottomNavigation.setOnItemReselectedListener {
            when (it.title) {
                "Anasayfa" -> {
                    navHostFragment.findNavController().navigate(R.id.homeFragment)
                }
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener,
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.title) {

                }
                return true
            }
        })
    }


    override fun getLayoutId(): Int = R.layout.activity_main
    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

        binding.bottomNavigation.menu.forEach {
            it.isEnabled = false
        }
        Handler().postDelayed({
            binding.bottomNavigation.menu.forEach {
                it.isEnabled = true
            }
        }, 500)
    }
}
