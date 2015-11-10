package com.prashantsolanki.secureprefmanager.encryptor;

import android.content.Context;

/**
 * Created by Prashant on 11/6/2015.
 */
public class BlankEncryptor extends Encryptor{

    public BlankEncryptor(Context context) {
        super(context,"");
    }

    @Override
    public String encrypt(String value) throws Exception {
        return value;
    }

    @Override
    public String decrypt(String encryptedValue) throws Exception {
        return encryptedValue;
    }
}
