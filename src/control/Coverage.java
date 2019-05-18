/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.Graph;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author João Pedro Schmitz, Bruno G. Rech, Mário Fronza
 * @since 23/11/2018
 * @version 1.0
 */
public class Coverage {

    private Graph graph;
        
    
    public Coverage(Graph graph, int size) {
        this.graph = graph;
        
    }

    //retorna o maior grau 
    public int higherDegree() {
        int maior = 0;
        int valor = 0;
        for (int i = 0; i < graph.getSize(); i++) {
            if(graph.getGraphList()[i].size() > maior){
                valor = i;
                maior = graph.getGraphList()[i].size(); 
            }
        }
        return valor;
    }
    
    //remove a vértice e todas ás suas arestas
    public int remove(int value){
        for (int i = 0; i < graph.getSize(); i++) {
            for (int j = 0; j < graph.getGraphList()[i].size(); j++) {
                if(graph.getGraphList()[i].get(j) == value){
                    graph.removeEdge(i, value);
                }
            }
        }
        int sz = graph.getGraphList()[value].size();
        for (int i = 0; i < sz; i++) {
            graph.getGraphList()[value].remove(0);
        }
        return value;
    }
        
    public void vertexCoverage(){
        int i = 0;
        while(graph.getNumEdges() != 0){
            int removed = remove(higherDegree());
            graph.getQueue().add(removed);
            System.out.println(removed);
            i++;
        }
        System.out.println(i+" vértives de "+graph.getSize());
    }


}
