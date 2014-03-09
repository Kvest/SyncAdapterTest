package com.kvest.sync_adapter_test.utility;

import android.content.ContentValues;
import android.content.Context;
import com.kvest.sync_adapter_test.contentprovider.LogProviderMetadata;
import com.kvest.sync_adapter_test.datastorage.table.LogTable;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 09.03.14
 * Time: 23:00
 * To change this template use File | Settings | File Templates.
 */
public abstract class Logger {
    public static void addLog(Context context, long date, String message) {
        ContentValues values = new ContentValues(2);
        values.put(LogTable.DATE_COLUMN, date);
        values.put(LogTable.MESSAGE_COLUMN, message);

        context.getContentResolver().insert(LogProviderMetadata.LOGS_URI, values);
    }

    public static void addLog(Context context, String message) {
        addLog(context, System.currentTimeMillis(), message);
    }
}
