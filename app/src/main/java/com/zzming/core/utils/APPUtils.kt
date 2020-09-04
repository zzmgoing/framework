package com.zzming.core.utils

import android.content.Intent
import com.zzming.core.LibCore
import com.zzming.core.common.LibCoreConfig


/**
 * @author ZhongWei
 * @time 2020/8/5 16:10
 * @description
 **/
object APPUtils {

    /**
     * 重启APP
     */
    fun reStartApp() {
        LibCore.context.packageManager.getLaunchIntentForPackage(LibCoreConfig.packageName)?.apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            LibCore.context.startActivity(this)
        }
    }

}