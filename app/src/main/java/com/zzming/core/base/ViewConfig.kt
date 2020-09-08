package com.zzming.core.base

/**
 * @author ZhongWei
 * @time 2020/9/4 14:42
 * @description 通用View控件配置
 **/
class ViewConfig private constructor() {

    companion object {

        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ViewConfig() }

    }

    /**
     * 默认加载的色值
     */
    var defaultLoadingColor: Int? = null

}