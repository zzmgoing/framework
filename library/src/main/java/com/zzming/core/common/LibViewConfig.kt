package com.zzming.core.common

import android.app.Activity
import com.zzming.core.dialog.LoadingDialogListener

/**
 * @author ZhongZiMing
 * @time 2020/9/4 14:42
 * @description 通用View控件配置
 **/
object LibViewConfig {

    /**
     * 是否支持多语言
     */
    var isSupportLanguage = false

    /**
     * 加载loading
     */
    var loadLoadingDialog: ((activity: Activity) -> LoadingDialogListener?)? = null

}