package com.company.main;

import com.company.main.graph.Graph;
import com.company.main.graph.Tour;
import com.company.main.strategy.AntColonySystemStrategy;
import com.company.main.strategy.NearestNeighbourStrategy;
import com.company.main.utils.FileParser;

import java.util.Random;

public class Main {
    private static String filename;
    private static long seed;

    public static void main(String[] args) {
        parseArgs(args);
        Graph g;
        FileParser parser = new FileParser();
        try {
            g = parser.parseFromFileName(filename);
            g.computeDistanceMatrix();
            if(Main.seed == 0) {
                Main.seed = new Random().nextLong();
            }
            Random magic = new Random(seed);
//            run nearest Neighbour first
            NearestNeighbourStrategy nearestNeighbour = new NearestNeighbourStrategy(g.getNodes().size());
            nearestNeighbour.setWorld(g);
            nearestNeighbour.setStart(magic.nextInt(g.getNodes().size()));
            nearestNeighbour.findSolution(g.getNodes());
//            get the solution
            Tour nearestNeighbourCityTour = nearestNeighbour.getSolution();
            System.out.println(nearestNeighbourCityTour.getTotalCost());
//            pass the cost to the ATS to setup the initial pheromone
            AntColonySystemStrategy ACS = new AntColonySystemStrategy(Integer.MAX_VALUE, g.getNodes().size(), nearestNeighbourCityTour, seed, magic, g, 1, 5, 0.05, 0.95, 10);
            ACS.findSolution();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    private static void parseArgs(String[] args) {
        String filename;
        Main.seed = 0;
        for (String rawArg : args) {
            String[] arg = rawArg.trim().split("=");

            if (arg[0].equals("filename")) {
                Main.filename = arg[1];

            }
            if (arg[0].equals("seed")) {
                Main.seed = Long.valueOf(arg[1]);
                System.out.println(seed);
            }
        }
    }

    private static void checkArgs(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("arguments not valid.");
        }
    }

}
