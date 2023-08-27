package com.zzming.core.share

import android.content.Intent
import android.net.Uri
import android.view.View
import com.zzming.core.base.BaseActivity
import com.zzming.core.collector.ActivityCollector
import com.zzming.core.extension.appContext
import com.zzming.core.utils.FileUtil
import com.zzming.core.utils.PermissionUtils

/**
 * @author MackZhong
 * @time 2023/2/9
 * @description
 **/
object GlobalShare {

    fun shareText(text: String, title: String = "分享到") {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.type = "text/plain"
        val activity = ActivityCollector.weakRefActivity?.get()
        activity?.startActivity(Intent.createChooser(shareIntent, title))
    }

    fun shareImage(uri: Uri, title: String = "分享到") {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.type = "image/*"
        val activity = ActivityCollector.weakRefActivity?.get()
        activity?.startActivity(Intent.createChooser(shareIntent, title))
    }

    fun shareViewToImage(view: View, result: (Uri?) -> Unit) {
        val activity = ActivityCollector.weakRefActivity?.get()
        if (activity is BaseActivity) {
            activity.requestPermission(PermissionUtils.getImagePermission()) {
                if (it) {
                    FileUtil.view2Bitmap(view)?.let { bitmap ->
                        FileUtil.saveImage(bitmap, result)
                    }
                }
            }
        }
    }

    fun checkAppInstall(packageName: String?): Boolean {
        if (packageName.isNullOrEmpty()) {
            return false
        }
        return try {
            appContext.packageManager.getPackageInfo(packageName, 0) != null
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}