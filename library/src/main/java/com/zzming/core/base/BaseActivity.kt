package com.zzming.core.base

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.zzming.core.utils.LanguageUtil
import com.zzming.core.utils.ViewUtils

/**
 * @author ZhongZiMing
 * @time 2020/6/5 18:57
 * @description Activity基类
 **/
abstract class BaseActivity : AppCompatActivity() {

    /**
     * 判断当前Activity是否在前台
     */
    var isActive: Boolean = false

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate()
        super.onCreate(savedInstanceState)
        beforeContentView()
        initContentView()
        initView()
    }

    /**
     * 调用onCreate之前
     */
    open fun beforeOnCreate() {}

    /**
     * 绑定view之前
     */
    open fun beforeContentView() {}

    /**
     * 绑定view
     */
    abstract fun initContentView()

    /**
     * 初始化view
     */
    abstract fun initView()

    /**
     * onStart
     */
    override fun onStart() {
        super.onStart()
    }

    /**
     * onResume
     */
    override fun onResume() {
        super.onResume()
        isActive = true
    }

    /**
     * onPause
     */
    override fun onPause() {
        super.onPause()
        isActive = false
    }

    /**
     * onStop
     */
    override fun onStop() {
        super.onStop()
    }

    /**
     * onDestroy
     */
    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * dispatchTouchEvent
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ViewUtils.hieKeyboard(currentFocus, ev)
        return super.dispatchTouchEvent(ev)
    }

    /**
     * attachBaseContext
     */
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LanguageUtil.attachBaseContext(newBase!!))
    }

}