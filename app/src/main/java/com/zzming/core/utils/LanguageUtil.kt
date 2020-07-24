package com.zzming.core.utils

import androidx.databinding.ViewDataBinding
import com.zzming.core.BR
import com.zzming.core.bean.Language
import java.util.*

/**
 * @author ZhongWei
 * @time 2020/7/24 19:11
 * @description
 **/
object LanguageUtil {

    /**
     * 当前语言
     */
    var language: Language  = initZH()

    /**
     * 中文
     */
    private val zhLanguage: Language by lazy { initZH() }

    /**
     * 英文
     */
    private val enLanguage: Language by lazy { initEN() }

    /**
     * 当前选中语言类型
     */
    var localType: Locale = Locale.SIMPLIFIED_CHINESE

    init {
        language = zhLanguage
    }

    /**
     * 初始化中文
     */
    private fun initZH(): Language {
        val zhLanguage = Language()
        zhLanguage.hello = "嘿"
        zhLanguage.world = "哈哈"
        return zhLanguage
    }

    /**
     * 初始化英文
     */
    private fun initEN(): Language {
        val enLanguage = Language()
        enLanguage.hello = "hello"
        enLanguage.world = "world"
        return enLanguage
    }

    /**
     * 选择语言
     */
    fun changeLanguage(locale: Locale,viewBinding: ViewDataBinding? = null) {
        if (localType == locale) {
            return
        }
        language = when (locale) {
            Locale.SIMPLIFIED_CHINESE -> zhLanguage
            Locale.ENGLISH -> enLanguage
            else -> zhLanguage
        }
        localType = locale
        viewBinding?.setVariable(BR.language,language)
    }

//    fun getString(local: Locale) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            LibCore.context.resources.configuration.setLocale(local)
//        } else {
//            LibCore.context.resources.configuration.locale = local
//        }
//    }

}