package com.zzming.example.ui.music

import android.os.Bundle
import androidx.media3.common.MediaItem
import com.zzming.core.base.BaseActivity
import com.zzming.example.databinding.ActivityMusicBinding
import com.zzming.example.util.GlobalExoPlayer

/**
 * @author MackZhong
 * @time 2023/8/7
 * @description
 **/
class MusicActivity: BaseActivity() {

    private lateinit var binding: ActivityMusicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.playerView.player = GlobalExoPlayer.player
        test()
    }

    private fun test() {
        val item = MediaItem.fromUri("https://c3-ex-swe.nct.vn/PreNCT7/SkyfallRap-DaveDays-2873237.mp4?st=QXvh0sXxZ2FKpdSFskwsow&e=1691474897")
//        val item = MediaItem.Builder()
//            .setUri("https://pili-live-hdl.kwrd.mobi/test-nctlive/4933.flv?sign=4b3d63c13054b67c142829bbfff82c1a&t=64d32ed6")
//            .setLiveConfiguration(MediaItem.LiveConfiguration.Builder().setMaxPlaybackSpeed(1.02f).build())
//            .build()
        GlobalExoPlayer.player.setMediaItem(item)
        GlobalExoPlayer.player.prepare()
    }

}