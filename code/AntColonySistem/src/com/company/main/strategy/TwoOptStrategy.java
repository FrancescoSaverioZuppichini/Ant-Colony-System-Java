package com.company.main.strategy;

import com.company.main.graph.Graph;

/**
 * Created by VeaVictis on 07/11/16.
 */
public class TwoOptStrategy implements Algorithm {
    private Graph world;

    public int findSolution(int[] cities) {
        int bestGain = Integer.MAX_VALUE, localGain = 0;
        int first = -1, second = -1, iteration = 0, a = 0, b = 0, c = 0, d = 0;
        boolean repeat = true;
        int max = 10;
        int dimension;
        int[][] distanceMatrix;
        distanceMatrix = this.world.getDistanceMatrix();
        dimension = cities.length;
        while (repeat) {
            repeat = false;
            iteration++;
            for (int i = 0; i < dimension; i++) {
                bestGain = 0;

                for (int j = i + 2; j < dimension; j++) {

                    // points to form 2 edges
                    a = cities[i];
                    b = cities[i + 1];
                    c = cities[j];
                    d = cities[((j + 1) % dimension)];


                    // if not ac > bc && not dc > ca then there is no improve due to triangle inequality
                    if (!(distanceMatrix[a][b] > distanceMatrix[b][c]) && !(distanceMatrix[c][d] > distanceMatrix[c][a]))
                        continue;

                    localGain = getMoveCost(a, b, c, d, distanceMatrix);
                    if (localGain < bestGain) {
                        first = i;
                        second = j;
                        bestGain = localGain;
                    }
                }
                if (bestGain < 0 ) {
                    repeat = true;
                    swap(first + 1, second, cities);
                }
            }

        }

        return getTourCost(cities, distanceMatrix);
    }

    // Private methods

    private int getMoveCost(int a, int b, int c, int d, int[][] matrix) {
        // (ac + bd) - (ab + cd)
        return ((matrix[a][c] + matrix[b][d]) - (matrix[a][b] + matrix[c][d]));
    }

    private void swap(int i, int j, int[] cities) {
        while (i < j) {
            cities[i] = cities[i] ^ cities[j];
            cities[j] = cities[i] ^ cities[j];
            cities[i] = cities[i] ^ cities[j];
            i++;
            j--;
        }
    }

    private int getTourCost(int[] cities, int[][] distanceMatrix) {
        int cost = 0;
        for (int i = 0; i < cities.length - 1; i++) {
            cost += distanceMatrix[cities[i]][cities[i + 1]];
        }
        cost += distanceMatrix[cities[cities.length - 1]][cities[0]];

        return cost;
    }


    public void setWorld(Graph world) {
        this.world = world;
    }
}
