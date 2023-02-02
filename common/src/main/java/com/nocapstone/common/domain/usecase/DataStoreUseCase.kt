package com.nocapstone.common.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


//datastore di
@Singleton
class DataStoreUseCase @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    // datastore 키값을 통해 값 읽기
    // map을 통해 datastore에서 flow<T> 형태로 값 받기


    val bearerJsonWebToken: Flow<String?> =
        dataStore.data.map { preferences -> preferences[jsonWebTokenKey]?.toBearerToken() }


    // Authorization 헤더 에 자체 JWT를 포함해서 패킷을 보내면 된다.
    // todo Bearer 붙이는 이유, 이렇게하는거 맞는지 내 local 일단 들고있어야하는거 맞는지
    private fun String.toBearerToken() = "Bearer $this"


    suspend fun editJsonWebToken(jwt : String){
        dataStore.edit {
            it[jsonWebTokenKey] = jwt
        }
    }


    // DataStore 이름 및 키 정의
    companion object {
        const val DATA_STORE_NAME = "app"

        private val accessTokenKey = stringPreferencesKey("access_token")
        private val jsonWebTokenKey = stringPreferencesKey("json_web_token")

    }

}