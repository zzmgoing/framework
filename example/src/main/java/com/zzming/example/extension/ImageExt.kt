package com.zzming.example.extension

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.zzming.core.extension.logDebug
import com.zzming.example.R
import com.zzming.example.util.GlideApp

/**
 * @author MackZhong
 * @time 2022/8/9
 * @description
 **/
fun RecyclerView?.enablePreLoader() {
    if (this == null || adapter == null || adapter !is GlidePreLoader<*>) {
        return
    }
    val loaderTag = getTag(R.id.recycler_pre_loader_enable)
    if (loaderTag != null && loaderTag is RecyclerViewPreloader<*>) {
        removeOnScrollListener(loaderTag)
    }
    val preAdapter = adapter as GlidePreLoader<*>
    val sizeProvider = ViewPreloadSizeProvider<String>()
    val preLoader = RecyclerViewPreloader(
        GlideApp.with(context),
        AppPreloadModelProvider(this, preAdapter, sizeProvider),
        sizeProvider,
        10
    )
    addOnScrollListener(preLoader)
    setTag(R.id.recycler_pre_loader_enable, preLoader)
    setItemViewCacheSize(0)
    addRecyclerListener { holder ->
        holder.itemView.findViewById<View>(preAdapter.getPreLoadImageViewId())?.let {
            logDebug("glide", "获取到imageview=${it is ImageView},清除")
            GlideApp.with(context).clear(it)
        }
    }
}