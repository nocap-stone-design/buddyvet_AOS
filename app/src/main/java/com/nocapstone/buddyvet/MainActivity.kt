package com.nocapstone.buddyvet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.nocapstone.buddyvet.databinding.ActivityMainBinding
import com.nocapstone.common_ui.MainActivityUtil

class MainActivity : AppCompatActivity(), MainActivityUtil {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initAppBar()
        initBottomNav()
    }

    private fun initBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_containerView) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home, R.id.diary, R.id.community, R.id.setting)
        )

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        binding.bottomNavigationView.run {
            setupWithNavController(navController)
            itemIconTintList = null
        }

    }

    private fun initAppBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.run {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun showAppBar() {
        binding.bottomAppBar.visibility = View.VISIBLE
        binding.fab.visibility = View.VISIBLE
        binding.topAppBar.visibility = View.VISIBLE
    }

    override fun setToolbarTitle(newTitle: String) {
        binding.toolbar.title = newTitle
    }

    override fun navigateToHome(start: Fragment) {
        start.findNavController().navigate(R.id.home)
        showAppBar()
    }

    override fun setVisibilityBottomAppbar(visibilityMode: Int) {
        binding.bottomAppBar.visibility = visibilityMode
        binding.fab.visibility = visibilityMode
    }

    override fun setVisibilityTopAppBar(visibilityMode: Int) {
        binding.topAppBar.visibility = visibilityMode
    }
}