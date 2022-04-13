package com.example.kalarilab;

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


        extractInfoFromResponse_Fragments();
    }

    public static Map<String, String> ConvertJsonObject(JSONObject jsonObj) {
        return  new Gson().fromJson(jsonObj.toString(),  Map.class);
    }

    private void extractInfoFromResponse_Fragments() {
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


        extractInfoFromResponse_Activities();
    }

    private void extractInfoFromResponse_Activities() {
        volleyCallbackMap.setMap(ConvertJsonObject(response));

        try {
            context.storeInfoInSharedPreference();


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onSuccess_PremiumActivity(JSONObject r) {

        response = r;


        extractInfoFromResponse_Activities();
    }


}
