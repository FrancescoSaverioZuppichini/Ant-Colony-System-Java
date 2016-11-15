package com.company.main.nest;

import com.company.main.utils.Pow;

import java.util.Random;

/**
 * Created by VeaVictis on 28/10/16.
 */
public class Worker {
    private int currentPosition;
    private int totalCost;
    private int state;
    private int[] citiesVisited;
    private int[] cities;
    private byte[] visitedMap;
    private int problemSize;
    private final int[][] distanceMatrix;
    private final double[][] inverseDistanceMatrix;
    private double[][] pheromoneMatrix;
    private double[][] probabilityMatrix;
    private int[][] NNList;
    private int NNsize;
    private int initialState;
    private boolean hasMemory;
    private static int nIteration;
    private int start;
    private double initialPheromone;
    private Random magic;
    private double alpha;
    private int beta;
    private double memory;
    private double q;


    public Worker(int problemSize, final int[][] distanceMatrix, final double[][] inverseDistanceMatrix, double[][] pheromoneMatrix,
                  double[][] probabilityMatrix,
                  double initialPheromone, int[] cities, int[][] NNList, Random rand, double alpha,
                  int beta, double memory, double q) {
//        negative pos mean NO position
        this.currentPosition = -1;
        this.start = -1;
        this.state = 0;
        this.alpha = alpha;
        this.beta = beta;
        this.memory = memory;
        this.q = q;
        this.problemSize = problemSize;
        this.cities = cities;
        this.citiesVisited = new int[problemSize];
        this.visitedMap = new byte[problemSize];
        this.distanceMatrix = distanceMatrix;
        this.inverseDistanceMatrix = inverseDistanceMatrix;
        this.pheromoneMatrix = pheromoneMatrix;
        this.probabilityMatrix = probabilityMatrix;
        this.NNList = NNList;
        this.initialState = 0;
        this.NNsize = NNList[0].length;
//        the max deep that an ant can go in the current row
        this.magic = rand;
        this.initialPheromone = initialPheromone;

    }

    public static int getnIteration() {
        return nIteration;
    }

    public int computeCost() {
        int temp = 0;
        for (int i = 0; i < this.problemSize; i++) {
            temp += this.distanceMatrix[this.citiesVisited[i]][this.citiesVisited[(i + 1) % (this.problemSize)]];
        }
        return temp;

    }

    public int getTotalCost() {
        return this.computeCost();
    }

    public void setCurrentPosition(int currentPosition) {

        this.currentPosition = currentPosition;
        this.start = currentPosition;
        this.addCity(this.currentPosition);
        this.initialState = 0;

    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    private void setPheromone(final int from, final int to, final double p) {
//        update pheromone
        this.pheromoneMatrix[from][to] = p;
        this.pheromoneMatrix[to][from] = p;
//        update probability
        final double prob = this.getProbabilityOnEdge(from, to);

        this.probabilityMatrix[from][to] = prob;
        this.probabilityMatrix[to][from] = prob;


    }


    private void localTrailUpdating(int bestCity) {
        int from, to;
        from = this.currentPosition;
        to = bestCity;

        final double p = (1 - 0.1) * this.pheromoneMatrix[from][to] + (0.1) * this.initialPheromone;
        this.setPheromone(from, to, p);

    }

    public void globalTrailUpdating() {
        double tau;
        int len, from, to;

        len = this.citiesVisited.length;
        tau = 0.1 * (1d / this.totalCost);
//      for each city in this tour
        for (int i = 0; i < len; i++) {
            from = this.citiesVisited[i];
            to = this.citiesVisited[(i + 1) % len];
//            compute new pheromone based on tau
            final double p = (0.9) * this.pheromoneMatrix[from][to] + tau;

//            final double p = tau;
            this.setPheromone(from, to, p);
        }

    }

    public double getProbabilityOnEdge(int row, int col) {
//        alpha is not used
        return this.pheromoneMatrix[row][col] * Pow.pow(inverseDistanceMatrix[row][col], this.beta);
    }

    private double getProbabilityOnEdges() {
        final int row;
        double totalProb;

        totalProb = 0.0;
        row = this.currentPosition;

        for (int i = 0; i < this.problemSize; i++) {
            if (this.isVisited(i) != 1) {
                totalProb += this.probabilityMatrix[row][i];
            }
        }

        return totalProb;
    }


    private int edgeExploitation() {
        double bestProb;
        int bestCity;
        final int row;

        bestProb = Double.MIN_VALUE;
        bestCity = -1;
        row = this.currentPosition;
//      Let's use our NNlist in order to speed up the search
        for (int i = 0; i < this.NNsize; i++) {
            final int NNCity = this.NNList[row][i];
            if (this.isVisited(NNCity) != 1) {
                final double tempProb = this.probabilityMatrix[row][NNCity];

                if (tempProb > bestProb) {
                    bestProb = tempProb;
                    bestCity = NNCity;
                }
            }
        }

        if (bestCity == -1) {
//        if we don't find any good city, let's do the old school way
            for (int i = 0; i < this.problemSize; i++) {
                if (this.isVisited(i) != 1) {
                    final double tempProb = this.probabilityMatrix[row][i];

                    if (tempProb >= bestProb) {
                        bestProb = tempProb;
                        bestCity = cities[i];
                    }
                }
            }
        }


        return bestCity;

    }

    private int edgeExploration() {
        int bestCity = -1;
        double max = 0;
        double probabilityOnEdges = 1d / this.getProbabilityOnEdges();
        double number = this.magic.nextDouble();

//        TODO decide if use or not the NNlist in exploration -> now i just removed it
//        for (int i = 0; i < this.NNsize; i++) {
//
//            final int NNCity = this.NNList[this.currentPosition][i];
//            if (this.isVisited(NNCity) != 1) {
////              multiplication is faster
//                final double res = this.probabilityMatrix[this.currentPosition][i] * probabilityOnEdges;
//                if (res + max >= number) {
//                    bestCity = NNCity;
//                    break;
//                }
//
//
//            }
//        }

        if (bestCity == -1) {
            max = 0;

            for (int i = 0; i < this.problemSize; i++) {
                if (this.isVisited(i) != 1) {

                    final double res = this.probabilityMatrix[this.currentPosition][i] * probabilityOnEdges;

                    if (res + max > number) {
                        bestCity = i;
                        break;
                    }

                    max += res;

                }
            }
        }


        return bestCity;
    }

    public void addLastEdge() {
        updateState(this.start);

    }

    private int getNextCity() {
        int bestCity;

        if (this.magic.nextDouble() < this.q) {
            bestCity = this.edgeExploitation();
        } else {
            bestCity = this.edgeExploration();
        }

        return bestCity;
    }

    public void doNextMove() {
        int bestCity;
//        this check is necessary since we can have ant with memory
        if (!this.isFinish()) {
            bestCity = this.getNextCity();

//      update tour
            this.updateState(bestCity);
        }
    }

    private void updateState(int city) {
//        add pheromone
        this.localTrailUpdating(city);
//        add city to tour and set city has visited
        this.addCity(city);

    }

    private void addCity(int city) {
        this.citiesVisited[this.state % this.problemSize] = city;
//        flag 1 -> true
        this.visitedMap[city] = 1;

        this.totalCost += this.distanceMatrix[this.currentPosition][city];
        // update next step state
        this.currentPosition = city;
        this.state++;

    }

    public int getInitialState() {
        return initialState;
    }

    public void setMemory(int[] tour) {
//        upper bound cannot be 0
        final int upperBound = this.magic.nextInt(this.problemSize - 2) + 1;
        int lowerBound;
        this.hasMemory = true;
        lowerBound = this.problemSize;

        while (lowerBound >= upperBound) {
            lowerBound = this.magic.nextInt(this.problemSize - upperBound);
        }
//      we only need to re-set the map
        this.visitedMap = new byte[this.problemSize];

        this.setCurrentPosition(tour[lowerBound]);

        for (int i = lowerBound + 1; i < upperBound; i++) {
            final int ancestorNode = tour[i];
            this.addCity(ancestorNode);
        }

        this.initialState = this.state;


    }

    public boolean hasMemory() {
        return this.magic.nextDouble() < this.memory;
    }

    private boolean isFinish() {
        return this.state == this.problemSize;
    }

    private int isVisited(int city) {
        return this.visitedMap[city];
    }

    public void resetWorker() {
//        TODO this is not working
        this.hasMemory = false;
        this.currentPosition = -1;
        this.start = -1;
        this.state = 0;
//        erase memory
//        this.citiesVisited = new int[this.problemSize];
        this.visitedMap = new byte[this.problemSize];
        this.totalCost = 0;
    }

    public int getSteps() {
        return this.state - this.initialState;
    }

    public int[] getCitiesVisited() {
        return citiesVisited;
    }

    public Random getMagic() {
        return magic;
    }
}
