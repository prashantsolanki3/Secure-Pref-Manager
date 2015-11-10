package com.prashantsolanki.secureprefmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.prashantsolanki.secureprefmanager.SecurePrefManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Prashant on 11/7/2015.
 */
public class HidePreferences {

    public static void addToCache(Context ctx,String data, String tag) {
        FileWriter fileWriter=null;
        BufferedWriter bufferedWriter=null;

        try {
            fileWriter = new FileWriter(ctx.getFileStreamPath(tag));
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("INTERNAL STORAGE WRITE", "ERROR: " + e.getMessage());
        }finally {
            try {
                if(bufferedWriter!=null)
                    bufferedWriter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static String getCache(Context ctx,String tag) {
        BufferedReader bufferedReader=null;
        FileReader fileReader;
        String temp;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fileReader = new FileReader(ctx.getFileStreamPath(tag));
            bufferedReader = new BufferedReader(fileReader);
            while ((temp=bufferedReader.readLine())!=null)
                stringBuilder.append(temp);
        } catch (Exception e) {
            Log.d("INTERNAL STORAGE READ","ERROR: "+e.getMessage());
            return null;
        }finally {
            try {
                if(bufferedReader!=null)
                    bufferedReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    Context context;
    SharedPreferences preferences;
    String fileName;


    public HidePreferences(Context context,boolean hide,PreferenceUpdateListener listener) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(hide)
            saveAndClearPreferences(listener);
        else
            addPreferencesToXml(listener);

        fileName= Base64.encodeToString(context.getApplicationInfo().packageName.getBytes(Charset.forName("UTF-8")), Base64.NO_PADDING);

    }

    private void saveAndClearPreferences(PreferenceUpdateListener listener){
      new AsyncXmlToFile(listener).execute();
    }

    private Map<String,String> retrievePreferencesFromFile(){
        return MapJsonConverter.revert(getCache(context,fileName));
    }

    private void addPreferencesToXml(PreferenceUpdateListener listener){
        new AsyncFileToXml(listener).execute();
    }

    class AsyncFileToXml extends AsyncTask<Void,Void,Void>{

        PreferenceUpdateListener listener = null;
        long start,end;
        public AsyncFileToXml(PreferenceUpdateListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            start = SystemClock.elapsedRealtime();
            if(listener==null)
                throw new NullPointerException();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Map<String, String> map = retrievePreferencesFromFile();
            SharedPreferences.Editor editor = preferences.edit();
            int i=1;
            if(!SecurePrefManager.isHidden)
                return null;

            if(map==null)
                return null;
            for (String key : map.keySet()) {
                editor.putString(key, map.get(key));
                listener.onProgress(i, map.size());
                ++i;
            }
            editor.apply();
            SecurePrefManager.isHidden=false;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.onSuccess();
            end = SystemClock.elapsedRealtime();
            Log.d(getClass().getSimpleName(), "Time: " + (end - start));
            SecurePrefManager.isHidden = true;
        }
    }

    class AsyncXmlToFile extends AsyncTask<Void,Void,Void>{

        PreferenceUpdateListener listener=null;
        long start,end;
        public AsyncXmlToFile(PreferenceUpdateListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            start = SystemClock.elapsedRealtime();
            if(listener==null)
                throw new NullPointerException();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Map<String,?> map = preferences.getAll();
            if(map==null)
                return null;
            if(SecurePrefManager.isHidden)
                return null;
            Map<String,String> stringMap = new HashMap<>();
            int i=1;
            for(String s:map.keySet()){
                stringMap.put(s,String.valueOf(map.get(s)));
                listener.onProgress(i, map.size());
                i++;
            }
            String json =MapJsonConverter.convert(stringMap);
            Log.d("hiding","size"+stringMap.size()+"json: "+json);
            addToCache(context, json,fileName);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            SecurePrefManager.isHidden=true;
            clearPreferences();
            listener.onSuccess();
            end = SystemClock.elapsedRealtime();
            Log.d(getClass().getSimpleName(),"Time: "+(end-start));
            SecurePrefManager.isHidden = true;
            super.onPostExecute(aVoid);
        }
    }


    private void clearPreferences(){
        SecurePrefManager.with(context)
                .clear()
                .confirm();
    }

    public interface PreferenceUpdateListener{
        void onFailure();
        void onProgress(int p, int max);
        void onSuccess();

    }
}
