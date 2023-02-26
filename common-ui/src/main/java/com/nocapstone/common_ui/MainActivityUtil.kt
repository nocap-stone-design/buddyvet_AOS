package com.nocapstone.common_ui

import androidx.fragment.app.Fragment

interface MainActivityUtil{
    fun setToolbarTitle(newTitle: String)
    fun setVisibilityBottomAppbar(visibilityMode : Int)
    fun setVisibilityTopAppBar(visibilityMode: Int)
}