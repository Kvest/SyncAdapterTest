package com.kvest.sync_adapter_test.contentprovider;

import android.net.Uri;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 09.03.14
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public abstract class LogProviderMetadata {
    public static final String AUTHORITY = "com.kvest.sync_adapter_test.contentprovider.LogProvider";

    public static final String LOGS_PATH = "logs";

    public static final Uri LOGS_URI = Uri.parse("content://" + AUTHORITY + "/" + LOGS_PATH);
}
