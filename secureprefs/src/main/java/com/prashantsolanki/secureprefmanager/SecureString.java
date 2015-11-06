package com.prashantsolanki.secureprefmanager;

/**
 * Created by Prashant on 11/5/2015.
 */
public class SecureString {

    private String string;

    public SecureString(String string) throws Exception{
        this.string = SecurePrefManagerInit.getEncryptor().encrypt(string);
    }

    public String getSecureString() {
        return string;
    }

}
