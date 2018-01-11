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

import com.codename1.io.Socket;
import com.codename1.io.SocketConnection;
import com.codename1.io.Storage;
import com.codename1.ui.Dialog;
import com.foodrive.myapp.Login;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author henry
 */
public class Sockets {
    
   
    InputStream inputSocket; OutputStream outputSocket; public boolean returnLoggedIn;
    public proccess proccesses;
    public void socketConnection(String input, int port, int cases){      
        //input="localhost"; port=5000;
        input="myvmlab.senecacollege.ca"; port=5937;
        proccesses.text.setText("started transfer, wait");
     Socket.connect(input,port,new SocketConnection() {
   
         @Override
         public void connectionError(int errorCode, String message) {
             Dialog.show("error", "connection error", "OK", "Cancel"); }
         @Override
         public void connectionEstablished(InputStream is, OutputStream os) {inputSocket=is; outputSocket=os;
         boolean loggedIn=login(); returnLoggedIn=loggedIn;
         if(cases==1 && loggedIn){aMethod();}
         else if(cases==2 &&  loggedIn){retrieveAll();}
         else if(cases==3 && loggedIn){retrieve2();}
         else if(cases==4 && loggedIn){sendSignUp();}
         else if(cases==5 && loggedIn){proccesses.addAllButtons();}
         try{System.out.println("closing---------"); outputSocket.write(4); is.close(); os.close(); proccesses.text.setText("completed transfer");}catch(Exception e){}
         }});
    }
    
    
    public String registerData;
    void sendSignUp(){int size=registerData.length(); byte type[]=new byte[1];
        try{type[0]=7; outputSocket.write(type[0]); 
        type[0]=(byte)(size/256); outputSocket.write(type,0,1);
        type[0]=(byte)(size%256); outputSocket.write(type,0,1);
        outputSocket.write(registerData.getBytes());
        }catch(Exception e){}
    }
    
    byte type[]=new byte[1];
     public String username="henryzhu9@hotmail.com"; public String password="password";
    boolean login(){ username= Login.loginStatic; password= Login.passwordStatic;
        boolean returned=true;try{System.out.println("logging in");
           System.out.println("username is: "+username+" "+password);
            type[0]=(byte)username.length(); outputSocket.write(type[0]); outputSocket.write(username.getBytes());
            type[0]=(byte)password.length(); outputSocket.write(type[0]); outputSocket.write(password.getBytes());
            type=new byte[1]; inputSocket.read(type, 0, 1); 
            if(type[0]==1){returned=true;} else{returned=false;}
            System.out.println(type[0]+" returnedStatus: "+returned);
       }catch(Exception e){
            Dialog.show("error", "connection error2", "OK", "Cancel");returned=false; }
            return returned;}
    
    void retrieve2(){
    System.out.println("started");
    type[0]=5; try{outputSocket.write(type[0]);}catch(Exception e){System.out.println("status error: "+e);}
     //retrieveFiles("/map/test4.html");
    retrieveFiles("/map/minus.png");retrieveFiles("/map/plus.png");
     retrieveFiles("/map/gm_0_0_1.png");
    retrieveFiles("/map/gm_1_1_2.png");
    retrieveFiles("/map/gm_2_2_3.png");
    retrieveFiles("/map/gm_4_5_4.png");
    retrieveFiles("/map/gm_8_11_5.png");
    retrieveFiles("/map/gm_17_23_6.png");
    retrieveFiles("/map/gm_35_46_7.png");
    retrieveFiles("/map/gm_71_93_8.png");
    retrieveFiles("/map/gm_142_186_9.png");retrieveFiles("/map/gm_143_186_9.png");retrieveFiles("/map/gm_143_187_9.png");retrieveFiles("/map/gm_142_187_9.png");
    for(int z1=285;z1<=287;z1++){for(int z2=372;z2<=374;z2++){retrieveFiles("/map/gm_"+z1+"_"+z2+"_10.png");}}
    for(int z1=570;z1<=574;z1++){for(int z2=745;z2<=748;z2++){retrieveFiles("/map/gm_"+z1+"_"+z2+"_11.png");}}
    for(int z1=1141;z1<=1148;z1++){for(int z2=1490;z2<=1496;z2++){retrieveFiles("/map/gm_"+z1+"_"+z2+"_12.png");}}
    
    System.out.println("completed recieving");    
    }

    void retrieve(){
    System.out.println("started");
    type[0]=3; try{outputSocket.write(type[0]);}catch(Exception e){System.out.println("status error: "+e);}
    retrieveFiles("/retrieved/temp");
    System.out.println("completed recieving");
    }
    
    void retrieveAll(){
    System.out.println("started");
    type[0]=6; try{outputSocket.write(type[0]);}catch(Exception e){System.out.println("status error: "+e);}
    while(retrieveFiles("/retrieved/a"+ioVar.count2("retrieved", 1))){}
    System.out.println("completed recieving");
    }
    
    boolean retrieveFiles(String name){try{ System.out.println("name is: "+name);
        ioVar.server= Storage.getInstance().createOutputStream(name);
      //String path=FileSystemStorage.getInstance().getAppHomePath()+name;
      //  System.out.println("home path is: "+path);
    //ioVar.server=FileSystemStorage.getInstance().openOutputStream("file://home/test4.html");
    int size=65535; byte []temp=new byte[1]; int tempSize;
    while(size==65535){inputSocket.read(temp, 0, 1); tempSize=256+(int)temp[0]; if(tempSize>=256){tempSize-=256;} size=tempSize*256;
                       inputSocket.read(temp, 0, 1); tempSize=256+(int)temp[0]; if(tempSize>=256){tempSize-=256;} size+=tempSize; System.out.println("size is: "+size);
                       if(size==0){ioVar.count2("retrieved", -1); return false;}
                       for(int i=0;i<size;i++){	temp[0]=(byte)inputSocket.read(); tempSize=256+(int)temp[0]; if(tempSize>=256){tempSize-=256;} System.out.println("zz"+tempSize); ioVar.server.write(temp, 0, 1); }
                       temp[0]=1; outputSocket.write(temp, 0, 1);
    }ioVar.server.close();
    }catch(Exception e){System.out.println("read error"+e);} return true;}

    boolean testExist(String filename){
        InputStream in;
        try{in = Storage.getInstance().createInputStream(filename); in.close(); System.out.println("exist"); }catch(Exception e){ System.out.println("not exists: "+e);
             return false;}
        return true;}

        boolean timeDifferance(String filename){
            try{
                InputStream in = Storage.getInstance().createInputStream(filename+"Start");
                byte type2 []=new byte[8]; in.read(type2, 0, 8);
                long value = 0;
                for (int i = 0; i < 8; i++)
                {
                    value = (value << 8) + (type2[i] & 0xff);
                }          in.close();
                in = Storage.getInstance().createInputStream(filename+"End");
                in.read(type2, 0, 8);
                long value2 = 0;
                for (int i = 0; i < 8; i++)
                {
                    value2 = (value2 << 8) + (type2[i] & 0xff);
                }
                in.close();
                System.out.println("value2: "+value2+" "+value);
                if(value2-value<180000){return false;}
            }catch(Exception e){return false;}
        return true;}

    void case1(String filename){try{
    InputStream in = Storage.getInstance().createInputStream(filename+"Start");
    byte type2 []=new byte[8]; in.read(type2, 0, 8); outputSocket.write(type2,0,8); in.close();
    in = Storage.getInstance().createInputStream(filename+"End");
    in.read(type2, 0, 8); outputSocket.write(type2,0,8); in.close();
    }catch(Exception e){}}
    
    io ioVar;
    void setIO(io input){ioVar=input;}
    
    boolean sendFile(String filename, int cases) { 
        try{
        System.out.println("printingFile");
        byte type3[]= new byte[1];
        System.out.println("zz");
        InputStream in = Storage.getInstance().createInputStream(filename);
        //InputStream in = Storage.getInstance()InputStream in = Storage.createInputStream("C:/Users/henry/Downloads/song.mp3");
        //InputStream in = Display.getInstance().getResourceAsStream(getClass(), filename);
        System.out.println("zz");
        byte type2 []=new byte[65535]; int size=65535; int removeTime=0; if(cases==1){removeTime=16;}
        while(size>=65535){
        size=in.read(type2, 0, 65535-removeTime);  size+=removeTime ;removeTime=0;
        System.out.println("/"+size); 
        type[0]=(byte)(size/256); outputSocket.write(type,0,1);
        type[0]=(byte)(size%256); outputSocket.write(type,0,1);
        if(cases==1){case1(filename); cases=0;}
        outputSocket.write(type2,0,size);
        inputSocket.read(type3, 0, 1); if(type3[0]!=1){
                Dialog.show("error", "file transfer problem", "OK", "Cancel"); return false;}
        }return true;
    }catch(Exception e){
            Dialog.show("error", "connection error3", "OK", "Cancel"); return false;}}
    
    void aMethod(){type=new byte[1]; int counts;
        try{
         counts=ioVar.count(0); System.out.println("counts is: "+counts);
         while(counts>=1){ if(timeDifferance("/gps/temp" + counts)) {
             type[0] = 1;
             outputSocket.write(type[0]);
             System.out.println("counts is: " + counts + " status is: " + (int) type[0]);
             if (!sendFile("/gps/temp" + counts, 1)) {
                 break;
             } //sendFile("/gps/tempStart1"+".txt"); sendFile("/gps/tempEnd1"+".txt");
         }
             counts=ioVar.count(-1);}
         }catch(Exception e){}
    }
    
    
}
