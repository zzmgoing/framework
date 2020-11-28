package com.zzming.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zzming.core.room.dao.ConfigDao
import com.zzming.core.room.table.Config

/**
 * @author ZhongZiMing
 * @time 2020/11/26
 * @description AppDatabase
 **/
@Database(entities = [Config::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun configDao(): ConfigDao
}