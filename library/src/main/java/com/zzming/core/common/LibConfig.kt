package com.zzming.core.common

import com.zzming.core.BuildConfig
import com.zzming.core.LibCore

/**
 * @author ZhongZiMing
 * @time 2020/8/20 16:02
 * @description 配置字段
 **/
object LibConfig {

    /**
     * 是否是debug模式
     */
    val isDebug = BuildConfig.DEBUG

    /**
     * 是否开启日志
     */
    var isOpenLog = BuildConfig.DEBUG

    /**
     * 应用名称
     */
    lateinit var packageName: String

    /**
     * 初始化配置字段
     */
    fun init() {
        packageName = LibCore.context.packageName
    }


}