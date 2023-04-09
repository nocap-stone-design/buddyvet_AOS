package com.nocapstone.buddyvet.buddy.domain.entity

data class BuddyRequest(
    val kind : String,
    var name : String = "DOWOO",
    var birthday : String = "2023-04-08",
    var adoptDay : String = "2023-04-08",
    var isNeutered : Boolean = false,
    var gender : String = "M"
)