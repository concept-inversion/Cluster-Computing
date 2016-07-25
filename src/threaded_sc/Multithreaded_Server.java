
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Multithreaded_Server {

  private static int port=11111, maxConnections=2;
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
        
       // Now read from client then write to the client 
       line=in.readLine();
          input=input + line;
          out.println("Your thread_id in server \t"+ threadid);
        

        
       //print the message
        System.out.println("Overall message is:" + input+ "\t From process \t"+ threadid);
        out.println("Overall message is:" + input+ "\t From process \t"+ threadid);
        

        server.close();
      } catch (IOException ioe) {
        System.out.println("IOException on socket listen: " + ioe);
        ioe.printStackTrace();
      }
    }
}
