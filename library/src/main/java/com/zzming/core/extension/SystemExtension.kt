package com.zzming.core.extension

/**
 * 是否是鸿蒙系统
 */
fun isHarmonyOs(): Boolean {
    return try {
        val buildExClass = Class.forName("com.huawei.system.BuildEx")
        val osBrand = buildExClass.getMethod("getOsBrand").invoke(buildExClass)
        "Harmony".equals(osBrand?.toString(), ignoreCase = true) ||
                "emui".equals(osBrand?.toString(), ignoreCase = true)
    } catch (e: Throwable) {
        false
    }
}