package com.zzming.core.base

import com.zzming.core.dialog.LoadingDialogListener

/**
 * @author ZhongWei
 * @time 2020/9/4 14:42
 * @description 通用View控件配置
 **/
class ViewConfig private constructor(){

    companion object {

        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ViewConfig() }

    }

    /**
     * loading
     */
    var loadingDialog : LoadingDialogListener? = null

}