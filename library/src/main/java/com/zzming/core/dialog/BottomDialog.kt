package com.zzming.core.dialog

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * @author ZhongZiMing
 * @time 2022/5/22
 * @description
 **/
class BottomDialog(activity: AppCompatActivity, view: View) : BottomSheetDialog(activity) {

    init {
        setContentView(view)
    }

}