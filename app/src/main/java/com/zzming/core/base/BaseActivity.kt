package com.zzming.core.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.zzming.core.collector.ActivityCollector
import com.zzming.core.common.Constant
import com.zzming.core.extension.logError
import com.zzming.core.utils.ViewUtils
import java.lang.ref.WeakReference

/**
 * @author ZhongZiMing
 * @time 2020/6/5 18:57
 * @description Activity基类
 **/
abstract class BaseActivity : AppCompatActivity(), ViewListener {

    /**
     * Activity TAG
     */
    val TAG = this.javaClass.name

    /**
     * 判断当前Activity是否在前台
     */
    protected var isActive: Boolean = false

    /**
     * 当前Activity的实例。
     */
    protected var activity: BaseActivity? = null

    /**
     * Activity弱引用
     */
    private var weakRefActivity: WeakReference<Activity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        weakRefActivity = WeakReference(this)
        ActivityCollector.INSTANCE.addActivity(weakRefActivity)
        beforeContentView()
        initContentView()
        initViewModel()
        initView()
    }

    /**
     * 绑定view之前
     */
    fun beforeContentView() {}

    /**
     * 绑定view
     */
    abstract fun initContentView()

    /**
     * 初始化view
     */
    abstract fun initView()

    /**
     * 初始化ViewModel
     */
    open fun initViewModel() {}

    /**************生命周期****************/

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        isActive = true
        ActivityCollector.INSTANCE.setCurrentActivity(this)
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity = null
        ActivityCollector.INSTANCE.removeActivity(weakRefActivity)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ViewUtils.hieKeyboard(currentFocus,ev)
        return super.dispatchTouchEvent(ev)
    }

    /**************生命周期****************/


    /**
     * Loading
     */
    override fun showLoadingState(type: Int) {

    }

    /**
     * LoadMore
     */
    override fun showLoadMoreState(type: Int) {

    }

    /**
     * 页面跳转
     */
    override fun startActivity(toTag: String, rootTag: String?, bundle: Bundle?, type: String?) {
        try {
            val intent = Intent(this, Class.forName(toTag))
            val mBundle: Bundle = Bundle().apply {
                putString(Constant.PAGE_FROM_TAG, TAG)
                putString(Constant.PAGE_FROM_TYPE, type)
                putString(Constant.PAGE_ROOT_TAG, rootTag ?: TAG)
            }
            bundle?.let { mBundle.putAll(it) }
            intent.putExtras(mBundle)
            startActivityForResult(intent, Constant.PAGE_REQUEST_CODE)
        } catch (e: ClassNotFoundException) {
            logError("$TAG 中跳转页面失败，未找到$toTag 请检查配置", e)
        }
    }

    /**
     * 处理返回页面逻辑
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Constant.PAGE_REQUEST_CODE == requestCode && Constant.PAGE_RESULT_CODE == resultCode) {
            val rootTag = data?.getStringExtra(Constant.PAGE_ROOT_TAG)
            if (!rootTag.isNullOrEmpty() && TAG != rootTag) {
                onBackPressed()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backToRootPage()
    }

    /**
     * 回到根页面
     */
    private fun backToRootPage() {
        val intent = Intent()
        val rootTag = getIntent().extras?.getString(Constant.PAGE_ROOT_TAG)
        if(!rootTag.isNullOrEmpty()){
            intent.putExtra(Constant.PAGE_ROOT_TAG, rootTag)
            setResult(Constant.PAGE_RESULT_CODE, intent)
        }
        finish()
    }

}