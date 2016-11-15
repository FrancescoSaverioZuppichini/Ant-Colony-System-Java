package com.company.main.graph;

import java.util.ArrayList;

/**
 * Created by VeaVictis on 28/10/16.
 */
public class Tour {

    private ArrayList<City> visitedCities;
    private boolean[] visitedMap;
    private int len;
    private int totalCost;
    private int state;

    public Tour(int problemSize) {
        this.totalCost = 0;
        this.len = problemSize;
        this.visitedCities = new ArrayList<>(problemSize);
        this.visitedMap = new boolean[problemSize];
        this.state = 1;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void addCity(City a) {
        this.visitedCities.add(a);
        this.visitedMap[a.getPos()] = true;
        this.state++;
    }

    public boolean isFinished(){
        return this.state >= this.len;
    }
    public boolean isCitiVisited(City a) {

        return this.visitedMap[a.getPos()];
    }

    public boolean isPositionVisited(int pos) {
        return this.visitedMap[pos];

    }

    //    reset all the tour
    public void resetTour() {
        this.totalCost = 0;
        this.state = 1;
        this.visitedCities.clear();
        this.visitedMap = new boolean[this.len];

    }

    public void addCost(int cost) {
        if (cost > 0) {
            this.totalCost += cost;
        }
    }


    @Override
    public String toString() {
        return "Cost: " + this.totalCost + this.visitedCities.toString();
    }

    public ArrayList<City> getVisitedCities() {
        return visitedCities;
    }

    public void setVisitedCities(ArrayList<City> visitedCities) {

        this.visitedCities = new ArrayList<City>(visitedCities);
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
