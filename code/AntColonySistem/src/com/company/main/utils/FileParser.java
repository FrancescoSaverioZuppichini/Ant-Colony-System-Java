package com.company.main.utils;

import com.company.main.graph.City;
import com.company.main.graph.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InvalidPropertiesFormatException;


/**
 * Created by VeaVictis on 29/10/16.
 */
public class FileParser {
    private File file;


    public void parseFromFile(File f) throws Exception {
        if (f.exists()) {
            this.file = f;
            this.parse();
        }
    }

    public Graph parseFromFileName(String filename) throws Exception {
        Graph graph;
        File f = new File(filename);
//        REVIEW bad program practice
        graph = null;
        if (f.exists()) {
            System.out.println("file opened.");
            this.file = f;
            try {
                graph = this.parse();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        } else {
            throw new FileNotFoundException();
        }

        return graph;
    }


    private Graph parse() throws Exception {
//        initial problem size to speed up the memory allocation
        int problemSize = 48;
        int pos = 0;
        int bestKnow = 0;
        Graph graph;

        graph = new Graph();
        FileReader fileReader = new FileReader(this.file);
        BufferedReader bufRead = new BufferedReader(fileReader);
        boolean shouldCopyNodes = false;
//        current line read
        String line;
//          read line by line
        while ((line = bufRead.readLine()) != null && !line.equals("EOF")) {
            if (shouldCopyNodes) {
//                add the new node to the graph
                graph.addNode(this.createNodeFromLine(line.trim(), problemSize, pos));
                pos++;
            } else if (line.split(":")[0].replace(" ", "").equals("BEST_KNOWN")) {
                bestKnow = Integer.valueOf(line.split(":")[1].replace(" ", ""));
                graph.setBestKnowSolution(bestKnow);

            } else if (line.split(":")[0].replace(" ", "").equals("DIMENSION")) {
                problemSize = Integer.valueOf(line.split(":")[1].replace(" ", ""));
                graph.initializeGraph(problemSize);

            }
//            check if we should change the state to start create the ndoes
            else if (line.replace(" ", "").equals("NODE_COORD_SECTION")) {
                shouldCopyNodes = true;
                System.out.println("start copying data...");
            }
        }

        System.out.println("Finish.");
//        close all open streams
        bufRead.close();
        fileReader.close();

        return graph;
    }

    private City createNodeFromLine(String line, int problemSize, int pos) throws Exception {
        City newNode;
        String splittedLine[];

        splittedLine = line.split(" ");

        if (splittedLine.length == 3) {
//            if now valid a Exception will be throws
            double x = Double.parseDouble(splittedLine[1]);
            double y = Double.parseDouble(splittedLine[2]);
//            create the node
            newNode = new City(x, y, splittedLine[0], pos);

        } else {
            throw new InvalidPropertiesFormatException("NODE_COORD_SECTION not properly formatted.");
        }

        return newNode;

    }



}
