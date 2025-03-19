package com.uhufor.udf.debug

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
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
    depth: Int,
    textColor: Int,
    backgroundColor: Int,
) : Drawable() {
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = context.dpToPx(FONT_SIZE).toFloat()
        color = textColor
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = backgroundColor
    }

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        alpha = (255 * 0.3f).toInt()
        strokeWidth = context.dpToPx(1).toFloat()
    }

    private val textWidth: Int =
        (textPaint.measureText(label) + context.dpToPx(HORIZONTAL_MARGIN)).toInt()
    private val textHeight: Int = context.dpToPx(LABEL_HEIGHT)
    private val textOffsetX: Float = context.dpToPx(HORIZONTAL_MARGIN).toFloat()
    private val textOffsetY: Float = context.dpToPx(FONT_SIZE).toFloat()
    private val offsetY: Float = context.dpToPx(LABEL_HEIGHT * depth).toFloat()

    override fun draw(canvas: Canvas) {
        val left = if (alignLeft) 0f else (bounds.right - textWidth).toFloat()
        val top = offsetY
        val right = left + textWidth
        val bottom = top + textHeight
        canvas.drawRect(left, top, right, bottom, backgroundPaint)
        canvas.drawLine(left, bottom.toFloat(), right, bottom.toFloat(), borderPaint)

        val textX = if (alignLeft) left + textOffsetX else left
        val textY = top + textOffsetY
        canvas.drawText(label, textX, textY, textPaint)
    }

    override fun setAlpha(alpha: Int) {
        textPaint.alpha = alpha
        backgroundPaint.alpha = alpha
        borderPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        textPaint.colorFilter = colorFilter
        backgroundPaint.colorFilter = colorFilter
        borderPaint.colorFilter = colorFilter
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    companion object {
        private const val LABEL_HEIGHT = 16
        private const val FONT_SIZE = 12
        private const val HORIZONTAL_MARGIN = 16
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
                depth = 0,
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

        rootView.post {
            if (rootView.foreground == null) {
                rootView.foreground = LabelDrawable(
                    context = f.requireContext(),
                    label = f.javaClass.simpleName,
                    alignLeft = false,
                    depth = f.computeFragmentDepth(),
                    textColor = Color.RED,
                    backgroundColor = Color.argb(100, 180, 180, 255),
                )
            }
        }
    }

    private fun Fragment.computeFragmentDepth(): Int {
        val childRect = Rect()
        view?.getGlobalVisibleRect(childRect)
        if (childRect.isEmpty) return 0
        val childTop = childRect.top

        var depth = 0
        var parentFragment = this.parentFragment

        while (parentFragment != null) {
            val parentRect = Rect()
            parentFragment.view?.getGlobalVisibleRect(parentRect)

            if (!parentRect.isEmpty && parentRect.top == childTop) {
                depth++
            }

            parentFragment = parentFragment.parentFragment
        }
        return depth
    }
}
