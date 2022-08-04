package com.zzming.example.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzming.core.base.BaseViewModel
import com.zzming.core.net.HttpUtils
import com.zzming.core.net.get
import com.zzming.core.utils.JsonUtil
import com.zzming.example.bean.BannerBean
import com.zzming.example.common.UrlConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author ZhongZiMing
 * @time 2020/10/29
 * @description
 **/
class HomeViewModel : BaseViewModel() {

    /**
     * banner
     */
    val bannerLiveData = MutableLiveData<BannerBean>()

    /**
     * banner
     */
    fun getBanner() {
        viewModelScope.launch(Dispatchers.IO) {
            get(UrlConstants.BANNER, BannerBean::class.java) {
                onSuccess {
                    bannerLiveData.value = it
                }
            }
        }
    }

}