package com.nocapstone.buddyvet.buddy.domain.entity

data class BuddyRequest(
    val kind : String,
    var name : String = "name",
    var birthDay : String = "birthday",
    var adoptDay : String = "adoptDay",
    var isNeutered : Boolean = false,
    var gender : String = "gender"
)