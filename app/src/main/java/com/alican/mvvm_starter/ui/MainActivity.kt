package com.alican.mvvm_starter.ui

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseActivity
import com.alican.mvvm_starter.databinding.ActivityMainBinding
import com.alican.mvvm_starter.util.Constant
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(),
    NavController.OnDestinationChangedListener {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {

        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navMainFragment) as NavHostFragment
        navController = navHostFragment.navController
        // Setup the bottom navigation view with navController

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener {
            val bundle = Bundle().apply {
                when (it.itemId) {
                    R.id.nav_flash -> {
                        putString("type", Constant.FLASHLIGHTS)
                    }
                    R.id.nav_colors -> {
                        putString("type", Constant.COLORLIGHTS)
                    }
                    R.id.nav_sos -> {
                        putString("type", Constant.SOSALERTS)
                    }
                }
            }


            navController.navigate(
                R.id.applicationFragment,
                bundle
            )

            binding.drawerLayout.closeDrawer(GravityCompat.START)

            return@setNavigationItemSelectedListener true
        }

        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.itemIconTintList = null

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment
            )
        )
        navHostFragment.navController.addOnDestinationChangedListener(this)

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
