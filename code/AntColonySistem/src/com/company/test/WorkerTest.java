//package com.company.test;
//
//import com.company.main.graph.City;
//import com.company.main.graph.Edge;
//import com.company.main.graph.Graph;
//import com.company.main.nest.Worker;
//import org.junit.Test;
//
//import static org.junit.Assert.assertTrue;
//
///**
// * Created by VeaVictis on 28/10/16.
// */
//public class WorkerTest {
//    @Test
//    public void getCitiesVisited() throws Exception {
//        Worker w1 = new Worker();
//        assertTrue(w1.getTour().getCities().size() == 0);
//
//    }
//
//    @Test
//    public void getRandomProbability() {
//        Worker w1 = new Worker();
//
//        assertTrue(w1.getRandomProbability() > 0);
//        assertTrue(w1.getRandomProbability() < 1);
//
//    }
//
//    @Test
//    public void localTrailUpdating() {
//        City a = new City(1, 1, 10, "a",0);
//        City b = new City(1, 2, 10, "b",1);
//        Edge e = new Edge(a, b);
//        e.setInitialPheromone(0.000000001);
//        Worker w1 = new Worker();
//        w1.localTrailUpdating(e);
//        assertTrue(e.getPheromone() > e.getInitialPheromone());
//        w1.localTrailUpdating(e);
//        assertTrue(e.getPheromone() > e.getInitialPheromone());
//        //create a bad node
//        City c = new City(1, 4, 100, "c",2);
////        create a graph
//        Graph g = new Graph(3);
//        g.addNode(a);
//        g.addNode(b);
//        g.addNode(c);
//        g.setEdgesBasedOnDistance();
////        now the worker at position a should always select position b instead of e
//        w1.resetWorker();
//        w1.setStartingPosition(a);
//        Edge aTob = null;
//        Edge aToc = null;
//        for (Edge edge : w1.getCurrentPosition().getAdj()) {
//            if (edge.getToNode() == b) {
//                aTob = edge;
//            } else if (edge.getToNode() == c) {
//                aToc = edge;
//            }
//        }
////        very big pheromone so we can see the results better
//        aTob.setInitialPheromone(2);
//        aToc.setInitialPheromone(2);
//
//        System.out.println(aToc);
//        assertTrue(w1.getProbabilityDistributionOnEdge(aTob) > w1.getProbabilityDistributionOnEdge(aToc));
//        assertTrue(w1.getProbabilityDistributionOnEdges() == w1.getProbabilityDistributionOnEdges());
//        assertTrue(w1.getProbabilityDistributionOnEdge(aTob) / w1.getProbabilityDistributionOnEdges()
//                > w1.getProbabilityDistributionOnEdge(aToc) / w1.getProbabilityDistributionOnEdges());
////      b MUST always be the correct choice
////        assertTrue(w1.edgeExploration().getToNode() == b);
//
//        w1.resetWorker();
//        w1.setStartingPosition(a);
////        assertTrue(w1.edgeExploitation().getToNode() == b);
//
//
//    }
//
//}