package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Random;

public class node1 {
    public static void main(String[] args) {
        try{
            //calculates start time
            double start = System.currentTimeMillis();



            //creating socket for communication with port
            Socket s=new Socket("localhost",6658);

            //generating secret s1
            Random random = new Random();
            int secret_n1 = random.nextInt(1250);
            int IMSI = random.nextInt(25652);
            int AggMsg = secret_n1+IMSI;

            System.out.println("IMSI => "+IMSI);
            System.out.println("a  => "+secret_n1);


            System.out.println("Aggregate Message =>"+AggMsg);

            // Sending s1 to server
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());
            dout.writeUTF(String.valueOf(IMSI));
            dout.writeUTF(String.valueOf(AggMsg));


            //recieve Session key
            DataInputStream din = new DataInputStream(s.getInputStream());
            String Sk=din.readUTF();
            System.out.println("Session key =>"+Sk);

            //receive partial secret from gateway



            dout.flush();
            dout.close();
            s.close();

            Runtime runtime = Runtime.getRuntime();
            // Run the garbage collector
            runtime.gc();

            // Calculate the used memory
            long memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Memory used => "+memory/1024+" KB");
            long end = System.currentTimeMillis();
            System.out.println("Total time =>  "+(end-start)+"ms");
            System.out.println("Total time =>  "+((end-start)/1000)+"sec");


        }catch(Exception e){System.out.println(e);}
    }
}

