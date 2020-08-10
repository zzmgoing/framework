package com.zzming.core.bean

/**
 * @author ZhongWei
 * @time 2020/8/10 17:36
 * @description
 * loadingState LOADING/SUCCESS/FAIL
 * requestTag 当前请求的标记对应某个请求
 **/
data class LoadingState(val loadingState: Int, val requestTag: String) {

}