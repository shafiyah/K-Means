/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k.means;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shaf
 */
public class Cluster {

    public List points;
    public Point centroid;
    public int id;
   
    public Cluster(int id) {
        this.id = id;
        this.points = new ArrayList();
        this.centroid=null;
    }

    public void setPoints(ArrayList points) {
        this.points = points;
    }

    public List getPoints() {
        return points;
    }
    
    public Point getPoints(int i) {
        return (Point) points.get(i);
    }
    
    public void addPoint(Point point){
        points.add(point);
    }
   
    public Point getCentroid() {
        return centroid;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void clear() {
	points.clear();
    }
	
  
    public void print(){
       System.out.println("cluster : "+id);
       System.out.println("centroid : "+centroid.getX()+","+centroid.getY());
       for(int i=0;i<points.size();i++){
          System.out.println(points.get(i).toString());
       }
    }
}
