package com.zzming.example.listpreloader.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.zzming.example.R;
import com.zzming.example.listpreloader.widget.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************************
 * Module Name:
 * Description:
 * Author: 李桐桐
 * Date:   2019/4/24
 *************************************************************************************/
public class PreLoadAdapter extends RecyclerView.Adapter<PreLoadAdapter.PicViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<String> mDatas = new ArrayList<>();
    private Context mContext;

    public PreLoadAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public PicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic, parent, false));
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
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
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

    public class PicViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tvNum, tvNumLeft;

        public PicViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvNumLeft = itemView.findViewById(R.id.tv_num_l);
        }
    }
}
