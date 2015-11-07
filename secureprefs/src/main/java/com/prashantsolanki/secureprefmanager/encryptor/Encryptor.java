package com.prashantsolanki.secureprefmanager.encryptor;

import android.content.Context;
import android.util.Base64;

import java.nio.charset.Charset;
import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Prashant on 11/6/2015.
 */
public abstract class Encryptor {

    final byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    Context context;
    final String passPhrase;
    private Key key;

    public Encryptor(Context context) {
        this.context = context;
        this.passPhrase = Base64.encodeToString(context.getApplicationInfo().packageName.getBytes(Charset.forName("UTF-8")), Base64.NO_PADDING);
        generateKey();
    }


    public Encryptor(Context context,String passPhrase) {
        this.passPhrase = passPhrase;
        this.context = context;
        generateKey();
    }

    void generateKey(){
        try {
            // Create key and cipher
            key = new SecretKeySpec(passPhrase.getBytes(Charset.forName("UTF-8")), "Blowfish");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Key getKey() {
        return key;
    }

    public abstract String encrypt(String value) throws Exception;

    public abstract String decrypt(String encryptedValue) throws Exception;

}
