package com.zzming.core.utils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.zzming.core.extension.appContext
import com.zzming.core.extension.isHarmonyOs

/**
 * @author ZhongZiMing
 * @time 2022/5/24
 * @description
 **/
object PermissionUtils {

    fun hasPermission(permission: Array<String>): Boolean {
        val listPermission = permission.filter {
            ContextCompat.checkSelfPermission(appContext, it) == PackageManager.PERMISSION_GRANTED
        }
        return permission.size == listPermission.size
    }

    fun getLocationPermission(): Array<String> {
        return arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fun getImagePermission(): Array<String> {
        val permission = arrayListOf<String>()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || isHarmonyOs()) {
            permission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission.add(Manifest.permission.READ_MEDIA_IMAGES)
        }
        return permission.toTypedArray()
    }

    fun getCameraPermission(): Array<String> {
        val permission = arrayListOf(Manifest.permission.CAMERA)
        permission.addAll(getImagePermission())
        return permission.toTypedArray()
    }

}