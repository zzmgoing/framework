package com.zzming.core.base

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.zzming.core.extension.bindLinearLayoutManager

/**
 * @author ZhongZiMing
 * @time 2020/6/8 17:19
 * @description BaseViewModelFragment
 **/
abstract class BaseViewModelFragment<T : BaseViewModel> : BaseFragment(), OnLoadMoreListener {

    /**
     * ViewModel
     */
    val viewModel: T by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application).create(getModelClass())
    }

    abstract fun getModelClass(): Class<T>

    /**
     * 初始化ViewModel
     */
    override fun initViewModel() {
        viewModel.loadingState.observe(this, Observer { showLoadingState(it) })
        viewModel.loadMoreState.observe(this, Observer { showLoadMoreState(it) })
    }

    /**
     * 初始化SmartRefreshLayout
     */
    open fun initRefreshLayout(smartRefreshLayout: SmartRefreshLayout){
        smartRefreshLayout.setRefreshHeader(ClassicsHeader(activity))
        smartRefreshLayout.setOnRefreshListener { onRefresh() }
    }

    /**
     * 初始化AdapterAndRecyclerView
     */
    open fun initAdapterAndRecyclerView(adapter: BaseQuickAdapter<*,BaseViewHolder>,recyclerView: RecyclerView) {
        recyclerView.bindLinearLayoutManager(activity!!)
        recyclerView.adapter = adapter
        adapter.animationEnable = true
        adapter.loadMoreModule.setOnLoadMoreListener(this)
        adapter.headerWithEmptyEnable = true
    }

    override fun showLoadingState(type: Int) {
    }

    override fun showLoadMoreState(type: Int) {
    }

    /**
     * 加载更多
     */
    override fun onLoadMore() {}

}