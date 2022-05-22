package com.zzming.core.base

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zzming.core.collector.LoadingCollector
import com.zzming.core.dialog.LoadingDialogListener

/**
 * @author ZhongZiMing
 * @time 2020/7/23 10:01
 * @description BasePager
 **/
open class BasePager(private val activity: AppCompatActivity) : LoadingDialogListener {

    lateinit var rootView: View

    constructor(activity: AppCompatActivity, rootView: View) : this(activity) {
        this.rootView = rootView
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