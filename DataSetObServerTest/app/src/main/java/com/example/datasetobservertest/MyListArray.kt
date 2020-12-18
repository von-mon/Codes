package com.example.datasetobservertest

import android.database.DataSetObservable
import android.database.DataSetObserver

class MyListArray<E>: ArrayList<E>(){

    val mDataSetObserver = DataSetObservable()

    fun registerDataSetObserver(observer: DataSetObserver){
        mDataSetObserver.registerObserver(observer)
    }

    fun unregisterDataSetObserver(observer: DataSetObserver){
        mDataSetObserver.unregisterObserver(observer)
    }

    fun notifyDataSetChanged(){
        mDataSetObserver.notifyChanged()
    }

    fun notifyDataSetInvalidated(){
        mDataSetObserver.notifyInvalidated()
    }
}