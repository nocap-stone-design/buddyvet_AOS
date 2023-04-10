package com.nocapstone.buddyvet.buddy.domain.entity

data class BuddyRequest(
    val kind : String,
    var name : String?,
    var birthday : String?,
    var adoptDay : String?,
    var isNeutered : Boolean?,
    var gender : String?
)