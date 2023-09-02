package com.zzming.core.room.dao

import androidx.room.*
import com.zzming.core.room.table.Json

/**
 * @author ZhongZiMing
 * @time 2020/11/26
 * @description ConfigDao
 **/
@Dao
interface JsonDao {

    @Query("SELECT * FROM json")
    fun getAll(): List<Json>

    @Query("SELECT * FROM json WHERE `key` ==:key")
    fun findByKey(key: String): Json?

    @Insert
    fun insert(vararg json: Json)

    @Update
    fun update(json: Json)

    @Delete
    fun delete(json: Json)

    @Query("DELETE FROM json WHERE `key` ==:key")
    fun deleteByKey(key: String)

    @Query("REPLACE INTO json(`key`,`value`) VALUES(:key,:value)")
    fun insertOrUpdate(key: String, value: String)

}