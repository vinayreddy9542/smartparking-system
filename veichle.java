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
    static veichleadd arr[][] = new veichleadd[2][1];
    public String add(String veichle_num,String type_of_veichle,String owner_name,String mobile,String hours,double amount_paid){
        //get the free slots and if available add this veichle
        // return the slot number or information regarding the addition of the veichle to the space
        int num=-1;
        String ss = getfirstslot();
        System.out.println("later : "+ss);
        //check the availability
        String index[] = ss.split("@");
        if(index.length==2){
            num=Integer.parseInt(index[0])+arr[0].length*Integer.parseInt(index[0]);
        }
        else{
            return index[0];
        }
        if(num>=0){
            int i = Integer.parseInt(index[0]);
            int j = Integer.parseInt(index[1]);
            veichleadd obj = new veichleadd();
            arr[i][j]=obj;
            arr[i][j].slot=num;
            arr[i][j].amount_paid=amount_paid;
            arr[i][j].in=LocalDateTime.now();
            arr[i][j].veichle_num=veichle_num;
            arr[i][j].hours=hours;
            arr[i][j].type_of_veichle=type_of_veichle;
            arr[i][j].owner_name=owner_name;
            arr[i][j].mobile=mobile;
            System.out.println(arr[i][j]);
            amount_collected+=amount_paid;
        }
        return num+"";
    }
    public String getfirstslot(){
        String slotss="";
        long max_minutes=Long.MAX_VALUE;
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j]==null && slotss==""){
                    slotss=i+"@"+j;
                    System.out.println("Initial : "+slotss);
                    break;
                }
                else if(slotss!=""){
                    //check for min time
                    LocalDateTime toDateTime = arr[i][j].in;
                    LocalDateTime fromDateTime = LocalDateTime.now();
                    String time = arr[i][j].hours;
                    LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);
                    long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS );
                    tempDateTime = tempDateTime.plusHours( hours );
                    long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES );
                    tempDateTime = tempDateTime.plusMinutes( minutes );
                    minutes+=(hours*60);
                    if(minutes<max_minutes){
                        max_minutes=minutes;
                    }
                    String tt[]=time.split("@");
                    long min = 60*Integer.parseInt(tt[0])+Integer.parseInt(tt[0]);
                    if(min<=(minutes-max_minutes)){
                        slotss+="wait for a while we clear slot "+i+arr[0].length*j;
                    }
                }
            }
        }
        if(slotss.equals(""))
            slotss="wait for "+max_minutes+" to get a free slot";
        return slotss;
    }
    public synchronized int getfreeslots(){
        int total=0;
        for(int i=0;i<2;i++){
            for(int j=0;j<10;j++){
                if(arr[i][j]==null)
                    total++;
            }
        }
        return total;
    }
    public String fetchveichle(int slot,String name){
        String str="";
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j]!=null){
                    if(arr[i][j].slot==slot && arr[i][j].owner_name==name){
                        //get the amount and check for complete payment
                        //check for extra payment
                        LocalDateTime in = arr[i][j].getindate();
                        LocalDateTime out = LocalDateTime.now();
                        double amount = getfinalamount(in,out);
                        double diff = amount-arr[i][j].getamountpaid();
                        if(diff>0){
                            //collect more money
                            amount_collected+=diff;
                            str=diff+"@";
                        }
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
    public double getfinalamount(LocalDateTime fromDateTime,LocalDateTime toDateTime){
        double amount = 0;
        double cost_per_min = 0.5,cost_per_extra_min=0.7;
        //calculate the difference of the time and calculate the final amount
        LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);
        long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS );
        tempDateTime = tempDateTime.plusHours( hours );
        long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES );
        tempDateTime = tempDateTime.plusMinutes( minutes );
        minutes+=(hours*60);
        if(minutes>0){
            if(minutes<31)
                amount+=minutes*cost_per_min;
            else{
                amount+=(minutes-30)*cost_per_min;  
            }
            minutes-=30;
        }
        if(minutes>0){
            amount+=minutes*cost_per_extra_min;
        }
        return amount;
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
