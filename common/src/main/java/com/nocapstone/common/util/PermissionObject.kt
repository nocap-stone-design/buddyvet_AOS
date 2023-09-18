package com.nocapstone.common.util

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class PermissionObject {
    companion object {
        fun checkPermission(
            fragment: Fragment,
            onSuccessCallBack: () -> Unit,
            onFailCallBack: () -> Unit
        ): ActivityResultLauncher<Array<String>> {
            return fragment.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                if (permissions.all { it.value }) {
                    onSuccessCallBack.invoke()
                }else{
                    onFailCallBack.invoke()
                }
            }
        }
    }
}

enum class PermissionType(val permissionArray: Array<String>) {
    GPS(
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    ),
    CAMERA(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)),
}