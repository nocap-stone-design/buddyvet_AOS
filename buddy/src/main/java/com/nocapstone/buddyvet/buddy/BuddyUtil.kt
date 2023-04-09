package com.nocapstone.buddyvet.buddy

object BuddyDtoToLocal {
    fun transformKind(kind: String): String = if (kind == "D") "강아지" else "고양이"
    fun transformGender(gender: String): String = if (gender == "M") "남자" else "여자"
    fun transformNeutered(neutered : Boolean) : String = if (neutered) "예" else "아니요"
}

object BuddyLocalToDto {
    fun transformKind(kind: String?): String = if (kind == "강아지") "D" else "C"
    fun transformGender(gender: String?): String = if (gender == "남자") "M" else "F"
    fun transformNeutered(neutered : String?) : Boolean = neutered == "예"
}
