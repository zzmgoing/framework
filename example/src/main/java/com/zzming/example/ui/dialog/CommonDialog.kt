package com.zzming.example.ui.dialog

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.zzming.core.collector.ActivityCollector
import com.zzming.core.extension.localized
import com.zzming.core.utils.APPUtils
import com.zzming.core.utils.SPUtils
import com.zzming.example.ExampleApplication
import com.zzming.example.R
import java.util.*

/**
 * @author ZhongWei
 * @time 2020/8/5 16:13
 * @description
 **/
object CommonDialog {

    /**
     * 切换语言
     */
    fun showLanguage(activity: Activity) {
        AlertDialog.Builder(activity).apply {
            setTitle(activity.getString(R.string.international_testing))
            setMessage(R.string.international_testing.localized(activity))
            setPositiveButton(R.string.change_language_chinese.localized(activity)) { _, _ ->
                APPUtils.changLanguage(ExampleApplication.context,Locale.SIMPLIFIED_CHINESE)
                ActivityCollector.recreateAll()
            }
            setNegativeButton(R.string.change_language_english.localized(activity)) { _, _ ->
                APPUtils.changLanguage(ExampleApplication.context,Locale.ENGLISH)
                ActivityCollector.recreateAll()
            }
        }.create().show()
    }

}