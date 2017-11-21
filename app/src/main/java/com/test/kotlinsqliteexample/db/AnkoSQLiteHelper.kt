package com.test.kotlinsqliteexample.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.test.kotlinsqliteexample.MainApplication
import org.jetbrains.anko.db.*

/**
 * Created by admin on 2017/11/21.
 */
class AnkoSQLiteHelper(var ctx: Context = MainApplication.instance) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {
    companion object {
        @JvmField
        val DB_NAME = "mydb"
        @JvmField
        val DB_VERSION = 1

        val instance by lazy { AnkoSQLiteHelper() }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.createTable(UserTable.TABLE_NAME, true,
                UserTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                UserTable.USER_ID to TEXT,
                UserTable.NAME to TEXT,
                UserTable.EMAIL to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}

val Context.database: AnkoSQLiteHelper
    get() = AnkoSQLiteHelper.instance