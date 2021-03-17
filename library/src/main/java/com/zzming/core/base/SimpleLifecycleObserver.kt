package com.zzming.core.base

import androidx.lifecycle.LifecycleOwner

/**
 * @author ZhongZiMing
 * @time 2020/9/8 11:02
 * @description
 **/
open class SimpleLifecycleObserver(val lifecycleOwner: LifecycleOwner) :
    FullLifecycleObserver {

    override fun onCreate() {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onStart() {}

    override fun onResume() {}

    override fun onPause() {}

    override fun onStop() {}

    override fun onDestroy() {
        lifecycleOwner.lifecycle.removeObserver(this)
    }

}