package com.example.viewpagerdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {
    private lateinit var mShow: ViewPager
    private lateinit var mList: List<String>
    private lateinit var mAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mShow = findViewById(R.id.viewpager_show)
        mList = listOf("apple", "banana", "pineapple", "watermelon")
        //初始化适配器
        mAdapter = MyAdapter(this, mList)
        //设置ViewPager的适配器
        mShow.adapter = mAdapter
        //注册监听事件
        mShow.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }
        })
    }
}