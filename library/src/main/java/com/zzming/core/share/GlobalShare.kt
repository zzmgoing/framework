package com.zzming.core.share

import android.content.ComponentName
import android.content.Intent
import com.zzming.core.LibCore
import com.zzming.core.R
import com.zzming.core.collector.ActivityCollector
import com.zzming.core.common.LibConfig
import com.zzming.core.extension.appContext
import com.zzming.core.utils.FileUtil
import java.io.File

/**
 * @author MackZhong
 * @time 2023/2/9
 * @description
 **/
object GlobalShare {

    private const val FACEBOOK_STORY_ACTION = "com.facebook.stories.ADD_TO_STORY"

    private const val INSTAGRAM_STORY_ACTION = "com.instagram.share.ADD_TO_STORY"

    private const val FACEBOOK_APP_ID_KEY = "com.facebook.platform.extra.APPLICATION_ID"

    private const val INSTAGRAM_APP_ID_KEY = "source_application"

    private const val ZALO_ACTIVITY_NAME = "com.zing.zalo.ui.TempShareViaActivity"

    fun share(obj: ShareObject) {
        when (obj.platform) {
            SharePlatform.FacebookStory -> {
                if (obj.type == ShareType.Sticker) {
                    shareIntent(obj, FACEBOOK_STORY_ACTION) {
                        shareSticker(it, obj)
                    }
                } else {
                    shareIntent(obj, FACEBOOK_STORY_ACTION) {
                        it.putExtra(FACEBOOK_APP_ID_KEY, LibConfig.FACEBOOK_APP_ID)
                    }
                }
            }
            SharePlatform.InstagramStory -> {
                if (obj.type == ShareType.Sticker) {
                    shareIntent(obj, INSTAGRAM_STORY_ACTION) {
                        shareSticker(it, obj)
                    }
                } else {
                    shareIntent(obj, INSTAGRAM_STORY_ACTION) {
                        it.putExtra(INSTAGRAM_APP_ID_KEY, LibConfig.FACEBOOK_APP_ID)
                    }
                }
            }
            SharePlatform.Zalo, SharePlatform.ZaloFeed -> {
                shareIntent(obj) {
                    it.component = ComponentName(SharePlatform.Zalo.packageName, ZALO_ACTIVITY_NAME)
                    if (obj.platform == SharePlatform.ZaloFeed) {
                        if (!obj.url.isNullOrEmpty()) {
                            it.putExtra(Intent.EXTRA_TEXT, obj.url)
                            it.putExtra(Intent.EXTRA_SUBJECT, obj.message)
                        }
                        it.putExtra("postFeed", true)
                    } else {
                        it.putExtra("hidePostFeed", true)
                    }
                    it.putExtra("token", System.currentTimeMillis().toString())
                    it.putExtra("backToSource", true)
                }
            }
            else -> shareIntent(obj)
        }
    }

    private fun shareIntent(obj: ShareObject, action: String? = null, config: ((Intent) -> Unit)? = null) {
        try {
            if (obj.type == ShareType.Link) {
                if (obj.content.isNullOrEmpty()) {
                    return
                }
            } else if (obj.type == ShareType.Sticker) {
                if (obj.stickerFilePath.isNullOrEmpty() || !File(obj.stickerFilePath!!).exists()) {
                    return
                }
                if (obj.stickerBackgroundColor.isNullOrEmpty()) {
                    return
                }
            } else {
                if (obj.filePath.isNullOrEmpty() || !File(obj.filePath!!).exists()) {
                    return
                }
            }
            val share = Intent(action ?: Intent.ACTION_SEND)
            share.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            if (obj.type == ShareType.Link) {
                share.type = obj.type.type
                share.putExtra(Intent.EXTRA_TEXT, obj.content)
            } else if (obj.type != ShareType.Sticker) {
                val uri = FileUtil.file2Uri(File(obj.filePath!!))
                share.setDataAndType(uri, obj.type.type)
                share.putExtra(Intent.EXTRA_STREAM, uri)
            }
            config?.invoke(share)
            val activity = ActivityCollector.weakRefActivity?.get() ?: return
            if (obj.platform == SharePlatform.System || obj.platform == SharePlatform.Copy) {
                val shareName = LibCore.context.getString(R.string.share_to)
                activity.startActivity(Intent.createChooser(share, shareName))
            } else if (activity.packageManager.resolveActivity(share, 0) != null) {
                if (obj.platform.packageName.isNotEmpty()) {
                    share.setPackage(obj.platform.packageName)
                }
                activity.startActivityForResult(share, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun shareSticker(it: Intent, obj: ShareObject) {
        it.type = obj.type.type
        if (obj.platform == SharePlatform.FacebookStory) {
            it.putExtra(FACEBOOK_APP_ID_KEY, LibConfig.FACEBOOK_APP_ID)
        } else {
            it.putExtra(INSTAGRAM_APP_ID_KEY, LibConfig.FACEBOOK_APP_ID)
        }
        val stickerAssetUri = FileUtil.file2Uri(File(obj.stickerFilePath!!))
        it.putExtra("interactive_asset_uri", stickerAssetUri)
        it.putExtra("top_background_color", obj.stickerBackgroundColor)
        it.putExtra("bottom_background_color", obj.stickerBackgroundColor)
        val activity = ActivityCollector.weakRefActivity?.get() ?: return
        activity.grantUriPermission(obj.platform.packageName, stickerAssetUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
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