package com.zzming.core.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import com.zzming.core.LibCore

/**
 * @author ZhongWei
 * @time 2020/8/6 11:11
 * @description
 **/
class RestartAppService : Service() {

    lateinit var appPackageName: String

    companion object {
        const val APP_PACKAGE_NAME = "app_package_name"
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        appPackageName = intent?.getStringExtra(APP_PACKAGE_NAME) ?: LibCore.context.packageName
        val launchIntent= packageManager.getLaunchIntentForPackage(appPackageName)
        launchIntent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(launchIntent)
        return START_STICKY
    }

}