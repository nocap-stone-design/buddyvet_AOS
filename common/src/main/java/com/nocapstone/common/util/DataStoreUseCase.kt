package com.nocapstone.common.util

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import java.util.prefs.Preferences

@Singletone
class DataStoreUseCase(private val dataStore: DataStore<Preferences>) {

    val refreshToken: Flow<String?> =
        dataStore.data.map { preferences -> preferences[refreshTokenKey] }


}