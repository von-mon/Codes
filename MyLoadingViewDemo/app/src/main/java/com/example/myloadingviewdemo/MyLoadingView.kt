package com.example.myloadingviewdemo

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.properties.Delegates


class MyLoadingView(context: Context) : View(context) {
    companion object {
        const val CIRCLE_COUNT = 12
        const val DEGREE_PER_CIRCLE = 360 / CIRCLE_COUNT
    }

    private lateinit var mCircleRadius: FloatArray
    private lateinit var mCircleColors: LongArray
    private var mAnimator: ValueAnimator? = null
    private var mMaxCircleRadius: Float? = 0f
    private var mAnimValue: Int? = 0
    private var mSize by Delegates.notNull<Float>()
    private var mColor by Delegates.notNull<Int>()
    private var mDuration by Delegates.notNull<Long>()
    private lateinit var mPaint: Paint

    constructor(context: Context, attributeSet: AttributeSet) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet, defaultStyle: Int) : this(context) {
        mCircleRadius = FloatArray(CIRCLE_COUNT)
        mCircleColors = LongArray(CIRCLE_COUNT)
        initAttrs(context, attributeSet)
        initPaint()
        initValue()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimation()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == VISIBLE) {
            startAnimation()
        } else {
            stopAnimation()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(mSize.toInt(), mSize.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        if (mSize > 0) {
            canvas?.rotate((DEGREE_PER_CIRCLE * mAnimValue!!).toFloat(), mSize / 2, mSize / 2)
            for (i in 0 until CIRCLE_COUNT) {
                mPaint.alpha = mCircleColors[i].toInt()
                canvas?.drawCircle(mSize / 2, mMaxCircleRadius!!, mCircleRadius[i], mPaint)
                canvas?.rotate(DEGREE_PER_CIRCLE.toFloat(), mSize / 2, mSize / 2)
            }
        }
    }

    private fun initAttrs(context: Context, attributeSet: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.MyLoadingView)
        mSize = typeArray.getDimension(R.styleable.MyLoadingView_size, dp2px(context, 100f))
        mColor = typeArray.getColor(R.styleable.MyLoadingView_color, Color.BLACK)
        mDuration = typeArray.getInt(R.styleable.MyLoadingView_duration, 2000).toLong()
        typeArray.recycle()
    }

    private fun initPaint() {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = mColor
        mPaint.style = Paint.Style.FILL
    }

    private fun initValue() {
        val minCircleRadius = mSize / 24f
        for (i in 0 until CIRCLE_COUNT) {
            when (i) {
                7 -> {
                    mCircleRadius[i] = minCircleRadius * 1.25f
                    mCircleColors[i] = (255 * 0.7f).toLong()
                }
                8 -> {
                    mCircleRadius[i] = minCircleRadius * 1.5f
                    mCircleColors[i] = (255 * 0.8f).toLong()
                }
                9, 11 -> {
                    mCircleRadius[i] = minCircleRadius * 1.75f
                    mCircleColors[i] = (255 * 0.9f).toLong()
                }
                10 -> {
                    mCircleRadius[i] = minCircleRadius * 2f
                    mCircleColors[i] = (255).toLong()
                }
                else -> {
                    mCircleRadius[i] = minCircleRadius
                    mCircleColors[i] = (255 * 0.5f).toLong()
                }
            }
        }
        mMaxCircleRadius = minCircleRadius * 2
    }

    private fun startAnimation() {
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, CIRCLE_COUNT - 1)
            mAnimator?.addUpdateListener(mUpdateListener)
            mAnimator?.duration = mDuration
            mAnimator?.repeatMode = ValueAnimator.RESTART
            mAnimator?.repeatCount = ValueAnimator.INFINITE
            mAnimator?.interpolator = LinearInterpolator()
            mAnimator?.start()
        } else if (!mAnimator?.isStarted!!) {
            mAnimator?.start()
        }
    }

    private fun stopAnimation() {
        if (mAnimator != null) {
            mAnimator?.removeUpdateListener(mUpdateListener)
            mAnimator?.removeAllUpdateListeners()
            mAnimator?.cancel()
        }
    }


    private val mUpdateListener =
        AnimatorUpdateListener { animation ->
            mAnimValue = animation.animatedValue as Int
            invalidate()
        }


    /**
     * dp转px
     */
    private fun dp2px(context: Context, dp: Float): Float {
        val density = context.resources.displayMetrics.density
        return density * dp + 0.5f
    }

}