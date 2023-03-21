package com.nocapstone.buddyvet.buddy.ui

import androidx.lifecycle.ViewModel
import com.nocapstone.buddyvet.buddy.util.BuddyType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BuddyViewModel : ViewModel() {
    private val _selectType = MutableStateFlow(BuddyType.NULL)
    val selectType: StateFlow<BuddyType> = _selectType

    /*
    private val _newBuddy = MutableStateFlow<BuddyRequest?>(null)
    val newBuddy : StateFlow<BuddyRequest?> = _newBuddy
    */
    
    fun setSelectBuddyType(buddyType : BuddyType){
        if (_selectType.value == buddyType){
            _selectType.value = BuddyType.NULL
        }else{
            //_newBuddy.value = BuddyRequest(buddyType.kind)
            _selectType.value = buddyType
        }
    }
}