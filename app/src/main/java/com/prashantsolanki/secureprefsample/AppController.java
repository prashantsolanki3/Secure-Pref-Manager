package com.prashantsolanki.secureprefsample;

import android.app.Application;

import com.prashantsolanki.secureprefmanager.SPM;
import com.prashantsolanki.secureprefmanager.SecurePrefManagerInit;
import com.prashantsolanki.secureprefmanager.encryptor.AESEncryptor;

import io.github.prashantsolanki3.shoot.Shoot;
import io.github.prashantsolanki3.shoot.listener.OnShootListener;

/**
 * Created by Prashant on 11/7/2015.
 */
public class AppController extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        final SecurePrefManagerInit.Configuration defaultConfig = new SecurePrefManagerInit.Configuration(this)
                .setCustomEncryption(new AESEncryptor(this))
                .setPreferenceFile("to_migrate");

        try {
            new SecurePrefManagerInit.Initializer(this)
                    .setDefaultConfiguration(defaultConfig)
                    .initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Shoot.with(getApplicationContext());

        Shoot.once("init", new OnShootListener() {
            @Override
            public void onExecute(int i, String s, int i1) {
                SPM.with(defaultConfig)
                        .set("String_test")
                        .value("Shit");
                SPM.with(defaultConfig)
                        .set("Int_test")
                        .value(10)
                        .go();
                SPM.with(defaultConfig)
                        .set("float")
                        .value(1.5f)
                        .go();
                SPM.with(defaultConfig)
                        .set("long")
                        .value(105l)
                        .go();
                SPM.with(defaultConfig)
                        .set("bool")
                        .value(true)
                        .go();
            }
        });

    }


}

