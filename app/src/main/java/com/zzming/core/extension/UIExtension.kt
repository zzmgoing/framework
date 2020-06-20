package com.zzming.core.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zzming.core.LibCore


/**
 *  Activity tag
 */
val Activity.A_TAG: String
    get() {
        return javaClass.name
    }

/**
 * Fragment tag
 */
val Fragment.F_TAG: String
    get() {
        return javaClass.name
    }

/**
 * 在主线程上执行操作
 */
fun runOnMainThread(runnable: () -> Unit) {
    LibCore.handler?.post(runnable)
}

/**
 * 显示土司
 */
fun showToast(msg: String){
    if("main" == Thread.currentThread().name){
        Toast.makeText(LibCore.context,msg,Toast.LENGTH_SHORT).show()
    }else{
        runOnMainThread {
            Toast.makeText(LibCore.context,msg,Toast.LENGTH_SHORT).show()
        }
    }
}

/**
 * 设置LayoutManager
 */
fun RecyclerView.bindLinearLayoutManager(context: Context) {
    val layoutManager = LinearLayoutManager(context)
    layoutManager.orientation = LinearLayoutManager.VERTICAL
    this.layoutManager = layoutManager
}