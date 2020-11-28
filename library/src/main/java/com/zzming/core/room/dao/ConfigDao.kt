package com.zzming.core.room.dao

import androidx.room.*
import com.zzming.core.room.table.Config

/**
 * @author ZhongZiMing
 * @time 2020/11/26
 * @description ConfigDao
 **/
@Dao
interface ConfigDao {

    @Query("SELECT * FROM config")
    fun getAll(): List<Config>

    @Query("SELECT * FROM config WHERE `key` ==:key")
    fun findByKey(key: String): Config?

    @Insert
    fun insert(vararg config: Config)

    @Update
    fun update(config: Config)

    @Delete
    fun delete(config: Config)

    @Query("REPLACE INTO config(`key`,`value`) VALUES(:key,:value)")
    fun insertOrUpdate(key: String, value: String)

}