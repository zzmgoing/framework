package com.zzming.core.utils

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @author MackZhong
 * @time 2023/11/8
 * @description
 **/
class HandleQueue<T>(private val isAuto: Boolean = true, private val callBack: HandleQueueCallBack<T>) {

    private var isHandle = false

    private val list = mutableListOf<T>()

    val size: Int
        get() = list.size

    val isEmpty: Boolean
        get() = list.isEmpty()

    val isNotEmpty: Boolean
        get() = list.isNotEmpty()

    fun add(item: T, index: Int? = -1, test: ((T) -> Boolean)? = null) {
        if (index != null && index > -1) {
            if (index == 0 && test != null) {
                val i = list.indexOfLast { test(it) }
                if (i > -1) {
                    list.add(i + 1, item)
                } else {
                    list.add(index, item)
                }
            } else {
                list.add(index, item)
            }
        } else {
            list.add(item)
        }
        take()
    }

    fun addAll(items: List<T>) {
        list.addAll(items)
        take()
    }

    fun takeCompleted() {
        take(true)
    }

    private fun take(isCompleted: Boolean = false) {
        MainScope().launch {
            if (!isHandle || isCompleted) {
                val item = next()
                if (item == null) {
                    isHandle = false
                    callBack.handle(null)
                } else {
                    try {
                        isHandle = true
                        callBack.handle(item)
                    } catch (e: Exception) {
                        isHandle = false
                    }
                    if (isAuto) {
                        isHandle = false
                        take()
                    }
                }
            }
        }
    }

    private fun next(): T? {
        return list.removeFirstOrNull()
    }

    fun first(): T? {
        return list.firstOrNull()
    }

    fun clear() {
        isHandle = false
        list.clear()
    }

}

interface HandleQueueCallBack<T> {

    suspend fun handle(t: T?)

}