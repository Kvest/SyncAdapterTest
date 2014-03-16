package com.kvest.sync_adapter_test;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.ContentResolver;
import android.content.PeriodicSync;
import android.os.Bundle;
import com.kvest.sync_adapter_test.account.AuthenticatorService;
import com.kvest.sync_adapter_test.contentprovider.LogProviderMetadata;
import com.kvest.sync_adapter_test.utility.Logger;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 09.03.14
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
public class LogApplication extends Application {
    public static final long SYNC_INTERVAL = 45 * 60;

    private static LogApplication application;

    private Account account;

    public static LogApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        setupAppAccount();
        startSyncAutomatically();
        startPeriodicSync();
    }

    private void setupAppAccount() {
        AccountManager accountManager = AccountManager.get(getApplicationContext());
        Account[] accounts = accountManager.getAccountsByType(AuthenticatorService.Authenticator.ACCOUNT_TYPE);
        if (accounts.length > 0) {
            account = accounts[0];
        } else {

            account = new Account(AuthenticatorService.Authenticator.ACCOUNT_NAME,
                    AuthenticatorService.Authenticator.ACCOUNT_TYPE);
            //usually it will contain real user info, like login/pass
            if (accountManager.addAccountExplicitly(account, null, null)) {
                // Inform the system that this account supports sync
                ContentResolver.setIsSyncable(account, LogProviderMetadata.AUTHORITY, 1);
            }
        }
    }

    private void startSyncAutomatically() {
        if (!ContentResolver.getSyncAutomatically(account, LogProviderMetadata.AUTHORITY)) {
            //Enable AutoSync
            ContentResolver.setSyncAutomatically(account, LogProviderMetadata.AUTHORITY, true);
        }
    }

    private void startPeriodicSync() {

        Bundle settingsBundle = new Bundle();
        settingsBundle.putString("kvest_action", "periodic_sync");
        ContentResolver.addPeriodicSync(
                account,
                LogProviderMetadata.AUTHORITY,
                settingsBundle,
                SYNC_INTERVAL);


//        //print a list of periodic sync
//        List<PeriodicSync> items = ContentResolver.getPeriodicSyncs(account, LogProviderMetadata.AUTHORITY);
//        for (PeriodicSync item : items) {
//
//            Logger.addLog(getApplicationContext(), item.period + " - " + item.authority + " - " + item.extras);
//        }
    }

    public void syncNow() {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(account, LogProviderMetadata.AUTHORITY, settingsBundle);
    }
}
