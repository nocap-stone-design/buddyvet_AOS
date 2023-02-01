package com.nocapstone.buddyvet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _success = MutableStateFlow(0)
    val success : StateFlow<Int> = _success

    private val _failure = MutableStateFlow(0)
    val failure: StateFlow<Int> = _failure

    fun addSuccess() {
        _success.value++
    }

    fun addFailure() {
        _failure.value++
    }

    fun withRefreshToken(callback: (String?) -> Unit) {
        viewModelScope.launch {
            callback.invoke()
        }
    }

}