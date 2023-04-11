package com.nocapstone.home.domain

import com.nocapstone.home.data.KaKaoService
import retrofit2.Call
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val kaKaoService: KaKaoService
) {
    suspend fun readSearchResult(key: String, keyWord: String) : Call<ResultSearchKeyword> {
        return kaKaoService.getSearchKeyword(key, keyWord)
    }
}