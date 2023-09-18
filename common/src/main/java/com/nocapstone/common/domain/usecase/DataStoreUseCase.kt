package com.nocapstone.common.domain.usecase

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
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
        dataStore.data.map { preferences -> preferences[jsonWebToken_key]?.toBearerToken() }
    // Authorization 헤더 에 자체 JWT를 포함해서 패킷을 보내면 된다.
    private fun String.toBearerToken() : String = "Bearer $this"

    suspend fun editJsonWebToken(jwt : String){
        dataStore.edit {
            Log.d("buddyTest","jwt 저장 $jwt")
            it[jsonWebToken_key] = jwt

        }
    }

    // DataStore 이름 및 키 정의
    companion object {
        const val DATA_STORE_NAME = "app"
        private val jsonWebToken_key = stringPreferencesKey("JSON_WEB_TOKEN")
        private val isOnBoarding_key = booleanPreferencesKey("JSON_WEB_TOKEN")
    }

}