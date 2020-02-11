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
public class Outservice{
    public static void main(String[] sai)throws IOException{
        try{
            DataInputStream dis;
            DataOutputStream dos;
            try (Scanner scn = new Scanner(System.in)) {
                String send;
                Socket s = new Socket("localhost", 9542);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                System.out.println("Enter 'OUT' to start..");
                String ini = scn.nextLine();
                dos.writeUTF(ini);
                while (true) {
                    send="";
                    System.out.println("Enter 'Enter' to start or Exit' to quit..");
                    String tosend = scn.nextLine();
                    dos.writeUTF(tosend);
                    if(tosend.equals("Exit")) {
                        System.out.println("Closing this connection : " + s);
                        s.close(); 
                        System.out.println("Connection closed");
                        break;
                    }
                    System.out.println("Enter your slot number : ");
                    send+=scn.nextLine();
                    System.out.println("Enter Mobile : ");
                    send+=("@"+scn.nextLine());
                    //send the send
                    dos.writeUTF(send);
                    String received = dis.readUTF();
                    //check for extra payment if exits
                    System.out.println(received);
                    System.out.println("Thanks for visiting..");
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
