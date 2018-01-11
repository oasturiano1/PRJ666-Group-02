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


import com.codename1.location.Location;
import com.codename1.location.LocationListener;

/**
 *
 * @author henry
 */
public class gpsTracker implements LocationListener {
    
    double latitude; double longitude; public boolean nonRepeat =true;
    io i; public String id="null";
    //public static double previousLat;
    //public static double previousLong;
    
    public gpsTracker(io input, boolean runState){i=input; nonRepeat=runState;}
    public double getLatitude(){return latitude;}
    public double getLongitude(){return longitude;}
    
    @Override
    public void locationUpdated(Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
    // previousLat=latitude; previousLong=longitude;
    //    String temp1=latitude+""; String temp2=longitude+"";
        //if(latitude>0){temp1="+"+latitude;}  if(longitude>0){temp2="+"+longitude;} 
     //   if(temp1.length()>10){temp1=temp1.substring(0, 10);}
      //  if(temp2.length()>10){temp2=temp2.substring(0, 10);} 
       // System.out.println("zzz"+latitude+" "+longitude);
       // System.out.println("zzz"+temp1+" "+temp2);
        //if(nonRepeat){i.write(temp1+temp2); nonRepeat=false;}
        
        if(nonRepeat){i.write2(latitude,longitude); }//nonRepeat=false;}
    }

    @Override
    public void providerStateChanged(int newState) { }
    
}

