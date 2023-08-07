package com.zzming.example.util

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.zzming.core.extension.appContext
import com.zzming.example.bean.PlayListBean

/**
 * @author MackZhong
 * @time 2023/8/7
 * @description
 **/
object GlobalExoPlayer {

    val player by lazy {
        ExoPlayer.Builder(appContext)
            .setMediaSourceFactory(DefaultMediaSourceFactory(appContext).setLiveTargetOffsetMs(5000))
            .build()
    }

    var currentPlaylist: MutableList<PlayListBean>? = null

    fun playPlaylist(list: MutableList<PlayListBean>) {

    }


    fun clearPlaylist() {
        currentPlaylist?.let {
            player.removeMediaItems(0, it.size)
        }
        currentPlaylist = null
    }

    fun playListMedia(list: MutableList<PlayListBean>): MutableList<MediaItem> {
        return list.map {
            MediaItem.Builder()
                .setMediaId(it.key)
                .setCustomCacheKey(it.key)
                .setUri(it.url)
                .build()
        }.toMutableList()
    }

}