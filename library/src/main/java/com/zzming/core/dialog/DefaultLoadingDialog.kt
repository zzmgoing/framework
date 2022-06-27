package com.zzming.core.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.StyleRes
import androidx.core.widget.ContentLoadingProgressBar
import com.zzming.core.R
import com.zzming.core.base.BaseActivity
import com.zzming.core.databinding.CoreCommonDialogLoadingBinding
import com.zzming.core.extension.isAlive
import com.zzming.core.extension.runOnMainThread
import com.zzming.core.utils.ViewUtils
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

/**
 * @author ZhongZiMing
 * @time 2020/9/8 11:35
 * @description DefaultLoadingDialog
 **/
class DefaultLoadingDialog(
    context: Context,
    private val gifResource: Int? = null,
    @StyleRes themeResId: Int = R.style.Default_Loading_Dialog
) : Dialog(context, themeResId), LoadingDialogListener {

    private lateinit var binding: CoreCommonDialogLoadingBinding

    var loadingBar: ContentLoadingProgressBar? = null

    var loadingImage: GifImageView? = null

    var gifDrawable: GifDrawable? = null

    private val activity by lazy {
        context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ViewUtils.createView(R.layout.core_common_dialog_loading)
        binding = CoreCommonDialogLoadingBinding.bind(view)
        setContentView(binding.root)
        loadingBar = binding.commonLoadingBar
        loadingImage = binding.commonLoadingImage
        loadingBarColor?.let { loadingBarColor = it }
        loadingBarDrawable?.let { loadingBarDrawable = it }
        gifResource?.let {
            loadingBar?.visibility = View.GONE
            loadingImage?.visibility = View.VISIBLE
            loadingImage?.setImageResource(it)
            gifDrawable = loadingImage?.drawable as GifDrawable
            gifDrawable?.stop()
        }
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
        val isAlive = if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).isActive
        } else {
            activity.isAlive()
        }
        if (isAlive) {
            runOnMainThread {
                show()
            }
        }
    }

    override fun hideLoading() {
        val isAlive = if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).isActive
        } else {
            activity.isAlive()
        }
        if (isAlive && isShowing) {
            runOnMainThread {
                dismiss()
            }
        }
    }

    override fun show() {
        super.show()
        showImage()
    }

    override fun dismiss() {
        super.dismiss()
        hideImage()
    }

    override fun bindActivity(): Activity {
        return activity
    }

    private fun showImage() {
        try {
            if (gifDrawable != null) {
                loadingImage?.post {
                    gifDrawable?.start()
                }
            } else {
                loadingBar?.post {
                    binding.commonLoadingBar.show()
                }
            }
        } catch (e: Exception) {
        }
    }

    private fun hideImage() {
        try {
            if (gifDrawable != null) {
                loadingImage?.post {
                    gifDrawable?.stop()
                }
            } else {
                loadingBar?.post {
                    binding.commonLoadingBar.hide()
                }
            }
        } catch (e: Exception) {
        }
    }

}