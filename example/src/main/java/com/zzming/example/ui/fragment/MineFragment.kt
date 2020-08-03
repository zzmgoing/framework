package com.zzming.example.ui.fragment

import android.view.View
import androidx.databinding.DataBindingUtil
import com.zzming.core.base.BaseFragment
import com.zzming.example.LanguageLib
import com.zzming.example.R
import com.zzming.example.databinding.FragmentMineBinding

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class MineFragment: BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun onCreateView(view: View): View {
        val bind = DataBindingUtil.bind<FragmentMineBinding>(view)!!
        LanguageLib.bindBinding(this,bind)
        return bind.root
    }

    override fun initView() {
    }

}