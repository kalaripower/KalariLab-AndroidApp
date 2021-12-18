package com.example.kalarilab;

public class ProgressTrackingSystem {
    private int levelReached;
    private int classReached;
    private int numOfClasses;

    public ProgressTrackingSystem(int levelReached, int classReached, int numOfClasses) {
        this.levelReached = levelReached;
        this.classReached = classReached;
        this.numOfClasses = numOfClasses;
    }

    public int getLevelReached() {
        return levelReached;
    }

    public int getClassReached() {
        return classReached;
    }

    public int getNumOfClasses() {
        return numOfClasses;
    }

    public void setLevelReached(int levelReached) {
        this.levelReached = levelReached;
    }

    public void setClassReached(int classReached) {
        this.classReached = classReached;
    }

    public void setNumOfClasses(int numOfClasses) {
        this.numOfClasses = numOfClasses;
    }
}
