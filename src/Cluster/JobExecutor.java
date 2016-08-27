/*
This class divides the task and creates a queue of the tasks in hashmap to be run by the Workers
 */
package Cluster;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath; 
import javax.swing.JOptionPane;

public class JobExecutor {

    private static int port = 11111, maxConnections =100;
    private static final Date date = new Date();
    private static long startTime;
    public static ArrayList<Apfloat> Result = new ArrayList<>();
     public static ArrayList<TaskQueue> tasklist = new ArrayList<>();

//List<String> syncal = Collections.synchronizedList(new ArrayList<String>());
    // Listen for incoming connections and handle them

    public static void main(String[] data ) throws InterruptedException {

        int id = 0;

        
        
       
        try {
            //create job array
           
            List threads = new ArrayList();  //create list of threads
            
            long start =System.currentTimeMillis();
            //division on arraylist starts here
            Apfloat max = new Apfloat(maxConnections, 26);
            Apfloat start1 = new Apfloat(data[0], 26);
            Apfloat end1 = new Apfloat(data[1], 26);
            Apfloat step1 = new Apfloat(data[2], 26);
            Apfloat id1 = new Apfloat(0, 26);
            Apfloat range = end1.subtract(start1);
            Apfloat h = range.divide(step1);
            Apfloat c_in = step1.divide(max);    // 2 is max thread , c_in is client size
            Apfloat one = new Apfloat(1, 26);
            Apfloat chunk_size = h.multiply(c_in);
            Apfloat e;
            Apfloat temp = new Apfloat(0, 26), sum = new Apfloat(0, 26); 
            
            for (e = start1; e.compareTo(end1) == -1; e = e.add(chunk_size)) {

                // TaskQueue taskid = new ;
                TaskQueue task = new TaskQueue(e.toString(true), c_in.toString(true), h.toString(true));
                tasklist.add(task);
                id1.add(one);
            }
            
            for (TaskQueue m : tasklist) {
                m.printDetails();
            }
            long end = System.currentTimeMillis();
            long total = end -start;
            System.out.println("\n Task Queue Generation Time : " + total+" ms "); 
             System.out.println("\n Task Queue Generated \n");   
            System.out.println("Notice: [" + date.toString() + "] Server started!");

            ServerSocket listener = new ServerSocket(port);
            Socket server;
            

            while (id<tasklist.size()) {
                // client listner and thread connection;
                server = listener.accept();
                if(id==0)
                {
                     startTime = System.currentTimeMillis();
                }
                TaskQueue pass = tasklist.get(id);
                Executor connector = new Executor(server, id, maxConnections,pass); // new object of class work , put arguments here
                ThreadGroup pi = new ThreadGroup("Workers");
                String name = Integer.toString(id);
                Thread t = new Thread(pi,connector,name);  // create new thread using that object
                InetAddress ip = server.getInetAddress();
                System.out.println("\n Notice: [" + date.toString() + "] - Worker connected! ID: " + id+ "\t IP address"+ip);
                t.start();                       //start the thread 
               threads.add(t);

                id++;
              /*  System.out.println("\n Starting pi.list");
                pi.list();
                System.out.println("\n Ending pi.list");
                */
}
            
            System.out.println("/No of client connected is : " + id);
            
            
            
            for (int i = 0; i < threads.size(); i++) //error may happen here
    {
        // Big number to wait so this can be debugged
         System.out.println("JOINING: " + threads.get(i));
        ((Thread)threads.get(i)).join();
        
    }
             System.out.println("All thread works finished ");
             long star = System.currentTimeMillis();
              Iterator itr = Result.iterator();
              Apfloat temper = new Apfloat(0,26);
              System.out.println("The items of result are:"); 
              long en;
            
    while(itr.hasNext())
    {
    
        Apfloat sing = (Apfloat) itr.next();
    
        //System.out.println(sing.toString(true)+ "  ");
    temper = temper.add(sing);
    }
    en = System.currentTimeMillis();
            long tota = en -star;
            System.out.println("\n Result collection and printing Time : " + tota+" ms "); 
        System.out.println("\n The result is " +temper.toString(true));
        long stopTime = System.currentTimeMillis();
        long totaltime= stopTime - startTime;
        System.out.println("\n Total time taken is : " + totaltime + " ms");
        Apfloat Pi = ApfloatMath.pi(100);
        Apfloat error = temper.subtract(Pi);
        System.out.println("\n The error in computed Value:  " +error.toString(true));
        JOptionPane.showMessageDialog(null ,"\n Value of Pi                        :  "+temper.toString(true)+ "\n"+"Error in Calculated value : "+ error.toString(true)+"\n Time taken : " + totaltime+ " ms");
        } catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
        
            
        
    }

 
    
   
}

