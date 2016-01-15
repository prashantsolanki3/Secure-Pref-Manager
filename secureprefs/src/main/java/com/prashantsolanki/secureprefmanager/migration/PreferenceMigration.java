package com.prashantsolanki.secureprefmanager.migration;

import android.support.annotation.NonNull;

import com.prashantsolanki.secureprefmanager.SPM;

import io.github.prashantsolanki3.shoot.Shoot;
import io.github.prashantsolanki3.shoot.listener.OnShootListener;

import static com.prashantsolanki.secureprefmanager.SecurePrefManagerInit.Configuration;

/**
 * Package com.prashantsolanki.secureprefmanager
 * <p>
 * Created by Prashant on 1/12/2016.
 * <p>
 * Email: solankisrp2@gmail.com
 * Github: @prashantsolanki3
 */
public class PreferenceMigration {

    Configuration oldConfig=null,newConfig=null;

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
                        .value(getValue(key, defaultValue))
                        .go();
                removePreference(key);
            }
        });
        return this;
    }

    /*public void migrateAll(final MigrationListener listener){
        Shoot.once(oldConfig.getPreferenceFile() + newConfig.getPreferenceFile(), new OnShootListener() {
            @Override
            public void onExecute(int a, String s, int a1) {
                Map map = SPM.with(oldConfig)
                        .getAllPreferences();
                int i = 0;

                for (Object o : map.keySet()) {
                    i++;


                    if (map.get(o) instanceof Integer)
                        migrate((String) o, (Integer) map.get(o));

                    else if (map.get(o) instanceof Long)
                        migrate((String) o,(Long) map.get(o));

                    else if (map.get(o) instanceof Boolean)
                        migrate((String) o, (Boolean) map.get(o));

                    else if (map.get(o) instanceof Float)
                        migrate((String) o, (Float) map.get(o));

                    if (map.get(o) instanceof String)
                        migrate((String) o, (String) map.get(o));
                    else
                        Log.e("Preference Migration","Type not supported");

                    listener.onPreferenceMigrated((String) o, getValue((String) o, (String) map.get(o)), i, map.keySet().size());
                }

                listener.onComplete(i);
            }
        });

    }*/


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

/*    private String getValue(String key, Object defaultValue){
        return SPM.with(oldConfig)
                .get(key)
                .defaultValue(defaultValue)
                .go();
    }*/

    private Float getValue(String key, Float defaultValue){
        return SPM.with(oldConfig)
                .get(key)
                .defaultValue(defaultValue)
                .go();
    }

    private PreferenceMigration(@NonNull Configuration oldConfig,
                                @NonNull Configuration newConfig) {
        this.oldConfig = oldConfig;
        this.newConfig = newConfig;
        Shoot.with(newConfig.getContext());
    }

    public interface MigrationListener{
        void onPreferenceMigrated(String key, Object value,int current,int total);
        void onComplete(int totalMigrations);
    }

    /**
     * Builder
     * */
    public static class Builder {

        Configuration oldConfig=null,newConfig=null;

        public Builder setOldConfiguration(@NonNull Configuration oldConfig) {
            this.oldConfig = oldConfig;
            return this;
        }

        public Builder setNewConfiguration(@NonNull Configuration newConfig) {
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
