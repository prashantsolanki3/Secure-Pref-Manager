package com.prashantsolanki.secureprefmanager.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 *
 * Created by Prashant on 11/7/2015.
 */
public class MapJsonConverter {
    public static String convert(Map<String, String> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return json;
    }

    /*
     * @Description: Method to convert JSON String to Map
     * @param: json String
     * @return: map Map<String, String>
     */
    public static Map<String, String> revert(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> map = gson.fromJson(json, type);
        return map;
    }
}
