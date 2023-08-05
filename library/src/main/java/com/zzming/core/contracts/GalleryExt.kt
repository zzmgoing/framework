package com.zzming.core.contracts

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ResolveInfo
import com.zzming.core.extension.appContext
import com.zzming.core.extension.logDebug

/**
 * 获取系统相册
 **/
@SuppressLint("PrivateApi")
fun Intent.filterSystemGallery(): Intent {
    val filterList = listOf("com.android.fileexplorer", "com.google.android.apps.photos")
    try {
        val activities = appContext.packageManager.queryIntentActivities(this, 0)
        val filter = activities.filter { !filterList.contains(it.activityInfo.packageName) }
            .filter {
                try {
                    val clazz = ResolveInfo::class.java
                    val systemField = clazz.getDeclaredField("system")
                    systemField.isAccessible = true
                    systemField.getBoolean(it)
                } catch (e: Exception) {
                    false
                }
            }
        if (filter.isEmpty()) {
            return this
        }
        var info: ActivityInfo? = null
        if (filter.size > 1) {
            info = filter.find { it.activityInfo.packageName.contains("gallery", true) }?.activityInfo
        }
        if (info == null) {
            info = filter.first().activityInfo
        }
        logDebug("zzm find system gallery: ${info!!.packageName}")
        component = ComponentName(info.packageName, info.name)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return this
}