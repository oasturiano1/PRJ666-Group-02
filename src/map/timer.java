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

import com.codename1.location.LocationManager;
import com.codename1.location.LocationRequest;

import java.util.Timer;

import static com.codename1.location.LocationRequest.PRIORITY_MEDIUM_ACCUARCY;

/**
 *
 * @author henry
 */
public class timer {
    
  gpsTracker z; static LocationRequest req; static LocationManager manager;
  Timer time; io i;
  boolean runState=false;
  
  public void active(){
  runState=true; z.nonRepeat=true; i.start();
  }
  
  public timer(io input){
      req = new LocationRequest(PRIORITY_MEDIUM_ACCUARCY,1);
      manager = LocationManager.getLocationManager();
      i=input; 
  }
  
  public void timerStop(){
  //time.cancel(); 
  runState=false; z.nonRepeat=false;}
    
  public void start(){

  z = new gpsTracker(i,runState);
  manager.setLocationListener(z,req);
}
    
}
