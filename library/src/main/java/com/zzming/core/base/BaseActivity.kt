package com.zzming.core.base

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.zzming.core.utils.LanguageUtil
import com.zzming.core.utils.ViewUtils

/**
 * @author ZhongZiMing
 * @time 2020/6/5 18:57
 * @description Activity基类
 **/
abstract class BaseActivity: AppCompatActivity(), Observer<AnyEvent> {

    /**
     * 判断当前Activity是否在前台
     */
    var isActive: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate()
        super.onCreate(savedInstanceState)
        registerObserver()
    }

    open fun beforeOnCreate() {}

    open fun registerObserver() {
        LiveEventBus
            .get(AnyEvent::class.java)
            .observe(this, this)
    }

    override fun onResume() {
        super.onResume()
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ViewUtils.hieKeyboard(currentFocus, ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LanguageUtil.attachBaseContext(newBase!!))
    }

    override fun onChanged(value: AnyEvent) {

    }

}