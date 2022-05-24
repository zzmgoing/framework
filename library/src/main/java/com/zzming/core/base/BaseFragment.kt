package com.zzming.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zzming.core.extension.hideLoading
import com.zzming.core.extension.showLoading

/**
 * @author ZhongZiMing
 * @time 2020/6/8 17:02
 * @description Fragment基类
 **/
abstract class BaseFragment : Fragment() {

    /**
     * activity
     */
    lateinit var baseActivity: BaseActivity

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
        val rootView = getContentView(inflater, container)
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
     * 初始化View
     */
    abstract fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View

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
     * 綁定BaseViewModel，目前只有loading
     */
    fun registerViewModel(viewModel: BaseViewModel) {
        viewModel.loading.observe(this) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }
}