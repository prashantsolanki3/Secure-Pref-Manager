package com.prashantsolanki.secureprefmanager;

/**
 *
 * Created by Prashant on 11/5/2015.
 */
public class SecurePrefManagerInit {

    private static String encryptionPhrase;
    private static boolean useEncryption;
    private static boolean isInit=false;

    public static boolean isInit() {
        return isInit;
    }

    public static String getEncryptionPhrase() {
        return encryptionPhrase;
    }

    public static boolean isUseEncryption() {
        return useEncryption;
    }

    public static class Initializer {

        private boolean useEncryption;
        private String encryptionPhrase;

        public Initializer useEncryption(boolean useEncryption){
            this.useEncryption = useEncryption;
            return this;
        }


        public Initializer setEncryptionPhrase(String encryptionPhrase){
            this.encryptionPhrase = encryptionPhrase;
            return this;
        }

        public void initialize(){
            if(useEncryption&& encryptionPhrase ==null)
                throw new IllegalStateException("Must set a Encryption Phase");

            SecurePrefManagerInit.isInit = true;
            SecurePrefManagerInit.useEncryption = useEncryption;
            SecurePrefManagerInit.encryptionPhrase = encryptionPhrase;
        }



    }
}
