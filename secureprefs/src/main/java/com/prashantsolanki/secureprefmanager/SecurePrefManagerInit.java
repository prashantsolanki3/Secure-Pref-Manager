package com.prashantsolanki.secureprefmanager;

import android.content.Context;

import com.prashantsolanki.secureprefmanager.encryptor.AESEncryptor;
import com.prashantsolanki.secureprefmanager.encryptor.BlankEncryptor;
import com.prashantsolanki.secureprefmanager.encryptor.Encryptor;

/**
 *
 * Created by Prashant on 11/5/2015.
 */
public class SecurePrefManagerInit {

    private static boolean isInit=false;
    private static Encryptor encryptor;
    private static Context context;

    public static Encryptor getEncryptor() {
        return encryptor;
    }

    public static Context getContext() {
        return context;
    }

    static void clear(){
        isInit = false;
        encryptor = null;
        context = null;
    }

    public static boolean isInit() {
        return isInit;
    }

    public static class Initializer {


        private Context context;
        private boolean useEncryption;
        private Encryptor encryptor=null;

        public Initializer(Context context) {
            this.context = context;
        }

        public Initializer useEncryption(boolean useEncryption){
            this.useEncryption = useEncryption;
            return this;
        }

        public void initialize() throws Exception{
            if(!useEncryption)
                //No Encryption
                SecurePrefManagerInit.encryptor = new BlankEncryptor(context);
           else if(encryptor==null)
                //Default Encryption
                SecurePrefManagerInit.encryptor = new AESEncryptor(context);
            else
                //User Defined Encryption Algorithm
                SecurePrefManagerInit.encryptor = encryptor;


            SecurePrefManagerInit.context = context;
            SecurePrefManagerInit.isInit = true;
        }

        public Initializer setCustomEncryption(Encryptor encryptor){
            this.encryptor = encryptor;
            return this;
        }



    }
}
