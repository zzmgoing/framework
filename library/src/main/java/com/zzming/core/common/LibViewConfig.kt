package com.zzming.core.common

import android.app.Activity

/**
 * @author ZhongZiMing
 * @time 2020/9/4 14:42
 * @description 通用View控件配置
 **/
object LibViewConfig {

    /**
     * 加载loading
     */
    var loadLoadingDialog: (activity: Activity) -> Unit = {}

}