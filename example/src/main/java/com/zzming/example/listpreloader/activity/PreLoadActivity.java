package com.zzming.example.listpreloader.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zzming.example.R;
import com.zzming.example.bean.ImageBean;
import com.zzming.example.bean.ImageListBean;
import com.zzming.example.listpreloader.adapter.PreLoadAdapter;
import com.zzming.example.listpreloader.widget.GlidePicPreLoadManager;
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
public class PreLoadActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView mRvPreLoad;
    private PreLoadAdapter mAdapter;
    private List<String> mData = new ArrayList<>();
    private GlidePicPreLoadManager mPreLoadManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);
        mAdapter = new PreLoadAdapter(this);
        mRvPreLoad = findViewById(R.id.rv_preload);
        mPreLoadManager = new GlidePicPreLoadManager();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        mRvPreLoad.addItemDecoration(itemDecoration);
        mRvPreLoad.setLayoutManager(layoutManager);
        mRvPreLoad.setAdapter(mAdapter);
        initData();
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
                mPreLoadManager.preLoadCover(mData);
            }
        });
        mineViewModel.load();
//        mData = Arrays.asList(getResources().getStringArray(R.array.url_list));
//        mAdapter.setDatas(mData);
//        mPreLoadManager.preLoadCover(mData);
    }
}
