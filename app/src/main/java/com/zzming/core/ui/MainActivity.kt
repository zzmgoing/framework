package com.zzming.core.ui

import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.zzming.core.R
import com.zzming.core.base.BaseActivity
import com.zzming.core.databinding.CoreActivityMainBinding
import com.zzming.core.utils.LanguageUtil
import kotlinx.android.synthetic.main.core_activity_main.*

/**
 * @author ZhongWei
 * @time 2020/7/8 18:34
 * @description
 **/
class MainActivity: BaseActivity() {
    override fun initContentView() {
       val viewBinding = DataBindingUtil.setContentView<CoreActivityMainBinding>(
            this,
            R.layout.core_activity_main
        )
        viewBinding.language = LanguageUtil.language.value
    }

    override fun initView() {
        hello.setOnClickListener { startActivity(Intent(this,Test1Activity::class.java)) }
    }
}