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
public class Inservice{
    public static void main(String[] sai) throws IOException{
        try{
            DataInputStream dis;
            DataOutputStream dos;
            try (Scanner scn = new Scanner(System.in)) {
                String send;
                Socket s = new Socket("localhost", 9542);
                Scanner sc = new Scanner(System.in);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                System.out.println("Enter 'IN' to start..");
                String ini = sc.nextLine();
                dos.writeUTF(ini);
                while (true) {
                    send="";
                    System.out.println("Enter 'Enter' to start or Exit' to quit..");
                    //System.out.println(dis.readUTF());
                    String tosend = scn.nextLine();
                    dos.writeUTF(tosend);
                    if(tosend.equals("Exit")) {
                        System.out.println("Closing this connection : " + s);
                        s.close(); 
                        System.out.println("Connection closed");
                        break;
                    }
                    //get the details
                    System.out.println("Enter veichle number : ");
                    send+=sc.nextLine();
                    System.out.println("Enter Type of veichle : ");
                    send+=("@"+sc.nextLine());
                    System.out.println("Enter Owner name : ");
                    send+=("@"+sc.nextLine());
                    System.out.println("Enter Mobile : ");
                    send+=("@"+sc.nextLine());
                    System.out.println("Enter outtime (HH:MM 24hour format) : ");
                    String time=sc.nextLine();
                    send+=("@"+time);
                    double amount=fare(time);
                    System.out.println("You have to pay "+amount);
                    System.out.println("Enter amount paid : ");
                    send+=("@"+sc.nextLine());
                    //send the send to the server
                    dos.writeUTF(send);
                    String received = dis.readUTF();
                    //check for number
                    System.out.println("Your slot number is : "+received);
                    //else print wait time
                    System.out.println("Your wait time is : "+received);
                }
            }
            dis.close(); 
            dos.close(); 
        }
        catch(IOException | NumberFormatException e){
            System.out.println(e);
        }
    }
    public static double fare(String time){
        double amount=0;
        double cost_per_min = 0.5,cost_per_extra_min=0.7;
        String t[]=time.split(":");
        int hour = Integer.parseInt(t[0]);
        int min = Integer.parseInt(t[1])+hour*60;
        if(min>0){
            if(min<31)
                amount+=min*cost_per_min;
            else{
                amount+=(min-30)*cost_per_min;  
            }
            min-=30;
        }
        if(min>0){
            amount+=min*cost_per_extra_min;
        }
        return amount;
    }
}
