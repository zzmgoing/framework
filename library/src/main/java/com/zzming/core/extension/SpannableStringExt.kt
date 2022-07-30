package com.zzming.core.extension

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.zzming.core.LibCore

fun TextView.buildSpannableString(init: DslSpannableStringBuilder.() -> Unit) {
    val spanStringBuilderImpl = DslSpannableStringBuilderImpl()
    spanStringBuilderImpl.init()
    movementMethod = LinkMovementMethod.getInstance()
    text = spanStringBuilderImpl.build()
}

interface DslSpannableStringBuilder {
    fun addText(text: String, method: (DslSpanBuilder.() -> Unit)? = null)
}

interface DslSpanBuilder {
    fun setStyle(style: Int)
    fun setSize(size: Float)
    fun setColor(@ColorRes color: Int)
    fun setColor(@ColorRes light: Int, @ColorRes dark: Int, isDark: Boolean = true)
    fun onClick(useUnderLine: Boolean = true, onClick: (View) -> Unit)
}

class DslSpannableStringBuilderImpl : DslSpannableStringBuilder {
    private val builder = SpannableStringBuilder()
    var lastIndex: Int = 0
    var isClickable = false

    override fun addText(text: String, method: (DslSpanBuilder.() -> Unit)?) {
        val start = lastIndex
        builder.append(text)
        lastIndex += text.length
        val spanBuilder = DslSpanBuilderImpl()
        method?.let { spanBuilder.it() }
        spanBuilder.apply {
            onClickSpan?.let {
                builder.setSpan(it, start, lastIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                isClickable = true
            }
            if (!useUnderLine) {
                val noUnderlineSpan = NoUnderlineSpan()
                builder.setSpan(noUnderlineSpan, start, lastIndex, Spanned.SPAN_MARK_MARK)
            }
            foregroundColorSpan?.let {
                builder.setSpan(it, start, lastIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            styleSpan?.let {
                builder.setSpan(it, start, lastIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            sizeSpan?.let {
                builder.setSpan(it, start, lastIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    fun build(): SpannableStringBuilder {
        return builder
    }
}

class DslSpanBuilderImpl : DslSpanBuilder {
    var styleSpan: StyleSpan? = null
    var sizeSpan: RelativeSizeSpan? = null
    var foregroundColorSpan: ForegroundColorSpan? = null
    var onClickSpan: ClickableSpan? = null
    var useUnderLine = true

    override fun setStyle(style: Int) {
        styleSpan = StyleSpan(style)
    }

    override fun setSize(size: Float) {
        sizeSpan = RelativeSizeSpan(size)
    }

    override fun setColor(@ColorRes color: Int) {
        foregroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(LibCore.context,color))
    }

    override fun setColor(@ColorRes light: Int, @ColorRes dark: Int, isDark: Boolean) {
        foregroundColorSpan = if (isDark) {
            ForegroundColorSpan(ContextCompat.getColor(LibCore.context,dark))
        } else {
            ForegroundColorSpan(ContextCompat.getColor(LibCore.context,light))
        }
    }

    override fun onClick(useUnderLine: Boolean, onClick: (View) -> Unit) {
        onClickSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                onClick(widget)
            }
        }
        this.useUnderLine = useUnderLine
    }
}

class NoUnderlineSpan : UnderlineSpan() {
    override fun updateDrawState(ds: TextPaint) {
        ds.color = ds.linkColor
        ds.isUnderlineText = false
    }
}