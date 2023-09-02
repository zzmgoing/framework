package com.zzming.core.base

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.zzming.core.contracts.CropPhotoContract
import com.zzming.core.contracts.SelectPhotoContract
import com.zzming.core.contracts.TakePhotoContract
import com.zzming.core.extension.logDebug
import com.zzming.core.utils.JsonUtil
import com.zzming.core.utils.PermissionUtils

/**
 * @author ZhongZiMing
 * @time 2020/6/8 17:02
 * @description Fragment基类
 **/
abstract class BaseFragment : Fragment(), Observer<AnyEvent> {

    private var isLoadData = false

    val baseActivity: BaseActivity?
        get() = activity as? BaseActivity

    /**
     * 拍照或选择照片回调
     */
    private var photoCallback: ((Uri?) -> Unit)? = null

    /**
     * 权限回调
     */
    private var permissionCallback: ((Boolean) -> Unit)? = null

    /**
     * 拍照片
     */
    private val takePhoto = registerForActivityResult(TakePhotoContract()) {
        photoCallback?.invoke(it)
    }

    /**
     * 拍照片并裁剪
     */
    private val takePhotoWithCrop = registerForActivityResult(TakePhotoContract()) {
        it?.let { uri-> cropPhoto.launch(uri) }
    }

    /**
     * 选择照片
     */
    private val selectPhoto = registerForActivityResult(SelectPhotoContract()) {
        photoCallback?.invoke(it)
    }

    /**
     * 选择照片并裁剪
     */
    private val selectPhotoWithCrop = registerForActivityResult(SelectPhotoContract()) {
        it?.let { uri-> cropPhoto.launch(uri) }
    }

    /**
     * 裁剪照片
     */
    private val cropPhoto = registerForActivityResult(CropPhotoContract()) {
        it?.uri?.let { uri-> photoCallback?.invoke(uri) }
    }

    /**
     * 申请权限
     */
    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        logDebug("zzm permission: ${JsonUtil.gson.toJson(it)}")
        permissionCallback?.invoke(!it.values.contains(false))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createContentView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLoadData = false
        LiveEventBus.get(AnyEvent::class.java).observe(viewLifecycleOwner, this)
    }

    override fun onResume() {
        super.onResume()
        if (!isLoadData) {
            lazyInit()
            isLoadData = true
        }
    }

    abstract fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View

    open fun lazyInit() {
    }

    override fun onChanged(value: AnyEvent) {

    }

    fun takePhoto(withCrop: Boolean = true, photoCallback: (Uri?) -> Unit) {
        requestPermission(PermissionUtils.getCameraPermission()) {
            if (it) {
                this.photoCallback = photoCallback
                if (withCrop) {
                    takePhotoWithCrop.launch(null)
                } else {
                    takePhoto.launch(null)
                }
            } else {
                photoCallback.invoke(null)
            }
        }
    }

    fun selectPhoto(withCrop: Boolean = true, photoCallback: (Uri?) -> Unit) {
        requestPermission(PermissionUtils.getImagePermission()) {
            if (it) {
                this.photoCallback = photoCallback
                if (withCrop) {
                    selectPhotoWithCrop.launch(null)
                } else {
                    selectPhoto.launch(null)
                }
            } else {
                photoCallback.invoke(null)
            }
        }
    }

    fun requestPermission(permission: Array<String>, permissionCallback: (Boolean) -> Unit) {
        this.permissionCallback = permissionCallback
        requestPermission.launch(permission)
    }

}