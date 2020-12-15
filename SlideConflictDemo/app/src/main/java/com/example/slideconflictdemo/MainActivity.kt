package com.example.slideconflictdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mList: ArrayList<String>
    lateinit var mAddListView: ArrayList<View>
    lateinit var mBaseAdapter: BaseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAddListView = ArrayList()
        mList = ArrayList()
        for (i in 0..50) {
            mList.add(i, "data: $i")
        }
        for (i in 0..4){
            val listView = ListView(this)
            listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mList)
            mAddListView.add(listView)
        }
        mBaseAdapter = BaseAdapter(mAddListView)
        baseViewPager.adapter = mBaseAdapter
    }
}