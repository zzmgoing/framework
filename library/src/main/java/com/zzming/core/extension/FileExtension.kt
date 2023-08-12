package com.zzming.core.extension

import com.zzming.core.LibCore
import java.io.File

/**
 * @author MackZhong
 * @time 2023/8/12
 * @description
 **/
fun libImageDir(): String {
    val dir = LibCore.context.getExternalFilesDir("lib_core_image")
    if (dir != null) {
        return dir.absolutePath
    }
    val cacheDir = File(LibCore.context.cacheDir, "lib_core_image")
    if (!cacheDir.exists()) {
        cacheDir.mkdirs()
    }
    return cacheDir.absolutePath
}