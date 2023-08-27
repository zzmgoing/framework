package com.zzming.core.bean

/**
 * @author ZhongZiMing
 * @time 2020/6/11 11:18
 * @description 列表加载页数
 **/
class PageInfo(private val index: Int = 0, private val size: Int = 10) {

    /**
     * 当前页数
     */
    var current = index

    /**
     * 总条数
     */
    var totalCount = 0

    /**
     * 是否是第一页
     */
    val isFirstPage
        get() = current == index

    /**
     * 下一页
     */
    fun nextPage() {
        current++
    }

    /**
     * 重置
     */
    fun reset() {
        current = index
    }

    /**
     * 是否有下一页
     */
    fun hasNextPage(): Boolean {
        return current < totalCount / size
    }

}