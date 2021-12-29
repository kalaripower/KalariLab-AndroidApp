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


    public void getPosturesLevelsFromDB() {
        //get values from cloud (when finished) and store them in our map and return the map.
       posturesLevels.put(1,0);
       posturesLevels.put(2,1);
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
