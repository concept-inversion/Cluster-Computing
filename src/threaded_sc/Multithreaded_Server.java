package threaded_sc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Multithreaded_Server {

    private static int port = 11111, maxConnections = 5;
    // Listen for incoming connections and handle them

    public static void main(String[] args) {
        int i = 0;

        try {
            ServerSocket listener = new ServerSocket(port);
            Socket server;

            while ((i++ < maxConnections) || (maxConnections == 0)) {
                // doComms connection;

                server = listener.accept();
                doComms conn_c = new doComms(server);
                Thread t = new Thread(conn_c);
                t.start();
            }
        } catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
    }

}

class doComms implements Runnable {

    private Socket server;
    private String line, input;

    doComms(Socket server) {
        this.server = server;
    }

    public void run() {

        input = "";

        try {
            // Define stream for read and write
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintStream out = new PrintStream(server.getOutputStream());

            //get thread id
            float threadid = Thread.currentThread().getId();
            float id = threadid - 9; //starting threadid for java application in 9
            //send connection msg to client '
            out.println("You are connected.Start communication  thread_id in server: \t" + threadid + " (" + id + ")");
            /*
        System.out.print("Enter the Starting  no.          :");  
         Scanner in1 = new Scanner(System.in); 
         float start = in1.nextFloat();
      
          System.out.print("Enter the ending  no.           :");
         float end =in1.nextFloat();
        
          System.out.print("Enter the no of iterations.: ");
          float step =in1.nextFloat();       
             */
            float thread = 5;
            float start = 0, end = 1, step = 1000;
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
