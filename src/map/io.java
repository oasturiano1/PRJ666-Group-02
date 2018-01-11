/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.codename1.io.Storage;
import com.codename1.io.Util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 *
 * @author henry
 */

public class io {
    OutputStream os;  InputStream i;
    OutputStream startTime; OutputStream endTime;
    public OutputStream server; 
    
    double lastLat; double lastLong;
    long startTiming; long endTiming;
    byte imageBuffer[]=new byte[40000];
    proccess p;
    
    void setProccess(proccess input){p=input;}
    
    public String readImage(String fileName){
        String returned="";  //System.out.println("------1");
        int size; 
        try{i= Storage.getInstance().createInputStream(fileName);  //System.out.println("------2");
        size=i.read(imageBuffer, 0, 40000); // System.out.println("------3");
        i.close();// System.out.println("------4");
 //   System.out.println("size is: "+size);
   // for(int ii=0;ii<size;ii++){System.out.println(byteToInt(imageBuffer[ii]));}
   // return Base64.getEncoder().encodeToString(imageBuffer);
   return MyBase64.encode(imageBuffer, size);
    }catch(Exception e){//System.out.println("io.readImage error: "+e); 
    String temp=e.toString().substring(0,29); if(temp.equals("java.io.FileNotFoundException")){returned=readImage("/map/plus.png"); } 
    } return returned;}
    
    public int byteToInt(byte temp){
    int tempSize=256+(int)temp; if(tempSize>=256){tempSize-=256;}
    return tempSize;}
    
    public void setLastLocation(double lat, double l){lastLat=lat; lastLong=l;}
    public String read(){String in=""; try{i= Storage.getInstance().createInputStream("/gps/temp"+count(0)); in= Util.readToString(i, "UTF-8"); i.close();}catch(Exception e){System.out.println("readError--"+e);} return in;}
    public void write(String input){writeLastTime(); try{os.write(input.getBytes("UTF-8")); System.out.println("---"+input+"---"); }catch(Exception e){System.out.println("write--"+e);}}
    boolean first=true; double previousLat; double previousLong;
    public void write2(double a, double b){writeLastTime();
    //double a=0; double b=0;
    try{//a=Double.parseDouble(lat); b=Double.parseDouble(lon); 
    byte [] temp= new byte[1];
    if(first){int c=(int)a; int d=(int)b; temp[0]=(byte)c; first=false;
    if(c<0 && d<0){temp[0]=0;} else if(c<0 && d>0){temp[0]=1;} else if(c>=0 && d<=0){temp[0]=2;} else if(c>=0 && d>=0){temp[0]=3;} os.write(temp, 0, 1);
    System.out.println("number is: "+a+" "+b);
    temp[0]=(byte)Math.abs(c); os.write(temp, 0, 1); temp[0]=(byte)Math.abs(d); os.write(temp, 0, 1);  previousLat=a; previousLong=b;  a=Math.abs(a); b=Math.abs(b);
    temp[0]=(byte)(((int)(a*256))%256); os.write(temp,0,1); temp[0]=(byte)(((int)(a*256*256))%256); os.write(temp,0,1); temp[0]=(byte)(((int)(a*256*256*256))%256); os.write(temp,0,1); //temp[0]=(byte)(((int)(a*256*256*256*256))%256); os.write(temp,0,1);
    temp[0]=(byte)(((int)(b*256))%256); os.write(temp,0,1); temp[0]=(byte)(((int)(b*256*256))%256); os.write(temp,0,1); temp[0]=(byte)(((int)(b*256*256*256))%256); os.write(temp,0,1); //temp[0]=(byte)(((int)(b*256*256*256*256))%256); os.write(temp,0,1);
    }
    else{double differanceA=a-previousLat; double differanceB=b-previousLong; differanceA*=1000000; differanceB*=1000000; int differanceAA=(int)differanceA; int differanceBB=(int)differanceB;
    int tempA=0; int tempB=0; boolean cancel=false;
    while(true){System.out.print("differance: "+differanceA+" "+differanceB+"    "+(int)differanceA/128+" "+(int)differanceB/128+" "+a+" "+b+" "+previousLat+" "+previousLong);
    temp[0]=0; tempA=0; tempB=0;
     if(differanceAA/128==0 &&differanceBB/128==0){cancel=true;}
     if(differanceAA>0){tempA+=128;} if(differanceAA/128>0){tempA+=127; differanceAA-=127;} else if(differanceAA/128<0){tempA+=127; differanceAA+=127;} else{tempA+=Math.abs(differanceAA%128); differanceAA=0;}
     if(differanceBB>0){tempB+=128;} if(differanceBB/128>0){tempB+=127; differanceBB-=127;} else if(differanceBB/128<0){tempB+=127; differanceBB+=127;} else{tempB+=Math.abs(differanceBB%128); differanceBB=0;}
     System.out.println("     "+tempA+" "+tempB);
     temp[0]=(byte)tempA; os.write(temp,0,1); temp[0]=(byte)tempB; os.write(temp,0,1); 
     if(cancel){break;}
     
//os.write((differanceA+"z").getBytes("UTF-8"));os.write((differanceB+"z").getBytes("UTF-8"));

    } previousLat=a;//+differanceA-(int)differanceA; 
    previousLong=b;//+differanceB-(int)differanceB; 
    }
   
    }catch(Exception e){}
    }
    int tickerCount=0;
    public void writeLastTime(){try{endTime = Storage.getInstance().createOutputStream("/gps/temp"+count(0)+"End");     long milliseconds=new Date().getTime(); endTiming=milliseconds;    byte longData[]=new byte[8]; longData=longToBytes(milliseconds); endTime.write(longData,0,8); endTime.close();  }catch(Exception e){}
  System.out.println("new time is: "+endTime);
 //   p.text.setText(tickerCount+" // "+(endTiming-startTiming)); tickerCount++;
   p.text.setText(tickerCount+" // "+previousLong+" "+previousLat); tickerCount++;
   if(tickerCount>80){p.t.start(); tickerCount=0;}
    }
    public void close(){try{ os.close(); tickerCount=0; }catch(Exception e){System.out.println("close--"+e);}}
    public void start(){first=true;
    try{
        os = Storage.getInstance().createOutputStream("/gps/temp"+count(1));

        startTime = Storage.getInstance().createOutputStream("/gps/temp"+count(0)+"Start");
    String time=new Date().toString(); //startTime.write(time.toString().getBytes()); 
    //byte [] tempTime=new byte[1];
    //String temp=time.substring(4, 7); if(temp.equals("Jan")){tempTime[0]=1;} else if(temp.equals("Feb")){tempTime[0]=2;} else if(temp.equals("Mar")){tempTime[0]=3;} else if(temp.equals("Apr")){tempTime[0]=4;} else if(temp.equals("May")){tempTime[0]=5;} else if(temp.equals("Jun")){tempTime[0]=6;} else if(temp.equals("Jul")){tempTime[0]=7;} else if(temp.equals("Aug")){tempTime[0]=8;} else if(temp.equals("Sept")){tempTime[0]=9;} else if(temp.equals("Oct")){tempTime[0]=10;} else if(temp.equals("Nov")){tempTime[0]=11;} else if(temp.equals("Dec")){tempTime[0]=12;}
    //startTime.write(tempTime,0,1); 
    //temp=time.substring(8, 10); tempTime[0]=(byte)Integer.parseInt(temp);
    //startTime.write(tempTime,0,1); 
    //if(time.length()==28){temp=time.substring(25,28);} else if(time.length()==25){temp=time.substring(22,25);}  else if(time.length()==24){temp=time.substring(21,24);}
    //tempTime[0]=(byte)Integer.parseInt(temp); startTime.write(tempTime,0,1);
    long milliseconds=new Date().getTime(); System.out.println("inputDate is: "+milliseconds); startTiming=milliseconds;
//String millisecondsString=milliseconds+""; 
    byte longData[]=new byte[8]; longData=longToBytes(milliseconds);
    startTime.write(longData,0,8);
 //   startTime.write(millisecondsString.getBytes()); 
    startTime.close();
    }catch(Exception e){System.out.println("start:"+e);}
    }

    public static byte[] longToBytes(long l) {
    byte[] result = new byte[8];
    for (int i = 7; i >= 0; i--) {
        result[i] = (byte)(l & 0xFF);
        l >>= 8;
    }
    return result;
}
    
    InputStream ii;  OutputStream oos; 
    public int count(int increment){int returned=0;
        try{ ii= Storage.getInstance().createInputStream("/gps/count.txt");  returned=Integer.parseInt(Util.readToString(ii, "UTF-8"));  ii.close();
        }catch(Exception e){System.out.println("error:"+e);}
        
        if(increment!=0){try{returned+=increment; System.out.println("returned: "+returned);
         oos = Storage.getInstance().createOutputStream("/gps/count.txt");  oos.write((returned+"").getBytes("UTF-8"));  oos.close();
        }catch(Exception e){System.out.println("errorWrite:"+e);}}
    return returned;}

        public int count2(String folder, int increment){int returned=0;
        try{ ii= Storage.getInstance().createInputStream("/"+folder+"/count.txt");  returned=Integer.parseInt(Util.readToString(ii, "UTF-8"));  ii.close();
        }catch(Exception e){System.out.println("error:"+e);}
        
        if(increment!=0){try{returned+=increment; System.out.println("returned: "+returned);
         oos = Storage.getInstance().createOutputStream("/"+folder+"/count.txt");  oos.write((returned+"").getBytes("UTF-8"));  oos.close();
        }catch(Exception e){System.out.println("errorWrite:"+e);}}
    return returned;}
    
}

