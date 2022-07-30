package com.zzming.core.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.zzming.core.R
import com.zzming.core.collector.ActivityCollector
import com.zzming.core.databinding.CoreCommonTitleBarBinding
import com.zzming.core.extension.dp2px

/**
 * @author ZhongZiMing
 * @time 2022/5/24
 * @description
 **/
class CommonTitleBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    Toolbar(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    var binding: CoreCommonTitleBarBinding

    var backListener: (() -> Unit?)? = null
        set(value) {
            field = value
            binding.titleBarBack.setOnClickListener {
                value?.invoke()
            }
        }

    private var leftIconWidth: Float = 20f
        set(value) {
            field = value
            binding.titleBarBack.apply {
                layoutParams = layoutParams.apply {
                    width = dp2px(value)
                }
            }
        }

    private var rightIconWidth: Float = 20f

    var title: String = ""
        set(value) {
            field = value
            binding.titleBarTitle.text = value
        }

    private var titleSize: Float = 18f
        set(value) {
            field = value
            binding.titleBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, value)
        }

    private var titleColor: Int = Color.BLACK
        set(value) {
            field = value
            binding.titleBarTitle.setTextColor(value)
        }

    private var titleLocation: Int = 1
        set(value) {
            field = value
            binding.titleBarTitle.apply {
                layoutParams = layoutParams.apply {
                    this as RelativeLayout.LayoutParams
                    removeRule(RelativeLayout.CENTER_IN_PARENT)
                    if (value == 1) {
                        addRule(RelativeLayout.CENTER_IN_PARENT)
                    } else {
                        addRule(RelativeLayout.RIGHT_OF, R.id.title_bar_left_layout)
                    }
                }
            }
        }

    private var backIconDrawable: Drawable? = null
        set(value) {
            field = value
            if (value != null) {
                binding.titleBarBack.setImageDrawable(value)
            }
        }

    var leftIconDrawable: Drawable? = null
        set(value) {
            field = value
            if (value != null) {
                createLeftImage(value)
            }
        }

    var rightIcon1Drawable: Drawable? = null
        set(value) {
            field = value
            if (value != null) {
                createRightImage(value)
            }
        }

    var rightIcon2Drawable: Drawable? = null
        set(value) {
            field = value
            if (value != null) {
                createRightImage(value)
            }
        }

    var rightIcon3Drawable: Drawable? = null
        set(value) {
            field = value
            if (value != null) {
                createRightImage(value)
            }
        }

    private fun createLeftImage(drawable: Drawable) {
        val leftImage = ImageView(context).apply {
            layoutParams = MarginLayoutParams(dp2px(rightIconWidth), -2).apply {
                leftMargin = dp2px(16f)
            }
            setImageDrawable(drawable)
        }
        binding.titleBarLeftLayout.addView(leftImage)
    }

    private fun createRightImage(drawable: Drawable) {
        val rightImage = ImageView(context).apply {
            layoutParams = MarginLayoutParams(dp2px(rightIconWidth), -2).apply {
                rightMargin = dp2px(16f)
            }
            setImageDrawable(drawable)
        }
        binding.titleBarRightLayout.addView(rightImage)
    }

    /**
     * 是否占据状态栏
     */
    private var fixStatusBar: Boolean = false
        set(value) {
            field = value
            if (value) {
                ActivityCollector.weakRefActivity?.get()?.let {
                    if (it is AppCompatActivity) {
                        setPadding(0, it.statusBarHeight, 0, 0)
                    }
                }
            }
        }

    /**
     * 是否隐藏返回按钮
     */
    var hideBackIcon: Boolean = false
        set(value) {
            field = value
            if (value) {
                binding.titleBarBack.visibility = View.GONE
            } else {
                binding.titleBarBack.visibility = View.VISIBLE
            }
        }

    var leftIconClickListener: (() -> Unit?)? = null
        set(value) {
            field = value
            if (leftIconDrawable != null && binding.titleBarLeftLayout.childCount > 1) {
                binding.titleBarLeftLayout.getChildAt(1).setOnClickListener {
                    value?.invoke()
                }
            }
        }

    var rightIcon1ClickListener: (() -> Unit?)? = null
        set(value) {
            field = value
            if (rightIcon1Drawable != null && binding.titleBarRightLayout.childCount > 0) {
                binding.titleBarRightLayout.getChildAt(0).setOnClickListener {
                    value?.invoke()
                }
            }
        }

    var rightIcon2ClickListener: (() -> Unit?)? = null
        set(value) {
            field = value
            if (rightIcon2Drawable != null && binding.titleBarRightLayout.childCount > 1) {
                binding.titleBarRightLayout.getChildAt(1).setOnClickListener {
                    value?.invoke()
                }
            }
        }

    var rightIcon3ClickListener: (() -> Unit?)? = null
        set(value) {
            field = value
            if (rightIcon3Drawable != null && binding.titleBarRightLayout.childCount > 2) {
                binding.titleBarRightLayout.getChildAt(2).setOnClickListener {
                    value?.invoke()
                }
            }
        }

    init {
        binding = CoreCommonTitleBarBinding.inflate(LayoutInflater.from(context), this, true)
        backListener = { ActivityCollector.weakRefActivity?.get()?.onBackPressed() }
        initAttr(attrs)
    }

    private fun initAttr(attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val types = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleBar)
        leftIconWidth = types.getDimension(R.styleable.CommonTitleBar_left_icon_width, 32f)
        rightIconWidth = types.getDimension(R.styleable.CommonTitleBar_right_icon_width, 20f)
        title = types.getString(R.styleable.CommonTitleBar_title).toString()
        titleSize = types.getDimension(R.styleable.CommonTitleBar_title_size, 18f)
        titleColor = types.getColor(R.styleable.CommonTitleBar_title_color, Color.BLACK)
        titleLocation = types.getInt(R.styleable.CommonTitleBar_title_location, 1)
        backIconDrawable = types.getDrawable(R.styleable.CommonTitleBar_back_icon_src)
        leftIconDrawable = types.getDrawable(R.styleable.CommonTitleBar_left_icon_src)
        rightIcon1Drawable = types.getDrawable(R.styleable.CommonTitleBar_right_icon1_src)
        rightIcon2Drawable = types.getDrawable(R.styleable.CommonTitleBar_right_icon2_src)
        rightIcon3Drawable = types.getDrawable(R.styleable.CommonTitleBar_right_icon3_src)
        fixStatusBar = types.getBoolean(R.styleable.CommonTitleBar_fix_status_bar, false)
        hideBackIcon = types.getBoolean(R.styleable.CommonTitleBar_hide_back_icon, false)
        types.recycle()
    }

}