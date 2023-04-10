package com.nocapstone.buddyvet

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        if(currentFocus is EditText) {
            currentFocus!!.clearFocus()
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun initBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_containerView) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                com.nocapstone.home.R.id.homeFragment,
                com.example.eye_check.R.id.checkResultFragment,
                com.nocapstone.community.R.id.communityFragment,
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
        binding.toolbar
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