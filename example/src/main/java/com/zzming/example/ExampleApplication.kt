package com.zzming.example

import android.app.Application
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        GlobalScope.launch {
            LanguageLib.init(context, LanguageType.CHINESE)
        }
    }

}