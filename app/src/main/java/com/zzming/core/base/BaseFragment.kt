package com.zzming.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.jessyan.autosize.internal.CustomAdapt

/**
 * @author ZhongZiMing
 * @time 2020/6/8 17:02
 * @description Fragment基类
 **/
abstract class BaseFragment : Fragment(), ViewListener, CustomAdapt {

    /**
     * Fragment中创建的布局
     */
    protected lateinit var rootView: View

    /**
     * activity
     */
    protected lateinit var baseActivity: BaseActivity

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
     * 是否按照宽度进行等比例适配 (为了保证在高宽比不同的屏幕上也能正常适配, 所以只能在宽度和高度之中选择一个作为基准进行适配)
     *
     * @return {@code true} 为按照宽度进行适配, {@code false} 为按照高度进行适配
     */
    override fun isBaseOnWidth(): Boolean {
        return true
    }

    /**
     * 这里使用 iPhone 的设计图, iPhone 的设计图尺寸为 750px * 1334px, 高换算成 dp 为 667 (1334px / 2 = 667dp)
     * <p>
     * 返回设计图上的设计尺寸, 单位 dp
     * {@link #getSizeInDp} 须配合 {@link #isBaseOnWidth()} 使用, 规则如下:
     * 如果 {@link #isBaseOnWidth()} 返回 {@code true}, {@link #getSizeInDp} 则应该返回设计图的总宽度
     * 如果 {@link #isBaseOnWidth()} 返回 {@code false}, {@link #getSizeInDp} 则应该返回设计图的总高度
     * 如果您不需要自定义设计图上的设计尺寸, 想继续使用在 AndroidManifest 中填写的设计图尺寸, {@link #getSizeInDp} 则返回 {@code 0}
     *
     * @return 设计图上的设计尺寸, 单位 dp
     */
    override fun getSizeInDp(): Float {
        return 1080f
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