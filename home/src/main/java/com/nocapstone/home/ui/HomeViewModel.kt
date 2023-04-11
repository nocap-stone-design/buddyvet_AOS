package com.nocapstone.home.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import com.nocapstone.home.R
import com.nocapstone.home.domain.HomeUseCase
import com.nocapstone.home.domain.Place
import com.nocapstone.home.domain.ResultSearchKeyword
import com.nocapstone.home.domain.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _nowWeatherInfo = MutableStateFlow<WeatherInfo?>(null)
    val nowWeatherInfo: StateFlow<WeatherInfo?> = _nowWeatherInfo

    private val _placeInfoList = MutableStateFlow<List<Place>?>(listOf())
    val placeInfoList: StateFlow<List<Place>?> = _placeInfoList

    private val _toastMessage = MutableStateFlow("")
    val toastMessage: StateFlow<String> = _toastMessage


    fun getWeatherInfo() {
        _nowWeatherInfo.value = WeatherInfo(
            "창원시", "18º", "20º", "14º", "산책은 힘들다",
            "74점", com.nocapstone.common_ui.R.color.black
        )
    }

    fun searchKeyword(keyword: String, longitude: String, latitude: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val key = "KakaoAK f7c8633cfa431790846824ecf40ac7f8"
            homeUseCase.readSearchResult(key, keyword, longitude, latitude)
                .enqueue(object : Callback<ResultSearchKeyword> {
                    override fun onResponse(
                        call: Call<ResultSearchKeyword>,
                        response: Response<ResultSearchKeyword>
                    ) {
                        _placeInfoList.value = response.body()?.documents
                        Log.d("test1234", "${response.body()?.documents}")
                    }

                    override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                        Log.w("MainActivity", "통신 실패: ${t.message}")
                    }
                })
        }


//        .enqueue(object: Callback<ResultSearchKeyword> {
//            override fun onResponse(
//                call: Call<ResultSearchKeyword>,
//                response: Response<ResultSearchKeyword>
//            ) {
//                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
//                Log.d("Test", "Raw: ${response.raw()}")
//                Log.d("Test", "Body: ${response.body()}")
//            }
//
//            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
//                // 통신 실패
//                Log.w("MainActivity", "통신 실패: ${t.message}")
//            }
//        })

    }

    private fun setToastMessage(newMessage: String) {
        _toastMessage.value = ""
        _toastMessage.value = newMessage
    }

}