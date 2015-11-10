package com.prashantsolanki.secureprefmanager.encryptor;

import android.content.Context;

import com.scottyab.aescrypt.AESCrypt;

/**
 *
 * Created by Prashant on 11/6/2015.
 */
public class AESEncryptor extends Encryptor{

    public AESEncryptor(Context context) {
        super(context,"AES");
    }

    public AESEncryptor(Context context,String encryptionKeyPhrase) {
        super(context,encryptionKeyPhrase,"AES");
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
