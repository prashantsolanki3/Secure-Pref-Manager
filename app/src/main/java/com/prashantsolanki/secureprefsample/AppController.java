package com.prashantsolanki.secureprefsample;

import android.app.Application;

import com.prashantsolanki.secureprefmanager.SPM;
import com.prashantsolanki.secureprefmanager.SecurePrefManager;
import com.prashantsolanki.secureprefmanager.SecurePrefManagerInit;
import com.prashantsolanki.secureprefmanager.encryptor.BlowFishEncryptor;

/**
 * Created by Prashant on 11/7/2015.
 */
public class AppController extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        try {

            new SecurePrefManagerInit.Initializer(this)
                    .useEncryption(true)
                    .setCustomEncryption(new BlowFishEncryptor(getApplicationContext()))
                    .initialize();

        } catch (Exception e) {
            e.printStackTrace();
        }


        SecurePrefManager.with(getApplicationContext())
                .clear()
                .confirm();

        SecurePrefManager.with(getApplicationContext(),"yo")
                .clear()
                .confirm();

        SecurePrefManager.with(getApplicationContext(),"whatsup")
                .clear()
                .confirm();


        for(int i =0;i<25;++i) {
            SecurePrefManager.with(getApplicationContext(),"yo")
                    .set("String" + i)
                    .value("value" + i)
                    .go();

            SecurePrefManager.with(getApplicationContext(),"whatsup")
                    .set("int" + i)
                    .value(i)
                    .go();

            SecurePrefManager.with(getApplicationContext(),"yeaaah")
                    .set("bool" + i)
                    .value(i % 2 == 0)
                    .go();

            SecurePrefManager.with(getApplicationContext())
                    .set("long" + i)
                    .value(Long.valueOf("" + i))
                    .go();

            SPM.with(new SecurePrefManagerInit.Configuration(getApplicationContext())
                    .setPreferenceFile("config"))
            .set("config_test"+i)
            .value(i)
            .go();

        }





    }


}

