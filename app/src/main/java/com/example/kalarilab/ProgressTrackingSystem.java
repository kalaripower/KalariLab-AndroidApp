package com.example.kalarilab;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgressTrackingSystem {
    private int levelReached;
    private int classReached;
    public Map<Integer, Integer> posturesLevels = new HashMap<Integer, Integer>();
    public static int SUCCESS = 1;
    public static int FAILURE = 0;


    private AtomicInteger points = new  AtomicInteger(0);


    public ProgressTrackingSystem() {


    }

    public int getPoints() {
        return points.get();
    }

    private int addPoints(int newPoints){
        try{
            points.addAndGet(newPoints);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAILURE;
        }
    }
    public int addPointAfterFinishingLessons(){
        return addPoints(10);
    }
    public int addPointsAfterFiveDaysStreakInAWeek(){
        return addPoints(20);
    }
    public int addPointsForEveryRestDay(){
        //rest days inside 7 days, only added if five days of these 7 days were used
        return addPoints(10);
    }
    public int addPointsForSkill(){
        int skillPoints = getSkillPoints();
        return addPoints(skillPoints);
    }

    private int getSkillPoints() {
        //API not finished yet

        return 0;
    }
    private int addHalfwayBonus(){
        return addPoints(100);
    }
    private int addEndOfLevelBonus(){
        return addPoints(100);
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
            return SUCCESS;
        }catch (Exception e){
            return FAILURE;
        }
    }

}
