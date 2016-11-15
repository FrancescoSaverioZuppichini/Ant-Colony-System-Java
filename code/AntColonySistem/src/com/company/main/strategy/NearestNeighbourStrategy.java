package com.company.main.strategy;

import com.company.main.graph.City;
import com.company.main.graph.Graph;
import com.company.main.utils.Tour;

import java.util.ArrayList;

/**
 * Created by VeaVictis on 01/11/16.
 */
public class NearestNeighbourStrategy implements Algorithm {
    private Tour tour;
    private int totalCost;
    private Graph world;
    private int start;

    public NearestNeighbourStrategy(Graph world) {
        this.world = world;
        this.tour = new Tour(world.getNodes().size());
//        dafault starting point is the first node
        this.start = 0;
        this.totalCost = 0;
    }


    public void setStart(int start) {
        this.start = start;
    }

    public void findSolution() {

        int[][] distanceMatrix;
        int currPos, tempPos, j, len, bestCost, temp;

        currPos = this.start;
        tempPos = 0;
        distanceMatrix = this.world.getDistanceMatrix();
        len = distanceMatrix.length;
        bestCost = Integer.MAX_VALUE;

        while (!tour.isFinish()) {
            this.tour.addCity(currPos);

            for (j = 0; j < len; j++) {
                temp = distanceMatrix[currPos][j];
                if (currPos != j && !this.tour.isCityVisited(j)) {
                    if (temp < bestCost) {
                        bestCost = temp;
                        tempPos = j;
                    }
                }
            }

            this.tour.addCost(distanceMatrix[currPos][tempPos]);
            currPos = tempPos;
            bestCost = Integer.MAX_VALUE;

        }

//        add last cost
        this.tour.addCost(distanceMatrix[currPos][start]);


    }


    public Tour getSolution() {
        return this.tour;
    }

    public void setWorld(Graph world) {
        this.world = world;
    }
}
