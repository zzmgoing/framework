package com.zzming.core.net

/**
 * @author ZhongZiMing
 * @time 2021/01/04
 * @description
 **/
enum class LoadStatus(val value: Int) {

    /**
     * 加载中
     */
    LOADING(0),

    /**
     * 加载成功
     */
    SUCCESS(1),

    /**
     * 加载失败
     */
    FAIL(2),

}