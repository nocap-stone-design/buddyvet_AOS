package com.nocapstone.buddyvet.buddy.ui

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyData
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyRequest
import com.nocapstone.buddyvet.buddy.domain.usecase.BuddyUseCase
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class BuddyViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val buddyUseCase: BuddyUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _buddyList = MutableStateFlow<List<BuddyData>?>(null)
    val buddyList: StateFlow<List<BuddyData>?> = _buddyList

    private val _newBuddy = MutableStateFlow<BuddyRequest?>(null)
    val newBuddy: StateFlow<BuddyRequest?> = _newBuddy

    private val _selectImgUri = MutableStateFlow<Uri?>(null)
    val selectImgUri: StateFlow<Uri?> = _selectImgUri

    fun setKind(kind: String) {
        _newBuddy.value = BuddyRequest(kind)
    }

    fun setName(name: String) {
        _newBuddy.value?.name = name
    }

    fun setBirthDay(birthDay: String) {
        _newBuddy.value?.birthDay = birthDay
    }

    fun setAdoptDay(adoptDay: String) {
        _newBuddy.value?.adoptDay = adoptDay
    }

    fun setNeutered(Neutered: Boolean) {
        _newBuddy.value?.isNeutered = Neutered
    }

    fun setGender(gender: String) {
        if (gender == "남자") {
            _newBuddy.value?.gender = "M"
        } else {
            _newBuddy.value?.gender = "W"
        }
    }

    fun setSelectImgUri(uri: Uri?) {
        _selectImgUri.value = uri
    }

    fun readBuddyList() {
        viewModelScope.launch {
            val jwt = dataStoreUseCase.bearerJsonWebToken.first()!!
            _buddyList.value = buddyUseCase.readBuddyList(jwt)
        }
    }

    fun createBuddy() {
        viewModelScope.launch {
            try {
                val jwt = dataStoreUseCase.bearerJsonWebToken.first()!!
                val buddyId = buddyUseCase.createBuddy(jwt, _newBuddy.value!!)
                if (_selectImgUri.value != null) {
                    uploadBuddyImg(jwt, buddyId)
                }
            } catch (e: Exception) {
                Log.d("createBuddy", e.message.toString())
            }
        }
    }

    fun readBuddyDetail(buddyId: Long) {
        viewModelScope.launch {
            val jwt = dataStoreUseCase.bearerJsonWebToken.first()!!
            buddyUseCase.readBuddyDetail(jwt, buddyId)
        }
    }

    fun deleteBuddy(buddyId: Long) {
        viewModelScope.launch {
            val jwt = dataStoreUseCase.bearerJsonWebToken.first()!!
            buddyUseCase.deleteBuddy(jwt, buddyId)
        }
    }

    private fun uploadBuddyImg(token: String, buddyId: Long) {
        viewModelScope.launch {
            val imgUri = _selectImgUri.value!!
            val inputStream = context.contentResolver.openInputStream(imgUri)
            val byteArray = inputStream!!.readBytes()
            val requestBody = byteArray.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val image = MultipartBody.Part.createFormData(
                "image",
                getFileName(context, imgUri),
                requestBody
            )
            buddyUseCase.uploadBuddyImg(token, buddyId, image)
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


}