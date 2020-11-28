package com.zzming.core.utils

import android.net.Uri
import androidx.core.content.FileProvider
import com.zzming.core.LibCore
import com.zzming.core.common.LibConfig
import java.io.File

/**
 * @author ZhongZiMing
 * @time 2020/11/24
 * @description
 **/
class FileUtil {

    companion object {

        /**
         * 获取Uri
         */
        fun getFileUri(file: File, provider: String): Uri {
            return if (BuildUtils.isAtLeast24Api()) {
                FileProvider.getUriForFile(LibCore.context, provider, file)
            } else {
                Uri.fromFile(file)
            }
        }

    }

}