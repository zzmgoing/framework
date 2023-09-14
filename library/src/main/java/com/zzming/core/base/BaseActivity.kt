package com.zzming.core.base

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.zzming.core.common.LibViewConfig
import com.zzming.core.contracts.CropPhotoContract
import com.zzming.core.contracts.SelectPhotoContract
import com.zzming.core.contracts.TakePhotoContract
import com.zzming.core.extension.logDebug
import com.zzming.core.utils.JsonUtil
import com.zzming.core.utils.LanguageUtil
import com.zzming.core.utils.PermissionUtils
import com.zzming.core.utils.ViewUtils

/**
 * @author ZhongZiMing
 * @time 2020/6/5 18:57
 * @description Activity基类
 **/
abstract class BaseActivity : AppCompatActivity(), Observer<AnyEvent> {

    /**
     * 判断当前Activity是否在前台
     */
    var isActive: Boolean = false

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
        it?.let { uri -> cropPhoto.launch(uri) }
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
        it?.let { uri -> cropPhoto.launch(uri) }
    }

    /**
     * 裁剪照片
     */
    private val cropPhoto = registerForActivityResult(CropPhotoContract()) {
        it?.uri?.let { uri -> photoCallback?.invoke(uri) }
    }

    /**
     * 申请权限
     */
    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        logDebug("zzm permission: ${JsonUtil.gson.toJson(it)}")
        permissionCallback?.invoke(!it.values.contains(false))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate()
        super.onCreate(savedInstanceState)
        registerObserver()
    }

    open fun beforeOnCreate() {}

    open fun registerObserver() {
        LiveEventBus
            .get(AnyEvent::class.java)
            .observe(this, this)
    }

    override fun onResume() {
        super.onResume()
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ViewUtils.hieKeyboard(currentFocus, ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(attachBaseLanguageContext(newBase))
    }

    open fun attachBaseLanguageContext(newBase: Context?): Context? {
        if (LibViewConfig.isSupportLanguage && newBase != null) {
            return LanguageUtil.attachBaseContext(newBase)
        }
        return newBase
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