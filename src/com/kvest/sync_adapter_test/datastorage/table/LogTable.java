package com.kvest.sync_adapter_test.datastorage.table;

import android.provider.BaseColumns;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 09.03.14
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
public class LogTable implements BaseColumns {
    public static final String TABLE_NAME = "log";

    public static final String DATE_COLUMN = "date";
    public static final String MESSAGE_COLUMN = "message";

    public static final String[] FULL_PROJECTION = {_ID, DATE_COLUMN, MESSAGE_COLUMN };

    public static final String CREATE_TABLE_SQL = "CREATE TABLE \"" + TABLE_NAME + "\" (\"" +
            _ID + "\" INTEGER PRIMARY KEY, \"" +
            DATE_COLUMN + "\" INTEGER DEFAULT 0, \"" +
            MESSAGE_COLUMN + "\" TEXT NOT NULL);";

    public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS \"" + TABLE_NAME + "\";";
}
