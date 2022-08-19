package com.zzming.example.ui.fragment.mine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zzming.core.base.BaseViewModel
import com.zzming.example.bean.ImageListBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author MackZhong
 * @time 2022/8/9
 * @description
 **/
class MineViewModel : BaseViewModel() {

    val data = MutableLiveData<ImageListBean>()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            get("https://api.apiopen.top/api/getImages?type=animal&page=0&size=20", ImageListBean::class.java) {
                onSuccess {
                    data.postValue(it)
                }
            }
        }
    }

}