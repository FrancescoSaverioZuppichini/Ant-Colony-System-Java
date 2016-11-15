package com.company.main.graph;


import com.company.main.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by VeaVictis on 29/10/16.
 */
public class Graph {
    private ArrayList<City> nodes;
    private int[] cities;
    private int index;
    private int problemSize;
    private int[][] distanceMatrix;
    private double[][] inverseDistanceMatrix;

    private int[][] nearestNeighborList;
    private int deep;
    private int bestKnowSolution;

    public Graph() {
        this.problemSize = 4;
        this.bestKnowSolution = 1;

    }

    public void initializeGraph(int problemSize) {

        this.nodes = new ArrayList<>(problemSize);
        this.problemSize = problemSize;
        this.distanceMatrix = new int[this.problemSize][this.problemSize];
        this.inverseDistanceMatrix = new double[this.problemSize][this.problemSize];
        this.cities = new int[this.problemSize];
    }

    public void addNode(City a) {
        this.nodes.add(a);
        this.cities[this.index] = a.getPos();
        this.index++;
    }

    public int[][] getNearestNeighborList() {
        return nearestNeighborList;
    }

    public void computeDistanceMatrix() {
        int i, j, len;

        len = this.distanceMatrix.length;

        for (i = 0; i < len; i++) {
            for (j = i + 1; j < len; j++) {
                int distance = (int) Math.round(this.distance(this.nodes.get(i), this.nodes.get(j)));
                this.distanceMatrix[i][j] = distance;
                this.distanceMatrix[j][i] = distance;
//                calculate and store the inverse matrix, 0.01 is a trash value that we add in
//                order to prevent / by 0
                final double inv = 1d / (distance + 0.01);
                this.inverseDistanceMatrix[i][j] = inv;
                this.inverseDistanceMatrix[j][i] = inv;
            }
        }
    }

    public int[][] computeNearestNeighborList(int deepth) {
        Pair[][] helper;
        int len, k;

        len = this.distanceMatrix.length;
        helper = new Pair[len][len - 1];
        this.nearestNeighborList = new int[len][deepth];

//        sort each row
        for (int i = 0; i < len; i++) {
            k = 0;
            for (int j = 0; j < len; j++) {
//                remove diagonal
                if (i != j) {
//                    store the ref to che column of the distance matrix and the actuall distance
                    helper[i][k] = new Pair(j, this.distanceMatrix[i][j]);
                    k++;
                }
            }
        }
//        copy only the given depth
        for (int i = 0; i < len; i++) {
//                sort row by distance
            Arrays.sort(helper[i]);
//            deep copy
            for (int j = 0; j < deepth; j++) {
                this.nearestNeighborList[i][j] = helper[i][j].getIndex();
            }
        }

        return this.nearestNeighborList;
    }

    private double distance(City a, City b) {
        double distance;

        distance = 0.0;

        distance += Math.pow((a.getX() - b.getX()), 2);
        distance += Math.pow((a.getY() - b.getY()), 2);

        return Math.sqrt(distance);

    }

    public double computeTotalCost(int[] cities) {
        double totalCost;

        totalCost = 0.0;
        for (int i = 0; i < cities.length - 1; i++) {
            totalCost += this.distance(this.nodes.get(cities[i]), this.nodes.get(cities[i + 1]));
        }

        totalCost += this.distance(this.nodes.get(cities[cities.length - 1]), this.nodes.get(cities[0]));

        return totalCost;
    }

    public double getError(int tourCost) {
        double error;

        error = ((double) tourCost - (double) this.bestKnowSolution) / (double) this.bestKnowSolution;

        return error * 100;
    }

    public ArrayList<City> getNodes() {
        return nodes;
    }

    public int[] getCities() {
        return cities;
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public double[][] getInverseDistanceMatrix() {
        return inverseDistanceMatrix;
    }


    public int getBestKnowSolution() {
        return bestKnowSolution;
    }

    public void setBestKnowSolution(int bestKnowSolution) {
        this.bestKnowSolution = bestKnowSolution;
    }

}
