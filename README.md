# Secure-Pref-Manager

##Secure Preference Manager is a simple Library to help you protect your Shared Preferences.
Secure Preference Manager for android. It uses AES Encryption to protect your application's Shared Preferences.

##Setup
Add jitpack to your project’s repositories.

```
repositories {
        // ...
        maven { url "https://jitpack.io" }
    }
```

Then add Secure-Pref-Manager to your Module’s dependencies

```
dependencies {
	        compile 'com.github.prashantsolanki3:Secure-Pref-Manager:0.20'
	}
```

##Usage

###Initialize SecurePrefManager anywhere before using it.

* Basic Initialization

```
       new SecurePrefManagerInit.Initializer(getApplicationContext())
               .initialize();
```

*  Recommended Initialization
  * AES Encryption by Default.
  * Auto Generated Key.

```
       new SecurePrefManagerInit.Initializer(getApplicationContext())
               .useEncryption(true)
               .initialize();
```

* Advance Initialization: Only if you wanna add Custom Encryption Methods.

```
       new SecurePrefManagerInit.Initializer(getApplicationContext())
               .useEncryption(true)
               .setCustomEncryption(new Encryptor(getApplicationContext()) {
                   @Override
                   public String encrypt(String s) throws Exception {
                       // Your Encryption Algorithm
                       return encryptedString;
                   }

                   @Override
                   public String decrypt(String s) throws Exception {
                       // Your Decryption Algorithm
                       return decryptedString;
                   }
               })
               .initialize();
```

### Adding and Retrieving Preferences

* Adding
```
        SecurePrefManager.with(this)
                .set("user_name")
                .value("LoremIpsum")
                .go();
```

* Retrieving

```
        String userName = SecurePrefManager.with(this)
                .get("user_name")
                .defaultValue("unknown")
                .go();
```


### Have Fun!

