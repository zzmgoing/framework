package com.zzming.core.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * AppCompatActivity协程
 */
fun AppCompatActivity.launch(
    block: suspend (CoroutineScope) -> Unit,
    context: CoroutineContext = Dispatchers.Main
): Job {
    return lifecycleScope.launch(context + CoroutineExceptionHandler { _, e ->
        logError(e.message, e)
    }) {
        try {
            block(this)
        } catch (e: Exception) {
            logError(e.message, e)
        }
    }
}

/**
 * Fragment协程
 */
fun Fragment.launch(
    block: suspend (CoroutineScope) -> Unit,
    context: CoroutineContext = Dispatchers.Main
): Job {
    return lifecycleScope.launch(context + CoroutineExceptionHandler { _, e ->
        logError(e.message, e)
    }) {
        try {
            block(this)
        } catch (e: Exception) {
            logError(e.message, e)
        }
    }
}

/**
 * 全局协程
 */
fun launch(
    block: suspend (CoroutineScope) -> Unit,
    context: CoroutineContext = Dispatchers.Main
): Job {
    return GlobalScope.launch(context + CoroutineExceptionHandler { _, e ->
        logError(e.message, e)
    }) {
        try {
            block(this)
        } catch (e: Exception) {
            logError(e.message, e)
        }
    }
}
