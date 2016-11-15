//package com.company.test;
//
//import com.company.main.graph.Edge;
//import com.company.main.graph.City;
//import org.junit.Test;
//
//import static org.junit.Assert.assertTrue;
//
///**
// * Created by VeaVictis on 01/11/16.
// */
//public class EdgeTest {
//    @Test
//    public void Edge() throws Exception {
//        City a = new City(1, 1);
//        City b = new City(1, 2);
//        Edge e = new Edge(a, b);
//        assertTrue(e.getFromNode() == a);
//        assertTrue(e.getToNode() == b);
//        assertTrue(e.getPheromone() == 1.0);
//    }
//
//    @Test
//    public void getCost() throws Exception {
//        City a = new City(1, 1);
//        City b = new City(1, 2);
//        Edge e = new Edge(a, b);
//        assertTrue(e.getCost() == 1.0);
//
//    }
//
//}