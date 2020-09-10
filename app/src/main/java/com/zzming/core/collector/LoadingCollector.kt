package com.zzming.core.collector

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.zzming.core.base.SimpleLifecycleObserver
import com.zzming.core.dialog.LoadingDialogListener
import com.zzming.core.extension.SIMPLE_NAME_TAG
import com.zzming.core.extension.isAlive

/**
 * @author ZhongWei
 * @time 2020/9/8 11:07
 * @description
 **/
object LoadingCollector {

    /**
     * loadingViewSet
     */
    private val loadingViewMap = HashMap<String, LoadingDialogListener>()

    /**
     * addLoading
     */
    fun addLoading(loadingDialogListener: LoadingDialogListener) {
        val activity = loadingDialogListener.bindActivity()
        val tag = activity.SIMPLE_NAME_TAG
        if (!loadingViewMap.containsKey(tag)) {
            if (activity is AppCompatActivity) {
                LoadingLifecycleObserver(activity, tag)
            }
            loadingViewMap[tag] = loadingDialogListener
        }
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
     * SimpleLifecycleObserver
     */
    class LoadingLifecycleObserver(lifecycleOwner: LifecycleOwner, private val tag: String) :
        SimpleLifecycleObserver(lifecycleOwner) {

        override fun onDestroy() {
            super.onDestroy()
            loadingViewMap.remove(tag)
        }

    }

}