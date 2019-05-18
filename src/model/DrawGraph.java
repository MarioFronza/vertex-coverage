/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author mario
 */
public class DrawGraph {
    
    private int Id;
    private int x;
    private int y;
    private boolean coverage;

    public DrawGraph() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isCoverage() {
        return coverage;
    }

    public void setCoverage(boolean coverage) {
        this.coverage = coverage;
    }
    
    
}
