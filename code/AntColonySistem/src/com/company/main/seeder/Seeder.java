//package com.company.main.seeder;
//
//import com.company.main.graph.Graph;
//import com.company.main.strategy.AntColonySystemStrategy;
//import com.company.main.strategy.NearestNeighbourStrategy;
//import com.company.main.utils.FileParser;
//import com.company.main.utils.Tour;
//
//import java.io.PrintWriter;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.Random;
//import java.util.concurrent.ExecutionException;
//
///**
// * TODO this class is a MESS! It is used to seed a set of problems
// * Created by VeaVictis on 10/11/16.
// */
//public class Seeder {
//
//
//    public static void main(String[] args) {
//        Graph g;
//        FileParser parser = new FileParser();
//        try {
//            while (true) {
//                int START = 0;
//                String BASE_PATH = "/Users/VeaVictis/Documents/Usi/V Semester/Artificial Intelligence/AI_Cup_JAVA/src/com/company/AI_cup_2016_problems/";
//                String[] filenames = {"d198.tsp", "fl1577.tsp", "lin318.tsp", "rat783.tsp", "pcb442.tsp", "u1060.tsp", "pr439.tsp", "kroA100.tsp"};
//                Date date = new Date();
//                for (int i = START; i < filenames.length; i++) {
//                    String filename = filenames[i];
//                    System.out.printf(filename);
//                    DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd-HH:mm:ss");
//                    String reportName = "/Users/VeaVictis/Documents/Usi/V Semester/Artificial Intelligence/AI_Cup_JAVA/reports/" + dateFormat.format(date) + "-" + filename + ".txt";
//                    g = parser.parseFromFileName(BASE_PATH + filename);
//                    g.computeDistanceMatrix();
//
//                    int[] solution;
//                    int totalCost = Integer.MAX_VALUE;
////          start threads to compute the algorihtm
//                    int n = 0;
////                seed 30 instance
//                    while (n++ < 30) {
//
//                        long seed = new Random().nextLong();
//                        Random magic = new Random(seed);
//
//
//                        NearestNeighbourStrategy nearestNeighbour = new NearestNeighbourStrategy(g.getNodes().size());
//                        nearestNeighbour.setWorld(g);
//                        nearestNeighbour.setStart(magic.nextInt(g.getNodes().size()));
//                        nearestNeighbour.findSolution(g.getNodes());
//
//                        Tour nearestNeighbourCityTour = nearestNeighbour.getSolution();
//                        System.out.println(nearestNeighbourCityTour);
//
//                        AntColonySystemStrategy localACS = new AntColonySystemStrategy(Integer.MAX_VALUE, g.getNodes().size(), nearestNeighbourCityTour, seed, magic, g, 1, 5, 0.05, 0.95, 10);
//                        localACS.findSolution();
//                        System.out.println("Iteration " + n);
//
//
//                        if (localACS.getTotalCost() < totalCost) {
//                            solution = localACS.getSolution();
//                            totalCost = localACS.getTotalCost();
//                            Seeder.createReport(localACS, reportName);
//                            localACS.printACSState();
////                        if we find a good seed break
//                            if (localACS.getSolutionError() < 0.0) {
//                                break;
//                            }
//                        }
//
//                    }
//                    System.out.println("-----------------\n");
//
//                }
//            }
//        } catch (ExecutionException e) {
//            e.getCause();
//            e.printStackTrace();
//            System.exit(-1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public static void createReport(AntColonySystemStrategy acs, String filename) {
//        try {
//
//            String version = "5.0 create not initialize";
//            System.out.printf("\nCreating report....");
//            System.out.printf(filename + "\n");
//
//            PrintWriter writer = new PrintWriter(filename, "UTF-8");
//
//            try {
//                writer.write("");
//                writer.write(filename);
//                writer.write("\nVERSION:" + version + "\n");
//                writer.write("PARAMS:" + " alpha= " + acs.getAlpha() + " beta= " + acs.getBeta() + " ant= " + acs.getWorkers().length + " memory=" + acs.getMemory() + " q= " + acs.getQ() + "\n");
//                writer.write("ITERATION: " + acs.getIterationPerSecond() + '\n');
//                writer.write("SEED: " + acs.getSeed() + "\n");
//                writer.write("TOUR: " + Arrays.toString(acs.getSolutionTour()) + "\n");
//                writer.write("COST: " + acs.getTotalCost() + "\n");
//                writer.write("ERROR: " + acs.getSolutionError() + "\n");
//                writer.write(' ');
//                writer.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                writer.close();
//                System.out.printf("done!\n");
//
//            }
//
//        } catch (Exception e) {
//            // do something
//        }
//    }
//
//}
