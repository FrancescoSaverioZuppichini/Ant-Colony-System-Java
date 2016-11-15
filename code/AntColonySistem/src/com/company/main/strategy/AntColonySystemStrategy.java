package com.company.main.strategy;

import com.company.main.graph.Graph;
import com.company.main.nest.Worker;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by VeaVictis on 28/10/16.
 */
public class AntColonySystemStrategy implements Algorithm {
    private Graph world;
    private int nIteration;
    private int problemSize;
    private int[] solution;
    private int totalCost;
    private double initialPheromone;
    private int state;
    private long startTime;
    private long endTime;
    private double[][] pheromoneMatrix;
    private double[][] probabilityMatrix;
    private int[][] NNList;
    private double alpha;
    private int beta;
    private double memory;
    private double q;
    private NearestNeighbourStrategy nearestNeighbourStrategy;
    private TwoOptStrategy twoOptStrategy;
    private Worker[] workers;
    private Worker bestWorker;
    private Random magic;

    private long seed;

    public AntColonySystemStrategy(int nIteration, int problemSize, long seed, Random magic, Graph world, double alpha,
                                   int beta, double memory, double q, int nAnts) {
        this.nIteration = nIteration;
        this.problemSize = problemSize;
        this.workers = new Worker[nAnts];
        this.state = 0;
        this.world = world;
        this.alpha = alpha;
        this.beta = beta;
        this.memory = memory;
        this.q = q;
        this.NNList = world.computeNearestNeighborList(4 + (problemSize / 10));
        this.seed = seed;
        this.magic = magic;
        this.totalCost = Integer.MAX_VALUE;
//        holds a reference to the pheromone in the edge from i to j
        this.pheromoneMatrix = new double[problemSize][problemSize];
//        holds a reference to the probability on each edge
        this.probabilityMatrix = new double[problemSize][problemSize];
        this.twoOptStrategy = new TwoOptStrategy(world);
        this.nearestNeighbourStrategy = new NearestNeighbourStrategy(world);
        this.initialPheromone = computeInitialPheromone();
        this.bestWorker = null;
        this.endTime = 180000;

    }

    public int[] getSolution() {
        return solution;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public long getSeed() {
        return seed;
    }


    private void createWorkers() {
        for (int i = 0; i < this.workers.length; i++) {
            this.workers[i] = new Worker(this.problemSize, this.world.getDistanceMatrix(), this.world.getInverseDistanceMatrix(),
                    this.pheromoneMatrix, this.probabilityMatrix,
                    this.initialPheromone, this.world.getCities(), this.NNList, this.magic, this.alpha,
                    this.beta, this.memory, this.q);
        }
    }

    private void initializeStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    private double computeInitialPheromone() {
        this.nearestNeighbourStrategy.setStart(this.magic.nextInt(this.problemSize));
        this.nearestNeighbourStrategy.findSolution();
        System.out.println(this.nearestNeighbourStrategy.getSolution());

        return 1d / (nearestNeighbourStrategy.getSolution().getCost() * this.problemSize);

    }

    public void initializePheromoneMatrix() {
        int len;

        len = this.pheromoneMatrix.length;

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                this.pheromoneMatrix[i][j] = this.initialPheromone;
                this.pheromoneMatrix[j][i] = this.initialPheromone;
            }
        }
    }

    public void initializeProbabilityMatrix() {
        int len;
        Worker w;

        w = this.workers[0];
        len = this.probabilityMatrix.length;

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                final double prob = w.getProbabilityOnEdge(i, j);
                this.probabilityMatrix[i][j] = prob;
                this.probabilityMatrix[j][i] = prob;
            }
        }
    }


    private void printStartACSState() {
        System.out.println("-------------------------------------");
        System.out.println("Starting at thread " + Thread.currentThread().getName());
        System.out.println("SEED: " + this.seed);
        this.printConfiguration();
    }

    private void checkForNewBestSolution(Worker w) {
        if (this.totalCost > w.getTotalCost()) {
            bestWorker = w;
//            set out new solution
            this.totalCost = w.getTotalCost();
            this.solution = w.getCitiesVisited().clone();
//            user feedback
            this.printACSLocalState();
        }
    }

    private Worker findBestWorker() {
        Worker best;
        int cost;

        cost = Integer.MAX_VALUE;
        best = null;

        for (Worker w : this.workers) {
            if (w.getTotalCost() < cost) {
                cost = w.getTotalCost();
                best = w;
            }
        }

        return best;
    }

    private void setWorkersOnRandomCities() {
        for (Worker w : this.workers) {
            w.setCurrentPosition(w.getMagic().nextInt(this.problemSize));
        }
    }

    private void makeAllMoves() {
        for (int i = 0; i < problemSize - 1; i++) {
            for (Worker w : this.workers) {
                w.doNextMove();
            }
        }

        for (Worker w : this.workers) {
            w.addLastEdge();
        }


    }

    private void localSearch() {
        for (Worker w : this.workers) {
            w.setTotalCost(this.twoOptStrategy.findSolution(w.getCitiesVisited()));
        }
    }

    private void resetWorkers() {
//        throw workers away and re-create them
        this.createWorkers();

        for (Worker w : this.workers) {

            if (w.hasMemory()) {
//                store the state from the best ant
                w.setMemory(this.solution);
            } else {
//                put the worker on a random position
                w.setCurrentPosition(this.magic.nextInt(this.problemSize));
            }

        }
    }

    public void findSolution() {
        this.printStartACSState();

        this.initializeStartTime();

        this.createWorkers();

        this.initializePheromoneMatrix();
        this.initializeProbabilityMatrix();
        this.setWorkersOnRandomCities();

        while (!this.isFinish()) {
//            create e full solution for each workers
            this.makeAllMoves();
//            run 2-opt on each ant
            this.localSearch();
//            update best solution if needed
            this.checkForNewBestSolution(this.findBestWorker());
//            update best tour
            this.bestWorker.globalTrailUpdating();
//            reset workers
            this.resetWorkers();
//            update state
            this.state++;

        }

        this.printACSState();

    }

    public boolean isFinish() {
        return !((System.currentTimeMillis() - this.startTime) < this.endTime && this.nIteration-- > 0 && this.getSolutionError() > 0.00);
//                return !((System.currentTimeMillis() - this.startTime) < this.endTime && this.nIteration-- > 0);

    }

    private void printACSLocalState() {
        System.out.println(this.state + ": " + this.totalCost);
        System.out.println("ERROR: " + this.world.getError(this.totalCost) + "%");
    }

    public void printACSState() {
        System.out.println("-------------------------------------");
        System.out.println("Finish after " + this.state + " iterations.");
        System.out.println((System.currentTimeMillis() - this.startTime) / 1000.0 + " seconds.");
        System.out.println(this.getIterationPerSecond() + " Iteration per seconds.");
        this.printConfiguration();
        System.out.println("BEST TOUR LEN: " + this.totalCost);
        System.out.println("WORKERS ITERATION: " + Worker.getnIteration());
//        System.out.println("REAL COST: " + this.world.computeTotalCost(this.solution));
        System.out.println("ERROR: " + this.getSolutionError() + "%");
        System.out.println("SEED: " + this.seed);
        System.out.println("SOLUTION TOUR: " + Arrays.toString(this.getSolutionTour()));
        System.out.println("\n");

    }

    private void printConfiguration() {
        System.out.println("PARAMS:" + " alpha=" + this.getAlpha() + " beta=" + this.getBeta() + " ant=" + this.getWorkers().length + " memory=" + this.getMemory() + " q=" + this.getQ() + "\n");

    }

    public double getIterationPerSecond() {
        return this.state / ((System.currentTimeMillis() - this.startTime) / 1000.0);
    }

    public int[] getSolutionTour() {
        int[] solutionTour;
//        make a new deep copy array
        solutionTour = Arrays.copyOf(this.solution, this.solution.length);
        for (int i = 0; i < this.solution.length; i++) {
            solutionTour[i]++;
        }
        return solutionTour;
    }

    public double getSolutionError() {
        return this.world.getError(this.totalCost);
    }

    public void createSolutionFile(String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            for (int node : this.solution) {
                writer.print(node);
                writer.print(' ');
            }
            writer.close();


        } catch (Exception e) {
            // do something
        }
    }


    public int getBeta() {
        return beta;
    }

    public double getMemory() {
        return memory;
    }

    public double getQ() {
        return q;
    }

    public Worker[] getWorkers() {
        return workers;
    }

    public double getAlpha() {
        return this.alpha;
    }
}
