package com.nocapstone.common.data.source

import com.nocapstone.common.data.dto.WeatherRequest
import com.nocapstone.common.data.dto.WeatherResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface WeatherService {

    /** todo addDto */
    @POST("api/v1/weather")
    suspend fun readWeather(@Body weatherRequest: WeatherRequest): WeatherResponse

}