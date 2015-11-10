package com.prashantsolanki.secureprefsample;

import android.app.Application;

import com.prashantsolanki.secureprefmanager.SecurePrefManager;
import com.prashantsolanki.secureprefmanager.SecurePrefManagerInit;

/**
 * Created by Prashant on 11/7/2015.
 */
public class AppController extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        new SecurePrefManagerInit.Initializer(this)
                .useEncryption(true)
                .initialize();




        for(int i =0;i<25;++i) {
            SecurePrefManager.with(getApplicationContext())
                    .set("String" + i)
                    .value("value" + i)
                    .go();

            SecurePrefManager.with(getApplicationContext())
                    .set("int" + i)
                    .value(i)
                    .go();

            SecurePrefManager.with(getApplicationContext())
                    .set("bool" + i)
                    .value(i % 2 == 0)
                    .go();

            SecurePrefManager.with(getApplicationContext())
                    .set("long" + i)
                    .value(Long.valueOf("" + i))
                    .go();

        }





    }


}

