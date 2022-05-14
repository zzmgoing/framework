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

    companion object {
        const val DEFAULT_DATA_STORE_NAME = "zzm_core_library_ds"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = storeName)

    fun putString(key: String, value: String, context: Context = LibCore.context) {
        GlobalScope.launch { context.dataStore.edit { it[stringPreferencesKey(key)] = value } }
    }

    fun putInt(key: String, value: Int, context: Context = LibCore.context) {
        GlobalScope.launch { context.dataStore.edit { it[intPreferencesKey(key)] = value } }
    }

    fun putLong(key: String, value: Long, context: Context = LibCore.context) {
        GlobalScope.launch { context.dataStore.edit { it[longPreferencesKey(key)] = value } }
    }

    fun putBoolean(key: String, value: Boolean, context: Context = LibCore.context) {
        GlobalScope.launch { context.dataStore.edit { it[booleanPreferencesKey(key)] = value } }
    }

    fun getString(key: String, context: Context = LibCore.context): Flow<String> {
        return context.dataStore.data.map { it[stringPreferencesKey(key)] ?: "" }
    }

    fun getInt(key: String, context: Context = LibCore.context): Flow<Int> {
        return context.dataStore.data.map { it[intPreferencesKey(key)] ?: 0 }
    }

    fun getLong(key: String, context: Context = LibCore.context): Flow<Long> {
        return context.dataStore.data.map { it[longPreferencesKey(key)] ?: 0 }
    }

    fun getBoolean(key: String, context: Context = LibCore.context): Flow<Boolean> {
        return context.dataStore.data.map { it[booleanPreferencesKey(key)] ?: false }
    }
}