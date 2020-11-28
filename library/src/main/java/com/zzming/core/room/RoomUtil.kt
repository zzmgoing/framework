package com.zzming.core.room

/**
 * @author ZhongZiMing
 * @time 2020/11/26
 * @description
 **/
object RoomUtil {

    fun setConfig(key: String, value: String) {
        RoomAgent.configDao.insertOrUpdate(key, value)
    }

    fun getStringConfig(key: String): String? {
        return RoomAgent.configDao.findByKey(key)?.value
    }

    fun getBooleanConfig(key: String): Boolean? {
        return getStringConfig(key)?.toBoolean()
    }

    fun getIntConfig(key: String): Int? {
        return getStringConfig(key)?.toInt()
    }

    fun getLongConfig(key: String): Long? {
        return getStringConfig(key)?.toLong()
    }

}