package Cluster;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Santosh
 */
public class ResultQueue {
 
    String result;
     ArrayList<String> Result = new ArrayList<>();
            
           
    public void ResultQueue(int id , String result) {
   System.out.println(result+"\n The result is from the result Queue \n");
   
   Result.add(result);
   
  
    Iterator itr = Result.iterator();
    System.out.println("The items of list are:"); 
    while(itr.hasNext())
    {
        Object sing = itr.next();
    System.out.println(sing+ "  ");
    }
}
      public void printDetails() {
   System.out.println(Result +"   ");

      }
}
