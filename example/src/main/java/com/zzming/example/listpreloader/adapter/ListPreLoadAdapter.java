package com.zzming.example.listpreloader.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.zzming.example.R;
import com.zzming.example.listpreloader.widget.GlideRoundTransform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*************************************************************************************
 * Module Name:
 * Description:
 * Author: 李桐桐
 * Date:   2019/4/24
 *************************************************************************************/
public class ListPreLoadAdapter extends RecyclerView.Adapter<ListPreLoadAdapter.PicViewHolder> implements ListPreloader.PreloadModelProvider<String> {
    private final String TAG = getClass().getSimpleName();
    private List<String> mDatas = new ArrayList<>();
    private Context mContext;
    private ViewPreloadSizeProvider mProvider;
    private final int imageWidthPixels = 1024;
    private final int imageHeightPixels = 768;

    public ListPreLoadAdapter(Context context, ViewPreloadSizeProvider provider) {
        mContext = context;
        mProvider = provider;
    }

    @NonNull
    @Override
    public PicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PicViewHolder holder = new PicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic, parent, false));
        mProvider.setView(holder.iv);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PicViewHolder holder, int position) {
        Log.d(TAG, "position:" + position + "  url:" + mDatas.get(position));

        holder.tvNumLeft.setText("" + position);
        holder.tvNum.setText("" + position);
        RequestOptions requestOptions = new RequestOptions()
                .transform(new GlideRoundTransform(mContext, 5))
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .override(imageWidthPixels, imageHeightPixels);
        Glide.with(mContext)
                .load(mDatas.get(position))
                .apply(requestOptions)
                .into(holder.iv);
    }

    public void setDatas(List<String> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @NonNull
    @Override
    public List getPreloadItems(int position) {
        Log.e(TAG, "getPreloadItems--position" + position);
        //告诉RecyclerViewPreloader每个item项需要加载的图片url集合
        String url = mDatas.get(position);
        if (TextUtils.isEmpty(url)) {
            return Collections.emptyList();
        }
        int size = mDatas.size();
        if (size - position > 10) {
            size = position + 10;
        }
        return mDatas.subList(position, size);
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull String url) {
        Log.e(TAG, "getPreloadRequestBuilder--url" + url);
        //返回一个加载图片的RequestBuilder（Glide内部的加载构建器）
        return Glide.with(mContext).load(url).override(imageWidthPixels, imageHeightPixels);
    }

    public class PicViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        private TextView tvNum, tvNumLeft;

        public PicViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvNumLeft = itemView.findViewById(R.id.tv_num_l);
        }
    }
}
