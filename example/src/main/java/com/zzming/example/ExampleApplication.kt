package com.zzming.example

import android.app.Application
import android.os.Process
import com.zzming.core.LibCore
import com.zzming.core.base.ViewConfig
import com.zzming.core.common.LibCoreConfig
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
        LibCoreConfig.isLoadDefaultLoading = true
        ViewConfig.INSTANCE.defaultLoadingColor = ViewUtils.getColor(R.color.colorAccent)
        logDebug(LibCore.TAG, "ExampleApplication初始化,进程ID:${Process.myPid()}")
    }

}