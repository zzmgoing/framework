package com.zzming.core.ui

import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.zzming.core.R
import com.zzming.core.base.BaseActivity
import com.zzming.core.databinding.CoreActivityTest2Binding
import com.zzming.core.utils.LanguageUtil
import kotlinx.android.synthetic.main.core_activity_test2.*

/**
 * @author ZhongWei
 * @time 2020/7/8 18:34
 * @description
 **/
class Test2Activity: BaseActivity() {
    override fun initContentView() {
        DataBindingUtil.setContentView<CoreActivityTest2Binding>(this,R.layout.core_activity_test2).language = LanguageUtil.language
    }

    override fun initView() {
        hello2.setOnClickListener { startActivity(Intent(this,Test3Activity::class.java)) }
    }

    override fun onResume() {
        super.onResume()

    }
}