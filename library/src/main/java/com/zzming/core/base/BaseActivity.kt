package com.zzming.core.base

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.zzming.core.extension.checkLoading
import com.zzming.core.utils.LanguageUtil
import com.zzming.core.utils.ViewUtils

/**
 * @author ZhongZiMing
 * @time 2020/6/5 18:57
 * @description Activity基类
 **/
abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity(), Observer<Any> {

    /**
     * BaseViewModel
     */
    lateinit var viewModel: T

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
        createContentView()
        initViewModel()
        registerViewModel()
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
     * 初始化ViewModel
     */
    private fun initViewModel() {
        viewModel = createViewModel()
    }

    /**
     * 初始化ViewModel
     */
    abstract fun createViewModel(): T

    /**
     * 绑定view
     */
    abstract fun createContentView()

    /**
     * 初始化view
     */
    abstract fun initView()

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

    /**
     * BaseViewModel
     */
    open fun registerViewModel() {
        viewModel.observer.observe(this, this)
    }

    /**
     * 来自ViewModel的消息
     */
    override fun onChanged(t: Any?) {
        checkLoading(t)
    }

}