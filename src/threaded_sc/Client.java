/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threaded_sc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

public class Client {

    Socket client1;

    public Client() {
        try {
            client1 = new Socket("localhost", 11111);

            BufferedReader in = new BufferedReader(new InputStreamReader(client1.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            PrintStream out = new PrintStream(client1.getOutputStream());

            String userinput, Sock_in, net = "";
            float start = 0, c_in = 0, h = 0;

            //connection message from server
            Sock_in = in.readLine();
            System.out.println(Sock_in);

            //first message from server
            Sock_in = in.readLine();
            start = Float.parseFloat(Sock_in);
            System.out.println(start + "\t start value\n");

            //second message from server
            Sock_in = in.readLine();
            c_in = Float.parseFloat(Sock_in);              
                  
            System.out.println(c_in + "\t no of iterations\n");

            //third message from server
            Sock_in = in.readLine();
            h = Float.parseFloat(Sock_in);
            System.out.println(h + "\t the step size for each iteration\n");

            Apfloat sum = calc(start, c_in, h);
            userinput = sum.toString(true);
            out.println(userinput);
            System.out.printf("yei kur vaneko" + userinput);

        } catch (IOException ioe) {
            System.out.println("Could not create Client socket on port 11111. Quitting.");
            System.exit(-1);
        }
    }

        //calculating function 
        static Apfloat calc(float start, float itr_in, float steps) {
            
            Apfloat x_start = new Apfloat(start, 26);
        System.out.print("value of a is " + x_start.toString(true) + "\n");
        Apfloat y_itr = new Apfloat(itr_in, 26);
        System.out.print("value of b is " + y_itr.toString(true) + "\n");
        Apfloat z_steps = new Apfloat(steps, 26);
        System.out.print("value of n is " + z_steps.toString(true) + "\n");
        Apfloat end = y_itr.multiply(z_steps).add(x_start);
        
        
         Apfloat e;
        Apfloat temp = new Apfloat(0,26),  sum = new Apfloat(0, 26), one = new Apfloat(1, 26);
        for (e = x_start; e.compareTo(end) == -1 || e.compareTo(end) == 0; e = e.add(z_steps)) {

            /*step size is h
      start is e
     end is y
      equation is described here ,,,, it is of finding value of pi
             */
            temp = e.multiply(e);
            temp = one.subtract(temp);
            temp = ApfloatMath.sqrt(temp);

            temp = temp.multiply(z_steps);
            temp = temp.multiply(new Apfloat(4));
            sum = sum.add(temp);
            //System.out.print(e.toString(true)+"\n");    
            //System.out.print(e.toString(true)+"\n");     
        }

        
          /*  float temp = 0, end = 0, sum = 0;
        end = start + h * c_in;              // start + no of iteration * step size
        System.out.println("\n value of end is  " + end);
        for (float i = start; i <= end; i += h) {

            
            temp = (float) Math.sqrt(1 - i * i);
            //System.out.print(temp+ "The floating value \n");
            temp = 4 * h * temp;
            sum = sum + temp;

        } */
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("i start here");
        new Client();
    }
}
