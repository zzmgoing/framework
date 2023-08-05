package com.zzming.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zzming.core.room.dao.JsonDao
import com.zzming.core.room.table.Json

/**
 * @author ZhongZiMing
 * @time 2020/11/26
 * @description AppDatabase
 **/
@Database(entities = [Json::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jsonDao(): JsonDao
}