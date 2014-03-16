package com.kvest.sync_adapter_test.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.kvest.sync_adapter_test.LogApplication;
import com.kvest.sync_adapter_test.R;

public class LogActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.sync_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogApplication.getApplication().syncNow();
            }
        });
    }
}
