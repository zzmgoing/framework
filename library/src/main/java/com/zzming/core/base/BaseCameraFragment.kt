package com.zzming.core.base

import android.content.ContentValues
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.zzming.core.utils.PermissionUtils


/**
 * @author MackZhong
 * @time 2023/8/7
 * @description
 **/
abstract class BaseCameraFragment : BaseFragment() {

    abstract val cameraView: PreviewView

    open var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    open val outputFileOptions: ImageCapture.OutputFileOptions
        get() {
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis().toString())
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            val contentResolver = requireContext().contentResolver
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            return ImageCapture.OutputFileOptions.Builder(contentResolver, contentUri, contentValues).build()
        }

    open val cameraController by lazy {
        LifecycleCameraController(requireContext()).apply {
            bindToLifecycle(this@BaseCameraFragment)
            cameraSelector = this@BaseCameraFragment.cameraSelector
            cameraView.controller = this
        }
    }

    fun switchCamera(selector: CameraSelector? = null) {
        cameraSelector = selector
            ?: if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
        cameraController.cameraSelector = cameraSelector
    }

    fun takePicture() {
        requestPermission(PermissionUtils.getCameraPermission()) {
            if (it) {
                cameraController.takePicture(outputFileOptions, ContextCompat.getMainExecutor(requireContext()),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onError(error: ImageCaptureException) {
                            takePictureError(error)
                        }
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            takePictureSuccess(outputFileResults)
                        }
                    })
            }
        }
    }

    abstract fun takePictureError(error: ImageCaptureException)

    abstract fun takePictureSuccess(outputFileResults: ImageCapture.OutputFileResults)

}