package com.zzming.core.bean

/**
 * @author ZhongZiMing
 * @time 2020/6/11 11:18
 * @description 列表加载页数
 **/
class PageInfo {

    /**
     * 当前页数
     */
    var page = 0

    /**
     * 加载页数
     */
    var PAGE_SIZE = 20

    /**
     * 是否是第一页
     */
    val isFirstPage
        get() = page == 0

    /**
     * 下一页
     */
    fun nextPage() {
        page++
    }

    /**
     * 重置
     */
    fun reset() {
        page = 0
    }

}