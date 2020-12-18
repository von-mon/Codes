package com.example.contentproviderdemo

import android.net.Uri
import android.provider.BaseColumns

object ProviderMetaData{

    const val AUTHORITY = "com.tang.contactsprovider"
    private val AUTHORITY_URI: Uri = Uri.parse("content://$AUTHORITY")

    object ContactsData : BaseColumns{
        const val TABLE_NAME = "contacts"
        val CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME)
        const val CONTENT_TYPE = "vnd.android.cursor.dir/contact"
        const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact"
        const val _ID = "_id"
        const val CONTACT_NAME = "name"
        const val CONTACT_TELEPHONE = "telephone"
        const val CONTACT_CREATE_DATE = "create_date"
        const val CONTACT_CONTENT = "content"
        const val CONTACT_GROUP = "group_name"
        const val DEFAULT_ORDERBY = "create_date DESC"

        val SQL_CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + CONTACT_NAME + " VARCHAR(50),"
                + CONTACT_TELEPHONE + " VARCHAR(11),"
                + CONTACT_CONTENT + " TEXT,"
                + CONTACT_CREATE_DATE + " VARCHAR(50)"
                + ");")
    }

}