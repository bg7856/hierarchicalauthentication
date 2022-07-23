package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class HSS {

    public static void main(String[] args){
        try{


            //socket for node1
            ServerSocket MmeSock = new ServerSocket(6656);
            Socket s1= MmeSock.accept();//establishes connection
            DataOutputStream MOut = new DataOutputStream(s1.getOutputStream());
            DataInputStream MIn = new DataInputStream(s1.getInputStream());
            
            
            //calculates start time
            double start = System.currentTimeMillis();

            //generating random value(Nonce) for GK
            //Random random = new Random();
             long GK = new Random().nextInt(1000000000);
            System.out.println(" Group Key =>"+GK);

           //sending GK to the MME
            MOut.writeUTF(String.valueOf(GK));
            System.out.println("GK sent to MME");

            int g = 128, p=93239, AggrKey=35687;
            System.out.println("Generator g = "+g);
            System.out.println("Prime p ="+p);
            System.out.println("Agreed Key = "+AggrKey);

            String IMSI1 = MIn.readUTF();
            String IMSI2 = MIn.readUTF();

            System.out.println("IMSI node1 ="+IMSI1);
            System.out.println("IMSI node2 ="+IMSI2);

            String Sk1 = Hash.getSHA(Integer.parseInt(IMSI1));
            String Sk2 = Hash.getSHA(Integer.parseInt(IMSI2));

            System.out.println("Session key1 = "+Sk1);
            System.out.println("Session key2 = "+Sk2);


            MOut.writeUTF(Sk1);
            MOut.writeUTF(Sk2);



            MIn.close();
            MOut.close();
            s1.close();
            MmeSock.close(); //closing port of node1



            Runtime runtime = Runtime.getRuntime();
            // Run the garbage collector
            runtime.gc();

            // Calculate the used memory
            long memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Memory used => "+memory/1024+" KB");

            long end = System.currentTimeMillis();
            System.out.println("Total time =>  "+((end-start))+"ms");
            System.out.println("Total time =>  "+((end-start)/1000)+"sec");

        }catch(Exception e){System.out.println(e);}
    }
}
