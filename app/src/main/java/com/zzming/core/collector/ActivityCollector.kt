package com.zzming.core.collector

import android.app.Activity
import com.zzming.core.extension.A_TAG
import com.zzming.core.extension.logDebug
import java.lang.ref.WeakReference
import java.util.*

/**
 * @author ZhongZiMing
 * @time 2020/6/5 14:18
 * @description Activity管理
 **/
class ActivityCollector private constructor() {

    private val TAG = javaClass.simpleName

    companion object {
        @JvmStatic
        val INSTANCE: ActivityCollector by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityCollector()
        }
    }

    /**
     * 当前栈顶Activity
     */
    lateinit var activity : Activity

    /**
     * Activity 集合
     */
    val activities = LinkedList<WeakReference<Activity>?>()

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
        return if (this.activity.A_TAG == activity.A_TAG) {
            Arrays.binarySearch(repeatActivities, activity.A_TAG) > 0
        } else {
            false
        }
    }

    /**
     * 添加Activity
     */
    fun addActivity(weakRefActivity: WeakReference<Activity>?){
        activities.add(weakRefActivity)
    }

    /**
     * 删除Activity
     */
    fun removeActivity(weakRefActivity: WeakReference<Activity>?){
        val result = activities.remove(weakRefActivity)
        logDebug(TAG, "remove activity reference $result")
    }

    /**
     * 设置当前Activity，即为栈顶Activity
     */
    fun setCurrentActivity(activity : Activity){
        this.activity = activity
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

}