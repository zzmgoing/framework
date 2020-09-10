package com.zzming.core.base

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zzming.core.collector.LoadingCollector
import com.zzming.core.dialog.LoadingDialogListener
import com.zzming.core.utils.ViewUtils

/**
 * @author ZhongWei
 * @time 2020/7/23 10:01
 * @description BasePager
 **/
abstract class BasePager(private val activity: AppCompatActivity, private val rootView: View) : FullLifecycleObserver,LoadingDialogListener {

    constructor(activity: AppCompatActivity, layoutId: Int) : this(
        activity,
        ViewUtils.createView(layoutId)
    )

    init {
        activity.lifecycle.addObserver(this)
    }

    override fun onCreate() {
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        activity.lifecycle.removeObserver(this)
    }

    override fun showLoading() {
        LoadingCollector.showLoading(activity)
    }

    override fun hideLoading() {
        LoadingCollector.hideLoading(activity)
    }

    override fun bindActivity(): Activity {
        return activity
    }


}