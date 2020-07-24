package com.zzming.core.ui

import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import com.zzming.core.R
import com.zzming.core.base.BaseActivity
import com.zzming.core.databinding.CoreActivityTest3Binding
import com.zzming.core.utils.LanguageUtil
import kotlinx.android.synthetic.main.core_activity_test3.*
import java.util.*

/**
 * @author ZhongWei
 * @time 2020/7/8 18:34
 * @description
 **/
class Test3Activity: BaseActivity() {
    lateinit var contentView: CoreActivityTest3Binding
    override fun initContentView() {
        contentView = DataBindingUtil.setContentView<CoreActivityTest3Binding>(
            this,
            R.layout.core_activity_test3
        )
        contentView.language = LanguageUtil.language
        contentView.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {

            }
        })
    }

    override fun initView() {
        hello3.setOnClickListener {
            val lang = if(LanguageUtil.localType == Locale.SIMPLIFIED_CHINESE){
                "英文"
            }else{
                "中文"
            }
            LanguageUtil.changeLanguage(Locale.ENGLISH)
//            contentView.language = LanguageUtil.language
            contentView.notifyChange()
            Toast.makeText(this,"语言环境已切换为$lang",Toast.LENGTH_SHORT).show()
        }
    }
}