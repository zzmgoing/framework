package com.zzming.core.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX
import com.zzming.core.LibCore
import com.zzming.core.base.DoThing
import com.zzming.core.base.DoThingWithParams

/**
 * @author ZhongZiMing
 * @time 2022/5/24
 * @description
 **/
object PermissionUtils {

    var location: Location? = null

    private val locationManager by lazy {
        LibCore.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    /**
     * 获取存储权限
     */
    fun getStorage(activity: FragmentActivity, listener: DoThingWithParams) {
        PermissionX.init(activity).permissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).request { a, _, _ ->
            if (a) {
                listener.invoke(true)
            } else {
                listener.invoke(false)
            }
        }
    }

    /**
     * 获取定位权限
     */
    @SuppressLint("MissingPermission")
    fun getLocation(activity: FragmentActivity, listener: DoThing) {
        PermissionX.init(activity).permissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).request { a, _, _ ->
            if (a) {
                location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
                listener.invoke()
            } else {
                listener.invoke()
            }
        }
    }

}