package com.nocapstone.common.util

import android.util.Log


fun printLog(text: String, e: Exception) {
    Log.d("sendApi", text+" :"+ e.message.toString())
}