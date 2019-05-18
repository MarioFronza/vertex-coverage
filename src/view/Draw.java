/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Graph;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import model.DrawGraph;

/**
 * @author João Pedro Schmitz, Bruno G. Rech, Mário Fronza
 * @since 23/11/2018
 * @version 1.0
 */
public class Draw extends JPanel {

    private List<DrawGraph> graphs = new ArrayList<DrawGraph>();
    private LinkedList<Integer> graphList[];
    private DrawGraph drawGraph;
    private Graph graph;

    public Draw(Graph graph) {

        JFrame frame = new JFrame();
        JScrollPane scrollPane = new JScrollPane(this);
        frame.add(scrollPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        this.graphList = graph.getGraphList();
        this.graph = graph;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(3000, 3000);
    }
    
     @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int j = 0;
        int k = 0;
        int sizeVertex = 0;
        int sz = graph.getSize();
        if (sz >= 30) {
            sizeVertex = 30;
        } else if(sz>=10 && sz<=20){
            sizeVertex = 10;
        }else{
            sizeVertex = 5;
        }
        for (int i = 0; i < sz; i++) {
            if (i % sizeVertex == 0) {
                k = k + 150;
                j = 150;
            }
            Color cor = Color.black;
            this.drawGraph = new DrawGraph();
            if (!graph.getQueue().isEmpty()) {
                if (graph.getQueue().contains(i)) {
                    cor = Color.BLUE;
                    //System.out.println("Mudando cor para verleho");
                    drawGraph.setCoverage(true);
                } else {
                    cor = Color.black;
                }
            }
            g.setColor(cor);
            g.fillOval(-100 + j, -100 + k, 10, 10);
            drawGraph.setId(i);
            drawGraph.setX(-100 + j);
            drawGraph.setY(-100 + k);
            graphs.add(drawGraph);
            g.setColor(Color.RED);
            g.drawString(Integer.toString(i+1), -100+ j, -100 + k);
            j = j + 100;
        }

        g.setColor(Color.black);
        for (int i = 0; i < sz; i++) {
            int x1 = 0;
            int y1 = 0;
            int x2 = 0;
            int y2 = 0;

            x1 = graphs.get(i).getX();
            y1 = graphs.get(i).getY();

            for (int l = 0; l < graphList[i].size(); l++) {
                x2 = graphs.get(graphList[i].get(l)).getX();
                y2 = graphs.get(graphList[i].get(l)).getY();

                g.drawLine(x1, y1, x2, y2);
            }
        }
        
    }
    
//    public void paint(Graphics g) {
////        int j = 150;
////        int k = 0;
//        
//    }

}
