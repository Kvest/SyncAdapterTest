package com.kvest.sync_adapter_test.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import java.util.Date;

import com.kvest.sync_adapter_test.R;
import com.kvest.sync_adapter_test.datastorage.table.LogTable;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 09.03.14
 * Time: 22:26
 * To change this template use File | Settings | File Templates.
 */
public class LogListAdapter extends CursorAdapter {
    private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy HH:mm:ss";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_PATTERN);

    private int dateColumnIndex = -1;
    private int messageColumnIndex = -1;

    public LogListAdapter(Context context) {
        super(context, null);
    }

    public LogListAdapter(Context context, boolean autoRequery) {
        super(context, null, autoRequery);
    }

    public LogListAdapter(Context context, int flags) {
        super(context, null, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        //create view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.log_list_item, viewGroup, false);

        //create holder
        ViewHolder holder = new ViewHolder();
        holder.date = (TextView)view.findViewById(R.id.log_date);
        holder.message = (TextView)view.findViewById(R.id.log_message);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder)view.getTag();

        if (!isColumnIndexesCalculated()) {
            calculateColumnIndexes(cursor);
        }

        Date date = new Date(cursor.getLong(dateColumnIndex));
        holder.date.setText(DATE_FORMAT.format(date));

        holder.message.setText(cursor.getString(messageColumnIndex));
    }

    private boolean isColumnIndexesCalculated() {
        return (dateColumnIndex >= 0);
    }

    private void calculateColumnIndexes(Cursor cursor) {
        dateColumnIndex = cursor.getColumnIndex(LogTable.DATE_COLUMN);
        messageColumnIndex = cursor.getColumnIndex(LogTable.MESSAGE_COLUMN);
    }

    private static class ViewHolder {
        public TextView date;
        public TextView message;
    }
}
