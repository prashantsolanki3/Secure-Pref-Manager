package com.prashantsolanki.secureprefsample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.prashantsolanki.secureprefmanager.Encryptor;
import com.prashantsolanki.secureprefmanager.SecurePrefManager;
import com.prashantsolanki.secureprefmanager.SecurePrefManagerInit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new SecurePrefManagerInit.Initializer(getApplicationContext())
                .useEncryption(true)
                .initialize();

            SecurePrefManager.with(getApplicationContext())
                    .set("key1")
                    .value(true)
                    .go();

            SecurePrefManager.with(getApplicationContext())
                    .set("key2")
                    .value(123)
                    .go();

        Log.d("Values", "key1=" + SecurePrefManager.with(getApplicationContext())
                .get("key1")
                .defaultValue(false)
                .go());

        Log.d("Values","key2="+SecurePrefManager.with(getApplicationContext())
                .get("key2")
                .defaultValue(183)
                .go());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
