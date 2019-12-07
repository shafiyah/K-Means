/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k.means;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Shaf
 */
public class KMeans {
    
    int [][] dataset = new int [75][3];
    int k;
    int [][] poinK;
   
    Cluster []clusterData ;
    Point[] centroid;
    Point[] trainingData = new Point[75];
    String nama = "D:/PENS/SMT05/Mesin Learning/praktikum/K-means/src/k/means/ruspini.csv";
     
    public void readData(String nama){
        BufferedReader br;
        String line;
        try {
            int i=0;
            br=new BufferedReader(new FileReader(nama));
            while((line=br.readLine())!=null){
                String[] ruspini=line.split(",");
                for(int j=0;j<3;j++){
                    dataset[i][j] = Integer.parseInt(ruspini[j]);
                }
                i++;   
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dataToPoint();
    }
    
    public void dataToPoint(){
       for(int i=0;i<dataset.length;i++){
          trainingData[i]= new Point(dataset[i][0],dataset[i][1]);
       }
    }
    
    public void inputK(){
       Scanner input = new Scanner(System.in);
       System.out.print("masukan jumlak k :");
       k=Integer.parseInt(input.nextLine());
       centroid = new Point[k];
       clusterData=new Cluster[k];
       
    }
    
    public double getRendomNumber(int min, int max){
        Random r = new Random();
    	double num = min + (max - min) * r.nextDouble();
        if(num<0){
    	  num=num*(-1);}
        return num;
    }
    
    public void rendomCentroid(){
       int minX=dataset[1][0],maxX=0,minY=dataset[1][1],maxY=0;
       for(int i=0;i<dataset.length;i++){
          minX=cekMinimal(minX,dataset[i][0]);
          maxX=cekMaximal(maxX,dataset[i][0]);
          minY=cekMinimal(minY,dataset[i][1]);
          maxY=cekMaximal(maxY,dataset[i][1]);
        }      
        for(int i=0;i<centroid.length;i++){
          centroid[i]= new Point((int)getRendomNumber(minX,maxX),(int)getRendomNumber(minY,maxY));
          clusterData[i] = new Cluster(i+1);
          clusterData[i].setCentroid(centroid[i]);
        }    
    }
   
    public int cekMinimal(int min,int minBaru){
        if(minBaru < min)
            min=minBaru;
        return min;
    }
    
    public int cekMaximal(int max,int maxBaru){
        if(maxBaru > max)
            max=maxBaru;
        return max;
    }
    
    public void asignClaster(){
       double min=100;
       int cluster=0;
       for(int i=0;i<trainingData.length;i++){
         for(int j=0;j<k;j++){
           double distance = trainingData[i].getDistance(trainingData[i], centroid[j]);
           if(distance < min){
               min=distance;
               cluster=j;
           }
         }
           trainingData[i].setClusterNumber(cluster+1);
           clusterData[cluster].addPoint(trainingData[i]);
       }
    }
    
    public void printCluster(){
        for(int i=0;i<k;i++){
           clusterData[i].print();
       }
    }
   
    public void calculateCentroid(){
        for(int i=0;i<k;i++){
         int poinX=0;
         int poinY=0;
         List poins = clusterData[i].getPoints();
         int n=poins.size();
            for(int j=0;j<n;j++){
             poinX +=clusterData[i].getPoints(j).getX();
             poinY +=clusterData[i].getPoints(j).getY();
            }
         if(n!=0){
            double newX=poinX/n;
            double newY=poinY/n;
            centroid[i].setX((int) newX);
            centroid[i].setX((int) newY);
         }       
      }
    }
    
    public void calculate(){
       boolean finish=false;
       int interaction=1;
       asignClaster();
       while(!finish){
           cleanClaster();
           Point[] lastCentroid =getCentroid(centroid);
           System.out.println("======== interaction " +interaction+ " ========");
           asignClaster();
           calculateCentroid();
           Point[] newCentroid = getCentroid(centroid);
           double distance=0;
           for(int i=0;i<k;i++){
            distance+=newCentroid[i].getDistance(lastCentroid[i],newCentroid[i]);
           }
           interaction++;
           printCluster();
           System.out.println("centroid distance = "+distance); 
           if(distance==0.0){
               finish=true;
           }
            sumOfSquaredError(); 
       }
      
    }
    
    public void cleanClaster(){
      for(int i=0;i<clusterData.length;i++){
          clusterData[i].clear();
      }
    }
    
    public Point[] getCentroid(Point[] centroid){
        Point[] point = new Point[centroid.length];
        for(int i=0;i<centroid.length;i++){
          point[i]=new Point(centroid[i].getX(),centroid[i].getY());
        }
        return point;
    }
    
    public void sumOfSquaredError(){
        double sse = 0;
        for(int i=0;i<k;i++){
            int poinX=0;
            int poinY=0;
            List poins = clusterData[i].getPoints();
            int n=poins.size();
            for(int j=0;j<n;j++){
                poinX +=clusterData[i].getPoints(j).getX();
                poinY +=clusterData[i].getPoints(j).getY();
            }
            if(n!=0){
                double newX=poinX/n;
                double newY=poinY/n;
                sse+=calculateSSE(i,(int)newX,(int)newY);
                
            }       
        }
        sse=sse/trainingData.length;
        System.out.println("nilai SSE= "+sse);
        
    }
    
    public double calculateSSE(int cluster, int poinX, int poinY){
       double sse=0;
       List poins = clusterData[cluster].getPoints();
       int n =poins.size();
       for(int i=0;i<n;i++){
          int x=(clusterData[cluster].getPoints(i).getX()-poinX);
          int y=(clusterData[cluster].getPoints(i).getY()-poinY);
          sse+=(x*x)+(y*y);
       }
       return pow(sse,2);
    }
    
    
    public void runProgram(){
       readData(nama);
       inputK();
       rendomCentroid(); 
       calculate(); 
    } 
    
    public static void main(String[] args) {
       KMeans kmeans = new KMeans();
       kmeans.runProgram();
    }
    
}
