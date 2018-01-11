/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.util.MathUtil;

import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author henry
 */
public class movementFile {
io i;
link previous=null; boolean state=true;
link arrayLink[]; int total;
void setIo(io input){i=input;}
ArrayList<Integer> z1=new ArrayList<Integer>();
ArrayList<Integer> z2=new ArrayList<Integer>();
ArrayList<Integer> z3=new ArrayList<Integer>();
ArrayList<Integer> z4=new ArrayList<Integer>(); 

ArrayList<drawRange> pixels=new ArrayList<drawRange>(); int sizeRecords=0;

public double getX(double input){return (input-defaultX)*256;}
public double getY(double input){return (input-defaultY)*256;}
//public double getX2(double input){return (input-defaultX)*256;}
//public double getY2(double input){return (input-defaultX)*256;}

public int currentPixel;
int getSize(){currentPixel=0; return sizeRecords*4;}

double scale;
double defaultX; double defaultY;
String longString;
void searchLoop(double inputLat, double inputLong, int inputZ){sizeRecords=0; longString=""; //pixels.removeAll(pixels);
z1.clear(); z2.clear(); z3.clear(); z4.clear(); sizeRecords=0;
    int temp1; int temp2; double temp11; double temp22;
    double tempLat; double tempLong;
    
      scale = 1 << inputZ;
      project(inputLat,inputLong); 
      defaultX= MathUtil.floor(xCordDouble); defaultY= MathUtil.floor(yCordDouble);
      double xRangeLeft=defaultX-1; double xRangeRight=defaultX+1;
      double yRangeUp=defaultY+1; double yRangeDown=defaultY-1;
      System.out.println("range is: "+xRangeLeft+" "+xRangeRight+" "+yRangeUp+" "+yRangeDown);
      
  //       xCord=Math.floor(tempX * scale / 256); yCord=Math.floor(tempY * scale / 256);
    double previousX; double previousY;
for(int ii=1;ii<=total;ii++){tempLat=arrayLink[ii].latitude; tempLong=arrayLink[ii].longitude; System.out.println("starter lat long "+tempLat+" "+tempLong);
project(tempLat,tempLong); previousX=xCordDouble; previousY=yCordDouble;
for(int iii=25; iii<arrayLink[ii].getSize();iii+=2){
    temp1=byteToInt(arrayLink[ii].data[iii]); temp2=byteToInt(arrayLink[ii].data[iii+1]);
    System.out.println(temp1+" "+temp2);
    if(temp1<128 && temp1!=0){temp1=-temp1;} else if(temp1>128){temp1-=128;}  temp11=temp1; temp11=temp11/1000000;
    if(temp2<128 && temp2!=0){temp2=-temp2;} else if(temp2>128){temp2-=128;}  temp22=temp2; temp22=temp22/1000000;
    tempLat+=temp11; tempLong+=temp22;
    project(tempLat,tempLong);
//System.out.println(temp1+" "+temp2); 
   System.out.println("new: "+tempLat+" "+tempLong+" "+xCordDouble+" "+yCordDouble+" "+getX(xCordDouble)+" "+getY(yCordDouble));
   if(xRangeLeft <= previousX && previousX<= xRangeRight && yRangeDown <= previousY && previousY <= yRangeUp){
   z1.add((int)getX(previousX)); z2.add((int)getY(previousY)); z3.add((int)getX(xCordDouble)); z4.add((int)getY(yCordDouble));
   longString+=getX(previousX)+","+getY(previousY)+","+getX(xCordDouble)+","+getY(yCordDouble)+","; sizeRecords++;
     //  pixels.add(new drawRange(previousX,previousY,xCordDouble,yCordDouble));
   }
   else if(xRangeLeft <= xCordDouble && xCordDouble<= xRangeRight && yRangeDown <= yCordDouble && yCordDouble <= yRangeUp){
   z1.add((int)getX(previousX)); z2.add((int)getY(previousY)); z3.add((int)getX(xCordDouble)); z4.add((int)getY(yCordDouble));
   longString+=getX(previousX)+","+getY(previousY)+","+getX(xCordDouble)+","+getY(yCordDouble)+","; sizeRecords++;         
//longString+=getX1()+","+getY1()+","+getX2()+","+getY2()+","; sizeRecords++;
    //   pixels.add(new drawRange(previousX,previousY,xCordDouble,yCordDouble));
   }
   previousX=xCordDouble; previousY=yCordDouble;
//System.out.println("pixels size: "+pixels.size());
//System.out.println(byteToInt(arrayLink[ii].data[iii])+" "+byteToInt(arrayLink[ii].data[iii]));
}
}}

void retrieveFiles(){
    link temp=previous;
byte tempByte[];
for(int ii=1;ii<=total;ii++){tempByte=arrayLink[ii].data;
for(int iii=0;iii<tempByte.length;iii++){System.out.print(byteToInt(tempByte[iii])+" ");} System.out.println("");
}
}

double xCordDouble; double yCordDouble;
void project(double lat, double lng) {
        double siny = Math.sin(lat * Math.PI / 180);

        // Truncating to 0.9999 effectively limits latitude to 89.189. This is
        // about a third of a tile past the edge of the world tile.
        siny = Math.min(Math.max(siny, -0.9999), 0.9999); 
//double test = MathUtil.log(2);
 yCordDouble=256 * (0.5 - MathUtil.log((1 + siny) / (1 - siny)) / (4 * 3.1415926535897));
        xCordDouble= 256 * (0.5 + lng / 360);
        yCordDouble*=scale/256; xCordDouble*=scale/256;
}
 



int byteToInt(byte temp){
int tempSize=256+(int)temp; if(tempSize>=256){tempSize-=256;}
return tempSize;}


void load(){
int count=i.count2("retrieved", 0); System.out.println("count is: "+count); System.out.println("----1");
InputStream in; byte [] temp=new byte[100000]; int size; System.out.println("----2");
//byte [] temp;
try{total=count; arrayLink=new link[total+1]; System.out.println("----3");
for(;count>0;count--){ System.out.println("count is//: "+count); in= Storage.getInstance().createInputStream("/retrieved/a"+count); size= Util.readAll(in, temp); /*temp=Util.readInputStream(in);*/
arrayLink[count]=new link(); arrayLink[count].setData(temp,size);  System.out.println("counts is: "+count);
in.close();
}retrieveFiles();
}catch(Exception e){System.out.println("movement: "+e);}

}
    //InputStream i= Storage.getInstance().createInputStream("/gps/temp"+count(0)); in= Util.readToString(i, "UTF-8"); i.close();
}
    

