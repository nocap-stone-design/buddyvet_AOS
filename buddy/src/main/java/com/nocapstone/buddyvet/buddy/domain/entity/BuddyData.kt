package com.nocapstone.buddyvet.buddy.domain.entity

import com.nocapstone.buddyvet.buddy.BuddyDtoToLocal

data class BuddyListResponse(
    val buddies: List<BuddyData>?
)

data class BuddyData(
    val id: Long,
    val kind: String,
    val name: String,
    val gender: String,
    val profile: String,
    val age: String,
) {
    fun replaceForLocal() = BuddyDataLocal(
        id,
        BuddyDtoToLocal.transformKind(kind),
        name,
        BuddyDtoToLocal.transformGender(gender),
        profile,
        age
    )
}

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
    val birthday: String,
    val adoptDay: String,
    val isNeutered: Boolean,
    val gender: String
) {
    fun replaceForLocal() = BuddyDetailDataLocal(
        BuddyDtoToLocal.transformKind(kind),
        name,
        BuddyDtoToLocal.transformGender(gender),
        profile,
        BuddyDtoToLocal.transformNeutered(isNeutered),
        adoptDay,
        birthday
    )
}
