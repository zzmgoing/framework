package com.zzming.example.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zzming.core.base.BaseFragment
import com.zzming.core.extension.showToast
import com.zzming.core.utils.PermissionUtils
import com.zzming.core.widget.setOnSingleClickListener
import com.zzming.example.databinding.FragmentHomeBinding
import com.zzming.example.ui.camera.CameraActivity
import com.zzming.example.ui.dialog.CommonDialog
import com.zzming.example.ui.music.MusicActivity

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java].apply {
            bannerLiveData.observe(this@HomeFragment) { showToast(it.toString()) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.testLanguage.setOnClickListener {
            baseActivity?.let { CommonDialog.showLanguage(it) }
        }
        binding.getRequest.setOnClickListener {
            viewModel.getBanner()
        }
        binding.playMusicActivity.setOnSingleClickListener {
            startActivity(Intent(requireContext(), MusicActivity::class.java))
        }
        binding.takePictureActivity.setOnSingleClickListener {
            PermissionUtils.getCamera(this) {
                if (it is Boolean && it) {
                    startActivity(Intent(requireContext(), CameraActivity::class.java))
                }
            }
        }
    }

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}