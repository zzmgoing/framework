package com.zzming.core.share

/**
 * @author MackZhong
 * @time 2023/2/9
 * @description
 **/
enum class ShareType(val type: String) {

    Link("text/plain"),

    Video("video/*"),

    Image("image/*"),

    Sticker("image/png");
}