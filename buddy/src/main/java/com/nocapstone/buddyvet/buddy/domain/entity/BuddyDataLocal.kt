package com.nocapstone.buddyvet.buddy.domain.entity

import com.nocapstone.buddyvet.buddy.BuddyLocalToDto

// BuddyDetailDataLocal를 BuddyRequest로 변환
// BuddyResponse를 BuddyDataLocal로 변환

data class BuddyDetailDataLocal(
    val kind: String,
    var name: String? = null,
    var gender: String? = null,
    var profile: String? = null,
    var neutered: String? = null,
    var adoptDay: String? = null,
    var birthday: String? = null
) {
    fun replaceForDto() = BuddyRequest(
        BuddyLocalToDto.transformKind(kind),
        name,
        birthday,
        adoptDay,
        BuddyLocalToDto.transformNeutered(neutered),
        BuddyLocalToDto.transformGender(gender)
    )
}


data class BuddyDataLocal(
    val id: Long,
    val kind: String,
    val name: String,
    val gender: String,
    val profile: String,
    val age: String,
    var isSelect: Boolean = false
)



