package com.zzming.core.base

import android.os.Bundle

/**
 * @author ZhongZiMing
 * @time 2020/6/10 0:06
 * @description View层接口
 **/
interface ViewListener {

    /**
     * 加载状态改变回调
     */
    fun changeLoadState(type: String, status: Int)

    /**
     * Activity跳转
     */
    fun startActivity(
        toTag: String,
        rootTag: String? = null,
        bundle: Bundle? = null,
        type: String? = "default"
    )

}