/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

/**
 *
 * @author henry
 */
public class link {
double latitude; double longitude;
public byte data[];
int size;
 
//void linkCurrent(link input){previous=input;}
//void setData(byte input[], int size){data=new byte[size]; for(int i=0;i<size;i++){data[i]=input[i];}}
void setData(byte input[],int inputSize){System.out.println("----1"); size=inputSize;
data=new byte[inputSize]; System.out.println("----2"); for(int i=0;i<inputSize;i++){data[i]=input[i];} System.out.println("----3");
//data=input;
getStarter();
}

void getStarter(){
    int status=byteToInt(data[16]);
    latitude=byteToInt(data[17]); longitude=byteToInt(data[18]);
    double temp;
    temp=byteToInt(data[19]); latitude+=temp/256; temp=byteToInt(data[20]); latitude+=temp/256/256; temp=byteToInt(data[21]); latitude+=temp/256/256/256;
    temp=byteToInt(data[22]); longitude+=temp/256; temp=byteToInt(data[23]); longitude+=temp/256/256; temp=byteToInt(data[24]); longitude+=temp/256/256/256;
    if(status==0){latitude=-latitude; longitude=-longitude;}
    else if(status==1){latitude=-latitude;}
    else if(status==2){longitude=-longitude;}
    else if(status==3){}
    System.out.println("latitude is: "+latitude+" longitude is: "+longitude);
}

int getSize(){return size;}
//double getLat(int index){}
//double getLong(int index){}


int byteToInt(byte temp){
int tempSize=256+(int)temp; if(tempSize>=256){tempSize-=256;}
return tempSize;}

}