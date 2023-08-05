package com.zzming.core.share

/**
 * @author MackZhong
 * @time 2023/2/9
 * @description
 **/
enum class SharePlatform(val packageName: String) {

    Facebook("com.facebook.katana"),

    FacebookStory("com.facebook.katana"),

    Instagram("com.instagram.android"),

    InstagramStory("com.instagram.android"),

    Tiktok("com.ss.android.ugc.trill"),

    Messenger("com.facebook.orca"),

    Zalo("com.zing.zalo"),

    ZaloFeed("com.zing.zalo"),

    Telegram("org.telegram.messenger"),

    Copy(""),

    System("");

}