package com.kvest.sync_adapter_test.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.kvest.sync_adapter_test.datastorage.table.LogTable;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 09.03.14
 * Time: 21:54
 * To change this template use File | Settings | File Templates.
 */
public class LogSQLStorage extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "log.db";
    private static final int DATABASE_VERSION = 1;

    public LogSQLStorage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create DB structure
        db.execSQL(LogTable.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Nothing to do yet
    }
}
