package com.zzming.core.share

import android.util.Patterns
import java.util.regex.Matcher

/**
 * @author MackZhong
 * @time 2023/2/9
 * @description
 **/
class ShareObject(val platform: SharePlatform, val type: ShareType) {

    var url: String? = null

    var message: String? = null

    var filePath: String? = null

    var stickerFilePath: String? = null

    var stickerBackgroundColor: String? = null

    var content: String? = null
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) {
                val matcher: Matcher = Patterns.WEB_URL.matcher(value)
                if (matcher.find()) {
                    val oldUrl = matcher.group()
//                    url = addShareParams(oldUrl)
                    url = oldUrl
                    message = value.replace(oldUrl, "")
                    url?.let { field = value.replace(oldUrl, it) }
                }
            }
        }

    fun share() {
        GlobalShare.share(this)
    }

    var name: String? = null

    var iconRes: Int? = null

    val id: Int
        get() {
            return when(platform) {
                SharePlatform.Facebook -> 0
                SharePlatform.FacebookStory -> {
                    if (type == ShareType.Video) 6 else 7
                }
                SharePlatform.Instagram -> 1
                SharePlatform.InstagramStory -> {
                    if (type == ShareType.Video) 8 else 9
                }
                SharePlatform.Tiktok -> 2
                SharePlatform.Messenger -> 3
                SharePlatform.Zalo -> 4
                SharePlatform.ZaloFeed -> 10
                SharePlatform.Telegram -> 5
                SharePlatform.Copy -> 11
                SharePlatform.System -> 12
                else -> -2
            }
        }

//    private fun addShareParams(url: String?): String? {
//        if (url.isNullOrEmpty() || !url.startsWith("http")) {
//            return url
//        }
//        val builder = Uri.parse(url).buildUpon()
//        val value = when(platform) {
//            SharePlatform.Facebook -> "fb"
//            SharePlatform.Instagram -> "instagram"
//            SharePlatform.Tiktok -> "tiktok"
//            SharePlatform.Messenger -> "messenger"
//            SharePlatform.Zalo -> "zalo"
//            SharePlatform.ZaloFeed -> "zaloActivity"
//            SharePlatform.Telegram -> "telegram"
//            SharePlatform.Copy -> "copy"
//            SharePlatform.System -> "other"
//            else -> "other"
//        }
//        if (!url.contains("from=share")) {
//            builder.appendQueryParameter("from", "share")
//        }
//        builder.appendQueryParameter("share_dest", value)
//        return builder.build().toString()
//    }

}