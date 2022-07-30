package com.zzming.core.common

import com.zzming.core.LibCore
import com.zzming.core.utils.BuildUtils

/**
 * @author ZhongZiMing
 * @time 2020/8/20 16:02
 * @description 配置字段
 **/
object LibConfig {

    /**
     * 是否是debug模式
     */
    val isDebug = true

    /**
     * 是否开启日志
     */
    var isOpenLog = false

    /**
     * 应用名称
     */
    val packageName: String
        get() = LibCore.context.packageName

    /**
     * 应用版本名称
     */
    val packageVersionName: String
        get() {
            val packageInfo = LibCore.context.packageManager.getPackageInfo(packageName, 0)
            return packageInfo.versionName
        }

    /**
     * 应用版本号
     */
    val packageVersionCode: Long
        get() {
            val packageInfo = LibCore.context.packageManager.getPackageInfo(packageName, 0)
            return if (BuildUtils.isAtLeast28Api()) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionCode.toLong()
            }
        }

}