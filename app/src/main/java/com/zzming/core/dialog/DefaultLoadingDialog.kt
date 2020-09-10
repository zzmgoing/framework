package com.zzming.core.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import com.zzming.core.R
import com.zzming.core.extension.isAlive
import com.zzming.core.extension.runOnMainThread
import kotlinx.android.synthetic.main.core_common_dialog_loading.*

/**
 * @author ZhongWei
 * @time 2020/9/8 11:35
 * @description DefaultLoadingDialog
 **/
class DefaultLoadingDialog(context: Context, private val loadingColor: Int? = null) :
    Dialog(context, R.style.Default_Loading_Dialog),
    LoadingDialogListener {

    private val activity by lazy {
        context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.core_common_dialog_loading)
        loadingColor?.apply {
            common_loading_bar.indeterminateDrawable.setColorFilter(this, PorterDuff.Mode.MULTIPLY)
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
        runOnMainThread {
            common_loading_bar.hide()
        }
    }

    override fun bindActivity(): Activity {
        return activity
    }

}