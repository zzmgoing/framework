package com.zzming.example

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.zzming.core.utils.LanguageUtil

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
        LanguageUtil.changLanguage(context)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LanguageUtil.attachBaseContext(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LanguageUtil.changLanguage(context)
    }

}