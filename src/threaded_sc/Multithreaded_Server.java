package threaded_sc;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;

public class Multithreaded_Server {

    private static int port = 11111, maxConnections = 2;
     private static Date date = new Date();
    List<String> syncal = 
         Collections.synchronizedList(new ArrayList<String>());
// Listen for incoming connections and handle them

    public static void main(String[] args) {
        int id = 0;
        System.out.println("Notice: [" + date.toString() + "] Server started!");
        JFrame frame = new JFrame("Notice: [" + date.toString() + "] Server started");
         frame.setSize(400,400);
        //JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);
         JLabel label=new JLabel("HUnter", JLabel.CENTER); 
        JTextField v0TextField = new JTextField ("initial velocity");
        frame.add(label);
        frame.setVisible(true);
        
                    
        System.out.print("Enter the Starting  no.          :");  
         Scanner in1 = new Scanner(System.in); 
         float start = in1.nextFloat();
      
          System.out.print("Enter the ending  no.           :");
         float end =in1.nextFloat();
        
          System.out.print("Enter the no of iterations.: ");
          float step =in1.nextFloat();       
             
        
 
        
        
        try {
            ServerSocket listener = new ServerSocket(port);
            Socket server;

            while ((id < maxConnections) || (maxConnections == 0)) {
                // client listner and thread connection;

                server = listener.accept(); 
                work connector = new work(server,id,maxConnections,start,end,step); // new object of class work , put arguments here
                Thread t = new Thread(connector);  // create new thread using that object
                t.start();                      //start the thread 
                System.out.println("Notice: [" + date.toString() + "] - Client connected! ID: " + id);
                id++;
            }
        } catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
    }

}

class work implements Runnable {

    private Socket server;
    private int id,thread;
    //private String line, input;
    private float start,end,step;
    
    work(Socket server,int id,int thread,float start,float end,float step) {                      //constructor
        this.server = server;
        this.id=id;
        this.end=end;
        this.start=start;
        this.step=step;
        this.thread=thread;
    }

    public void run() {

      
        try {
            // Define stream for read and write
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintStream out = new PrintStream(server.getOutputStream());

            //get thread id
            float threadid = Thread.currentThread().getId();
            
            //send connection msg to client '
            out.println("You are connected.Start communication  thread_id in server: \t" + threadid + " (" + id + ")");

      //      float thread = 5;
        //    float start = 0, end = 1, step = 1000;
            float h = (end - start) / step;
            float c_in = step / thread;    // 2 is max thread , c_in is client size

            //each client receives starting value,step size and no of iterations
            //send starting value
            out.println(id * (end - start) / thread); //start = range/thread =range for a client

            //send no of iterations for a thread
            out.println(c_in);

            //send step size
            out.println(h);

            float result = Float.parseFloat(in.readLine());
            System.out.println("The result is " + result + "from thread no : " + threadid);
            server.close();
        } catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
    }
}
