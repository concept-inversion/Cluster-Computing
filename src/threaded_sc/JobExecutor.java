/*
This class divides the task and creates a queue of the tasks in hashmap to be run by the Workers
 */
package threaded_sc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.apfloat.Apfloat;
import static threaded_sc.JobExecutor.Result;

public class JobExecutor {

    private static int port = 11111, maxConnections = 5;
    private static final Date date = new Date();
    public static ArrayList<Apfloat> Result = new ArrayList<>();
    //List<String> syncal = Collections.synchronizedList(new ArrayList<String>());
    // Listen for incoming connections and handle them

    public static void main(String[] data) throws InterruptedException {

        int id = 0;

        
        //Thread tmain= Thread.currentThread();
        //System.out.println("\n Thread main" + tmain); 
        try {
            //create job array
            ArrayList<TaskQueue> tasklist;
            tasklist = new ArrayList<>();
            List threads = new ArrayList();  //create list of threads
            
            
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
            System.out.println("Notice: [" + date.toString() + "] Server started!");

            ServerSocket listener = new ServerSocket(port);
            Socket server;

            while ((id < maxConnections) || (maxConnections == 0)) {
                // client listner and thread connection;
                server = listener.accept();
                TaskQueue pass = tasklist.get(id);
                work connector = new work(server, id, maxConnections,pass); // new object of class work , put arguments here
                ThreadGroup pi = new ThreadGroup("Workers");
                String name = Integer.toString(id);
                Thread t = new Thread(pi,connector,name);  // create new thread using that object
                System.out.println("\n Notice: [" + date.toString() + "] - Client connected! ID: " + id);
                t.start();                      //start the thread 
               threads.add(t);

                id++;
              /*  System.out.println("\n Starting pi.list");
                pi.list();
                System.out.println("\n Ending pi.list");
                */
}
            
            System.out.println("/NO of client connected is : " + id);
            
            
            
            for (int i = 0; i < threads.size(); i++) //error may happen here
    {
        // Big number to wait so this can be debugged
         System.out.println("JOINING: " + threads.get(i));
        ((Thread)threads.get(i)).join();
        
    }
             System.out.println("JOINed all threads ");
              Iterator itr = Result.iterator();
              Apfloat temper = new Apfloat(0,26);
              System.out.println("The items of list are:"); 
    while(itr.hasNext())
    {
    
        Apfloat sing = (Apfloat) itr.next();
    
        System.out.println(sing+ "  ");
    temper = temper.add(sing);
    }
        System.out.println("\n The result is " +temper.toString(true));
         JOptionPane.showMessageDialog(null ,temper.toString(true));
        } catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
        
            
        
    }

    static TaskQueue get(int id) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
}

class work implements Runnable {

    private Socket server;
    private int id, thread;
    //private String line, input;
    TaskQueue test  = new TaskQueue("","",""); 
    //private float collect[];
    
    work(Socket server, int id, int thread,TaskQueue test) {                      //constructor
        this.server = server;
        this.id = id;
             
        this.thread = thread;
        this.test = test;
    }
    

    @Override
    public void run() {

        try {
            test.printDetails();
            //TaskQueue gh = JobExecutor.get(id);
// Define stream for read and write
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintStream out = new PrintStream(server.getOutputStream());

            //get thread id
            Thread tes = Thread.currentThread();
            System.out.println("thread id os :" + tes);
            float threadid = Thread.currentThread().getId();

            //send connection msg to client '
            out.println("You are connected.Start communication  thread_id in server: \t" + threadid + " (" + id + ")");
            

            //each client receives starting value,step size and no of iterations
            //send starting value
            out.println(test.start); //start = range/thread =range for a client

            //send no of iterations for a thread
            out.println(test.increment);

            //send step size
            out.println(test.stepsize);
           System.out.println("\n Message Sent. Waiting for reply : ");
        
         
         ResultQueue reply = new ResultQueue();
         
        Apfloat result2;
            result2 = new Apfloat(in.readLine(), 26);
       //     reply.ResultQueue(id,result2.toString(true) );    
       System.out.println("\n Adding result to the queue ");     
       Result.add(result2);            
//  System.out.println("\n im behind");
            System.out.println(" The result is " + result2 + "from thread no : " + threadid + "\n");

        server.close();
        } catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
        
    }
    
   
}
