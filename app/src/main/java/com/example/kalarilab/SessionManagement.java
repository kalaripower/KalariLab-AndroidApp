package com.example.kalarilab;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

public class SessionManagement implements Serializable {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";
    String FRESH_INSTALL = "fresh_install" ;
    String REFRESH_TOKEN = "refresh_token" ;
    String ACCESS_TOKEN = "access_token";
    String USER_ID = "user_id";
    String COSTUMER_ID = "costumer_id";

    public SessionManagement(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void saveSession(String id) {
        editor.putString(SESSION_KEY, id).commit();
    }

    public String returnSession() {
        return sharedPreferences.getString(SESSION_KEY, "");
    }

    public void removeSession() {
        editor.putString(SESSION_KEY, "").commit();
    }



    public void onBoardingSeen(){
        editor.putBoolean(FRESH_INSTALL, false).commit();
    }

    public boolean getFRESH_INSTALLStatus(){
        return sharedPreferences.getBoolean(FRESH_INSTALL, true);
    }
    public String returnRefreshToken(){
        return sharedPreferences.getString(REFRESH_TOKEN, "");
    }
    public void saveRefreshToken(String refresh_token){
        editor.putString(REFRESH_TOKEN, refresh_token).commit();
    }
    public String returnAccessToken(){
        return sharedPreferences.getString(ACCESS_TOKEN, "");

    }
    public void saveAccessToken(String access_token){
        editor.putString(ACCESS_TOKEN, access_token).commit();
    }
    public void saveUserId(String user_id){
        editor.putString(USER_ID, user_id).commit();
    }
    public void saveCostumerId(String costumer_id){
        editor.putString(COSTUMER_ID, costumer_id).commit();
    }
    public String returnUserId(){
        return sharedPreferences.getString(USER_ID, "");

    }
    public String returnCostumerId(){
        return sharedPreferences.getString(COSTUMER_ID, "");

    }

}
