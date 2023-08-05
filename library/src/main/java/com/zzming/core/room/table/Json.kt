package com.zzming.core.room.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author ZhongZiMing
 * @time 2020/11/26
 * @description 应用信息表
 **/
@Entity
data class Json(
    @PrimaryKey val key: String,
    @ColumnInfo(name = "value") val value: String
)