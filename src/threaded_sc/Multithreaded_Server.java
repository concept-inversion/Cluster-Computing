package threaded_sc;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

public class Multithreaded_Server {

    private static int port = 11111, maxConnections = 5;
    private static Date date = new Date();
    public static float[] collect = new float[maxConnections];
//List<String> syncal = Collections.synchronizedList(new ArrayList<String>());
// Listen for incoming connections and handle them

    public static void main(String[] data) {

        int id = 0;
        
        System.out.println("Notice: [" + date.toString() + "] Server started!");
        
        JFrame frame = new JFrame("Notice: [" + date.toString() + "] Server started");
         frame.setSize(800,600);
        //JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);
         JTextArea textField = new JTextArea("INFORMATION ZONE.....");
        textField.setEditable(false);
 
       // JTextField v0TextField = new JTextField ("initial velocity");
        frame.add(textField);
        JScrollPane scroll = new JScrollPane(textField);
        scroll.setBounds(10, 11, 455, 249);                     // <-- THIS

        frame.add(scroll);
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        frame.setVisible(true);
         textField.setText("im here \n");
                textField.setText("line cchanged \n");
                


 /*
        float start = Float.parseFloat(Multithreaded_ServerUI.data[0]);

        System.out.print(" the starting   no. is            :" + start);
        float end = Float.parseFloat(Multithreaded_ServerUI.data[1]);

        System.out.print("Enter the no of iterations.: ");
        float step = Float.parseFloat(Multithreaded_ServerUI.data[2]);

         */
        try {
            ServerSocket listener = new ServerSocket(port);
            Socket server;

            while ((id < maxConnections) || (maxConnections == 0)) {
                // client listner and thread connection;

                server = listener.accept();
                work connector = new work(server, id, maxConnections, data[0], data[1], data[2]); // new object of class work , put arguments here
                Thread t = new Thread(connector);  // create new thread using that object
                t.start();                      //start the thread 
                System.out.println("\n Notice: [" + date.toString() + "] - Client connected! ID: " + id);
                
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
    private int id, thread;
    //private String line, input;
    private String start, end, step;

    //private float collect[];
    work(Socket server, int id, int thread, String start, String end, String step) {                      //constructor
        this.server = server;
        this.id = id;
        this.end = end;
        this.start = start;
        this.step = step;
        this.thread = thread;

    }

    @Override
    public void run() {

        try {
            // Define stream for read and write
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintStream out = new PrintStream(server.getOutputStream());

            //get thread id
            float threadid = Thread.currentThread().getId();

            //send connection msg to client '
            out.println("You are connected.Start communication  thread_id in server: \t" + threadid + " (" + id + ")");
            Apfloat threadid1 = new Apfloat(thread, 26);
            Apfloat start1 = new Apfloat(start, 26);
            Apfloat end1 = new Apfloat(end, 26);
            Apfloat step1 = new Apfloat(step, 26);
            Apfloat id1 = new Apfloat(id, 26);
            Apfloat range = end1.subtract(start1);
            Apfloat h = range.divide(step1);
            Apfloat c_in = step1.divide(threadid1);    // 2 is max thread , c_in is client size

            //each client receives starting value,step size and no of iterations
            //send starting value
            out.println(id1.multiply(range.divide(threadid1))); //start = range/thread =range for a client

            //send no of iterations for a thread
            out.println(c_in);

            //send step size
            out.println(h);

            Apfloat result2 = new Apfloat(in.readLine(), 26);

            System.out.println(" The result is " + result2 + "from thread no : " + threadid + "\n");
            server.close();
        } catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
    }
}
