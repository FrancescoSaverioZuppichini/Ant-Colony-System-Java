package com.company.main.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * City class extends City in order to provide a
 * more detail description of it adding it's name
 * Created by VeaVictis on 29/10/16.
 */
public class City {
    private String name;
    private double x;
    private double y;
//    private ArrayList<Edge> adj;

    private int pos;

    public City(double x, double y, String name, int pos) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.pos = pos;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getPos() {
        return pos;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return pos == city.pos;

    }

    @Override
    public int hashCode() {
        return pos;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
