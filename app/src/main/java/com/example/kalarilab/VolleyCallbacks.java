package com.example.kalarilab;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

public class VolleyCallbacks {
    private final VolleyCallbackMap volleyCallbackMap;
    private JSONObject response;
    private  ProfileFragment fragment;
    private  EditInfoActivity context;
    private SessionManagement sessionManagement ;

    public VolleyCallbacks(ProfileFragment fragment, VolleyCallbackMap volleyCallbackMap) {

        this.volleyCallbackMap = volleyCallbackMap;
        this.fragment = fragment;
    }
    public VolleyCallbacks(EditInfoActivity context, VolleyCallbackMap volleyCallbackMap, SessionManagement sessionManagement) {

        this.volleyCallbackMap = volleyCallbackMap;
        this.context = context;
        this.sessionManagement = sessionManagement;
    }

    public void onSuccess_ProfileFragment(JSONObject r) {

        response = r;


        extractInfoFromResponse_ProfileFragment();
    }

    public static Map<String, String> ConvertJsonObject(JSONObject jsonObj) {
        return  new Gson().fromJson(jsonObj.toString(),  Map.class);
    }

    private void extractInfoFromResponse_ProfileFragment() {
        volleyCallbackMap.setMap(ConvertJsonObject(response));
        this.fragment.setFragmentMap();
        try {

            this.fragment.storeInfoInSharedPreference();
            this.fragment.setViewsContent();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onSuccess_EditInfoActivity(JSONObject r) {

        response = r;


        extractInfoFromResponse_EditInfoActivity();
    }

    private void extractInfoFromResponse_EditInfoActivity() {
        volleyCallbackMap.setMap(ConvertJsonObject(response));

        try {
            context.storeInfoInSharedPreference();
            Log.d("backPressedDebug", sessionManagement.returnUser_Bio());


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
