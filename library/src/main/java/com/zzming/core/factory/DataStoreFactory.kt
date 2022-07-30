package com.zzming.core.factory

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.zzming.core.LibCore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author ZhongZiMing
 * @time 2022/5/14
 * @description Preferences DataStore
 **/
class DataStoreFactory(storeName: String) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = storeName)

    suspend fun putString(key: String, value: String) {
        LibCore.context.dataStore.edit { it[stringPreferencesKey(key)] = value }
    }

    suspend fun putInt(key: String, value: Int) {
        LibCore.context.dataStore.edit { it[intPreferencesKey(key)] = value }
    }

    suspend fun putLong(key: String, value: Long) {
        LibCore.context.dataStore.edit { it[longPreferencesKey(key)] = value }
    }

    suspend fun putBoolean(key: String, value: Boolean) {
        LibCore.context.dataStore.edit { it[booleanPreferencesKey(key)] = value }
    }

    fun getString(key: String): Flow<String> {
        return LibCore.context.dataStore.data.map { it[stringPreferencesKey(key)] ?: "" }
    }

    fun getInt(key: String): Flow<Int> {
        return LibCore.context.dataStore.data.map { it[intPreferencesKey(key)] ?: 0 }
    }

    fun getLong(key: String): Flow<Long> {
        return LibCore.context.dataStore.data.map { it[longPreferencesKey(key)] ?: 0 }
    }

    fun getBoolean(key: String): Flow<Boolean> {
        return LibCore.context.dataStore.data.map { it[booleanPreferencesKey(key)] ?: false }
    }
}