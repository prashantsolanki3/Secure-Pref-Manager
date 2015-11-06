package com.prashantsolanki.secureprefmanager;

import android.content.Context;

/**
 * Created by Prashant on 11/6/2015.
 */
public abstract class Encryptor {

    Context context;

    public Encryptor(Context context) {
        this.context = context;
    }

    public abstract String encrypt(String value) throws Exception;

    public abstract String decrypt(String encryptedValue) throws Exception;

}
