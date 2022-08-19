package com.zzming.example.bean

/**
 * @author MackZhong
 * @time 2022/8/9
 * @description
 **/
data class ImageListBean(
    val result: ListDataBean,
    val code: Int,
    val message: String
)

data class ListDataBean(
    val total: Int,
    val list: MutableList<ImageBean>,
)

data class ImageBean(
    val id: Int,
    val title: String,
    val url: String,
    val type: String,
)