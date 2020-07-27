package com.zzming.core.utils

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.zzming.core.BR
import com.zzming.core.Language
import com.zzming.core.LibCore
import org.json.JSONObject
import kotlin.collections.HashMap

/**
 * @author ZhongWei
 * @time 2020/7/24 19:11
 * @description 切换语言工具类
 **/
object LanguageUtil {

    /**
     * 当前语言包
     */
    @JvmField
    val language: MutableLiveData<Language> = MutableLiveData()

    /**
     * 所有语言包
     */
    private val languageMap: HashMap<LanguageType, Language> = HashMap()

    /**
     * 语言类型
     */
    enum class LanguageType(val key: String) {
        CHINESE("values_zh"), ENGLISH("values")
    }

    /**
     * 当前选中语言类型
     */
    var currentType: LanguageType = LanguageType.CHINESE

    /**
     * 初始化语言包
     */
    fun init() {
        loadLanguageJson()
    }

    private fun loadLanguageJson() {
        try {
            val json = LibCore.context.resources.assets.open("language.json").bufferedReader().use {
                it.readText()
            }
            val jsonObject = JSONObject(json)

            val language = Gson().fromJson(json, Language::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 选择语言
     */
    fun changeLanguage(type: LanguageType, viewBinding: ViewDataBinding? = null) {
        if (currentType == type) {
            return
        }
        val language = languageMap[type]
        this.language.postValue(language)
        this.currentType = type
        viewBinding?.setVariable(BR.language, language)
    }

}