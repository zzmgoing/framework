package com.zzming.example

import android.annotation.SuppressLint
import android.app.Application
import android.os.Process
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.zzming.core.common.LibConfig
import com.zzming.core.common.LibViewConfig
import com.zzming.core.dialog.DefaultLoadingDialog
import com.zzming.core.extension.SIMPLE_NAME_TAG
import com.zzming.core.extension.logDebug
import com.zzming.core.utils.ViewUtils

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class ExampleApplication : Application() {

    companion object {
        lateinit var context: Application
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        LibConfig.apply {
            isOpenLog = true
        }

        LibViewConfig.apply {
            loadLoadingDialog = {
                DefaultLoadingDialog(it).apply {
                    loadingBarColor = ViewUtils.getColor(R.color.colorAccent)
                }
            }
        }

        Glide.init(this, GlideBuilder().setLogLevel(Log.DEBUG))

        logDebug(SIMPLE_NAME_TAG, "ExampleApplication初始化,进程ID:${Process.myPid()}")
    }

}