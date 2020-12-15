package com.example.slideconflictdemo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import androidx.viewpager.widget.PagerAdapter

class BaseAdapter(list: ArrayList<View>) : PagerAdapter() {

    private val mList = list

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getCount() = mList.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(mList[position] as ListView)
        return mList[position] as ListView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(mList[position])
    }


}