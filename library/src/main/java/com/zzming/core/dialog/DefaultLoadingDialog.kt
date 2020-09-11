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
import com.zzming.core.extension.isAlive
import com.zzming.core.extension.runOnMainThread
import kotlinx.android.synthetic.main.core_common_dialog_loading.*

/**
 * @author ZhongZiMing
 * @time 2020/9/8 11:35
 * @description DefaultLoadingDialog
 **/
class DefaultLoadingDialog(
    context: Context,
    @StyleRes themeResId: Int = R.style.Default_Loading_Dialog
) : Dialog(context, themeResId), LoadingDialogListener {

    var loadingBar: ContentLoadingProgressBar? = null

    private val activity by lazy {
        context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.core_common_dialog_loading)
        loadingBar = common_loading_bar
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
        common_loading_bar.post {
            common_loading_bar.show()
        }
    }

    override fun dismiss() {
        super.dismiss()
        common_loading_bar.hide()
    }

    override fun bindActivity(): Activity {
        return activity
    }

}