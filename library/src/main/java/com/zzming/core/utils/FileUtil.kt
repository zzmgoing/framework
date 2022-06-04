package com.zzming.core.utils

import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.zzming.core.LibCore
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

        fun uri2File(uri: Uri): File? {
            var cursor: Cursor? = null
            try {
                val projection = arrayOf(MediaStore.Images.Media.DATA)
                cursor = LibCore.context.contentResolver.query(uri, projection, null, null, null)
                val index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor?.moveToFirst()
                val imgPath = index?.let { cursor?.getString(it) }
                return if (imgPath != null) File(imgPath) else null
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
            }
            return null
        }


    }

}