package com.zzming.core.factory

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.zzming.core.LibCore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * @author ZhongZiMing
 * @time 2022/5/14
 * @description Preferences DataStore
 **/
@OptIn(DelicateCoroutinesApi::class)
class DataStoreFactory(storeName: String) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = storeName)

    fun putString(key: String, value: String) {
        GlobalScope.launch { LibCore.context.dataStore.edit { it[stringPreferencesKey(key)] = value } }
    }

    fun putInt(key: String, value: Int) {
        GlobalScope.launch { LibCore.context.dataStore.edit { it[intPreferencesKey(key)] = value } }
    }

    fun putLong(key: String, value: Long) {
        GlobalScope.launch { LibCore.context.dataStore.edit { it[longPreferencesKey(key)] = value } }
    }

    fun putBoolean(key: String, value: Boolean) {
        GlobalScope.launch { LibCore.context.dataStore.edit { it[booleanPreferencesKey(key)] = value } }
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