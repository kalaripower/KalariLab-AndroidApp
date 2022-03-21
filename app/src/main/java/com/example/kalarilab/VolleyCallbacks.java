package com.example.kalarilab;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

public class VolleyCallbacks {
    private final VolleyCallbackMap volleyCallbackMap;
    private JSONObject response;
    private final ProfileFragment fragment;

    public VolleyCallbacks(ProfileFragment fragment, VolleyCallbackMap volleyCallbackMap) {

        this.volleyCallbackMap = volleyCallbackMap;
        this.fragment = fragment;
    }

    public void onSuccess(JSONObject r) {

        response = r;


        extractInfoFromResponse();
    }

    public static Map<String, String> ConvertJsonObject(JSONObject jsonObj) {
        return  new Gson().fromJson(jsonObj.toString(),  Map.class);
    }

    private void extractInfoFromResponse() {
        volleyCallbackMap.setMap(ConvertJsonObject(response));
        this.fragment.setFragmentMap();
        try {
            Log.d("SessionDebug", "FirstCall");

            this.fragment.storeInfoInSharedPreference();
            this.fragment.setViewsContent();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
