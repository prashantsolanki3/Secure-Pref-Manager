package com.prashantsolanki.secureprefmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.prashantsolanki.secureprefmanager.utils.HidePreferences;
import com.prashantsolanki.secureprefmanager.utils.SecureString;


/**
 *
 * Created by Prashant on 11/5/2015.
 */
public class SecurePrefManager {

    // Shared Preferences
    static SharedPreferences pref;
    // Editor for Shared preferences
    static SharedPreferences.Editor editor;
    public static boolean isHidden;

    private Context context;

    private SecurePrefManager(Context context,@Nullable String fileName,@Nullable Integer mode) {
        this.context=context;
        if(fileName==null)
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        else if (mode !=null)
            pref = context.getSharedPreferences(fileName, mode);
        else
            pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        editor = pref.edit();
    }

    public static SecurePrefManager with(Context context){
        if(!SecurePrefManagerInit.isInit())
            throw new IllegalStateException("SecurePrefManagerInit must be initialized before calling SecurePrefManager");

        return new SecurePrefManager(context,null,null);
    }

    public static SecurePrefManager with(Context context,String preferenceFileName){
        if(!SecurePrefManagerInit.isInit())
            throw new IllegalStateException("SecurePrefManagerInit must be initialized before calling SecurePrefManager");

        return new SecurePrefManager(context,preferenceFileName,Context.MODE_PRIVATE);
    }
    public Deleter clear(){
        return new Deleter(null);
    }

    public Deleter remove(String key){
        try {
            return new Deleter(SecurePrefManagerInit.getEncryptor().encrypt(key));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void hide(HidePreferences.PreferenceUpdateListener listener){

        new HidePreferences(context,true,listener);
    }

    public void unhide(HidePreferences.PreferenceUpdateListener listener){
        new HidePreferences(context,false,listener);
    }

    public Setter set(String key){
        try {
            return new Setter(SecurePrefManagerInit.getEncryptor().encrypt(key));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Setter set(SecureString key) {
        try{
        return new Setter(key.getSecureString());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Getter get(SecureString key){
        try {
            return new Getter(key.getSecureString());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public Getter get(String key) {
        try{
        return new Getter(SecurePrefManagerInit.getEncryptor().encrypt(key));
    }catch (Exception e){
        e.printStackTrace();
            return null;
        }
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
            public String go() {
                try {
                    return SecurePrefManagerInit.getEncryptor()
                            .decrypt(pref.getString(key,
                                    SecurePrefManagerInit.getEncryptor()
                                            .encrypt(String.valueOf(defaultValue))));
                }catch (Exception e){
                    e.printStackTrace();
                    return defaultValue;
                }
            }

        }

        public static class DefaultValueFloat extends DefaultValue{

            Float defaultValue;

            public DefaultValueFloat(String key, Float defaultValue) {
                super(key);
                this.defaultValue = defaultValue;
            }

            public Float go(){
             try {
                    return Float.parseFloat(SecurePrefManagerInit.getEncryptor()
                            .decrypt(pref.getString(key,
                                    SecurePrefManagerInit.getEncryptor()
                                            .encrypt(String.valueOf(defaultValue)))));
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            }

            }


        public static class DefaultValueLong extends DefaultValue {

            Long defaultValue;

            public DefaultValueLong(String key, Long defaultValue) {
                super(key);
                this.defaultValue = defaultValue;
            }
            public Long go(){
             try {
                    return Long.parseLong(SecurePrefManagerInit.getEncryptor()
                            .decrypt(pref.getString(key,
                                    SecurePrefManagerInit.getEncryptor()
                                            .encrypt(String.valueOf(defaultValue)))));
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            }

        }

        public static class DefaultValueInteger extends DefaultValue{

            Integer defaultValue;

            public DefaultValueInteger(String key, Integer defaultValue) {
                super(key);
                this.defaultValue = defaultValue;
            }

            public Integer go(){
                    try {
                        return Integer.parseInt(SecurePrefManagerInit.getEncryptor()
                                .decrypt(pref.getString(key,
                                        SecurePrefManagerInit.getEncryptor()
                                                .encrypt(String.valueOf(defaultValue)))));
                    }catch (Exception e){
                        e.printStackTrace();
                    return null;
                }
            }

        }

        public static class DefaultValueBoolean extends DefaultValue{

            Boolean defaultValue;

            public DefaultValueBoolean(String key, Boolean defaultValue) {
                super(key);
                this.defaultValue = defaultValue;
            }

            public Boolean go(){
                try {
                     return Boolean.parseBoolean(SecurePrefManagerInit.getEncryptor().decrypt(pref.getString(key, SecurePrefManagerInit.getEncryptor()
                             .encrypt(String.valueOf(defaultValue)))));
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
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

        /**
         * Sets the the given value to preferences.
         * */
        public void go(){

            if(value.length()<1)
                throw new IllegalArgumentException("Value cannot be empty");

                try{
                value = SecurePrefManagerInit.getEncryptor().encrypt(value);
                }catch (Exception e){
                    e.printStackTrace();
                    throw new IllegalArgumentException();
                }

            editor.putString(key, value);
            editor.apply();
        }
    }


    public static class Deleter{

        final String valueToBeDeleted;

        public Deleter(@Nullable String valueToBeDeleted) {
            this.valueToBeDeleted = valueToBeDeleted;
        }

        /**
         * Confirm deletion.
         * */
        public void confirm(){

            if(valueToBeDeleted!=null)
                editor.remove(valueToBeDeleted);
            else
                editor.clear();

            editor.apply();

        }
    }

}
