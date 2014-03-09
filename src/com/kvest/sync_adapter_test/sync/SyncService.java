package com.kvest.sync_adapter_test.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 09.03.14
 * Time: 23:31
 * To change this template use File | Settings | File Templates.
 */
public class SyncService extends Service {
    private static SyncAdapter sSyncAdapter = null;
    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        /*
        * Get the object that allows external processes
        * to call onPerformSync(). The object is created
        * in the base class code when the SyncAdapter
        * constructors call super()
        */
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
