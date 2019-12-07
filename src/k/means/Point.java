/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k.means;

import static java.lang.Math.sqrt;
import java.util.Random;

/**
 *
 * @author Shaf
 */
public class Point {
    public int x;
    public int y;
    public int clusterNumber=0;
 

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getClusterNumber() {
        return clusterNumber;
    }

    public void setClusterNumber(int clusterNumber) {
        this.clusterNumber = clusterNumber;
    }
    
    public double getDistance(Point trainingData, Point testingData){
        double distance = sqrt(((trainingData.getX()-testingData.getX())*
                                (trainingData.getX()-testingData.getX()))+
                                ((trainingData.getY()-testingData.getY())*
                                (trainingData.getY()-testingData.getY())));
       return distance;  
    }
   
     public String toString() {
    	return "("+x+","+y+")";
    }
}
