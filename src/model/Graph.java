/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.sun.xml.internal.ws.util.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author João Pedro Schmitz, Bruno G. Rech, Mário Fronza
 * @since 23/11/2018
 * @version 1.0
 */
public class Graph {

    private String fileName;
    private String text;
    private int size = 1000;
    private int numEdges;
    private LinkedList<Integer> graphList[];
    private PriorityQueue<Integer> queue;
    private File arq;

    public Graph() {
        graphList = new LinkedList[size];
        fileName = "grafo.txt";
        arq = new File(fileName);
        queue = new PriorityQueue<>();
        for (int i = 0; i < size; i++) {
            graphList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int destiny) {
        graphList[source].add(destiny);
        graphList[destiny].add(source);
        numEdges++;
    }

    public void removeEdge(int source, Integer destiny) {
        graphList[source].remove(destiny);
        numEdges--;
    }

    public void showGraph() {
        for (int i = 0; i < size; i++) {
            System.out.println(i + " -> " + graphList[i].toString());
        }
    }

    public void readGraph() {
        int value1;
        int value2;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            this.size = Integer.parseInt(br.readLine().trim());
            while ((text = br.readLine()) != null) {
                String parts[] = text.split(" ");
                value1 = Integer.parseInt(parts[0].trim())-1;
                value2 = Integer.parseInt(parts[1].trim())-1;
                addEdge(value1, value2);
            }
            System.out.println("Arquivo adicionado com sucesso");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumEdges() {
        return numEdges;
    }

    public int getSize() {
        return size;
    }

    public LinkedList<Integer>[] getGraphList() {
        return graphList;
    }

    public PriorityQueue<Integer> getQueue() {
        return queue;
    }

    public void setQueue(PriorityQueue<Integer> queue) {
        this.queue = queue;
    }

}
