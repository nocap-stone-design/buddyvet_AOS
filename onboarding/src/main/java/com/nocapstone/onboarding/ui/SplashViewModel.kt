package com.nocapstone.onboarding.ui

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocapstone.common.domain.entity.UserNameRequest
import com.nocapstone.common.domain.usecase.AuthUseCase
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authUseCase: AuthUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _selectImgUri = MutableStateFlow<Uri?>(null)
    val selectImgUri: StateFlow<Uri?> = _selectImgUri

    fun withJsonWebToken(callback: (String?) -> Unit) {
        viewModelScope.launch {
            callback.invoke(dataStoreUseCase.bearerJsonWebToken.firstOrNull())
        }
    }

    fun signup(token: String, loginCallback: () -> Unit, createCallback: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = authUseCase.login(token)
                dataStoreUseCase.editJsonWebToken(response.body()?.data?.jwt!!)
                Log.d("responsecode", response.code().toString())
                if (response.code() == 200) {
                    loginCallback.invoke()
                } else if (response.code() == 201) {
                } else {
                    Log.d("responsecode", response.code().toString())
                }
                createCallback.invoke()
            } catch (e: Exception) {
                Log.d("buddyTest", e.message.toString())
            }
        }
    }

    fun postUserInfo(nickname: String, callback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = dataStoreUseCase.bearerJsonWebToken.first()
            if (token != null) {
                try {
                    authUseCase.inputUserInfo(
                        dataStoreUseCase.bearerJsonWebToken.first()!!,
                        UserNameRequest(nickname)
                    )
                    uploadUserImg(token, _selectImgUri.value!!).await()
                    callback.invoke()
                } catch (e: Exception) {
                    Log.d("nickNameApi", e.message.toString())
                }
            }


        }
    }

    private fun uploadUserImg(token: String, imgUri: Uri): Deferred<Unit> {
        return viewModelScope.async(Dispatchers.IO) {
            val inputStream = context.contentResolver.openInputStream(imgUri)
            val byteArray = inputStream!!.readBytes()
            val requestBody = byteArray.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val image = MultipartBody.Part.createFormData(
                "image",
                getFileName(context, imgUri),
                requestBody
            )
            authUseCase.uploadUserImg(token, image)
        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            it.moveToFirst()
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            return it.getString(nameIndex)
        }
    }

    fun setSelectImgUri(uri: Uri?) {
        _selectImgUri.value = uri
    }
}


