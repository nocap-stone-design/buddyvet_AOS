package com.nocapstone.home.data

import com.nocapstone.home.domain.ResultSearchKeyword
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KaKaoService {
    @GET("v2/local/search/keyword.json") // Keyword.json의 정보를 받아옴
    fun getSearchKeyword(
        @Header("Authorization") key: String, // 카카오 API 인증키 [필수]
        @Query("query") query: String, // 검색을 원하는 질의어 [필수]
        @Query("x") x : String, //(longitude) 값
        @Query("y") y : String, //(latitude) 값
        @Query("radius") radius : String //중심 좌표부터의 반경거리 단위 미터
    ) : Call<ResultSearchKeyword>
}