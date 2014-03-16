package com.kvest.sync_adapter_test.ui.fragment;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.kvest.sync_adapter_test.R;
import com.kvest.sync_adapter_test.contentprovider.LogProviderMetadata;
import com.kvest.sync_adapter_test.datastorage.table.LogTable;
import com.kvest.sync_adapter_test.ui.adapter.LogListAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 09.03.14
 * Time: 22:24
 * To change this template use File | Settings | File Templates.
 */
public class LogListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOAD_LOG_ID = 0;

    private LogListAdapter adapter;
    private TextView filter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View header = LayoutInflater.from(getActivity()).inflate(R.layout.log_list_header, null);
        filter = (TextView)header.findViewById(R.id.filter);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilter();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        getListView().addHeaderView(header);

        getListView().setCacheColorHint(Color.TRANSPARENT);

        //create and set adapter
        adapter = new LogListAdapter(getActivity(), LogListAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);

        //load cursor
        getLoaderManager().initLoader(LOAD_LOG_ID, null, this);
    }

    private void applyFilter() {
        getLoaderManager().restartLoader(LOAD_LOG_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        switch (id) {
            case LOAD_LOG_ID :
                String selection = null;
                String[] selectionArgs = null;
                if (!TextUtils.isEmpty(filter.getText())) {
                    selection = LogTable.MESSAGE_COLUMN + " LIKE ?";
                    selectionArgs = new String[]{"%" + filter.getText() + "%"};
                }
                return new CursorLoader(getActivity(),
                                        LogProviderMetadata.LOGS_URI,
                                        LogTable.FULL_PROJECTION, selection, selectionArgs, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        adapter.swapCursor(null);
    }
}
