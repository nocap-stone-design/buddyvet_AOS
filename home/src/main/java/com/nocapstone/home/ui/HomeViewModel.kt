package com.nocapstone.home.ui

import androidx.lifecycle.ViewModel
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import com.nocapstone.home.R
import com.nocapstone.home.domain.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    //private val homeUseCase: HomeUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _nowWeatherInfo = MutableStateFlow<WeatherInfo?>(null)
    val nowWeatherInfo: StateFlow<WeatherInfo?> = _nowWeatherInfo

    private val _toastMessage = MutableStateFlow("")
    val toastMessage: StateFlow<String> = _toastMessage


    fun getWeatherInfo() {
        _nowWeatherInfo.value = WeatherInfo(
            "창원시", "18º", "20º", "14º", "산책은 힘들다",
            "74점", com.nocapstone.common_ui.R.color.black
        )
    }


    private fun setToastMessage(newMessage: String) {
        _toastMessage.value = ""
        _toastMessage.value = newMessage
    }

}