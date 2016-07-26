package threaded_sc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Multithreaded_Server {

  private static final int port=11111, maxConnections=2;
  // Listen for incoming connections and handle them
  public static void main(String[] args) {
    int i=0;

    try{
      ServerSocket listener = new ServerSocket(port);
      Socket server;

      while((i++ < maxConnections) || (maxConnections == 0)){
       // doComms connection;

        server = listener.accept();
        doComms conn_c= new doComms(server);
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
    private String line,input;

    doComms(Socket server) {
      this.server=server;
    }

    public void run () {

      input="";

      try {
        // Define stream for read and write
        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        PrintStream out = new PrintStream(server.getOutputStream());

       //get thread id
       long threadid = Thread.currentThread().getId();
       
       //send connection msg to client 
       out.println("You are connected.Start communication  thread_id in server: \t"+ threadid);
                
               
       //send starting value
        out.println("0");
        
        //send ending value
        out.println("1");
        
        //send step size
        out.println("100000");

        float result=Float.parseFloat(in.readLine());
         System.out.println("The result is " + result + "from thread no : "+threadid);
        server.close();
      } catch (IOException ioe) {
        System.out.println("IOException on socket listen: " + ioe);
        ioe.printStackTrace();
      }
    }
}
