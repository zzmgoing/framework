package com.zzming.example.ui.dialog

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.zzming.core.LibCore
import com.zzming.core.collector.ActivityCollector
import com.zzming.core.extension.localized
import com.zzming.core.utils.APPUtils
import com.zzming.core.utils.CorePreferencesUtils
import com.zzming.example.LanguageConfig
import com.zzming.example.R
import java.util.*

/**
 * @author ZhongWei
 * @time 2020/8/5 16:13
 * @description 切换语言
 **/
object CommonDialog {

    /**
     * 切换语言
     */
    fun showLanguage(activity: Activity) {
        AlertDialog.Builder(activity).apply {
            setTitle(activity.getString(R.string.international_testing))
            setMessage(LanguageConfig.INTERNATIONAL_TESTING.localized(activity))
            setPositiveButton(LanguageConfig.CHANGE_LANGUAGE_CHINESE.localized(activity)) { _, _ ->
                if(CorePreferencesUtils.saveLocale(Locale.SIMPLIFIED_CHINESE)){
                    ActivityCollector.INSTANCE.reCreateAll()
                }
            }
            setNegativeButton(LanguageConfig.CHANGE_LANGUAGE_ENGLISH.localized(activity)) { _, _ ->
                if(CorePreferencesUtils.saveLocale(Locale.ENGLISH)){
                    ActivityCollector.INSTANCE.reCreateAll()
                }
            }
        }.create().show()
    }

}