package com.zzming.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.zzming.core.extension.checkLoading

/**
 * @author ZhongZiMing
 * @time 2020/6/8 17:02
 * @description Fragment基类
 **/
abstract class BaseFragment<T : BaseViewModel> : Fragment(), Observer<Any> {

    /**
     * activity
     */
    lateinit var baseActivity: BaseActivity<*>

    /**
     * BaseViewModel
     */
    lateinit var viewModel: T

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
        val rootView = createContentView(inflater, container)
        isLoadData = false
        initViewModel()
        registerViewModel()
        initView()
        return rootView
    }

    /**
     * onAttach
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = requireActivity() as BaseActivity<*>
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
     * 初始化View
     */
    abstract fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View

    /**
     * 初始化view
     */
    abstract fun initView()

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
     * 加载数据
     */
    open fun onRefresh() {
    }

    /**
     * 綁定BaseViewModel，目前只有loading
     */
    open fun registerViewModel() {
        viewModel.observer.observe(viewLifecycleOwner, this)
    }

    /**
     * 接收消息
     */
    override fun onChanged(t: Any?) {
        checkLoading(t)
    }
}