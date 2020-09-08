package com.zzming.core.dialog

import android.app.Activity

/**
 * @author ZhongWei
 * @time 2020/9/4 14:45
 * @description loading
 **/
interface LoadingDialogListener {

    fun showLoading()

    fun hideLoading()

    fun bindActivity(): Activity

}