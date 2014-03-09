package com.kvest.sync_adapter_test;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.ContentResolver;
import com.kvest.sync_adapter_test.account.AuthenticatorService;
import com.kvest.sync_adapter_test.contentprovider.LogProviderMetadata;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 09.03.14
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
public class LogApplication extends Application {
    private Account account;

    @Override
    public void onCreate() {
        super.onCreate();

        setupAppAccount();
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
            accountManager.addAccountExplicitly(account, null, null);

            //Enable AutoSync
            ContentResolver.setSyncAutomatically(account, LogProviderMetadata.AUTHORITY, true);
        }
    }
}
