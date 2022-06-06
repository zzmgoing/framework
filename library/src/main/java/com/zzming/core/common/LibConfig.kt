package com.zzming.core.common

import android.os.Build
import androidx.annotation.RequiresApi
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
     * 应用版本
     */
    lateinit var packageVersionName: String

    /**
     * 应用版本
     */
    var packageVersionCode: Long = 0

    /**
     * 初始化配置字段
     */
    fun init() {
        packageName = LibCore.context.packageName
        val packageInfo = LibCore.context.packageManager.getPackageInfo(packageName, 0)
        packageVersionName = packageInfo.versionName
        packageVersionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            packageInfo.versionCode.toLong()
        }
    }


}