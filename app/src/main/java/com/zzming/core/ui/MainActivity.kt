package com.zzming.core.ui

import android.content.Intent
import com.zzming.core.R
import com.zzming.core.base.BaseActivity
import com.zzming.core.base.BaseH5Activity
import kotlinx.android.synthetic.main.core_activity_main.*

/**
 * @author ZhongWei
 * @time 2020/7/8 18:34
 * @description
 **/
class MainActivity: BaseActivity() {
    override fun initContentView() {
        setContentView(R.layout.core_activity_main)
    }

    override fun initView() {
        main_h5.setOnClickListener { startActivity(Intent(this,
            BaseH5Activity::class.java)) }
    }
}