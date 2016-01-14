package com.prashantsolanki.secureprefmanager.migration;

import android.support.annotation.NonNull;

import com.prashantsolanki.secureprefmanager.SPM;
import com.prashantsolanki.secureprefmanager.SecurePrefManagerInit;

import java.util.Map;

import io.github.prashantsolanki3.shoot.Shoot;
import io.github.prashantsolanki3.shoot.listener.OnShootListener;

/**
 * Package com.prashantsolanki.secureprefmanager
 * <p>
 * Created by Prashant on 1/12/2016.
 * <p>
 * Email: solankisrp2@gmail.com
 * Github: @prashantsolanki3
 */
public class PreferenceMigration {

    String migrationId;
    SecurePrefManagerInit.Configuration oldConfig=null,newConfig=null;

/*    public Migrator setMigrationId(String migrationId){
        this.migrationId = migrationId;
        return new Migrator();
    }*/

    public PreferenceMigration migrate(final String key, final Integer defaultValue){
        Shoot.once(key, new OnShootListener() {
            @Override
            public void onExecute(int scope, String TAG, int iteration) {
                SPM.with(newConfig)
                        .set(key)
                        .value(getValue(key,defaultValue))
                        .go();
                removePreference(key);
            }
        });
        return this;
    }

    public PreferenceMigration migrate(final String key, final Float defaultValue){
        Shoot.once(key, new OnShootListener() {
            @Override
            public void onExecute(int scope, String TAG, int iteration) {
                SPM.with(newConfig)
                        .set(key)
                        .value(getValue(key,defaultValue))
                        .go();
                removePreference(key);
            }
        });
        return this;
    }

    public PreferenceMigration migrate(final String key, final String defaultValue){
        Shoot.once(key, new OnShootListener() {
            @Override
            public void onExecute(int scope, String TAG, int iteration) {
                SPM.with(newConfig)
                        .set(key)
                        .value(getValue(key,defaultValue))
                        .go();
                removePreference(key);
            }
        });
        return this;
    }

    public PreferenceMigration migrate(final String key, final Long defaultValue){
        Shoot.once(key, new OnShootListener() {
            @Override
            public void onExecute(int scope, String TAG, int iteration) {
                SPM.with(newConfig)
                        .set(key)
                        .value(getValue(key,defaultValue))
                        .go();
                removePreference(key);
            }
        });
        return this;
    }

    public PreferenceMigration migrate(final String key, final Boolean defaultValue){
        Shoot.once(key, new OnShootListener() {
            @Override
            public void onExecute(int scope, String TAG, int iteration) {
                SPM.with(newConfig)
                        .set(key)
                        .value(getValue(key,defaultValue))
                        .go();
                removePreference(key);
            }
        });
        return this;
    }

    public void migrateAll(MigrationListener listener){
        Map map = SPM.with(oldConfig)
                .getAllPreferences();
        for(Object o:map.keySet()){
            if(map.get(o) instanceof Integer||
                    map.get(o) instanceof String||
                    map.get(o) instanceof Long||
                    map.get(o) instanceof Boolean||
                    map.get(o) instanceof Float){
                //TODO: Complete migrateAll
                // TODO: Support Objects in Default Value and Support To serialize objects and save in preferences.
                /*SPM.with(newConfig)
                        .set((String)o)
                        .value(getValue((String)o,map.get(o)))
                        .go();*/

                removePreference((String)o);
            }
        }
    }


    private void removePreference(String key){
        SPM.with(oldConfig)
                .remove(key)
                .confirm();
    }

    private Integer getValue(String key,Integer defaultValue){
       return SPM.with(oldConfig)
                .get(key)
                .defaultValue(defaultValue)
                .go();
    }

    private String getValue(String key,String defaultValue){
        return SPM.with(oldConfig)
                .get(key)
                .defaultValue(defaultValue)
                .go();
    }

    private Boolean getValue(String key,Boolean defaultValue){
        return SPM.with(oldConfig)
                .get(key)
                .defaultValue(defaultValue)
                .go();
    }

    private Long getValue(String key,Long defaultValue){
        return SPM.with(oldConfig)
                .get(key)
                .defaultValue(defaultValue)
                .go();
    }

    private Float getValue(String key, Float defaultValue){
        return SPM.with(oldConfig)
                .get(key)
                .defaultValue(defaultValue)
                .go();
    }

    private PreferenceMigration(@NonNull SecurePrefManagerInit.Configuration oldConfig,
                                @NonNull SecurePrefManagerInit.Configuration newConfig) {
        this.oldConfig = oldConfig;
        this.newConfig = newConfig;
        Shoot.with(newConfig.getContext());
    }

    public abstract class MigrationListener{
        abstract void progress(int current,int total);
        private void internalMigrate(){
            //Init Using oldConfig
            //Fetch old value.

            //Switch to new Encryptor and same or defined file.
            //Set the new value.

            //Delete previous values.
        }
    }

    /**
     * Builder
     * */
    public static class Builder {

        SecurePrefManagerInit.Configuration oldConfig=null,newConfig=null;

        public Builder setOldConfiguration(@NonNull SecurePrefManagerInit.Configuration oldConfig) {
            this.oldConfig = oldConfig;
            return this;
        }

        public Builder setNewConfiguration(@NonNull SecurePrefManagerInit.Configuration newConfig) {
            this.newConfig = newConfig;
            return this;
        }

        public PreferenceMigration build(){

            if(newConfig==null||oldConfig==null)
                throw new RuntimeException("Configurations cannot be null");

            return new PreferenceMigration(oldConfig,newConfig);
        }
    }

}
