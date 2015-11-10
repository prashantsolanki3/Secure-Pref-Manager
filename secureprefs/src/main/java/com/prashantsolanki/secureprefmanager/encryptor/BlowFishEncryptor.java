package com.prashantsolanki.secureprefmanager.encryptor;

import android.content.Context;
import android.util.Base64;

import javax.crypto.Cipher;

/**
 * Created by Prashant on 11/7/2015.
 */
public class BlowFishEncryptor extends Encryptor {

    Cipher cipher;
    static final String algorithmName="Blowfish";

    public BlowFishEncryptor(Context context) {
        super(context,algorithmName);
        initCipher();
    }


    public BlowFishEncryptor(Context context, String encryptionKeyPhrase) {
        super(context, encryptionKeyPhrase,algorithmName);
        initCipher();
    }



    void initCipher(){
        try {
            // Create key and cipher
            cipher = Cipher.getInstance(algorithmName);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String encrypt(String value) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        byte[] bytes = cipher.doFinal(value.getBytes());
        return new String(Base64.encode(bytes, Base64.DEFAULT));
    }

    @Override
    public String decrypt(String encryptedValue) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        byte[] bytes = cipher.doFinal(Base64.decode(encryptedValue.getBytes(),Base64.DEFAULT));
        return new String(bytes);
    }
}
