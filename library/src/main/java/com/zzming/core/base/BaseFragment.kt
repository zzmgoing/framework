package com.zzming.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * @author ZhongZiMing
 * @time 2020/6/8 17:02
 * @description Fragment基类
 **/
abstract class BaseFragment : Fragment(), Observer<AnyEvent> {

    private var isLoadData = false

    val baseActivity: BaseActivity?
        get() = activity as? BaseActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createContentView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLoadData = false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(object : DefaultLifecycleObserver{
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                onActivityCreated()
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    open fun onActivityCreated() {
        LiveEventBus
            .get(AnyEvent::class.java)
            .observe(this, this)
    }

    override fun onResume() {
        super.onResume()
        if (!isLoadData) {
            lazyInit()
            isLoadData = true
        }
    }

    abstract fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View

    open fun lazyInit() {
    }

    override fun onChanged(t: AnyEvent?) {

    }

}