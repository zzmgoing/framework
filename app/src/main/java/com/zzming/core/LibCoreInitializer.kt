package com.zzming.core

import android.content.Context
import androidx.startup.Initializer

/**
 * @author ZhongWei
 * @time 2020/7/9 10:02
 * @description 初始化SDK
 **/
class LibCoreInitializer : Initializer<LibCore> {
    override fun create(context: Context): LibCore {
        LibCore.instance.init(context)
        return LibCore.instance
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return emptyList<Class<out Initializer<*>>>().toMutableList()
    }
}