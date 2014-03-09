package com.kvest.sync_adapter_test.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.kvest.sync_adapter_test.datastorage.LogSQLStorage;
import com.kvest.sync_adapter_test.datastorage.table.LogTable;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 09.03.14
 * Time: 22:14
 * To change this template use File | Settings | File Templates.
 */
public class LogProvider extends ContentProvider {
    private LogSQLStorage sqlStorage;

    private static final int LOGS_URI_INDICATOR = 1;
    private static final int LOG_URI_INDICATOR = 2;

    private static final UriMatcher uriMatcher;
    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(LogProviderMetadata.AUTHORITY, LogProviderMetadata.LOGS_PATH, LOGS_URI_INDICATOR);
        uriMatcher.addURI(LogProviderMetadata.AUTHORITY, LogProviderMetadata.LOGS_PATH + "/#", LOG_URI_INDICATOR);
    }

    @Override
    public boolean onCreate() {
        sqlStorage = new LogSQLStorage(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case LOGS_URI_INDICATOR :
                queryBuilder.setTables(LogTable.TABLE_NAME);
                break;
            case LOG_URI_INDICATOR :
                queryBuilder.setTables(LogTable.TABLE_NAME);
                queryBuilder.appendWhere(LogTable._ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown uri = " + uri);
        }

        //make query
        SQLiteDatabase db = sqlStorage.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = sqlStorage.getWritableDatabase();
        long rowId = 0;

        switch (uriMatcher.match(uri)) {
            case LOGS_URI_INDICATOR :
                //replace works as "INSERT OR REPLACE"
                rowId = db.replace(LogTable.TABLE_NAME, null, values);
                if (rowId > 0)
                {
                    Uri resultUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(resultUri, null);
                    return resultUri;
                }
                break;
        }

        throw new IllegalArgumentException("Faild to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted = 0;
        boolean hasSelection = !TextUtils.isEmpty(selection);
        SQLiteDatabase db = sqlStorage.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case LOGS_URI_INDICATOR :
                rowsDeleted = db.delete(LogTable.TABLE_NAME, selection, selectionArgs);
                break;
            case LOG_URI_INDICATOR :
                rowsDeleted = db.delete(LogTable.TABLE_NAME, LogTable._ID + "=" + uri.getLastPathSegment() +
                        (hasSelection ? (" AND " + selection) : ""), (hasSelection ? selectionArgs : null));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Can't update log: " + uri);
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Can't get type for URI" + uri);
    }
}
