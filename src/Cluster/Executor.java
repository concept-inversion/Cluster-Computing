/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cluster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import org.apfloat.Apfloat;
import static Cluster.JobExecutor.Result;
import static Cluster.JobExecutor.tasklist ;
       
;
/**
 *
 * @author concept-inversion
 */
class Executor implements Runnable {

    private Socket server;
    private int id, thread;
    
    private static String IP;
    TaskQueue test = new TaskQueue("", "", "");
    //private float collect[];

    Executor(Socket server, int id, int thread, TaskQueue test) {                      //constructor
        this.server = server;
        this.id = id;

        this.thread = thread;
        this.test = test;
    }

    @Override
    public void run() {
        
        long threadid = Thread.currentThread().getId();
        try {
//            test.printDetails();
            //TaskQueue gh = JobExecutor.get(id);
// Define stream for read and write
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintStream out = new PrintStream(server.getOutputStream());

                
            
              IP = in.readLine();
            //send connection msg to client '
            out.println("connected.Start communication  thread_id in server: \t" + threadid + " (" + id + ")");
            
            
            /*each client receives starting value,step size and no of iterations
            //send starting value */
            out.println(test.start); //start = range/thread =range for a client

            //send no of iterations for a thread
            out.println(test.increment);

            //send step size
            out.println(test.stepsize);
            

            Apfloat result2;
            result2 = new Apfloat(in.readLine(), 26);

            //System.out.println("\n Adding result to the queue ");
            Result.add(result2);
            System.out.println(" The result is " + result2 + "from Computer no : " + IP + "\n");
            in.close();
            out.close();
            server.close();
        } catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            // if error again throw it to the list
            tasklist.add(test);
            System.out.println("Fault occured at Computer IP  : "+ IP);
            System.out.println("\n Rewriting the task to the tasklist...."); 
            
            ioe.printStackTrace();
        }

    }

}
