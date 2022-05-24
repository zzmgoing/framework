package com.zzming.core.base

/**
 * @author ZhongZiMing
 * @time 2020/8/4 19:19
 * @description 通用接口，做想做的事
 **/
typealias DoThing = () -> Any?

typealias DoThingWithParams = (Any?) -> Any?

interface DoListener {

    /**
     * 做想做的事
     */
    fun doThing(any: Any? = null): Any?

}