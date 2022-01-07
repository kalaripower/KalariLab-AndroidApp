package com.example.kalarilab;

import android.content.SharedPreferences;

import java.io.Serializable;

public class StreakSharedPreference  implements Serializable {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public void setFirstDay(int firstDay){
        editor.putInt("SHARED_PREF_FIRST_DAY", firstDay).commit();
    }
    public void setCounter(int weekProgress){
        editor.putInt("SHARED_PREF_COUNTER", weekProgress).commit();
    }
    public int getFirstDay(){
        return sharedPreferences.getInt("SHARED_PREF_FIRST_DAY", 0);
    }
    public int getCounter(){
        return sharedPreferences.getInt("SHARED_PREF_COUNTER", 0);
    }
    public void setLastTimeTheCounterUpdated(int date){
        editor.putInt("SHARED_PREF_LAST_TIME_COUNTER_UPDATED", date).commit();
    }
    public int getLastTimeTheCounterUpdated(){
        return sharedPreferences.getInt("SHARED_PREF_LAST_TIME_COUNTER_UPDATED", 0);
    }

}
