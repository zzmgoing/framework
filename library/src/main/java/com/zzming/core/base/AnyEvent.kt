package com.zzming.core.base

import com.jeremyliao.liveeventbus.core.LiveEvent

/**
 * @author MackZhong
 * @time 2022/7/29
 * @description
 **/
data class AnyEvent(val key: String, val obj: Any? = null) : LiveEvent