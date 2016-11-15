package com.company.main.utils;

/**
 * Created by VeaVictis on 09/11/16.
 */
public class Pair implements Comparable<Pair> {
    private final int index;
    private final int value;

    public Pair(int index, int value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public int compareTo(Pair other) {



        return  Integer.valueOf(this.value).compareTo(other.value);
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }
}