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

class MainActivity : AppCompatActivity(), MainActivityUtil {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initAppBar()
        initBottomNav()


        printAppKeyHash()


        setStartDestination(isLogin())
    }

    /**
     * login 여부에 따른 startDestination 조작을 위해
     * main graph를 가져와서 startDestination을 조작 후 변경내용을 적용한다.
     * */

    private fun setStartDestination(isLogin: Boolean) {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_main)
        if (isLogin) navGraph.setStartDestination(R.id.home)
        else navGraph.setStartDestination(com.nocapstone.onboarding.R.id.nav_onboarding)
        navController.setGraph(navGraph, null)
    }

    // todo 로그인 체크 로직
    private fun isLogin(): Boolean {
        return false
    }

    private fun initBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_containerView) as NavHostFragment
        navController = navHostFragment.navController

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


    /** for kakao Developers hash */
    private fun printAppKeyHash() {
        var keyHash = Utility.getKeyHash(this)
        Log.d("KeyHash", keyHash)
    }

    /** [MainActivityUtil] */

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