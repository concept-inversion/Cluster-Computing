/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threaded_sc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Client {

    Socket client1;
 


    public Client() 
    { 
        try 
        { 
             client1 = new Socket("localhost",11111); 
             
             BufferedReader in = new BufferedReader(new InputStreamReader(client1.getInputStream())); 
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); 
             PrintStream out = new PrintStream(client1.getOutputStream());
             
             String userinput,Sock_in,net="";
             
             userinput=stdIn.readLine();
                          
             out.println(userinput);
             Sock_in=in.readLine();
             
             System.out.println("Message sent to server:" + userinput+"\t");
             System.out.println(Sock_in+"\t");
             
             int a=0;
        while(a!=0)
        {}
             
            // out.println(net);
                   
         
        } 
        catch(IOException ioe) 
        { 
            System.out.println("Could not create Client socket on port 11111. Quitting."); 
            System.exit(-1); 
        } 
   } 

    public static void main (String[] args) 
    { 
        new Client();        
    } 
}


    