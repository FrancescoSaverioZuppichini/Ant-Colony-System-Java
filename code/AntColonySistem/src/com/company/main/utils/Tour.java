package com.company.main.utils;

import java.util.Arrays;

/**
 * Created by VeaVictis on 15/11/16.
 */
public class Tour {

    private int[] visitedCities;
//    map of byte that replace a HasMap -> less memory usage, no hashing cost
    private byte[] visitedMap;
    private int cost;
    private int problemSize;
    private int state;

    public Tour(int problemSize){
        this.visitedCities = new int[problemSize];
        this.visitedMap = new byte[problemSize];
        this.cost = 0;
        this.state = 0;
        this.problemSize = problemSize;
    }

    public int[] getVisitedCities() {
        return visitedCities;
    }

    public int getCost() {
        return cost;
    }

    public boolean isCityVisited(int city){
        return this.visitedMap[city] == 1;
    }

    public void addCity(int city){
        if(!this.isCityVisited(city)){
            this.visitedMap[city] = 1;
            this.visitedCities[this.state++] = city;
        }
    }

    public boolean isFinish(){
        return this.state == this.problemSize;
    }
    public void resetTour(){
        this.cost = 0;
        this.visitedMap = new byte[problemSize];
    }

    public void addCost(int cost){
        this.cost += cost;
    }

    @Override
    public String toString() {
        return this.cost + " " + Arrays.toString(this.visitedCities);
    }
}
