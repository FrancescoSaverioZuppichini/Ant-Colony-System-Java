//package com.company.test;
//
//import com.company.main.graph.City;
//import com.company.main.nest.Nest;
//import com.company.main.nest.Worker;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.*;
//
///**
// * Created by VeaVictis on 28/10/16.
// */
//public class NestTest {
//    private Nest nest1;
//
//    @org.junit.Test
//    public void createNest() {
//        this.nest1 = new Nest(100);
//        assertEquals(nest1.getWorkers().size(), 100);
//        assertTrue(this.nest1.getWorkers().get(10).getCurrentPosition() == null);
//        nest1.killThemAll();
//        assertEquals(nest1.getWorkers().size(), 0);
//
//    }
//
//    @org.junit.Test
//    public void setWorkersOnRandCities() {
//        ArrayList<City> nodes = new ArrayList<City>();
//        City n1 = new City(10, 10);
//        nodes.add(n1);
//        this.nest1 = new Nest(100);
//        this.nest1.setWorkersOnRandCities(nodes);
//        assertTrue(this.nest1.getWorkers().get(10).getCurrentPosition() != null);
//        assertTrue(this.nest1.getWorkers().get(20).getCurrentPosition().getX() == n1.getX());
//    }
//
//    @org.junit.Test
//    public void resetWorker() {
//        ArrayList<City> nodes = new ArrayList<City>();
//        City n1 = new City(10, 10);
//        nodes.add(n1);
//        this.nest1 = new Nest(100);
//        this.nest1.setWorkersOnRandCities(nodes);
////            add some stuff
//        Worker w1 = this.nest1.getWorkers().get(10);
//        w1.setCurrentPosition(n1);
//        assertTrue(w1.getCurrentPosition().getX() == n1.getX());
////          reset
//        this.nest1.resetWorkers(nodes.size());
//        w1 = this.nest1.getWorkers().get(10);
//        assertTrue(w1.getCurrentPosition() == null);
//        assertFalse(w1.getTotalCost() == 5);
//
//    }
//
//    @org.junit.Test
//    public void whoIsTheBestWorker() {
//        this.nest1 = new Nest(0);
//        Worker w1 = new Worker();
//        Worker w2 = new Worker();
//
//    }
//
//}