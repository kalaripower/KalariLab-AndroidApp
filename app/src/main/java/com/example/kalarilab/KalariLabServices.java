package com.example.kalarilab;

public class KalariLabServices {
    private final static String BASE_URL = "http://192.168.31.89:8000/api/";
    private static  String userID = "";


    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        KalariLabServices.userID = userID;
    }
}
