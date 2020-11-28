package com.zzming.core.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.core.widget.ContentLoadingProgressBar
import com.zzming.core.R
import com.zzming.core.databinding.CoreCommonDialogLoadingBinding
import com.zzming.core.extension.isAlive
import com.zzming.core.extension.runOnMainThread
import com.zzming.core.utils.ViewUtils

/**
 * @author ZhongZiMing
 * @time 2020/9/8 11:35
 * @description DefaultLoadingDialog
 **/
class DefaultLoadingDialog(
    context: Context,
    @StyleRes themeResId: Int = R.style.Default_Loading_Dialog
) : Dialog(context, themeResId), LoadingDialogListener {

    private lateinit var binding: CoreCommonDialogLoadingBinding

    var loadingBar: ContentLoadingProgressBar? = null

    private val activity by lazy {
        context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ViewUtils.createView(R.layout.core_common_dialog_loading)
        binding = CoreCommonDialogLoadingBinding.bind(view)
        setContentView(binding.root)
        loadingBar = binding.commonLoadingBar
        loadingBarColor?.let { loadingBarColor = it }
        loadingBarDrawable?.let { loadingBarDrawable = it }
    }

    /**
     * loadingBarColor
     */
    var loadingBarColor: Int? = null
        set(value) {
            value?.let {
                field = it
                loadingBar?.indeterminateDrawable?.setColorFilter(
                    it,
                    PorterDuff.Mode.MULTIPLY
                )
            }
        }

    /**
     * loadingBarColor
     */
    var loadingBarDrawable: Drawable? = null
        set(value) {
            value?.let {
                field = it
                loadingBar?.progressDrawable = it
            }
        }

    override fun showLoading() {
        if (activity.isAlive()) {
            runOnMainThread {
                show()
            }
        }
    }

    override fun hideLoading() {
        if (activity.isAlive()) {
            runOnMainThread {
                dismiss()
            }
        }
    }

    override fun show() {
        super.show()
        binding.commonLoadingBar.post {
            binding.commonLoadingBar.show()
        }
    }

    override fun dismiss() {
        super.dismiss()
        binding.commonLoadingBar.hide()
    }

    override fun bindActivity(): Activity {
        return activity
    }

}