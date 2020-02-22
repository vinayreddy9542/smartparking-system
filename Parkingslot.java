/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooad;

import java.io.*;
import java.net.*;
/**
 *
 * @author reddy
 */
public class Parkingslot{
    public static void main(String[] sai) throws IOException{
        // creating server socket
        veichle vh = new veichle();
        ServerSocket ss = new ServerSocket(9542);
        while (true){ 
            Socket s = null; 
            try{ 
                // socket object to receive incoming client requests 
		s = ss.accept(); 
		System.out.println("Connected to : " + s);
		// obtaining input and out streams 
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                String service = dis.readUTF();
                System.out.println(service);
                // create a new thread object
                switch (service) {
                    case "IN":
                        Thread tin = new Inservic(s, dis, dos,vh);
                        tin.start();
                        break;
                    case "OUT":
                        Thread tout = new Outservic(s, dis, dos,vh);
                        tout.start();
                        break;
                    case "OWNER":
                        Thread towner = new Owner(s, dis, dos,vh);
                        towner.start();
                        break;
                    default:
                        dos.writeUTF("Enter correct service type");
                        s.close();
                        break;
                }
            } 
            catch (Exception e){ 
		System.out.println(e); 
            } 
	} 
    }
}
class Inservic extends Thread {
    final DataInputStream dis; 
    final DataOutputStream dos; 
    veichle vh;
    final Socket s; 
    public Inservic(Socket s, DataInputStream dis, DataOutputStream dos,veichle vh) { 
	this.s = s; 
	this.dis = dis; 
	this.dos = dos; 
        this.vh=vh;
    }
    @Override
    public void run(){ 
        String received; 
        while (true) { 
            try {
                // receive the answer from client 
                received = dis.readUTF(); 
                if(received.equals("Exit")){ 
                    System.out.println("Inservice " + this.s + " sends exit..."); 
                    System.out.println("Closing this connection."); 
                    this.s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                }
                else if(received.equals("Enter")){
                    received = dis.readUTF();
                }
                System.out.println(received);
                //process the data recieved
                String details[]=received.split("@");
                String vnum=details[0];
                String vtype=details[1];
                String oname=details[2];
                String mobile=details[3];
                String time=details[4];
                String amount=details[5];
                //get the slot number or slot info
                String slotnum = vh.add(vnum, vtype, oname, mobile, time,Double.parseDouble(amount));
                System.out.println("To inservice : "+slotnum);
                dos.writeUTF(slotnum);
            }
            catch (IOException e) { 
                e.printStackTrace(); 
           }
        }
        try{
            this.dis.close(); 
            this.dos.close(); 
	}
        catch(IOException e){ 
            e.printStackTrace(); 
        } 
    }
}
class Outservic extends Thread { 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
    veichle vh;
    public Outservic(Socket s, DataInputStream dis, DataOutputStream dos,veichle vh) { 
	this.s = s; 
	this.dis = dis; 
	this.dos = dos; 
        this.vh=vh;
    }
    @Override
    public void run(){ 
        String received;
        while (true) { 
            try {
                // receive the answer from client 
                received = dis.readUTF(); 
                if(received.equals("Exit")){ 
                    System.out.println("Outservice " + this.s + " sends exit..."); 
                    System.out.println("Closing this connection."); 
                    this.s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                }
                else if(received.equals("Enter")){
                    received = dis.readUTF();
                }
                System.out.println(received);
                String check[] = received.split("@");
                String status = vh.fetchveichle(Integer.parseInt(check[0]), check[1]);
                /*
                1. Available allow ot to go and replace with null
                2. Not available send veichle not registered
                */
                System.out.println(status);
                dos.writeUTF("To outservice : "+status);
            }
            catch (IOException e) { 
                e.printStackTrace(); 
           }
        }
        try{
            this.dis.close(); 
            this.dos.close(); 
	}
        catch(IOException e){ 
            e.printStackTrace(); 
        } 
    }
}
class Owner extends Thread { 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
    veichle vh;
    public Owner(Socket s, DataInputStream dis, DataOutputStream dos,veichle vh) { 
	this.s = s; 
	this.dis = dis; 
	this.dos = dos; 
        this.vh=vh;
    }
    @Override
    public void run(){ 
        String received; 
        while (true) { 
            try {
                // receive the answer from client 
                received = dis.readUTF(); 
                if(received.equals("Exit")){ 
                    System.out.println("Onwer " + this.s + " sends exit..."); 
                    System.out.println("Closing this connection."); 
                    this.s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                }
                else if(received.equals("Enter")){
                    received = dis.readUTF();
                }
                System.out.println(received);
                /*
                1. amount_collected
                2. alloted_slots
                3. free_slots
                */
                String send="";
                //based on the input get the required param
                switch(received){
                    case "1":
                        send="The total amount collected is "+vh.getamount();//get the total amount collected
                        break;
                    case "2":
                        send="The total alloted slots are "+vh.getallotedslots();
                        break;
                    case "3":
                        send="The total free slots are "+vh.getfreeslots();
                        break;
                }
                System.out.println("To owner : "+send);
                dos.writeUTF(send);
            }
            catch (IOException e) { 
                e.printStackTrace(); 
           }
        }
        try{
            this.dis.close(); 
            this.dos.close(); 
	}
        catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
}
