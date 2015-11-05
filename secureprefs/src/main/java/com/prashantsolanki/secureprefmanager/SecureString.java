package com.prashantsolanki.secureprefmanager;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

/**
 * Created by Prashant on 11/5/2015.
 */
public class SecureString {

    private String string;

    public SecureString(String string) throws GeneralSecurityException{
        this.string = AESCrypt.encrypt(SecurePrefManagerInit.getEncryptionPhrase(), string);
    }

    public String getSecureString() {
        return string;
    }

}
