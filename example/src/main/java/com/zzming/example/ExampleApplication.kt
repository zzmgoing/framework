package com.zzming.example

import android.app.Application
import android.os.Process
import com.zzming.core.collector.LoadingCollector
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
                LoadingCollector.addLoading(DefaultLoadingDialog(it).apply {
                    loadingBarColor = ViewUtils.getColor(R.color.colorAccent)
                })
            }
        }

        logDebug(SIMPLE_NAME_TAG, "ExampleApplication初始化,进程ID:${Process.myPid()}")
    }

}