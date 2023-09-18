package com.nocapstone.buddyvet.buddy.domain.usecase

import com.nocapstone.buddyvet.buddy.data.BuddyService
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyData
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyDetailResponse
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyRequest
import com.nocapstone.buddyvet.buddy.domain.entity.MasterInfoResponse
import com.nocapstone.common.data.dto.CommonResponse
import okhttp3.MultipartBody
import javax.inject.Inject

class BuddyUseCase @Inject constructor(
    private val buddyService: BuddyService
) {

    suspend fun readBuddyList(token: String): List<BuddyData>? {
        return buddyService.readBuddyList(token).data.buddies
    }

    suspend fun createBuddy(token: String, buddyRequest: BuddyRequest): Long {
        return buddyService.createBuddy(token, buddyRequest).data.buddyId
    }

    suspend fun readBuddyDetail(token: String, buddyId: Long): BuddyDetailResponse {
        return buddyService.readBuddyDetail(token, buddyId).data
    }

    suspend fun deleteBuddy(token: String, buddyId: Long) {
        buddyService.deleteBuddy(token, buddyId)
    }

    suspend fun putBuddy(token: String,buddyId: Long,buddyRequest: BuddyRequest){
        buddyService.putBuddy(token, buddyRequest, buddyId)
    }
    suspend fun uploadBuddyImg(token: String, buddyId: Long, image: MultipartBody.Part) {
        buddyService.uploadBuddyImg(token, buddyId, image)
    }

    suspend fun readMasterInfo(token: String): MasterInfoResponse {
       return buddyService.readMasterInfo(token).data
    }

}