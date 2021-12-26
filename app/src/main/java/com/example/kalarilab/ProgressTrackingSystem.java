package com.example.kalarilab;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ProgressTrackingSystem {
    private int levelReached;
    private int classReached;
    public Map<Integer, Integer> posturesLevels = new HashMap<Integer, Integer>();
    public static int Success = 1;
    public static int Failure = 0;

    public ProgressTrackingSystem() {
        // fill the hashmap up with all the postures and their levels
        for (int i = 0 ; i < 2; i++){
            posturesLevels.put(i, 0);
        }

    }



    public int getLevelReached() {
        return levelReached;
    }

    public int getClassReached() {
        return classReached;
    }


    public void setLevelReached(int levelReached) {
        this.levelReached = levelReached;
    }

    public void setClassReached(int classReached) {
        this.classReached = classReached;
    }




    public int updateAPosture (Integer posture, Integer level){
        try{
            posturesLevels.put(posture, level);
            return Success;
        }catch (Exception e){
            return Failure;
        }
    }

}
