package com.zzming.example.listpreloader.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.zzming.example.R;
import com.zzming.example.bean.ImageBean;
import com.zzming.example.bean.ImageListBean;
import com.zzming.example.listpreloader.adapter.ListPreLoadAdapter;
import com.zzming.example.ui.fragment.mine.MineViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*************************************************************************************
 * Module Name:
 * Description:
 * Author: 李桐桐
 * Date:   2019/4/24
 *************************************************************************************/
public class ListPreLoadActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView mRvPreLoad;
    private ListPreLoadAdapter mAdapter;
    private List<String> mData = new ArrayList<>();
    private Activity mActivity = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpreload);
        ViewPreloadSizeProvider<String> preloadSizeProvider = new ViewPreloadSizeProvider<>();

        mAdapter = new ListPreLoadAdapter(this, preloadSizeProvider);
        mRvPreLoad = findViewById(R.id.rv_list_preload);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        mRvPreLoad.addItemDecoration(itemDecoration);
        mRvPreLoad.setLayoutManager(layoutManager);
        mRvPreLoad.setAdapter(mAdapter);
        initData();
        //创建一个ViewPreloadSizeProvider，它是用于记录列表item显示图片的View的尺寸的
        /**
         * MyPreloadModelProvider是自己写的实现ListPreloader.PreloadModelProvider接口的类，作用是
         * 1.返回需要提前加载的图片url
         * 2.返回一个加载图片的RequestBuilder（Glide内部的加载构建器）
         */
        //将ViewPreloadSizeProvider和MyPreloadModelProvider对象以及设定的提前加载item个数作为参数创建一个RecyclerViewPreloader
        RecyclerViewPreloader<String> preloader = new RecyclerViewPreloader<>(this, mAdapter
                , preloadSizeProvider, 10);
        //将创建的RecyclerViewPreloader对象设置为RecyclerView的滚动监听器
        mRvPreLoad.addOnScrollListener(preloader);
        mRvPreLoad.setItemViewCacheSize(0);
        mRvPreLoad.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                ListPreLoadAdapter.PicViewHolder myViewHolder = (ListPreLoadAdapter.PicViewHolder) holder;
                Glide.with(mActivity).clear(myViewHolder.iv);
            }
        });
    }

    private void initData() {
        MineViewModel mineViewModel = new ViewModelProvider(this).get(MineViewModel.class);
        mineViewModel.getData().observe(this, new Observer<ImageListBean>() {
            @Override
            public void onChanged(ImageListBean imageListBean) {
                for (ImageBean item: imageListBean.getResult().getList()) {
                    mData.add(item.getUrl());
                }
                mAdapter.setDatas(mData);
            }
        });
        mineViewModel.load();
//        mData = Arrays.asList(getResources().getStringArray(R.array.url_list));
//        mAdapter.setDatas(mData);
    }
}
