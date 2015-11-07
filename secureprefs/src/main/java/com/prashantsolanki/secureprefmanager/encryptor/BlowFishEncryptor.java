package com.prashantsolanki.secureprefmanager.encryptor;

import android.content.Context;
import android.util.Base64;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Prashant on 11/7/2015.
 */
public class BlowFishEncryptor extends Encryptor {

    Cipher cipher;

    public BlowFishEncryptor(Context context) {
        super(context);
        initCipher();
    }


    public BlowFishEncryptor(Context context, String encryptionKeyPhrase) {
        super(context, encryptionKeyPhrase);
        initCipher();
    }



    void initCipher(){
        try {
            // Create key and cipher
            cipher = Cipher.getInstance("Blowfish");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String encrypt(String value) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        byte[] hasil = cipher.doFinal(value.getBytes());
        return new String(Base64.encode(hasil, Base64.DEFAULT));
    }

    @Override
    public String decrypt(String encryptedValue) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        byte[] hasil = cipher.doFinal(Base64.decode(encryptedValue.getBytes(),Base64.DEFAULT));
        return new String(hasil);
    }
}
