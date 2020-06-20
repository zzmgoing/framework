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

    fun nextPage() {
        page++
    }

    fun reset() {
        page = 0
    }

    fun isFirstPage(): Boolean {
        return page == 0
    }

    fun setPageSize(size: Int){
        PAGE_SIZE = size
    }

}