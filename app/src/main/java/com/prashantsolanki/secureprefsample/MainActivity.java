package com.prashantsolanki.secureprefsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
        /*SecurePrefManager.with(getApplicationContext())
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
                });*/

    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        /*SecurePrefManager.with(getApplicationContext())
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
                });*/

    }
}
