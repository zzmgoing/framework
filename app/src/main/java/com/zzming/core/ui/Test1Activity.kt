package com.zzming.core.ui

import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.zzming.core.R
import com.zzming.core.base.BaseActivity
import com.zzming.core.databinding.CoreActivityTest1Binding
import com.zzming.core.utils.LanguageUtil
import kotlinx.android.synthetic.main.core_activity_test1.*

/**
 * @author ZhongWei
 * @time 2020/7/8 18:34
 * @description
 **/
class Test1Activity: BaseActivity() {
    override fun initContentView() {
        DataBindingUtil.setContentView<CoreActivityTest1Binding>(this,R.layout.core_activity_test1).language = LanguageUtil.language
    }

    override fun initView() {
        hello1.setOnClickListener { startActivity(Intent(this,Test2Activity::class.java)) }
    }
}