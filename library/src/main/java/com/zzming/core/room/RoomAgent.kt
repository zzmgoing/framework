package com.zzming.core.room

import androidx.room.Room
import com.zzming.core.LibCore

/**
 * @author ZhongZiMing
 * @time 2020/11/26
 * @description
 **/
object RoomAgent {

    /**
     * 数据库名
     */
    private const val DB_NAME = "lib_sdk_db"

    /**
     * 数据库
     */
    private val db by lazy {
        Room.databaseBuilder(
            LibCore.context, AppDatabase::class.java, DB_NAME
        ).allowMainThreadQueries().build()
    }

    /**
     * configDao
     */
    val configDao by lazy { db.configDao() }

}