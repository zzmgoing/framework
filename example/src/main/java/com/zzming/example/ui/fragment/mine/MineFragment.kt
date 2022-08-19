package com.zzming.example.ui.fragment.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.zzming.core.base.BaseFragment
import com.zzming.example.ExampleApplication
import com.zzming.example.databinding.FragmentMineBinding
import com.zzming.example.listpreloader.adapter.PreLoadAdapter
import com.zzming.example.listpreloader.widget.GlidePicPreLoadManager
import com.zzming.example.ui.adapter.ImageAdapter

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class MineFragment: BaseFragment() {

    private lateinit var binding: FragmentMineBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[MineViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val adapter = ImageAdapter(requireContext())
//        val callback: ListPreloader.Callback = ListPreloader.Callback { position, preloader ->
//            val imageUrl = adapter.getItem(position).url
//            val itemWidth: Int = binding.recycler.width
//            val itemHeight = itemWidth / 2
//            val preloadRequest =
//                glide.load(imageUrl)
//                    .priority(Priority.LOW)
//                    .skipMemoryCache(true)
//            preloader.preload(preloadRequest, itemWidth, itemHeight)
//        }
//        ListPreloader(glide, callback, 10).attach(binding.recycler)
        binding.recycler.adapter = adapter
//        binding.recycler.enablePreLoader()
        viewModel.data.observe(viewLifecycleOwner) { bean->
            adapter.setNewInstance(bean.result.list)
            val option = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            val list = bean.result.list.map {
                Glide.with(ExampleApplication.context)
                    .load(bean)
                    .apply(option)
                    .preload()
                it.url
            }
        }
        viewModel.load()
    }

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMineBinding.inflate(inflater, container, false)
        return binding.root
    }

}