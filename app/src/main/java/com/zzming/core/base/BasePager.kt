package com.zzming.core.base

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.zzming.core.utils.ViewUtils

/**
 * @author ZhongWei
 * @time 2020/7/23 10:01
 * @description BasePager
 **/
class BasePager(private val lifecycleOwner: LifecycleOwner, rootView: View) : FullLifecycleObserver {

    constructor(lifecycleOwner: LifecycleOwner, layoutId: Int) : this(
        lifecycleOwner,
        ViewUtils.createView(layoutId)
    )

    init {
        lifecycleOwner.lifecycle.addObserver(this)
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
        lifecycleOwner.lifecycle.removeObserver(this)
    }


}