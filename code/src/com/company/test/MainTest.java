//package com.company.test;
//
//import com.company.main.AntColonySystem;
//import com.company.main.NearestNeighbour;
//import com.company.main.graph.City;
//import com.company.main.graph.Edge;
//import com.company.main.graph.Graph;
//import com.company.main.utils.FileParser;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import static org.junit.Assert.assertTrue;
//
///**
// * Created by VeaVictis on 29/10/16.
// */
//public class MainTest {
//    protected static Graph graph;
//    protected static AntColonySystem ATS;
//
//
//    @BeforeClass
//    public static void setup() {
//        FileParser parser = new FileParser();
//        try {
//            MainTest.graph = parser.parseFromFileName("/Users/VeaVictis/Documents/Usi/V Semester/Artificial Intelligence/AI_Cup_JAVA/src/com/company/AI_cup_2016_problems/small.tsp");
//            MainTest.graph.setEdgesBasedOnDistance();
//            //            run nearest Neighbour first
//            NearestNeighbour nearestNeighbour = new NearestNeighbour(graph.getNodes().size());
//            nearestNeighbour.findSolution(graph);
////            get the solution
//            Tour nearestNeighbourCityTour = nearestNeighbour.getTour();
//            MainTest.ATS = new AntColonySystem(10, graph.getNodes().size(), nearestNeighbourCityTour.getTotalCost());
//            MainTest.ATS.setInitialPheromoneOnEdges(graph);
//
////            check if the initial pheromone is correct
//            for (City n : graph.getNodes()) {
//                for (Edge e : n.getAdj()) {
//                    assertTrue(e.getInitialPheromone() ==  Math.pow(graph.getNodes().size() * nearestNeighbourCityTour.getTotalCost(),-1));
//                }
//            }
//
//            MainTest.ATS.findSolution(MainTest.graph);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(-1);
//        }
//    }
//
//    @Test
//    public void findSolution() {
//        assertTrue(MainTest.graph.getNodes().size() == 4);
////        assertTrue(MainTest.ATS);
//    }
//
//
//}
//
