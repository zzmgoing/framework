package com.zzming.core.collector

import android.app.Dialog
import androidx.activity.ComponentActivity
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
        if (loadingDialogListener is Dialog) {
            val dialog = loadingDialogListener as Dialog
            if (dialog.context is ComponentActivity) {
                val activity = dialog.context as ComponentActivity
                activity.lifecycle.addObserver(this)
                loadingViewMap[activity.SIMPLE_NAME_TAG] = loadingDialogListener
            }
        }
    }

    /**
     * showLoading
     */
    fun showLoading(activity: ComponentActivity?) {
        if (!activity.isAlive()) {
            loadingViewMap.remove(activity?.SIMPLE_NAME_TAG)
            return
        }
        loadingViewMap[activity?.SIMPLE_NAME_TAG]?.showLoading()
    }

    /**
     * hideLoading
     */
    fun hideLoading(activity: ComponentActivity?) {
        if (!activity.isAlive()) {
            loadingViewMap.remove(activity?.SIMPLE_NAME_TAG)
            return
        }
        loadingViewMap[activity?.SIMPLE_NAME_TAG]?.hideLoading()
    }

    override fun onPause(owner: LifecycleOwner) {
        if (owner is ComponentActivity) {
            loadingViewMap[owner.SIMPLE_NAME_TAG]?.hideLoading()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        removeLoading(owner)
    }

    /**
     * removeLoading
     */
    private fun removeLoading(owner: LifecycleOwner) {
        if (owner is ComponentActivity) {
            loadingViewMap.remove(owner.SIMPLE_NAME_TAG)
        }
    }
}