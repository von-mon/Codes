package com.example.datasetobservertest

import android.database.DataSetObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var observer: DataSetObserver
    lateinit var listArray: MyListArray<String>
    val TAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observer = DataChangeSetObserver()
        listArray = MyListArray()
        listArray.registerDataSetObserver(observer)

        val add: Button = findViewById(R.id.btnAdd)

        add.setOnClickListener {
            listArray.add("Hello")
            //通知数据改变
            listArray.notifyDataSetChanged()
        }

    }


    /**
     * 数据观察类
     */
    inner class DataChangeSetObserver : DataSetObserver() {

        override fun onChanged() {
            super.onChanged()
            for (temp in listArray) {
                Log.d(TAG, "onChanged: $temp")
            }
        }

        override fun onInvalidated() {
            super.onInvalidated()
            Log.d(TAG, "onInvalidated: ")
        }
    }
}