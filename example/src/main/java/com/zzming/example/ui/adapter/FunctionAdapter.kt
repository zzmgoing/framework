package com.zzming.example.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zzming.example.R

/**
 * @author ZhongZiMing
 * @time 2022/5/18
 * @description
 **/
class FunctionAdapter(data: MutableList<String>? = null) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_function_list, data) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.text, "列表条目${item}")
    }
}