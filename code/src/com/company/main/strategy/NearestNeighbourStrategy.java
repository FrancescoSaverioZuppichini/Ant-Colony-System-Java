package com.company.main.strategy;

import com.company.main.graph.City;
import com.company.main.graph.Graph;
import com.company.main.graph.Tour;

import java.util.ArrayList;

/**
 * Created by VeaVictis on 01/11/16.
 */
public class NearestNeighbourStrategy implements Algorithm {
    private Tour solution;
    private Graph world;

    private int start;

    public NearestNeighbourStrategy(int problemSize) {
        this.solution = new Tour(problemSize);
    }


    public void setStart(int start) {
        this.start = start;
    }

    public void findSolution(ArrayList<City> cities) {

        int[][] distanceMatrix;
        int currPos, tempPos, j, len, bestCost, temp;

        currPos = this.start;
        tempPos = 0;
        distanceMatrix = this.world.getDistanceMatrix();
        len = distanceMatrix.length;
        bestCost = Integer.MAX_VALUE;

        while (!this.solution.isFinished()) {
            this.solution.addCity(cities.get(currPos));

            for (j = 0; j < len; j++) {
                temp = distanceMatrix[currPos][j];
                if (currPos != j && !this.solution.isPositionVisited(j)) {
                    if (temp < bestCost) {
                        bestCost = temp;
                        tempPos = j;
                    }
                }
            }
            currPos = tempPos;
            this.solution.addCost(bestCost);
            bestCost = Integer.MAX_VALUE;

        }

//        add last cost
        this.solution.addCost(distanceMatrix[currPos][start]);


    }


    public Tour getSolution() {
        return this.solution;
    }

    public void setWorld(Graph world) {
        this.world = world;
    }
}
