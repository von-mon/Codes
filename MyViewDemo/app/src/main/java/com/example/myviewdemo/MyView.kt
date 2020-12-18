package com.example.myviewdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.properties.Delegates

/**
 *Created by TZL
 *on 2020/11/17
 *Description:
 */
class MyView(context: Context): View(context) {

    private val mContext = context
    private var defaultSize by Delegates.notNull<Int>()
    private val TAG = MyView::class.simpleName

    constructor(context: Context, attributeSet: AttributeSet) : this(context){
        val typeArray = context.obtainStyledAttributes(attributeSet,R.styleable.MyView);
        defaultSize = typeArray.getDimensionPixelSize(R.styleable.MyView_default_size,100)
        typeArray.recycle()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = getMySize(100, widthMeasureSpec)
        var height = getMySize(100, heightMeasureSpec)
        if (width < height) {
            height = width
        } else {
            width = height
        }
        setMeasuredDimension(width, height)
        Log.d(TAG, "onMeasure: $defaultSize")
    }

    private fun getMySize(defaultSize: Int, measureSpec: Int): Int {
        var mySize = defaultSize
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)

        when (mode) {
            MeasureSpec.UNSPECIFIED -> {
                mySize = defaultSize
            }
            MeasureSpec.EXACTLY -> {
                mySize = size
            }
            MeasureSpec.AT_MOST -> {
                mySize = size
            }
        }
        return mySize
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val r = measuredWidth / 2 //也可以是getMeasuredHeight()/2,我们已经将宽高设置相等了
        val centerX =  r //圆心的横坐标为当前的View的左边起始位置+半径
        val centerY = r  //圆心的纵坐标为当前的View的顶部起始位置+半径
        val paint = Paint()
        paint.color = Color.GREEN
        //开始绘制
        canvas!!.drawCircle(centerX.toFloat(), centerY.toFloat(), r.toFloat(), paint)
//        canvas!!.drawColor(Color.RED)
    }

}