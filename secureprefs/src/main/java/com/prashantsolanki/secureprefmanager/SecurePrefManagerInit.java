package com.prashantsolanki.secureprefmanager;

/**
 *
 * Created by Prashant on 11/5/2015.
 */
public class SecurePrefManagerInit {

    private static String encryptionPhase;
    private static boolean useEncryption;
    private static boolean isInit=false;

    public static boolean isInit() {
        return isInit;
    }

    public static String getEncryptionPhase() {
        return encryptionPhase;
    }

    public static boolean isUseEncryption() {
        return useEncryption;
    }

    public static class Initializer {

        private boolean useEncryption;
        private String encryptionPhase;

        public Initializer useEncryption(boolean useEncryption){
            this.useEncryption = useEncryption;
            return this;
        }


        public Initializer setEncryptionPhase(String encryptionPhase){
            this.encryptionPhase = encryptionPhase;
            return this;
        }

        public void initialize(){
            if(useEncryption&&encryptionPhase==null)
                throw new IllegalStateException("Must set a Encryption Phase");

            SecurePrefManagerInit.isInit = true;
            SecurePrefManagerInit.useEncryption = useEncryption;
            SecurePrefManagerInit.encryptionPhase = encryptionPhase;
        }



    }
}
