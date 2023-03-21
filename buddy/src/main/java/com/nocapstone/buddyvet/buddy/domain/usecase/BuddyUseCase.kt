package com.nocapstone.buddyvet.buddy.domain.usecase

import com.nocapstone.buddyvet.buddy.data.BuddyService
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyData
import com.nocapstone.buddyvet.buddy.domain.entity.BuddyRequest
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

    suspend fun readBuddyDetail(token: String, buddyId: Long) {
        buddyService.readBuddyDetail(token, buddyId)
    }

    suspend fun deleteBuddy(token: String, buddyId: Long) {
        buddyService.deleteBuddy(token, buddyId)
    }

    suspend fun uploadBuddyImg(token: String, buddyId: Long, image: MultipartBody.Part) {
        buddyService.uploadBuddyImg(token, buddyId, image)
    }

}