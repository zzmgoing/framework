package com.zzming.example.extension

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.zzming.core.extension.logDebug
import com.zzming.example.util.GlideApp

/**
 * @author MackZhong
 * @time 2022/8/8
 * @description
 **/
interface GlidePreLoader<T> {

    fun getPreLoadImageViewId(): Int

    fun getPreLoadData(): MutableList<T>

    fun getPreLoadImageUrl(t: T): String

}

class AppPreloadModelProvider<T>(
    private val recyclerView: RecyclerView,
    private val preLoader: GlidePreLoader<T>,
    private val sizeProvider: ViewPreloadSizeProvider<String>
) : ListPreloader.PreloadModelProvider<String> {

    private val imageSizeArray = intArrayOf(0, 0)

    override fun getPreloadItems(position: Int): MutableList<String> {
        getImageViewSize()
        val data = preLoader.getPreLoadData()
        val size = if (data.size - position > 10) position + 10 else data.size
        return data.subList(position, size).map { preLoader.getPreLoadImageUrl(it) }.toMutableList()
    }

    override fun getPreloadRequestBuilder(item: String): RequestBuilder<*> {
        logDebug("glide", "预加载imageview==width=${imageSizeArray[0]}height=${imageSizeArray[1]}")
        return GlideApp.with(recyclerView.context).load(item).override(imageSizeArray[0], imageSizeArray[1])
    }

    private fun getImageViewSize() {
        if (imageSizeArray[0] != 0 && imageSizeArray[1] != 0) {
            return
        }
        recyclerView.findViewHolderForAdapterPosition(0)?.apply {
            itemView.findViewById<View>(preLoader.getPreLoadImageViewId())?.let {
                sizeProvider.setView(it)
                imageSizeArray[0] = it.width
                imageSizeArray[1] = it.height
                logDebug("glide", "获取到imageview==width=${imageSizeArray[0]}height=${imageSizeArray[1]}")
            }
        }
    }
}