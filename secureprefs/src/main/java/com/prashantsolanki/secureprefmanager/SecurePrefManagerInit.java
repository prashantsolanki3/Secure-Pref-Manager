package com.prashantsolanki.secureprefmanager;

import android.content.Context;
import android.support.annotation.Nullable;

import com.prashantsolanki.secureprefmanager.encryptor.AESEncryptor;
import com.prashantsolanki.secureprefmanager.encryptor.BlankEncryptor;
import com.prashantsolanki.secureprefmanager.encryptor.Encryptor;

/**
 *
 * Created by Prashant on 11/5/2015.
 */
public class SecurePrefManagerInit {

    private static Configuration configuration=null;

    public static Encryptor getEncryptor() {
        return configuration.getEncryptor();
    }

    public static Context getContext() {
        return configuration.getContext();
    }

    public static Configuration getDefaultConfiguration() {
        return configuration;
    }

    public static void clear(){
        configuration =null;
    }

    public static String getDefaultPreferenceFile(){
        return configuration.getPreferenceFile();
    }

    public static boolean isInit() {
        return configuration!=null;
    }

    public static class Initializer {

        private Configuration configuration=null;
        private boolean useEncryption;

        public Initializer(Context context) {
            configuration = new Configuration(context);
        }

        @Deprecated
        public Initializer useEncryption(boolean useEncryption){
            this.useEncryption=useEncryption;
            return this;
        }

        public Configuration initialize() throws Exception{
            if(!useEncryption&&configuration.getEncryptor()==null)
                //No Encryption
                configuration.setCustomEncryption(new BlankEncryptor(configuration.getContext()));
           else if(configuration.getEncryptor()==null)
                //Default Encryption
                configuration.setCustomEncryption(new AESEncryptor(configuration.getContext()));

            SecurePrefManagerInit.configuration = configuration;

            return SecurePrefManagerInit.configuration;
        }

        public Initializer setCustomEncryption(Encryptor encryptor){
            this.configuration.setCustomEncryption(encryptor);
            return this;
        }

        public Initializer setPreferenceFileName(String preferenceFileName){
            configuration.setPreferenceFile(preferenceFileName);
            return this;
        }

        public Initializer setDefaultConfiguration(Configuration defaultConfiguration){
            configuration=defaultConfiguration;
            return this;
        }
    }

    public static class Configuration{

        private Context context=null;
        private Encryptor encryptor=null;
        private String preferenceFile=null;

        public Configuration(Context context) {
            this.context = context;
        }

        public Configuration setCustomEncryption(@Nullable Encryptor encryptor){
            this.encryptor = encryptor;
            return this;
        }

        public Configuration setPreferenceFile(@Nullable String preferenceFileName){
            this.preferenceFile= preferenceFileName;
            return this;
        }

        public Context getContext() {
            return context;
        }

        public Encryptor getEncryptor() {
            if(encryptor==null)
                encryptor = new BlankEncryptor(getContext());
            return encryptor;
        }

        public String getPreferenceFile() {

            if(preferenceFile!=null&&preferenceFile.isEmpty())
                preferenceFile =null;

            return preferenceFile;
        }
    }

}
