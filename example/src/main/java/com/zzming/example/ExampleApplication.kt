package com.zzming.example

import android.app.Application
import android.os.Process
import com.zzming.core.LibCore
import com.zzming.core.extension.logDebug

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
        logDebug(LibCore.TAG, "ExampleApplication初始化,进程ID:${Process.myPid()}")
    }

}