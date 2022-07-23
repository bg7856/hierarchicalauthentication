package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class MME {
    public static void main(String[] args) {
        try{
            //calculates start time
            double start = System.currentTimeMillis();


            //creating socket for communication with port
            Socket Hss = new Socket("localhost",6656);
            ServerSocket femSock = new ServerSocket(6657);
            Socket s2= femSock.accept();

            //Recieve it from HSS
            DataInputStream din = new DataInputStream(Hss.getInputStream());
            String GK = din.readUTF();
            System.out.println("GK received from HSS "+GK);

            //sending to femto
            DataOutputStream dout = new DataOutputStream(s2.getOutputStream());
            dout.writeUTF(GK);
            System.out.println("GK sent to Femto");

            DataInputStream din2 = new DataInputStream(s2.getInputStream());
            String IMSI1 = din2.readUTF();
            String IMSI2 = din2.readUTF();
            System.out.println("IMSI1 = "+IMSI1);
            System.out.println("IMSI2 = "+IMSI2);

            System.out.println("Sending IMSI to HSS");
            DataOutputStream dout1 = new DataOutputStream(Hss.getOutputStream());
            dout1.writeUTF(IMSI1);
            dout1.writeUTF(IMSI2);
            System.out.println("Sent..");

            String SK1 = din.readUTF();
            System.out.println("SK1 received from HSS  "+SK1);
            String SK2 = din.readUTF();
            System.out.println("SK2 received from HSS "+SK2);

            dout.writeUTF(SK1);
            dout.writeUTF(SK2);
            System.out.println("Session keys sent to Femto");

            din.close();
            dout.flush();
            dout.close();
            s2.close();

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
