package com.nocapstone.onboarding.domain

data class BuddyRequest(
    val kind : String,
    var name : String = "name",
    var bread : String = "bread",
    var birthDay : String = "birthday",
    var adoptDay : String = "adoptDay",
    var isNeutered : Boolean = false,
    var gender : String = "gender"
)