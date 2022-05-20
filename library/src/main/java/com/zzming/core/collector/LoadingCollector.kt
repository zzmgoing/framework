package com.zzming.core.collector

import android.app.Activity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.zzming.core.dialog.LoadingDialogListener
import com.zzming.core.extension.SIMPLE_NAME_TAG
import com.zzming.core.extension.isAlive

/**
 * @author ZhongZiMing
 * @time 2020/9/8 11:07
 * @description
 **/
object LoadingCollector : DefaultLifecycleObserver {

    /**
     * loadingViewSet
     */
    private val loadingViewMap = HashMap<String, LoadingDialogListener>()

    /**
     * addLoading
     */
    fun addLoading(loadingDialogListener: LoadingDialogListener) {
        val activity = loadingDialogListener.bindActivity()
        if (activity is LifecycleOwner) {
            activity.lifecycle.addObserver(this)
        }
        loadingViewMap[activity.SIMPLE_NAME_TAG] = loadingDialogListener
    }

    /**
     * showLoading
     */
    fun showLoading(activity: Activity?) {
        if (!activity.isAlive()) {
            loadingViewMap.remove(activity?.SIMPLE_NAME_TAG)
            return
        }
        loadingViewMap[activity?.SIMPLE_NAME_TAG]?.showLoading()
    }

    /**
     * hideLoading
     */
    fun hideLoading(activity: Activity?) {
        if (!activity.isAlive()) {
            loadingViewMap.remove(activity?.SIMPLE_NAME_TAG)
            return
        }
        loadingViewMap[activity?.SIMPLE_NAME_TAG]?.hideLoading()
    }

    /**
     * removeLoading
     */
    private fun removeLoading(owner: LifecycleOwner) {
        val iterator = loadingViewMap.iterator()
        while (iterator.hasNext()) {
            val dialog = iterator.next().value
            if (owner == dialog.bindActivity()) {
                iterator.remove()
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        removeLoading(owner)
    }

}