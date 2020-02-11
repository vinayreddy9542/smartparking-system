/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooad;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author reddy
 */
public class Ownerservice {
    public static void main(String[] sai) throws IOException{
        try{
            DataInputStream dis;
            DataOutputStream dos;
            try (Scanner scn = new Scanner(System.in)) {
                Socket s = new Socket("localhost", 9542);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                System.out.println("Enter any one the following : ");
                //give the list of service numbers
                System.out.println("'OWNER' to start..");
                String ini = scn.nextLine();
                dos.writeUTF(ini);
                System.out.println("1. Total amount collected..");
                System.out.println("2. Slots alloted..");
                System.out.println("3. Free slots..");
                while (true){
                    String send="";
                    System.out.println("Enter 'Enter' to start or Exit' to quit..");
                    String tosend = scn.nextLine();
                    dos.writeUTF(tosend);
                    if(tosend.equals("Exit")) {
                        System.out.println("Closing this connection : " + s);
                        s.close();
                        System.out.println("Connection closed");
                        break;
                    }
                    System.out.println("Enter the service number : ");
                    int service = Integer.parseInt(scn.nextLine());
                    switch(service){
                        //match the int with list of services.
                        case 1:
                            send = "amount_collected";
                            break;
                        case 2:
                            send="alloted_slots";
                            break;
                        case 3:
                            send="free_slots";
                            break;
                    }
                    //send the send
                    dos.writeUTF(send);
                    String received = dis.readUTF();
                    System.out.println(received);
                }
            }
            dis.close(); 
            dos.close(); 
        }
        catch(IOException | NumberFormatException e){
            System.out.println(e);
        }
    }
}
