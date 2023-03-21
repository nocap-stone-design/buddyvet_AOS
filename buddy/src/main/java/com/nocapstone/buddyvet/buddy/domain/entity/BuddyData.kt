package com.nocapstone.buddyvet.buddy.domain.entity

data class BuddyListResponse(
    val buddies: List<BuddyData>?
)

data class BuddyData(
    val id: Int,
    val kind: String,
    val name: String,
    val gender: String,
    val profile: String,
    val age: String
)

data class BuddyId(
    val buddyId: Long
)

data class BuddyDetailResponse(
    val buddy: BuddyDetailData
)

data class BuddyDetailData(
    val kind: String,
    val name: String,
    val profile: String,
    val birthDay: String,
    val adoptDay: String,
    val isNeutered: Boolean,
    val gender: String
)
