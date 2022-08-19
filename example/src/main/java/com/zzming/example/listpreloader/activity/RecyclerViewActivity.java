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
import com.zzming.example.listpreloader.adapter.NoPreLoadAdapter;
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
public class RecyclerViewActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView mRvPreLoad;
    private NoPreLoadAdapter mAdapter;
    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mAdapter = new NoPreLoadAdapter(this);
        mRvPreLoad = findViewById(R.id.rv_no_preload);

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
            }
        });
        mineViewModel.load();
//        mData = Arrays.asList(getResources().getStringArray(R.array.url_list));
//        mAdapter.setDatas(mData);
    }
}
