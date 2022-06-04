package com.zzming.core.utils

import android.Manifest
import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionMediator
import com.permissionx.guolindev.PermissionX
import com.zzming.core.base.DoThingWithParams

/**
 * @author ZhongZiMing
 * @time 2022/5/24
 * @description
 **/
object PermissionUtils {

    /**
     * 获取存储权限
     */
    fun getStorage(fragment: Fragment, listener: DoThingWithParams) {
        getStorageImpl(getPermissionMediator(fragment), listener)
    }

    /**
     * 获取存储权限
     */
    fun getStorage(activity: FragmentActivity, listener: DoThingWithParams) {
        getStorageImpl(getPermissionMediator(activity), listener)
    }

    /**
     * 获取存储权限
     */
    private fun getStorageImpl(
        permissionMediator: PermissionMediator,
        listener: DoThingWithParams
    ) {
        permissionMediator.permissions(
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
    fun getLocation(fragment: Fragment, listener: DoThingWithParams) {
        getLocationImpl(getPermissionMediator(fragment), listener)
    }

    /**
     * 获取定位权限
     */
    fun getLocation(activity: FragmentActivity, listener: DoThingWithParams) {
        getLocationImpl(getPermissionMediator(activity), listener)
    }

    /**
     * 获取定位权限
     */
    @SuppressLint("MissingPermission")
    private fun getLocationImpl(
        permissionMediator: PermissionMediator,
        listener: DoThingWithParams
    ) {
        permissionMediator.permissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).request { a, _, _ ->
            if (a) {
                LocationUtils.requestLocation(listener)
            } else {
                listener.invoke(false)
            }
        }
    }

    private fun getPermissionMediator(activity: FragmentActivity): PermissionMediator {
        return PermissionX.init(activity)
    }

    private fun getPermissionMediator(fragment: Fragment): PermissionMediator {
        return PermissionX.init(fragment)
    }

}