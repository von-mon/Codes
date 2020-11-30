package com.example.viewpagerdemo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

class MyAdapter(context: Context, list: List<String>) : PagerAdapter() {

    private val mContext = context
    private val mList = list

    /**
     * 判断view是不是instantiateItem()返回的view
     */
    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    /**
     * 初始化界面，添加page
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = View.inflate(mContext, R.layout.viewpager_item, null)
        val textView: TextView = view.findViewById(R.id.show_text)
        textView.text = mList[position]
        //添加界面
        container.addView(view)
        //返回，view当作一个id
        return view
    }

    /**
     * 返回可滑动的界面的数量
     */
    override fun getCount() = mList.size

    /**
     * 销毁item
     */
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}