package com.zzming.example.ui.camera

import android.os.Bundle
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.PreviewView
import com.zzming.core.base.BaseCameraActivity
import com.zzming.core.extension.showToast
import com.zzming.core.widget.setOnSingleClickListener
import com.zzming.example.databinding.ActivityCameraBinding

/**
 * @author MackZhong
 * @time 2023/8/12
 * @description
 **/
class CameraActivity: BaseCameraActivity() {

    private lateinit var binding: ActivityCameraBinding
    override val cameraView: PreviewView
        get() = binding.previewView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        switchCamera(CameraSelector.DEFAULT_FRONT_CAMERA)
        binding.take.setOnSingleClickListener {
            takePicture()
        }
        binding.switchCamera.setOnSingleClickListener {
            switchCamera()
        }
    }

    override fun takePictureError(error: ImageCaptureException) {
        showToast(error.toString())
    }

    override fun takePictureSuccess(outputFileResults: ImageCapture.OutputFileResults) {
        showToast(outputFileResults.savedUri?.path ?: "error")
    }


}