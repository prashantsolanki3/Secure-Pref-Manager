package com.prashantsolanki.secureprefsample;

import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.prashantsolanki.secureprefmanager.SecurePrefManager;
import com.prashantsolanki.secureprefmanager.SecurePrefManagerInit;
import com.prashantsolanki.secureprefmanager.encryptor.BlowFishEncryptor;
import com.prashantsolanki.secureprefmanager.utils.HidePreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }



    @Override
    protected void onStart() {
        super.onStart();
        SecurePrefManager.with(getApplicationContext())
                .unhide(new HidePreferences.PreferenceUpdateListener() {
                    @Override
                    public void onFailure() {
                        Log.d("unhiding", "Failed");
                    }

                    @Override
                    public void onProgress(int p, int max) {
                        Log.d("unhiding", "Progress: " + p + "/" + max);
                    }

                    @Override
                    public void onSuccess() {
                        Log.d("unhiding", "Success");
                    }
                });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        SecurePrefManager.with(getApplicationContext())
                .hide(new HidePreferences.PreferenceUpdateListener() {
                    @Override
                    public void onFailure() {

                    }

                    @Override
                    public void onProgress(int p, int max) {

                    }

                    @Override
                    public void onSuccess() {

                    }
                });
        super.onPause();
    }
}
