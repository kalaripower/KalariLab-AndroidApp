package com.example.kalarilab;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

public class SessionManagement implements Serializable {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";
    String ON_BOARDING_SEEN ;

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

    public void setOnBoardingAlreadySeen(){
        editor.putString(ON_BOARDING_SEEN, "seen");
    }
    public String getOnBoardingStatus(){
        return ON_BOARDING_SEEN;
    }

}
