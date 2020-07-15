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
     * Fragment中创建的布局
     */
    protected var rootView: View? = null

    /**
     * activity
     */
    protected var baseActivity: BaseActivity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        return onCreateView(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseActivity = activity as BaseActivity
        onRefresh()
    }

    /**
     * 初始化view
     */
    abstract fun initView()

    /**
     * 初始化ViewModel
     */
    open fun initViewModel() {}

    /**
     * 加载数据
     */
    open fun onRefresh() {}

    /**
     * 获取布局
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化
     */
    fun onCreateView(view: View): View {
        this.rootView = view
        return view
    }

    /**
     * Loading
     */
    override fun showLoadingState(type: Int) {
        baseActivity?.showLoadingState(type)
    }

    /**
     * LoadMore
     */
    override fun showLoadMoreState(type: Int) {
        baseActivity?.showLoadMoreState(type)
    }

    /**
     * startActivity
     */
    override fun startActivity(toTag: String, rootTag: String?, bundle: Bundle?, type: String?) {
        baseActivity?.startActivity(toTag, rootTag, bundle, type)
    }

}