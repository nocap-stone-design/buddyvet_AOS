package com.nocapstone.community.ui

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocapstone.common.domain.usecase.DataStoreUseCase
import com.nocapstone.community.domain.CommunityUseCase
import com.nocapstone.community.domain.CreatePostRequest
import com.nocapstone.community.dto.Content
import com.nocapstone.community.dto.Post
import com.nocapstone.community.dto.PostDetailData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


@HiltViewModel
class CommunityViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val communityUseCase: CommunityUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _detailData = MutableStateFlow<PostDetailData?>(null)
    val detailData: StateFlow<PostDetailData?> = _detailData

    private val _imageUriList = MutableStateFlow<MutableList<Uri>>(mutableListOf())
    val imageUriList: StateFlow<List<Uri>> = _imageUriList

    private val _postList = MutableStateFlow<MutableList<Post>>(mutableListOf())
    val postList: StateFlow<List<Post>> = _postList

    fun readPostList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                communityUseCase.readPostList(dataStoreUseCase.bearerJsonWebToken.first()!!).let {
                    _postList.value = it.toMutableList()
                }
                val tmp = mutableListOf<Post>()
            } catch (e: Exception) {

            }
        }
    }

    fun createPost(createDiaryRequest: CreatePostRequest, callBack: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = dataStoreUseCase.bearerJsonWebToken.first()!!
                communityUseCase.createPost(token, createDiaryRequest).let { postId ->
                    if (_imageUriList.value.size > 0) {
                        val images = _imageUriList.value.map { imageUri ->
                            Log.d("postUri", imageUri.toString())

                            // ContentResolver로부터 데이터를 읽기 위해 inputStream을 열어줍니다.
                            val inputStream = context.contentResolver.openInputStream(imageUri)

                            // 읽은 데이터를 byteArray로 변환하고 RequestBody로 변환합니다.
                            val byteArray = inputStream!!.readBytes()
                            val requestBody =
                                byteArray.toRequestBody("multipart/form-data".toMediaTypeOrNull())

                            // MultipartBody.Part를 생성하여 반환합니다.
                            MultipartBody.Part.createFormData(
                                "image",
                                getFileName(context, imageUri),
                                requestBody
                            )
                        }
                        communityUseCase.createPostImage(token, postId, images)
                        _imageUriList.value.clear()
                    }
                }
                callBack.invoke()
            } catch (e: Exception) {
                Log.d("postTest", e.message.toString())
            }
        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            it.moveToFirst()
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            Log.d("postUri", it.getString(nameIndex))
            return it.getString(nameIndex)
        }
    }

    fun readDetailPost(postId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _detailData.value = communityUseCase.readPostDetail(
                    dataStoreUseCase.bearerJsonWebToken.first()!!,
                    postId
                )
            } catch (e: Exception) {
                Log.d("postTest", e.message.toString())
            }
        }
    }

    fun createReply(postId: Long, content: Content) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = dataStoreUseCase.bearerJsonWebToken.first()
            if (token != null) {
                try {
                    communityUseCase.createReply(token, postId, content)
                } catch (e: Exception) {

                }
            }
        }
    }

    fun deleteReply(postId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = dataStoreUseCase.bearerJsonWebToken.first()
            if (token != null) {
                try {
                    communityUseCase.deleteReply(token, postId)
                } catch (e: Exception) {

                }
            }
        }
    }


    fun setImage(newUriList: List<Uri>) {
        _imageUriList.value = newUriList.toMutableList()
    }

}