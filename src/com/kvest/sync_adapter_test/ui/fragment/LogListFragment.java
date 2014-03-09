package com.kvest.sync_adapter_test.ui.fragment;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setCacheColorHint(Color.TRANSPARENT);

        //create and set adapter
        adapter = new LogListAdapter(getActivity(), LogListAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);

        //load cursor
        getLoaderManager().initLoader(LOAD_LOG_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        switch (id) {
            case LOAD_LOG_ID : return new CursorLoader(getActivity(),
                                                        LogProviderMetadata.LOGS_URI,
                                                        LogTable.FULL_PROJECTION, null, null, null);
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
