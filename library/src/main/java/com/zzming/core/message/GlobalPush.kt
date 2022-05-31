package com.zzming.core.message

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.zzming.core.base.DoListener
import com.zzming.core.extension.SIMPLE_NAME_TAG
import com.zzming.core.extension.logDebug
import com.zzming.core.extension.runOnMainThread

/**
 * @author ZhongZiMing
 * @time 2022/5/18
 * @description 注册分发消息
 **/
object GlobalPush : DefaultLifecycleObserver {

    private val message = HashMap<LifecycleOwner, MutableSet<DoListener>>()

    private val messageListener = HashMap<String, MutableSet<DoListener>>()

    /**
     * 注册消息
     */
    fun register(owner: LifecycleOwner, key: String, listener: DoListener) {
        if (!message.containsKey(owner)) {
            message[owner] = hashSetOf(listener)
        } else {
            message[owner]?.add(listener)
        }
        if (!messageListener.containsKey(key)) {
            messageListener[key] = hashSetOf(listener)
        } else {
            messageListener[key]?.add(listener)
        }
        owner.lifecycle.addObserver(this)
        logDebug(SIMPLE_NAME_TAG,"${owner.SIMPLE_NAME_TAG}注册${key}消息成功")
    }

    /**
     * 分发消息
     */
    fun dispatchMessage(key: String, obj: Any? = null) {
        messageListener[key]?.let { set ->
            set.forEach {
                runOnMainThread {
                    it.doThing(obj)
                    logDebug(SIMPLE_NAME_TAG,"分发${key}消息成功")
                }
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        if (!message.containsKey(owner)) {
            return
        }
        val removeListeners = message.remove(owner) ?: return
        for (listeners in messageListener) {
            val iterator = listeners.value.iterator()
            while (iterator.hasNext()) {
                if (removeListeners.contains(iterator.next())) {
                    iterator.remove()
                    logDebug(SIMPLE_NAME_TAG,"${owner.SIMPLE_NAME_TAG}取消注册${listeners.key}消息成功")
                }
            }
        }
    }

}