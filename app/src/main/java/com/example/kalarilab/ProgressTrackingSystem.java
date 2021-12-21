package com.example.kalarilab;

public class ProgressTrackingSystem {
    private int levelReached;
    private int classReached;
    private int posture;
    private int postureLevel;

    public ProgressTrackingSystem(int levelReached, int classReached, int numOfClasses) {
        this.levelReached = levelReached;
        this.classReached = classReached;
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


    public int getPosture() {
        return posture;
    }

    public void setPosture(int posture) {
        this.posture = posture;
    }

    public int getPostureLevel() {
        return postureLevel;
    }

    public void setPostureLevel(int postureLevel) {
        this.postureLevel = postureLevel;
    }
}
