package com.zzming.core.utils

import android.content.ContentValues
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.core.content.FileProvider
import com.zzming.core.LibCore
import com.zzming.core.extension.appContext
import com.zzming.core.extension.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
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
        fun file2Uri(file: File): Uri {
            return if (BuildUtils.isAtLeast24Api()) {
                val provider = appContext.packageName + ".androidx-startup"
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

        fun view2Bitmap(view: View): Bitmap? {
            if (view is ImageView) {
                val drawable = view.drawable
                if (drawable is BitmapDrawable) {
                    return drawable.bitmap
                }
            }
            view.clearFocus()
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            if (bitmap != null) {
                val canvas = Canvas(bitmap)
                view.draw(canvas)
                canvas.setBitmap(null)
            }
            return bitmap
        }

        fun saveImage(toBitmap: Bitmap, result: (Uri?) -> Unit) {
            MainScope().launch(Dispatchers.IO) {
                val insertUri = appContext.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues()
                ) ?: kotlin.run {
                    return@launch
                }
                appContext.contentResolver.openOutputStream(insertUri).use {
                    if (toBitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)) {
                        result.invoke(insertUri)
                    } else {
                        result.invoke(null)
                    }
                }
            }
        }

    }

}