package com.example.kalarilab;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgressTrackingSystem {
    private int levelReached;
    private int lessonReached;
    public Map<Integer, Integer> posturesLevels = new HashMap<Integer, Integer>();
    public static int SUCCESS = 1;
    public static int FAILURE = 0;


    private AtomicInteger totalPoints = new  AtomicInteger(0);
    private AtomicInteger weeklyPoints = new AtomicInteger(0);


    public ProgressTrackingSystem() {
        addUnopenedPosturesToDB();
    }

    private void addUnopenedPosturesToDB() {
        posturesLevels.put(0, 0);
        posturesLevels.put(1, 0);
        posturesLevels.put(2, 0);
        posturesLevels.put(3, 0);
        posturesLevels.put(4, 0);
        posturesLevels.put(5, 0);
        posturesLevels.put(6, 0);
        posturesLevels.put(7, 0);
        //Push map to DataBase, when api is done



    }

    public int getTotalPoints() {
        return totalPoints.get();
    }

    private void addPointsToTotalPoints(int newPoints){

            totalPoints.addAndGet(newPoints);
    }

    private void addPointsToWeeklyPoints(int newPoints){

            weeklyPoints.addAndGet(newPoints);
    }

    public void addPointAfterFinishingLessons(){
        addPointsToWeeklyPoints(10);
        addPointsToTotalPoints(10);
    }
    public void addPointsAfterFiveDaysStreakInAWeek(){
        addPointsToWeeklyPoints(20);
        addPointsToTotalPoints(20);
    }
    public void addPointsForEveryRestDay(){
        //rest days inside 7 days, only added if five days of these 7 days were used
        addPointsToWeeklyPoints(10);
        addPointsToTotalPoints(10);
    }
    public void addPointsForSkill(){
        int skillPoints = getSkillPoints();
        addPointsToWeeklyPoints(skillPoints);
        addPointsToTotalPoints(skillPoints);
    }

    private int getSkillPoints() {
        //API not finished yet

        return 0;
    }
    private void addHalfwayBonus(){
        addPointsToWeeklyPoints(100);
        addPointsToTotalPoints(100);
    }
    private void addEndOfLevelBonus(){
        addPointsToWeeklyPoints(100);
        addPointsToTotalPoints(100);
    }


    public int getLevelReached() {
        return levelReached;
    }

    public int getLessonReached() {
        return lessonReached;
    }


    public void setLevelReached(int levelReached) {
        this.levelReached = levelReached;
    }

    public void setLessonReached(int lessonReached) {
        this.lessonReached = lessonReached;
    }


    public void getPosturesLevelsFromDB() {
        //get values from cloud (when finished) and store them in our map and return the map.
       posturesLevels.put(1,1);
       posturesLevels.put(2,2);
    }

    public int updateAPosture (Integer posture, Integer level){
        try{
            posturesLevels.put(posture, level);
            return SUCCESS;
        }catch (Exception e){
            return FAILURE;
        }
    }

    public int getWeeklyPoints() {
        return weeklyPoints.get();
    }
    public void setWeeklyPointsZero(){
        //called at the beginning of every week
        weeklyPoints.set(0);
    }
}
