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

    Context context;
    final String passPhrase;
    private Key key;
    private String algorithmName;

    public Encryptor(Context context,String algorithmName) {
        this.context = context;
        this.algorithmName = algorithmName;
        this.passPhrase = Base64.encodeToString(context.getApplicationInfo().packageName.getBytes(Charset.forName("UTF-8")), Base64.NO_PADDING);

        generateKey(algorithmName);
    }


    public Encryptor(Context context,String passPhrase,String algorithmName) {
        this.passPhrase = passPhrase;
        this.context = context;
        this.algorithmName = algorithmName;
        generateKey(algorithmName);
    }


    void generateKey(String algorithmName){
        try {
            // Create key and cipher
            key = new SecretKeySpec(passPhrase.getBytes(Charset.forName("UTF-8")), algorithmName);
        }catch(Exception e) {
            e.printStackTrace();
            key=null;
        }
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public Key getKey() {
        return key;
    }

    public abstract String encrypt(String value) throws Exception;

    public abstract String decrypt(String encryptedValue) throws Exception;

}
