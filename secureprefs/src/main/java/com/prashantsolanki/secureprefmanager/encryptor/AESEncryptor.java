package com.prashantsolanki.secureprefmanager.encryptor;

import android.content.Context;
import android.util.Base64;

import com.scottyab.aescrypt.AESCrypt;

import javax.crypto.Cipher;

/**
 *
 * Created by Prashant on 11/6/2015.
 */
public class AESEncryptor extends Encryptor{

    public AESEncryptor(Context context) {
        super(context);
    }

    public AESEncryptor(Context context,String encryptionKeyPhrase) {
        super(context,encryptionKeyPhrase);
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
