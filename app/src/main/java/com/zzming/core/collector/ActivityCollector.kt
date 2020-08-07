package com.zzming.core.collector

import android.app.Activity
import com.zzming.core.extension.A_TAG
import com.zzming.core.extension.SIMPLE_NAME_TAG
import com.zzming.core.extension.logDebug
import java.lang.ref.WeakReference
import java.util.*

/**
 * @author ZhongZiMing
 * @time 2020/6/5 14:18
 * @description Activity管理
 **/
class ActivityCollector private constructor() {

    companion object {
        @JvmStatic
        val INSTANCE: ActivityCollector by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityCollector()
        }
    }

    /**
     * 当前栈顶Activity
     */
    var weakRefActivity: WeakReference<Activity>? = null

    /**
     * Activity 集合
     */
    private val activities = LinkedList<WeakReference<Activity>?>()

    /**
     * 允许重复打开的Activity，避免点击或回调打开多个相同页面
     * 比如H5页面，允许同时打开多个
     */
    private val repeatActivities = arrayOf("CcpConfig.H5Activity")

    /**
     * 检查是否为重复页面，true则表明当前栈顶已有相同Activity
     * @param activity
     */
    fun checkRepeatActivity(activity: Activity): Boolean {
        return if (this.weakRefActivity?.get()?.A_TAG == activity.A_TAG) {
            Arrays.binarySearch(repeatActivities, activity.A_TAG) > 0
        } else {
            false
        }
    }

    /**
     * 添加Activity
     */
    fun addActivity(activity: Activity) {
        activities.add(WeakReference(activity))
        logDebug(SIMPLE_NAME_TAG, "add ${activity.SIMPLE_NAME_TAG}")
    }

    /**
     * 删除Activity
     */
    fun removeActivity(activity: Activity) {
        findActivityReference(activity)?.let {
            val result = activities.remove(it)
            logDebug(SIMPLE_NAME_TAG, "remove activity reference $result")
        }
    }

    /**
     * 寻找WeakReference
     */
    private fun findActivityReference(activity: Activity): WeakReference<Activity>? {
        var weakRefActivity: WeakReference<Activity>? = null
        activities.forEach {
            if (activity == it?.get()) {
                weakRefActivity = it
            }
        }
        return weakRefActivity
    }

    /**
     * 设置当前Activity，即为栈顶Activity
     */
    fun setCurrentActivity(activity: Activity) {
        findActivityReference(activity)?.let {
            this.weakRefActivity = it
        }
    }

    /**
     * activity个数
     */
    fun size(): Int {
        return activities.size
    }

    /**
     * 清除所有Activity
     */
    fun finishAll() {
        if (activities.isNotEmpty()) {
            for (activityWeakReference in activities) {
                val activity = activityWeakReference?.get()
                if (activity != null && !activity.isFinishing) {
                    activity.finish()
                }
            }
            activities.clear()
        }
    }

    /**
     * 重新创建所有Activity
     */
    fun reCreateAll(){
        if (activities.isNotEmpty()) {
            for (activityWeakReference in activities) {
                val activity = activityWeakReference?.get()
                if (activity != null && !activity.isFinishing) {
                    activity.recreate()
                }
            }
        }
    }

}