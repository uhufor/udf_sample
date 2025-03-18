package com.uhufor.udf.debug

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextPaint
import android.util.TypedValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

object DebugLabelInitializer {
    fun initialize(app: Application) {
        app.registerActivityLifecycleCallbacks(DebugLabelActivityLifecycleCallbacks())
    }
}

private fun Context.dpToPx(dp: Int): Int {
    return try {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            this.resources?.displayMetrics
        ).toInt()
    } catch (_: Exception) {
        dp
    }
}

private class LabelDrawable(
    context: Context,
    private val label: String,
    private val alignLeft: Boolean,
    textColor: Int,
    backgroundColor: Int,
) : Drawable() {
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = context.dpToPx(TEXT_SIZE).toFloat()
        color = textColor
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = backgroundColor
    }

    private val textWidth: Int =
        (textPaint.measureText(label) + context.dpToPx(TEXT_HORIZONTAL_MARGIN)).toInt()
    private val textHeight: Int = context.dpToPx(TEXT_HEIGHT)
    private val textOffsetX: Float = context.dpToPx(TEXT_HORIZONTAL_MARGIN).toFloat()
    private val textOffsetY: Float = context.dpToPx(TEXT_SIZE).toFloat()

    override fun draw(canvas: Canvas) {
        val left = if (alignLeft) 0f else (bounds.right - textWidth).toFloat()
        val top = 0f
        val right = left + textWidth
        val bottom = top + textHeight
        canvas.drawRect(left, top, right, bottom, backgroundPaint)

        val textX = if (alignLeft) left + textOffsetX else left
        val textY = top + textOffsetY
        canvas.drawText(label, textX, textY, textPaint)
    }

    override fun setAlpha(alpha: Int) {
        textPaint.alpha = alpha
        backgroundPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        textPaint.colorFilter = colorFilter
        backgroundPaint.colorFilter = colorFilter
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    companion object {
        private const val TEXT_HEIGHT = 18
        private const val TEXT_SIZE = 12
        private const val TEXT_HORIZONTAL_MARGIN = 16
    }
}

private class DebugLabelActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    DebugLabelFragmentLifecycleCallbacks(),
                    true
                )
        }
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        val rootView = activity.window.decorView
        if (rootView.foreground == null) {
            rootView.foreground = LabelDrawable(
                context = activity,
                label = activity.javaClass.simpleName,
                alignLeft = true,
                textColor = Color.WHITE,
                backgroundColor = Color.argb(100, 0, 0, 0),
            )
        }
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}

private class DebugLabelFragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        val rootView = f.view ?: return
        if (rootView.foreground == null) {
            rootView.foreground = LabelDrawable(
                context = f.requireContext(),
                label = f.javaClass.simpleName,
                alignLeft = false,
                textColor = Color.RED,
                backgroundColor = Color.argb(100, 180, 180, 255),
            )
        }
    }
}
