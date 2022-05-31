package com.zzming.core.contracts

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.youth.banner.util.LogUtils

/**
 * 选择照片的协定
 */
class SelectPhotoContract : ActivityResultContract<Unit?, Uri?>() {

    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(Intent.ACTION_PICK).setType("image/*")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        LogUtils.d("Select photo uri: ${intent?.data}")
        return intent?.data
    }
}
