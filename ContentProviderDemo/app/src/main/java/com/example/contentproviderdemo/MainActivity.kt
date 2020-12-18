package com.example.contentproviderdemo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.contentproviderdemo.ProviderMetaData.ContactsData
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
Ac
        findViewById<Button>(R.id.btn_insert).setOnClickListener {
            insertContact()
        }
        findViewById<Button>(R.id.btn_update).setOnClickListener {
            updateContact()
        }
        findViewById<Button>(R.id.btn_delete).setOnClickListener {
            deleteContact()
        }
        findViewById<Button>(R.id.btn_query).setOnClickListener {
            queryContact()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun insertContact() {
        val values = ContentValues().apply {
            put(ContactsData.CONTACT_NAME, "James")
            put(ContactsData.CONTACT_TELEPHONE, "15894132648")
            put(ContactsData.CONTACT_CONTENT, "NBA Star")
            put(ContactsData.CONTACT_CREATE_DATE, SimpleDateFormat("yyyy-MM-dd").format(Date()))
        }
        val uri = contentResolver.insert(ContactsData.CONTENT_URI, values)
        Log.d("Test", "uri=$uri")
    }

    private fun updateContact() {
        val values = ContentValues()
        values.put(ContactsData.CONTACT_TELEPHONE, "16666666666")
        val count = contentResolver.update(ContactsData.CONTENT_URI, values, "${ContactsData.CONTACT_NAME}='James'", null)
        Log.d("Test", "count = $count")
    }

    private fun deleteContact(){
        val delete = contentResolver.delete(
            ContactsData.CONTENT_URI,
            "${ContactsData.CONTACT_NAME} = 'James'",
            null
        )
        Log.d("Test", "deleteContact: $delete")
    }

    private fun queryContact(){
        val cursor: Cursor? = this.contentResolver.query(
            ContactsData.CONTENT_URI,
            null,
            ContactsData.CONTACT_NAME + "=?",
            arrayOf("James"),
            null
        )
        if (cursor != null) {
            Log.e("test ", "count=" + cursor.count)
        }
        cursor?.moveToFirst()
        while (!cursor?.isAfterLast!!) {
            val id = cursor.getString(cursor.getColumnIndex(ContactsData._ID))
            val name = cursor.getString(cursor.getColumnIndex(ContactsData.CONTACT_NAME))
            val telephone = cursor.getString(cursor.getColumnIndex(ContactsData.CONTACT_TELEPHONE))
            val createDate = cursor.getString(cursor.getColumnIndex(ContactsData.CONTACT_CREATE_DATE))
            Log.e("Test","id: $id")
            Log.e("Test", "name: $name")
            Log.e("Test", "telephone: $telephone")
            Log.e("Test", "date: $createDate")
            cursor.moveToNext()
        }
        cursor.close()
    }
}