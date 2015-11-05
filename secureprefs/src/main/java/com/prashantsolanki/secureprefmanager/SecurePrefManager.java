package com.prashantsolanki.secureprefmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

/**
 * Created by Prashant on 11/5/2015.
 */
public class SecurePrefManager {

    // Shared Preferences
    static SharedPreferences pref;
    // Editor for Shared preferences
    static SharedPreferences.Editor editor;

    private SecurePrefManager(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
    }

    public static SecurePrefManager with(Context context){
        if(!SecurePrefManagerInit.isInit())
            throw new IllegalStateException("SecurePrefManagerInit must be initialized before calling SecurePrefManager");

        return new SecurePrefManager(context);
    }

    public Setter set(String key) throws GeneralSecurityException{
        if(SecurePrefManagerInit.isUseEncryption())
            key =new SecureString(key).getSecureString();

        return new Setter(key);
    }

    public Setter set(SecureString key) throws GeneralSecurityException{
        return new Setter(key.getSecureString());
    }

    public Getter get(SecureString key) throws GeneralSecurityException{
        return new Getter(key.getSecureString());
    }

    public Getter get(String key) throws GeneralSecurityException{
        if(SecurePrefManagerInit.isUseEncryption())
            key =new SecureString(key).getSecureString();

        return new Getter(key);
    }

    public static class Getter{
        public String key;

        public Getter(String key) {
            this.key = key;
        }

        public DefaultValueString defaultValue(String defaultValue){
            return new DefaultValueString(key,defaultValue);
        }

        public DefaultValueBoolean defaultValue(Boolean defaultValue){
            return new DefaultValueBoolean(key,defaultValue);
        }

        public DefaultValueInteger defaultValue(Integer defaultValue){
            return new DefaultValueInteger(key,defaultValue);
        }

        public DefaultValueFloat defaultValue(Float defaultValue){
            return new DefaultValueFloat(key,defaultValue);
        }

        public DefaultValueLong defaultValue(Long defaultValue){
            return new DefaultValueLong(key,defaultValue);
        }

        public static class DefaultValueString extends DefaultValue{

            String defaultValue;

            public DefaultValueString(String key, String defaultValue) {
                super(key);
                this.defaultValue = defaultValue;
            }

            public String go() throws GeneralSecurityException{
                String value;

                if(SecurePrefManagerInit.isUseEncryption()) {
                    value = new SecureString(String.valueOf(defaultValue)).getSecureString();
                    return AESCrypt.decrypt(SecurePrefManagerInit.getEncryptionPhase(), pref.getString(key, value));
                }

                return pref.getString(key, defaultValue);
            }

        }

        public static class DefaultValueFloat extends DefaultValue{

            Float defaultValue;

            public DefaultValueFloat(String key, Float defaultValue) {
                super(key);
                this.defaultValue = defaultValue;
            }

            public Float go() throws GeneralSecurityException{
                String value;
                if(SecurePrefManagerInit.isUseEncryption()) {
                    value = new SecureString(String.valueOf(defaultValue)).getSecureString();
                    return Float.parseFloat(AESCrypt.decrypt(SecurePrefManagerInit.getEncryptionPhase(), pref.getString(key, value)));
                }

                return pref.getFloat(key, defaultValue);
            }

        }

        public static class DefaultValueLong extends DefaultValue {

            Long defaultValue;

            public DefaultValueLong(String key, Long defaultValue) {
                super(key);
                this.defaultValue = defaultValue;
            }

            public Long go() throws GeneralSecurityException{
                String value;
                if(SecurePrefManagerInit.isUseEncryption()) {
                    value = new SecureString(String.valueOf(defaultValue)).getSecureString();
                    return Long.parseLong(AESCrypt.decrypt(SecurePrefManagerInit.getEncryptionPhase(), pref.getString(key, value)));
                }

                return pref.getLong(key, defaultValue);
            }

        }

        public static class DefaultValueInteger extends DefaultValue{

            Integer defaultValue;

            public DefaultValueInteger(String key, Integer defaultValue) {
                super(key);
                this.defaultValue = defaultValue;
            }

            public Integer go() throws GeneralSecurityException{
                String value;
                if(SecurePrefManagerInit.isUseEncryption()) {
                    value = new SecureString(String.valueOf(defaultValue)).getSecureString();
                    return Integer.parseInt(AESCrypt.decrypt(SecurePrefManagerInit.getEncryptionPhase(), pref.getString(key, value)));
                }

                return pref.getInt(key, defaultValue);
            }

        }

        public static class DefaultValueBoolean extends DefaultValue{

            Boolean defaultValue;

            public DefaultValueBoolean(String key, Boolean defaultValue) {
                super(key);
                this.defaultValue = defaultValue;
            }

            public Boolean go() throws GeneralSecurityException{
                String value;
                if(SecurePrefManagerInit.isUseEncryption()) {
                    value = new SecureString(String.valueOf(defaultValue)).getSecureString();
                     return Boolean.parseBoolean(AESCrypt.decrypt(SecurePrefManagerInit.getEncryptionPhase(), pref.getString(key, value)));
                }

                return pref.getBoolean(key,defaultValue);
            }

        }

        public static abstract class DefaultValue{
            String key;

            public DefaultValue(String key) {
                this.key = key;
            }
        }
    }

    public static class Setter{
        private String key;
        private String value;

        public Setter(String key) {
            if(key.length()<1)
                throw new IllegalArgumentException("Key cannot be empty");
            this.key = key;
        }

        public Setter value(String value){
            this.value=value;
            return this;
        }

        public Setter value(Boolean value){
            this.value=String.valueOf(value);
            return this;
        }

        public Setter value(Integer value){
            this.value=String.valueOf(value);
            return this;
        }

        public Setter value(Float value){
            this.value=String.valueOf(value);
            return this;
        }

        public Setter value(Long value){
            this.value=String.valueOf(value);
            return this;
        }

        public void go() throws GeneralSecurityException{
            if(value.length()<1)
                throw new IllegalArgumentException("Value cannot be empty");

            if(SecurePrefManagerInit.isUseEncryption())
                value = new SecureString(value).getSecureString();

            editor.putString(key, value);
            editor.apply();
        }
    }

}
