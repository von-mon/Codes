package com.example.contentproviderdemo

import android.content.*
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.example.contentproviderdemo.ProviderMetaData.ContactsData


class ContactsContentProvider : ContentProvider() {

    private val URI_MATCHER = UriMatcher(UriMatcher.NO_MATCH)
    private val CONTACTS_PROJECTION_MAP = HashMap<String, String>()
    private val CONTACTS = 1
    private val CONTACTS_ID = 2
    private lateinit var mDbHelper:DatabaseHelper

    private fun buildUriMatcher(): UriMatcher {
        val matcher = URI_MATCHER
        matcher.addURI(ProviderMetaData.AUTHORITY, "contacts", CONTACTS)
        matcher.addURI(ProviderMetaData.AUTHORITY, "contacts/#", CONTACTS_ID)
        val map = CONTACTS_PROJECTION_MAP
        map[ContactsData._ID] = ContactsData._ID
        map[ContactsData.CONTACT_NAME] = ContactsData.CONTACT_NAME
        map[ContactsData.CONTACT_TELEPHONE] = ContactsData.CONTACT_TELEPHONE
        map[ContactsData.CONTACT_CONTENT] = ContactsData.CONTACT_CONTENT
        map[ContactsData.CONTACT_CREATE_DATE] = ContactsData.CONTACT_CREATE_DATE
        return matcher
    }


    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        val writableDatabase = mDbHelper.writableDatabase
        val id = writableDatabase.insertOrThrow(ContactsData.TABLE_NAME, null, p1)
        context?.contentResolver?.notifyChange(ContactsData.CONTENT_URI, null)
        return if (id > 0) ContentUris.withAppendedId(p0, id) else null
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        val queryBuilder = SQLiteQueryBuilder()
        when (buildUriMatcher().match(p0)) {
            CONTACTS -> {
                queryBuilder.tables = ContactsData.TABLE_NAME
                queryBuilder.projectionMap = CONTACTS_PROJECTION_MAP
            }
            CONTACTS_ID -> {
                queryBuilder.tables = ContactsData.TABLE_NAME
                queryBuilder.projectionMap = CONTACTS_PROJECTION_MAP
                queryBuilder.appendWhere("${ContactsData.TABLE_NAME}._id=${ContentUris.parseId(p0)}")
            }
        }

        val orderBy: String = p4 ?: ContactsData.DEFAULT_ORDERBY
        val db = mDbHelper.readableDatabase
        return queryBuilder.query(db, p1, p2, p3, null, null, orderBy)
    }

    override fun onCreate(): Boolean {
        mDbHelper = DatabaseHelper(context)
        return true
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        val db = mDbHelper.writableDatabase
        var modified = 0
        when (buildUriMatcher().match(p0)) {
            CONTACTS -> {
                modified = db.update(ContactsData.TABLE_NAME, p1, p2, p3)
            }
            CONTACTS_ID -> {
                val selection =
                    DatabaseUtils.concatenateWhere(p2, "${ContactsData.TABLE_NAME}._id=?")
                val selectionArgs = DatabaseUtils.appendSelectionArgs(
                    p3,
                    arrayOf(ContentUris.parseId(p0).toString())
                )
                modified = db.update(ContactsData.TABLE_NAME, p1, selection, selectionArgs)
            }
        }
        context?.contentResolver?.notifyChange(ContactsData.CONTENT_URI, null)
        return modified
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        val db = mDbHelper.writableDatabase
        var deleted = 0
        when (buildUriMatcher().match(p0)) {
            CONTACTS -> {
                deleted = db.delete(ContactsData.TABLE_NAME, p1, p2)
            }
            CONTACTS_ID -> {
                val selection =
                    DatabaseUtils.concatenateWhere(p1, "${ContactsData.TABLE_NAME}._id=?")
                val selectionArgs = DatabaseUtils.appendSelectionArgs(
                    p2,
                    arrayOf(ContentUris.parseId(p0).toString())
                )
                deleted = db.delete(ContactsData.TABLE_NAME, selection, selectionArgs)
            }
        }

        // 更新数据时，通知其他ContentObserver
        context!!.contentResolver.notifyChange(ContactsData.CONTENT_URI, null)

        return deleted
    }

    override fun getType(p0: Uri): String? {
        when (buildUriMatcher().match(p0)) {
            CONTACTS -> return ContactsData.CONTENT_TYPE
            CONTACTS_ID -> return ContactsData.CONTENT_ITEM_TYPE
        }
        return null

    }

    private class DatabaseHelper(context: Context?) :
        SQLiteOpenHelper(
            context,
            DATABASE_NAME,
            null,
            DATABASE_VERSION
        ) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(ContactsData.SQL_CREATE_TABLE)
        }

        override fun onUpgrade(
            db: SQLiteDatabase,
            oldVersion: Int,
            newVersion: Int
        ) {
            onCreate(db)
        }

        companion object {
            const val DATABASE_NAME = "test.db"
            const val DATABASE_VERSION = 1
        }
    }
}