package com.prashantsolanki.secureprefmanager;

import android.content.Context;

import com.prashantsolanki.secureprefmanager.encryptor.BlankEncryptor;
import com.prashantsolanki.secureprefmanager.encryptor.Encryptor;

import java.util.HashSet;
import java.util.Set;

/**
 * Package com.prashantsolanki.secureprefmanager
 * <p>
 * Created by Prashant on 1/12/2016.
 * <p>
 * Email: solankisrp2@gmail.com
 * Github: @prashantsolanki3
 */
public class PreferenceMigration {

    Context context;
    String migrationId;
    Encryptor oldEncryption,newEncryption;
    String oldPreferenceFile,newPreferenceFile;


    public Migrator setMigrationId(String migrationId){
        this.migrationId = migrationId;
        return new Migrator();
    }

    private PreferenceMigration( Context context, Encryptor oldEncryption, Encryptor newEncryption, String oldPreferenceFile, String newPreferenceFile) {

        if(oldEncryption==null)
            this.oldEncryption = new BlankEncryptor(context);
        else
            this.oldEncryption = oldEncryption;

        if (newEncryption!=null)
            this.newEncryption = newEncryption;
        else
            this.newEncryption = new BlankEncryptor(context);

        this.oldPreferenceFile = oldPreferenceFile;
        this.newPreferenceFile = newPreferenceFile;

        this.context = context;
    }

    public class Migrator {

        Set<String> keys;
        private MigrationListener listener=null;

        public Migrator() {
            this.keys = new HashSet<>();
        }

        //TODO: make all the default value methods.
        //TODO: try HashMap for multiple preference. <KEY,DEFAULTVALUE(Object)>
        //TODO: make a method to check the instance of defaultValue and return the corresponding object.
        //TODO: make fetching and setting methods and implement listeners
        public Migrator setPreferenceKey(String key,boolean defaultValue){
            this.keys.add(key);
            return this;
        }

        public Migrator setPreferenceKeys(Set<String> keys){
            this.keys.addAll(keys);
            return this;
        }

        public void migrate(){

        }

        public void migrate(MigrationListener listener){

        }

        private void internalMigrate(){
            for(String key:keys){

                //Init Using old Encryptor and File.
                //Fetch old value.

                //Switch to new Encryptor and same or defined file.
                //Set the new value.

                //Delete previous values.
            }
        }

        private void fetch(){
            SecurePrefManager.with(context,oldPreferenceFile)
                    .get("")
                    .defaultValue(true)
                    .go();
        }

        private void initSPMOld() throws Exception{
           new SecurePrefManagerInit.Initializer(context)
                   .useEncryption(true)
                   .setCustomEncryption(oldEncryption)
                   .initialize();
        }

        private void initSPMNew() throws Exception{
            new SecurePrefManagerInit.Initializer(context)
                    .useEncryption(true)
                    .setCustomEncryption(newEncryption)
                    .initialize();
        }
    }

    public interface MigrationListener{
       void progress(int current,int total);
    }


    public static class Builder {

        Context context=null;
        Encryptor oldEncryption=null,newEncryption=null;
        String oldPreferenceFile=null,newPreferenceFile=null;

        public Builder(Context mContext) {
            context = mContext;
        }
        /** Optional: Sets the old Encryption Technique used to store the old preferences. No Encryption used if not specified.*/
        public Builder setOldEncryption(Encryptor oldEncryption) {
            this.oldEncryption = oldEncryption;
            return this;
        }
        /** Optional: Sets the new Encryption Technique. No Encryption done if not specified.*/
        public Builder setNewEncryption(Encryptor newEncryption) {
            this.newEncryption = newEncryption;
            return this;
        }

        /** Optional: The File where Preferences are store. Default preference file used if not specified*/
        public Builder setOldPreferenceFile(String oldPreferenceFile) {
            this.oldPreferenceFile = oldPreferenceFile;
            return this;
        }

        /** Optional: Preference Saved to same file if newPreferenceFile is not specified.*/
        public Builder setNewPreferenceFile(String newPreferenceFile) {
            this.newPreferenceFile = newPreferenceFile;
            return this;
        }

        public PreferenceMigration build(){
            return new PreferenceMigration(context,oldEncryption,newEncryption,oldPreferenceFile,newPreferenceFile);
        }
    }

}
