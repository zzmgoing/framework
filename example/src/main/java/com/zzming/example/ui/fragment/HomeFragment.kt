package com.zzming.example.ui.fragment

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.zzming.core.base.BaseFragment
import com.zzming.example.Language
import com.zzming.example.LanguageLib
import com.zzming.example.LanguageType
import com.zzming.example.R
import com.zzming.example.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class HomeFragment: BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onCreateView(view: View): View {
        val bind = DataBindingUtil.bind<FragmentHomeBinding>(view)!!
        LanguageLib.bindBinding(this,bind)
        return bind.root
    }

    override fun initView() {
        test_language.setOnClickListener {
            AlertDialog.Builder(activity!!).apply {
                setTitle(LanguageLib.find(Language.INTERNATIONAL_TESTING))
                setMessage(LanguageLib.find(Language.INTERNATIONAL_TESTING))
                setPositiveButton(LanguageLib.find(Language.CHANGE_LANGUAGE_CHINESE)) { _, _ ->
                    LanguageLib.changeLanguage(LanguageType.CHINESE)
                }
                setNegativeButton(LanguageLib.find(Language.CHANGE_LANGUAGE_ENGLISH)) { _, _ ->
                    LanguageLib.changeLanguage(LanguageType.ENGLISH)
                }
            }.create().show()
        }
    }

}