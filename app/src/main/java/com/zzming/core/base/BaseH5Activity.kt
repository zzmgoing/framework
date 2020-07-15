package com.zzming.core.base

import android.view.ViewGroup
import com.just.agentweb.AgentWeb
import com.zzming.core.R
import com.zzming.core.common.Constant
import com.zzming.core.utils.ViewUtils
import com.zzming.core.viewmodel.H5ViewModel
import kotlinx.android.synthetic.main.core_activity_h5.*
import kotlinx.android.synthetic.main.core_common_title_bar.*

/**
 * @author ZhongZiMing
 * @time 2020/6/7 16:29
 * @description H5Activity
 **/
class BaseH5Activity : BaseViewModelActivity<H5ViewModel>() {

    /**
     * LayoutParams
     */
    private val layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    /**
     * AgentWeb
     */
    val mAgentWeb: AgentWeb by lazy {
        AgentWeb.with(this)
            .setAgentWebParent(web_container, layoutParams)
            .useDefaultIndicator(ViewUtils.getColor(R.color.colorPrimary))
            .createAgentWeb()
            .ready()
            .get()
    }

    /**
     * webView
     */
    val mWebView by lazy {
        mAgentWeb.webCreator.webView
    }

    /**
     * urlLoader
     */
    val mUrlLoader by lazy {
        mAgentWeb.urlLoader
    }

    /**
     * url
     */
    val mTitle by lazy {
        intent.extras?.getString(Constant.PARAMS_H5_TITLE)
    }

    /**
     * url
     */
    val mUrl by lazy {
        intent.extras?.getString(Constant.PARAMS_H5_URL)
    }

    override fun getModelClass(): Class<H5ViewModel> {
        return H5ViewModel::class.java
    }

    override fun initContentView() {
        setContentView(R.layout.core_activity_h5)
    }

    override fun initView() {
        initTitleBar()
        loadUrl()
    }

    /**
     * loadUrl
     */
    fun loadUrl(){
        if(!mUrl.isNullOrEmpty()){
            mUrlLoader.loadUrl(mUrl)
        }
    }

    /**
     * 初始化标题栏
     */
    fun initTitleBar() {
        title_view_stub.layoutResource = R.layout.core_common_title_bar
        title_view_stub.inflate()
        title_bar_back.setOnClickListener { onBackPressed() }
        if(!mTitle.isNullOrEmpty()){
            title_bar_title.text = mTitle
        }
    }

}