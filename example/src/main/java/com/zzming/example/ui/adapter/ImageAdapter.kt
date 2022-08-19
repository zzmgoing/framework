package com.zzming.example.ui.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zzming.core.collector.ActivityCollector
import com.zzming.example.R
import com.zzming.example.bean.ImageBean
import com.zzming.example.extension.GlidePreLoader
import com.zzming.example.listpreloader.widget.GlideRoundTransform

/**
 * @author ZhongZiMing
 * @time 2022/5/18
 * @description
 **/
class ImageAdapter(private val mContext: Context) :
    BaseQuickAdapter<ImageBean, BaseViewHolder>(R.layout.item_image), GlidePreLoader<ImageBean> {
    override fun convert(holder: BaseViewHolder, item: ImageBean) {
        val imageView = holder.getView<ImageView>(R.id.image)
//        val act = ActivityCollector.weakRefActivity?.get()
//        val option = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//        Glide.with(act!!).load(item.url).apply(option).into(imageView)

        val requestOptions = RequestOptions()
            .transform(GlideRoundTransform(mContext, 5))
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        Glide.with(mContext)
            .load(item.url)
            .apply(requestOptions)
            .into(imageView)
    }

    override fun getPreLoadImageViewId(): Int {
        return R.id.image
    }

    override fun getPreLoadData(): MutableList<ImageBean> {
        return data
    }

    override fun getPreLoadImageUrl(t: ImageBean): String {
        return t.url
    }

}