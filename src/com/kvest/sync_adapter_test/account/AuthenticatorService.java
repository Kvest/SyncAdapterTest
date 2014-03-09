package com.kvest.sync_adapter_test.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 09.03.14
 * Time: 23:07
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticatorService extends Service {
    private Authenticator authenticator;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create a new authenticator object
        authenticator = new Authenticator(this);
    }
    /*
* When the system binds to this Service to make the RPC call
* return the authenticator's IBinder.
*/
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }


    /*
* Implement AbstractAccountAuthenticator and stub out all
* of its methods
*/
    public static class Authenticator extends AbstractAccountAuthenticator {
        public static final String ACCOUNT_TYPE = "com.kvest.sync_adapter_test";
        public static final String ACCOUNT_NAME = "Application Account";

        // Simple constructor
        public Authenticator(Context context) {
            super(context);
        }
        // Editing properties is not supported
        @Override
        public Bundle editProperties(AccountAuthenticatorResponse r, String s) {
            throw new UnsupportedOperationException();
        }
        // Don't add additional accounts
        @Override
        public Bundle addAccount(
                AccountAuthenticatorResponse r,
                String s,
                String s2,
                String[] strings,
                Bundle bundle) throws NetworkErrorException {
            return null;
        }
        // Ignore attempts to confirm credentials
        @Override
        public Bundle confirmCredentials(
                AccountAuthenticatorResponse r,
                Account account,
                Bundle bundle) throws NetworkErrorException {
            return null;
        }
        // Getting an authentication token is not supported
        @Override
        public Bundle getAuthToken(
                AccountAuthenticatorResponse r,
                Account account,
                String s,
                Bundle bundle) throws NetworkErrorException {
            throw new UnsupportedOperationException();
        }
        // Getting a label for the auth token is not supported
        @Override
        public String getAuthTokenLabel(String s) {
            throw new UnsupportedOperationException();
        }
        // Updating user credentials is not supported
        @Override
        public Bundle updateCredentials(
                AccountAuthenticatorResponse r,
                Account account,
                String s, Bundle bundle) throws NetworkErrorException {
            throw new UnsupportedOperationException();
        }
        // Checking features for the account is not supported
        @Override
        public Bundle hasFeatures(
                AccountAuthenticatorResponse r,
                Account account, String[] strings) throws NetworkErrorException {
            throw new UnsupportedOperationException();
        }
    }
}
