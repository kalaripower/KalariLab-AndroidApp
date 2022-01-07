package com.example.kalarilab;

import java.util.Calendar;


public class Streak {
    StreakSharedPreference streakSharedPreference = new StreakSharedPreference();
    //This is gonna be tricky so help me god
    private ProgressTrackingSystem progressTrackingSystem;
    Calendar c = Calendar.getInstance();

    private  int weekProgress  ;
    private int counter = streakSharedPreference.getCounter();
    private static int WEEK = 7;
    private int startDay = streakSharedPreference.getFirstDay();
    private int currentDay = c.get(Calendar.DAY_OF_YEAR);
    private int lastTimeTheCounterUpdated = streakSharedPreference.getLastTimeTheCounterUpdated();




    public Streak(ProgressTrackingSystem progressTrackingSystem) {
        this.progressTrackingSystem = progressTrackingSystem;
    }


    public void addDailyLearningReward(){
        //Called every time the user finish 60% of a lesson
        //But only adds point once a day
        checkIfANewLearningDayPassed();
    }

    public void setWeekProgress (){
        int daysPassedSinceFirstDay = currentDay - startDay;
        if (daysPassedSinceFirstDay <= 7 ){
            weekProgress =  daysPassedSinceFirstDay;
        }else {
            weekProgress = 1;
            streakSharedPreference.setFirstDay(currentDay);
            streakSharedPreference.setCounter(0);


        }

    }

    private void addNewPoints() {
        if (counter < 5){
            progressTrackingSystem.addPointAfterFinishingLessons();
        }else if(counter == 5){
            progressTrackingSystem.addPointAfterFinishingLessons();
            progressTrackingSystem.addPointsAfterFiveDaysStreakInAWeek();
        }

    }

    private void addDayToCounter(){

            streakSharedPreference.setCounter(counter + 1);
            streakSharedPreference.setLastTimeTheCounterUpdated(currentDay);
        }


    private void checkIfANewLearningDayPassed(){
        //a day of exercise is added when a user finish 60% of  a lesson inside a day
        //The next line to make sure that this method is only called once a day
        if (currentDay - lastTimeTheCounterUpdated >= 1){
            addDayToCounter();
            addNewPoints();
        }
    }

    //A method in the cloud is called at the end of every week to give points for rest days
    //only when 5 days of exercise where done
    private int countRestDays(){
        if (counter >= 5) {
            return weekProgress - counter;
        }
        return 0;
    }

    public int getWeekProgress(){
        return weekProgress;
    }

}
