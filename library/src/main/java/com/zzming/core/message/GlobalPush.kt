package com.zzming.core.message

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.zzming.core.base.DoListener

/**
 * @author ZhongZiMing
 * @time 2022/5/18
 * @description 注册分发消息
 **/
object GlobalPush : DefaultLifecycleObserver {

    private val message = HashMap<String, HashMap<LifecycleOwner, DoListener>>()

    /**
     * 注册消息
     */
    fun register(key: String, lifecycleOwner: LifecycleOwner, listener: DoListener) {
        if (message[key] == null) {
            message[key] = HashMap()
        }
        message[key]?.set(lifecycleOwner, listener)
        lifecycleOwner.lifecycle.addObserver(this)
    }

    /**
     * 分发消息
     */
    fun dispatchMessage(key: String, obj: Any? = null) {
        if (message[key].isNullOrEmpty()) {
            return
        }
        val queue = message[key]
        for (item in queue!!.iterator()) {
            item.value.doThing(obj)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        for (queue in message) {
            val iterator = queue.value.iterator()
            while (iterator.hasNext()) {
                if (iterator.next().key == owner) {
                    owner.lifecycle.removeObserver(this)
                    iterator.remove()
                }
            }
        }
    }

}