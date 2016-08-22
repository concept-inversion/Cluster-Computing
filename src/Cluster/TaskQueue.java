/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cluster;

/**
 *
 * @author Santosh
 */
public class TaskQueue {

    String start,increment,stepsize;
    
    public TaskQueue(String start,String increment,String stepsize)
    {
      this.start=start;
      this.increment=increment;
      this.stepsize = stepsize;
    }
    public void printDetails() {
   System.out.println(start + " this is starting value \n" + increment +
                                     "  increment is here \n " + stepsize + " and stepsize \n");
 }
}


    
    
