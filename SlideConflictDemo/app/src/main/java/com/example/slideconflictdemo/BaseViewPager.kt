package com.example.slideconflictdemo

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class BaseViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var mLastXIntercept: Float = 0.0f
    private var mLastYIntercept: Float = 0.0f

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val intercept: Boolean
        val x = ev?.x
        val y = ev?.y
        when (ev?.action?.and(MotionEvent.ACTION_MASK)) {
            MotionEvent.ACTION_DOWN -> {
                intercept = false
                super.onInterceptTouchEvent(ev)
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x?.minus(mLastXIntercept)
                val deltaY = y?.minus(mLastYIntercept)
                intercept = abs(deltaX!!) > abs(deltaY!!)
            }
            MotionEvent.ACTION_UP -> intercept = false
            else -> intercept = false
        }
        mLastXIntercept = x!!
        mLastYIntercept = y!!
        return intercept
    }

}