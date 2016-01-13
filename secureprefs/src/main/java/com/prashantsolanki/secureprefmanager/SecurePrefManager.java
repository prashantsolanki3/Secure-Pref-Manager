package com.prashantsolanki.secureprefmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.prashantsolanki.secureprefmanager.utils.HidePreferences;
import com.prashantsolanki.secureprefmanager.utils.SecureString;

import static com.prashantsolanki.secureprefmanager.SecurePrefManagerInit.Configuration;
import static com.prashantsolanki.secureprefmanager.SecurePrefManagerInit.isInit;


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

    Configuration configuration=null;

    protected SecurePrefManager() {
    }

    /**
     * Uses the default Encryption with provided file name.
     *@param fileName Preference File Name
     **/
    protected SecurePrefManager(Context context,@Nullable String fileName) {
        this.context=context;

        if(fileName==null)
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        else
            pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        configuration = SecurePrefManagerInit.getDefaultConfiguration();
        editor = pref.edit();
    }

    protected SecurePrefManager(Context context) {
        this.context=context;
        configuration = SecurePrefManagerInit.getDefaultConfiguration();
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
    }


    protected SecurePrefManager(Configuration configuration) {

        this.context = configuration.getContext();
        this.configuration = configuration;

        if(configuration.getPreferenceFile()!=null&&!configuration.getPreferenceFile().isEmpty())
            pref = context.getSharedPreferences(configuration.getPreferenceFile(), Context.MODE_PRIVATE);
        else
            pref = PreferenceManager.getDefaultSharedPreferences(context);

        editor = pref.edit();
    }

    //TODO: Make a constructor with Configuration param.
    /**Uses the default Configurations set in SecurePreferenceManagerInit.*/
    public static SecurePrefManager with(Context context){
        if(!isInit())
            throw new IllegalStateException("SecurePrefManagerInit must be initialized before calling SecurePrefManager");

        return new SecurePrefManager(context);
    }

    @Deprecated
    public static SecurePrefManager with(Context context,String preferenceFileName){
        if(!isInit())
            throw new IllegalStateException("SecurePrefManagerInit must be initialized before calling SecurePrefManager");

        return new SecurePrefManager(context,preferenceFileName);
    }

    public static SecurePrefManager with(Configuration configuration){
        return new SecurePrefManager(configuration);
    }

    public Deleter clear(){
        return new Deleter(null);
    }

    public Deleter remove(String key){
        try {
            return new Deleter(configuration.getEncryptor().encrypt(key));
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
            return new Setter(configuration.getEncryptor().encrypt(key),this);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public Setter set(SecureString key) {
        try{
        return new Setter(key.getSecureString(),this);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public Getter get(SecureString key){
        try {
            return new Getter(key.getSecureString(),this);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public Getter get(String key) {
        try{
        return new Getter(configuration.getEncryptor().encrypt(key),this);
    }catch (Exception e){
        e.printStackTrace();
            return null;
        }
    }

    public static class Getter{
        public String key;
        private SecurePrefManager manager;

        public Getter(String key, SecurePrefManager manager) {
            this.key = key;
            this.manager = manager;
        }

        public DefaultValueString defaultValue(String defaultValue){
            return new DefaultValueString(key,manager,defaultValue);
        }

        public DefaultValueBoolean defaultValue(Boolean defaultValue){
            return new DefaultValueBoolean(key,manager,defaultValue);
        }

        public DefaultValueInteger defaultValue(Integer defaultValue){
            return new DefaultValueInteger(key,manager,defaultValue);
        }

        public DefaultValueFloat defaultValue(Float defaultValue){
            return new DefaultValueFloat(key,manager,defaultValue);
        }

        public DefaultValueLong defaultValue(Long defaultValue){
            return new DefaultValueLong(key,manager,defaultValue);
        }

        public static class DefaultValueString extends DefaultValue{

            String defaultValue;

            public DefaultValueString(String key, SecurePrefManager manager, String defaultValue) {
                super(key, manager);
                this.defaultValue = defaultValue;
            }

            public String go() {
                try {
                    return manager.configuration.getEncryptor()
                            .decrypt(pref.getString(key,
                                    manager.configuration.getEncryptor()
                                            .encrypt(String.valueOf(defaultValue))));
                }catch (Exception e){
                    e.printStackTrace();
                    return defaultValue;
                }
            }

        }

        public static class DefaultValueFloat extends DefaultValue{

            Float defaultValue;

            public DefaultValueFloat(String key, SecurePrefManager manager, Float defaultValue) {
                super(key, manager);
                this.defaultValue = defaultValue;
            }

            public Float go(){
             try {
                    return Float.parseFloat(manager.configuration.getEncryptor()
                            .decrypt(pref.getString(key,
                                    manager.configuration.getEncryptor()
                                            .encrypt(String.valueOf(defaultValue)))));
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            }

            }


        public static class DefaultValueLong extends DefaultValue {

            Long defaultValue;

            public DefaultValueLong(String key, SecurePrefManager manager, Long defaultValue) {
                super(key, manager);
                this.defaultValue = defaultValue;
            }

            public Long go(){
             try {
                    return Long.parseLong(manager.configuration.getEncryptor()
                            .decrypt(pref.getString(key,
                                    manager.configuration.getEncryptor()
                                            .encrypt(String.valueOf(defaultValue)))));
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            }

        }

        public static class DefaultValueInteger extends DefaultValue{

            Integer defaultValue;

            public DefaultValueInteger(String key, SecurePrefManager manager, Integer defaultValue) {
                super(key, manager);
                this.defaultValue = defaultValue;
            }

            public Integer go(){
                    try {
                        return Integer.parseInt(manager.configuration.getEncryptor()
                                .decrypt(pref.getString(key,
                                        manager.configuration.getEncryptor()
                                                .encrypt(String.valueOf(defaultValue)))));
                    }catch (Exception e){
                        e.printStackTrace();
                    return null;
                }
            }

        }

        public static class DefaultValueBoolean extends DefaultValue{

            Boolean defaultValue;

            public DefaultValueBoolean(String key, SecurePrefManager manager, Boolean defaultValue) {
                super(key, manager);
                this.defaultValue = defaultValue;
            }

            public Boolean go(){
                try {
                     return Boolean.parseBoolean(manager.configuration.getEncryptor().decrypt(pref.getString(key, manager.configuration.getEncryptor()
                             .encrypt(String.valueOf(defaultValue)))));
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
                }

        }

        public static abstract class DefaultValue{
            String key;
            SecurePrefManager manager;

            public DefaultValue(String key, SecurePrefManager manager) {
                this.key = key;
                this.manager = manager;
            }
        }
    }

    public static class Setter{
        private String key;
        private String value;
        SecurePrefManager manager;

        public Setter(String key,SecurePrefManager manager) {
            if(key.length()<1)
                throw new IllegalArgumentException("Key cannot be empty");
            this.key = key;
            this.manager= manager;
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
                value = manager.configuration.getEncryptor().encrypt(value);
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
