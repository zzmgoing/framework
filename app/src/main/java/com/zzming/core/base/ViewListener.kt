package com.zzming.core.base

import android.os.Bundle

/**
 * @author ZhongZiMing
 * @time 2020/6/10 0:06
 * @description View层接口
 **/
interface ViewListener {

    /**
     * 0刷新中
     * 1刷新成功
     * 2刷新失败
     */
    fun showLoadingState(type: Int)

    /**
     * 0加载中
     * 1加载成功
     * 2加载失败
     */
    fun showLoadMoreState(type: Int)

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