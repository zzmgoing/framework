package com.zzming.core.base

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
     * rootView
     */
    protected lateinit var rootView: View

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
        val view = inflater.inflate(getLayoutId(), container, false)
        return onCreateView(view)
    }

    /**
     * onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    /**
     * onActivityCreated
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseActivity = requireActivity() as BaseActivity
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
        isLoadData = true
    }

    /**
     * 初始化View
     */
    open fun onCreateView(view: View): View {
        this.rootView = view
        return view
    }

    /**
     * Loading
     */
    override fun showLoadingState(type: Int) {
        baseActivity.showLoadingState(type)
    }

    /**
     * LoadMore
     */
    override fun showLoadMoreState(type: Int) {
        baseActivity.showLoadMoreState(type)
    }

    /**
     * startActivity
     */
    override fun startActivity(toTag: String, rootTag: String?, bundle: Bundle?, type: String?) {
        baseActivity.startActivity(toTag, rootTag, bundle, type)
    }

}