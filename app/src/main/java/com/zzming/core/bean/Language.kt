package com.zzming.core.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

/**
 * @author ZhongWei
 * @time 2020/7/24 9:46
 * @description
 **/
class Language: BaseObservable() {

    @Bindable
    var hello: String = ""

    @Bindable
    var world: String = ""

    @Bindable
    var test: String = ""

}