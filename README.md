# Secure-Pref-Manager
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Secure%20Preference%20Manager-brightgreen.svg?style=flat-square)](http://android-arsenal.com/details/1/2747)
[![Travis CI](https://travis-ci.org/prashantsolanki3/Secure-Pref-Manager.svg)](https://travis-ci.org/prashantsolanki3/Secure-Pref-Manager)
##Secure Preference Manager is a simple Library to help you protect your Shared Preferences.
Secure Preference Manager for android. It uses various encryption techniques to protect your application's Shared Preferences.

##Setup
Add jitpack to your project’s repositories.

```groovy
repositories {
        // ...
        maven { url "https://jitpack.io" }
}
```

Then add Secure-Pref-Manager to your Module’s dependencies

```groovy
dependencies {
	compile 'com.github.prashantsolanki3:Secure-Pref-Manager:0.25'
}
```

##Usage

###Initialize SecurePrefManager anywhere before using it.

* Basic Initialization

```java
new SecurePrefManagerInit.Initializer(getApplicationContext())
       .initialize();
```

*  Recommended Initialization
  * AES Encryption by Default.
  * Auto Generated Key.

```java
new SecurePrefManagerInit.Initializer(getApplicationContext())
       .useEncryption(true)
       .initialize();
```

* Advance Initialization: Only if you wanna add Custom Encryption Methods.

```java
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
```java
SecurePrefManager.with(this)
        .set("user_name")
        .value("LoremIpsum")
        .go();
```

* Retrieving

```java
String userName = SecurePrefManager.with(this)
        .get("user_name")
        .defaultValue("unknown")
        .go();
```

### Hide Preferences from 3rd Party applications (EXPERIMENTAL)

* Unhide Preferences when the activity starts
```java
@Override
protected void onStart() {
super.onStart();
SecurePrefManager.with(getApplicationContext())
        .unhide(new HidePreferences.PreferenceUpdateListener() {
            @Override
            public void onFailure() {
                Log.d("unhiding", "Failed");
            }

            @Override
            public void onProgress(int p, int max) {
                Log.d("unhiding", "Progress: " + p + "/" + max);
            }

            @Override
            public void onSuccess() {
                Log.d("unhiding", "Success");
            }
        });
}
```
* Hide preferences when leaving the activity

```java
@Override
protected void onPause() {
SecurePrefManager.with(getApplicationContext())
        .hide(new HidePreferences.PreferenceUpdateListener() {
            @Override
            public void onFailure() {

            }

            @Override
            public void onProgress(int p, int max) {

            }

            @Override
            public void onSuccess() {

            }
        });
}
```


### Have Fun!

