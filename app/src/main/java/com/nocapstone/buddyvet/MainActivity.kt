package com.nocapstone.buddyvet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.kakao.sdk.common.util.Utility
import com.nocapstone.buddyvet.databinding.ActivityMainBinding
import com.nocapstone.common_ui.MainActivityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityUtil {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initAppBar()
        initBottomNav()
        //printAppKeyHash()
    }

    private fun initBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_containerView) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                com.nocapstone.home.R.id.homeFragment,
                com.nocapstone.home.R.id.checkResultFragment,
                com.example.diary.R.id.diaryFragment
            )
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


    /** for kakao Developers hash */
    private fun printAppKeyHash() {
        var keyHash = Utility.getKeyHash(this)
        Log.d("KeyHash", keyHash)
    }

    /** [MainActivityUtil] */

    override fun setToolbarTitle(newTitle: String) {
        binding.toolbar.title = newTitle
    }


    override fun setVisibilityBottomAppbar(visibilityMode: Int) {
        binding.bottomAppBar.visibility = visibilityMode
        binding.fab.visibility = visibilityMode
    }

    override fun setVisibilityTopAppBar(visibilityMode: Int) {
        binding.topAppBar.visibility = visibilityMode
    }
}