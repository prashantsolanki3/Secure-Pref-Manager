package com.prashantsolanki.secureprefmanager;

import android.content.Context;
import android.util.Base64;

import com.scottyab.aescrypt.AESCrypt;

/**
 * Created by Prashant on 11/6/2015.
 */
public class AESEncryptor extends Encryptor{

    private final String passPhrase;

    public AESEncryptor(Context context) {
        super(context);
        this.passPhrase = Base64.encodeToString(context.getApplicationInfo().packageName.getBytes(),Base64.DEFAULT);
    }

    @Override
    public String encrypt(String value) throws Exception{
        return AESCrypt.encrypt(passPhrase, value);
    }

    @Override
    public String decrypt(String encryptedValue) throws Exception{
        return AESCrypt.decrypt(passPhrase, encryptedValue);
    }

}
