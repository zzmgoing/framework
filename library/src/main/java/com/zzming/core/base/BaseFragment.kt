package com.zzming.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @author ZhongZiMing
 * @time 2020/6/8 17:02
 * @description Fragment基类
 **/
abstract class BaseFragment : Fragment(), ViewListener {

    /**
     * activity
     */
    protected lateinit var baseActivity: BaseActivity

    /**
     * 是否已经加载过数据
     */
    var isLoadData = false

    /**
     * onCreateView
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = onCreateView(inflater.inflate(getLayoutId(), container, false))
        isLoadData = false
        initView()
        return rootView
    }

    /**
     * onAttach
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = requireActivity() as BaseActivity
    }

    /**
     * onResume
     */
    override fun onResume() {
        super.onResume()
        if (!isLoadData) {
            onRefresh()
            isLoadData = true
        }
    }

    /**
     * 布局ID
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化view
     */
    abstract fun initView()

    /**
     * 加载数据
     */
    open fun onRefresh() {
    }

    /**
     * 初始化View
     */
    open fun onCreateView(view: View): View {
        return view
    }

    /**
     * changeLoadState
     */
    override fun changeLoadState(type: String, status: Int) {
        baseActivity.changeLoadState(type, status)
    }

    /**
     * startActivity
     */
    override fun startActivity(toTag: String, rootTag: String?, bundle: Bundle?, type: String?) {
        baseActivity.startActivity(toTag, rootTag, bundle, type)
    }

}