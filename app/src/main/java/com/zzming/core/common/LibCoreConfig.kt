package com.zzming.core.common

import com.zzming.core.LibCore
import com.zzming.core.R
import kotlin.properties.Delegates

/**
 * @author ZhongWei
 * @time 2020/8/20 16:02
 * @description 配置字段
 **/
object LibCoreConfig {

    /**
     * 是否是debug模式
     */
    var isDebug by Delegates.notNull<Boolean>()

    /**
     * 应用名称
     */
    lateinit var packageName: String


    /**
     * 初始化配置字段
     */
    fun init() {
        isDebug = LibCore.context.getString(R.string.is_debug).toBoolean()
        packageName = LibCore.context.applicationInfo?.packageName.toString()
    }


}