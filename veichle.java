/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooad;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author reddy
 */
class veichle {
    //initialization of varibles
    double amount_collected=0;
    veichleadd arr[][] = new veichleadd[1][1];
    public String add(String veichle_num,String type_of_veichle,String owner_name,String mobile,String hours,double amount_paid){
        //get the free slots and if available add this veichle
        // return the slot number or information regarding the addition of the veichle to the space
        int num=-1;
        String ss = getfirstslot();
        System.out.println("later : "+ss);
        //check the availability
        String index[] = ss.split("@");
        if(index.length==2){
            num=Integer.parseInt(index[0])*arr[0].length+Integer.parseInt(index[1]);
            System.out.println("num 1 : "+num);
        }
        else{
            return index[0];
        }
        if(num>=0){
            System.out.println("num 2 : "+num);
            int i = Integer.parseInt(index[0]);
            int j = Integer.parseInt(index[1]);
            System.out.println(i+" : "+j);
            veichleadd obj = new veichleadd();
            obj.amount_paid=amount_paid;
            obj.in=LocalDateTime.now();
            obj.veichle_num=veichle_num;
            obj.hours=hours;
            obj.type_of_veichle=type_of_veichle;
            obj.owner_name=owner_name;
            obj.mobile=mobile;
            obj.slot=num;
            arr[i][j]=obj;
            System.out.println(arr[i][j]);
            amount_collected+=amount_paid;
        }
        return num+"";
    }
    public String getfirstslot(){
        String slotss="";
        boolean found=false;
        long max_minutes=Long.MAX_VALUE;
        long diff=Long.MAX_VALUE;
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j]==null){
                    slotss=i+"@"+j;
                    System.out.println("Initial : "+slotss);
                    found=true;
                    break;
                }
                else if(slotss.length()==0){
                    //check for min time
                    LocalDateTime toDateTime = arr[i][j].in;
                    LocalDateTime fromDateTime = LocalDateTime.now();
                    String time = arr[i][j].hours;
                    LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);
                    long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS );
                    tempDateTime = tempDateTime.plusHours( hours );
                    long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES );
                    minutes+=(hours*60);
                    String tt[]=time.split(":");
                    long min = 60*Integer.parseInt(tt[0])+Integer.parseInt(tt[1]);
                    if(minutes>min){
                        diff=minutes-min;
                    }
                    else{
                        long timediff = min-minutes;
                        if(max_minutes>timediff)
                            max_minutes=timediff;
                    }
                }
            }
            if(found){
                break;
            }
        }
        if(slotss.equals("") && diff<=0)
            slotss="wait for a while we will clear a slot";
        if(slotss.equals(""))
            slotss="wait for "+max_minutes+" minutes to get a free slot";
        return slotss;
    }
    public synchronized int getfreeslots(){
        int total=0;
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j]==null)
                    total++;
            }
        }
        return total;
    }
    public  int getallotedslots(){
        int total=0;
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j]!=null)
                    total++;
            }
        }
        return total;
    }
    public String fetchveichle(int slot,String mobile){
        String str="";
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j]!=null){
                    if(arr[i][j].slot==slot && arr[i][j].mobile.equals(mobile)){
                        //get the amount and check for complete payment
                        //check for extra payment
                        arr[i][j]=null;
                        str+="allow the person to take veichle";
                    }
                }
            }
        }
        if(str.equals(""))
            str="not_alloted";
        return str;
    }
    public double getamount(){
        return amount_collected;
    }
}
class veichleadd{
    String veichle_num,type_of_veichle,owner_name,mobile,hours;
    LocalDateTime in;
    double amount_paid;
    int slot;
    public String gethours(){
        return hours;
    }
    public String getmobile(){
        return mobile;
    }
    public String gettypeofveichle(){
        return type_of_veichle;
    }
    public String getownername(){
        return owner_name;
    }
    public String getveichlenum(){
        return veichle_num;
    }
    public LocalDateTime getindate(){
        return in;
    }
    public double getamountpaid(){
        return amount_paid;
    }
    public int getslot(){
        return slot;
    }
}
