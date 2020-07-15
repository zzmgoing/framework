package com.zzming.core.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/**
 * @author ZhongWei
 * @time 2020/7/9 10:19
 * @description 自定义TextView
 **/
@SuppressLint("AppCompatCustomView")
class CommonTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {


    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
    }

}