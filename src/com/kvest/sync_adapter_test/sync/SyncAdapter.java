package com.kvest.sync_adapter_test.sync;

import android.accounts.Account;
import android.content.*;
import android.os.Bundle;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 09.03.14
 * Time: 23:30
 * To change this template use File | Settings | File Templates.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private ContentResolver contentResolver;

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        contentResolver = context.getContentResolver();
    }

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        contentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras,
                              String authority, ContentProviderClient providerClient,
                              SyncResult syncResult) {
        //TODO
        Log.d("Kvest", "!!!!!!!!!!!!!!!!!!!!");
    }
}
