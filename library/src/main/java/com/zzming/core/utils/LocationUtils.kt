package com.zzming.core.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.text.TextUtils
import com.permissionx.guolindev.PermissionX
import com.zzming.core.LibCore
import com.zzming.core.base.DoThingWithParams
import com.zzming.core.extension.logDebug

/**
 * @author ZhongZiMing
 * @time 2022/6/5
 * @description
 **/
object LocationUtils : LocationListener {

    var location: Location? = null
    private var listener: DoThingWithParams? = null

    private val locationManager by lazy {
        LibCore.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @SuppressLint("MissingPermission")
    fun requestLocation(listener: DoThingWithParams?) {
        if (!PermissionX.isGranted(LibCore.context, Manifest.permission.ACCESS_COARSE_LOCATION)
            || !PermissionX.isGranted(LibCore.context, Manifest.permission.ACCESS_FINE_LOCATION)
        ) {
            return
        }
        this.listener = listener
        val criteria = Criteria().apply { powerRequirement = Criteria.POWER_LOW }
        val bestProvider = locationManager.getBestProvider(criteria, true)
        if (!TextUtils.isEmpty(bestProvider)) {
            locationManager.requestLocationUpdates(bestProvider!!, 0L, 0F, this)
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0F, this)
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0F, this)
        } else {
            logDebug("当前定位不可用，请打开GPS")
            listener?.invoke(false)
        }
    }

    override fun onLocationChanged(location: Location) {
        if (location.latitude != 0.0 && location.longitude != 0.0) {
            this.location = location
            this.listener?.invoke(true)
            locationManager.removeUpdates(this)
        }
    }

}