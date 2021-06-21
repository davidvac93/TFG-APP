package com.example.finalproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import kotlinx.coroutines.test.withTestContext


class HelperDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    object FeedEntry : BaseColumns {

        const val TABLE_NAME = "clients "
        const val COLUMN_ID = "ID"
        const val COLUMN_NAME = "name "
        const val COLUMN_LASTNAME = "lastname "
        const val COLUMN_DNI = "dni "          //Ver si lo metemos o lo pasamos con ID
        const val COLUMN_FLAT = "flat "
        const val COLUMN_USER = "user "
        const val COLUMN_PASSWORD = "password "


        const val TABLE_NAME_SERVICE= "services"
        const val COLUMN_USER_SERVICE = "userClient "
        const val COLUMN_DATE= "date"
        const val COLUMN_HOUR= "hour"
        const val COLUMN_PEOPLE= "people"
        const val COLUMN_SERVICES="text"
        const val COLUMN_AUTO="auto"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
        db.execSQL(SQL_CREATE_SERVICE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        db.execSQL(SQL_DELETE_SERVICE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }


    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FinalProjectBD.db"
        const val SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS ${FeedEntry.TABLE_NAME} (" +
                        "${FeedEntry.COLUMN_NAME} TEXT," +
                        "${FeedEntry.COLUMN_LASTNAME} TEXT," +                  //CREAMOS LA DATABASE
                        "${FeedEntry.COLUMN_DNI} INTEGER," +         //estructura nombre,apellido,dni,piso,usuario,contraseña
                        "${FeedEntry.COLUMN_FLAT} INTEGER," +
                        "${FeedEntry.COLUMN_USER} TEXT PRIMARY KEY," +
                        "${FeedEntry.COLUMN_PASSWORD} INTEGER)"
        const val SQL_CREATE_SERVICE =
                "CREATE TABLE IF NOT EXISTS ${FeedEntry.TABLE_NAME_SERVICE} (" +
                    "${FeedEntry.COLUMN_USER_SERVICE} TEXT," +
                    "${FeedEntry.COLUMN_DATE} TEXT,"+
                    "${FeedEntry.COLUMN_HOUR} TEXT,"+
                    "${FeedEntry.COLUMN_PEOPLE} TEXT,"+
                    "${FeedEntry.COLUMN_AUTO} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${FeedEntry.COLUMN_SERVICES} TEXT," +
                        "FOREIGN KEY (userClient) REFERENCES clients(user))"

                //BORRAMOS

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${HelperDB.FeedEntry.TABLE_NAME}"
        const val SQL_DELETE_SERVICE = "DROP TABLE IF EXISTS ${HelperDB.FeedEntry.TABLE_NAME}"

    }
}
