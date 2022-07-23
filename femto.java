package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class femto {
    public static void main(String[] args) {
        try{
            //calculates start time
            double start = System.currentTimeMillis();



            //creating socket for communication with port

            ServerSocket node1Sock = new ServerSocket(6658);
            ServerSocket node2Sock = new ServerSocket(6659);
            Socket MME = new Socket("localhost",6657);

            Socket n1= node1Sock.accept();
            Socket n2= node2Sock.accept();

            DataInputStream din = new DataInputStream(MME.getInputStream());
            String GK = din.readUTF();
            System.out.println("GK received from MME "+GK+"\n");

            DataInputStream din2 = new DataInputStream(n1.getInputStream());
            String IMSI1 = din2.readUTF();
            System.out.println("IMSI received from Node1 "+IMSI1);

            DataInputStream din3 = new DataInputStream(n2.getInputStream());
            String IMSI2 = din3.readUTF();
            System.out.println("IMSI received from Node2 "+IMSI2);

            String AggMsg1 = din2.readUTF();
            String AggMsg2 = din3.readUTF();

            System.out.println("AggMsg received from Node1 "+AggMsg1);
            System.out.println("AggMsg received from Node2 "+AggMsg2+"\n");

            int g = 128, p=93239, AggrKey=35687;
            System.out.println("Generator g "+g);
            System.out.println("Prime p "+p);
            System.out.println("Agreed Key = "+AggrKey);

            DataOutputStream dout = new DataOutputStream(MME.getOutputStream());
            dout.writeUTF(IMSI1);
            dout.writeUTF(IMSI2);
            System.out.println("IMSIs sent to MME ");


            DataInputStream DHS = new DataInputStream(MME.getInputStream());
            String SK1 = DHS.readUTF();
            String SK2 = DHS.readUTF();
            System.out.println("Session key node1"+SK1);
            System.out.println("Session key node2"+SK2);

            DataOutputStream dout1 = new DataOutputStream(n1.getOutputStream());
            dout1.writeUTF(SK1);
            System.out.println("Session key sent to node1 ");

            DataOutputStream dout2 = new DataOutputStream(n2.getOutputStream());
            dout2.writeUTF(SK2);
            System.out.println("Session key sent to node2 ");

            din.close();
            dout.close();
            dout1.close();
            dout2.close();
            n1.close();
            din2.close();
            node1Sock.close();
            node2Sock.close();

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
