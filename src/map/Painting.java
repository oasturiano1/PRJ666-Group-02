/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import com.codename1.charts.util.ColorUtil;
import com.codename1.io.Storage;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.util.MathUtil;

import java.io.InputStream;

import static com.codename1.ui.Image.createImage;

/**
 *
 * @author henry
 */
public class Painting {
   Form hi; Image image; Image images [][]=new Image[3][3];
   MenuBar menu=new MenuBar();
   double startLat=43.762102; double startLong=-79.3872; int zoom=10;
   int tileXCenter; int tileYCenter; 
   proccess p;
   
       movementFile movement=new movementFile();
    void loadMap(){
        io tempIo=new io();
    movement.setIo(tempIo);
    movement.load();
   // getInRange(0,0,0,0);
    }
   
   
       double getY(double lat){
        double siny = Math.sin(lat * Math.PI / 180);  
        double scale = MathUtil.pow(2, zoom);
        siny = Math.min(Math.max(siny, -0.9999), 0.9999);
       double temp=256 * (0.5 - MathUtil.log((1 + siny) / (1 - siny)) / (4 * 3.1415926535897));
        temp*=scale/256;
        return temp;
    }
    double getX(double lng){ double scale = MathUtil.pow(2, zoom);  double temp= 256 * (0.5 + lng / 360); temp*=scale/256; return temp; }
   
    int currentLocationX; int currentLocationY; boolean displayLocation=false;
    void setStartingTile(){
       tileXCenter=(int)getX(startLong); 
   tileYCenter=(int)getY(startLat);
   currentLocationX=(int)((getX(startLong)-tileXCenter)*256);
   currentLocationY=(int)((getY(startLat)-tileYCenter)*256);
    }

    double scale=480;
   Painting(Form input){hi=input; loadMap();
setStartingTile();
int width= Display.getInstance().getDisplayWidth();
int height= Display.getInstance().getDisplayHeight(); scale=height/scale;
System.out.println("width is:"+width+"height is: "+height);
   }


   
   void setProccess(proccess input){p=input;}
   
   void setImage(int x, int y, int zoom, int addX, int addY){
   InputStream i; 
   try{i= Storage.getInstance().createInputStream("/map/gm_"+(x+addX)+"_"+(y+addY)+"_"+zoom+".png"); images[addX+1][addY+1]=createImage(i);}
   catch(Exception e){try{i= Storage.getInstance().createInputStream("/map/plus.png"); images[addX+1][addY+1]=createImage(i);}catch(Exception ee){}}
   }
   
   void setPainting(){
       setStartingTile();
      InputStream i; 
try{System.out.println("tile:"+tileXCenter+" "+tileYCenter+" "+zoom);
setImage(tileXCenter,tileYCenter,zoom,0,0); setImage(tileXCenter,tileYCenter,zoom,1,0);setImage(tileXCenter,tileYCenter,zoom,-1,0);
setImage(tileXCenter,tileYCenter,zoom,0,-1);setImage(tileXCenter,tileYCenter,zoom,1,-1);setImage(tileXCenter,tileYCenter,zoom,-1,-1);
setImage(tileXCenter,tileYCenter,zoom,0,1);setImage(tileXCenter,tileYCenter,zoom,1,1);setImage(tileXCenter,tileYCenter,zoom,-1,1);
}catch(Exception e){System.out.println("error:"+e);}
   }
   
   int downloadStatus=0; String name="startDownload";
void menu(){Style s = UIManager.getInstance().getComponentStyle("TitleCommand");  FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ACCESSIBLE, s);
//  hi.getToolbar().addCommandToLeftBar("Left", icon, (e) -> System.out.println("Clicked"));//hi.getToolbar().addCommandToRightBar("Right", icon, (e) -> System.out.println("Clicked")); //hi.getToolbar().addCommandToSideMenu("Sidemenu", icon, (e) -> System.out.println("Clicked"));
hi.getToolbar().addCommandToOverflowMenu("back", icon, (e) -> back());
hi.getToolbar().addCommandToOverflowMenu("zoom in", icon, (e) -> zoomIn());
hi.getToolbar().addCommandToOverflowMenu("zoom out", icon, (e) -> zoomOut());
hi.getToolbar().addCommandToOverflowMenu("current location", icon, (e) -> currentLocation());
//hi.getToolbar().addCommandToOverflowMenu("clear current location", icon, (e) -> clearCurrentLocation());
if(downloadStatus==0){hi.getToolbar().addCommandToOverflowMenu(name, icon, (e) -> startDownload()); }
if(downloadStatus==1){  hi.getToolbar().addCommandToOverflowMenu("download end point", icon, (e) -> downloadEndPoint()); downloadStatus=2;}
}

void startDownload(){downloadStatus=1; name="blank";
}

void downloadEndPoint(){
}

void zoomOut(){zoom--; setPainting(); movement.searchLoop(startLat,startLong,zoom); tileX=0; tileY=0;  paint(tileX,tileY);}
void zoomIn(){zoom++; setPainting(); movement.searchLoop(startLat,startLong,zoom);  tileX=0; tileY=0; paint(tileX,tileY);}
void currentLocation(){
    startLong=p.t.z.getLongitude(); startLat=p.t.z.getLatitude();
    tileX=0; tileY=0;
    //startLong=p.i.previousLong; startLat=p.i.previousLat;
    //startLong=p.t.z.getLongitude(); startLat=p.t.z.getLatitude();
   // startLong=p.t.z.previousLong; startLat=p.t.z.previousLat;
    if(startLong!=0 && startLat!=0){   movement.searchLoop(startLat,startLong,zoom); displayLocation=true;}
    System.out.println(startLong+" ------ "+startLat);

    setPainting();
      paint(tileX,tileY);
    /*
int tempX=(int)((getX(p.t.z.getLongitude())-tileXCenter)*256);    int tempY=(int)((getY(p.t.z.getLatitude())-tileYCenter)*256);
System.out.println("current location is: "+p.t.z.getLatitude()+" "+p.t.z.getLongitude()+" "+tempX+" "+tempY);
System.out.println((tempX+tileX)+" "+(tempY+tileY));
        hi.add(BorderLayout.CENTER, new Component() {
    @Override
    public void paint(Graphics g) {
        g.drawLine(tempX+tileX, tempY+tileY, tempX+tileX, tempY-40+tileY);
        g.drawLine(tempX+tileX, tempY+tileY, tempX+15+tileX, tempY-15+tileY);
        g.drawLine(tempX+tileX, tempY+tileY, tempX-15+tileX, tempY-15+tileY);
    }
});*/
}
void back(){System.out.println("hello"); p.addAllButtons();}

int startXMouse; int startYMouse; int tileX; int tileY; int differanceX; int differanceY; boolean release=true;
void motionListenerClick(){
     hi.addPointerDraggedListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
         if(release){startXMouse = (int)(evt.getX()/scale); startYMouse= (int)(evt.getY()/scale); release=false;}
         }
     });
}
void motionListenererDrag(){
      hi.addPointerDraggedListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
         int x =(int)(evt.getX()/scale); int y= (int)(evt.getY()/scale);
      //   System.out.println(x+" "+y);
         differanceX=x-startXMouse; differanceY=y-startYMouse;
        // System.out.println(x+" "+startXMouse+" "+y+" "+startYMouse+" "+differanceX+" "+differanceY);
         if(tileX+differanceX>256){tileX-=256; tileXCenter--; incrementLong(false);  movement.searchLoop(startLat,startLong,zoom); clearCurrentLocation(); } else if(tileX+differanceX<-256){tileX+=256; tileXCenter++; incrementLong(true); movement.searchLoop(startLat,startLong,zoom); clearCurrentLocation();}
         if(tileY+differanceY>256){tileY-=256; tileYCenter--; incrementLat(true); movement.searchLoop(startLat,startLong,zoom); clearCurrentLocation();} else if(tileY+differanceY<-256){tileY+=256; tileYCenter++; incrementLat(false); movement.searchLoop(startLat,startLong,zoom); clearCurrentLocation();}
         //paint(tileX+differanceX,tileY+differanceY);
         //hi.show();
         }
     });
}

    void incrementLong(boolean increment){double scale2=1/ MathUtil.pow(1.7,zoom); double tempLong=startLong; double tempX;
        for(double i=0;i<300;i++){if(increment){tempLong+=scale2;}
        else{tempLong-=scale2;} tempX=getX(tempLong); System.out.println("templong is: "+tempLong+" "+tempX+" "+tileXCenter); if((int)tempX==tileXCenter){startLong=tempLong; setPainting(); break;}
        }}
    void incrementLat(boolean increment){double scale2=1/ MathUtil.pow(1.7,zoom); double tempLat=startLat; double tempY;
        for(double i=0;i<300;i++){if(increment){tempLat+=scale2;}
        else{tempLat-=scale2;} tempY=getY(tempLat); System.out.println("templat is: "+tempLat+" "+tempY+" "+tileYCenter); if((int)tempY==tileYCenter){startLat=tempLat; setPainting(); break;}
        }
        }

void motionListererRelease(){
      hi.addPointerReleasedListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
             paint(tileX+differanceX,tileY+differanceY); System.out.println("released");
             tileX+=differanceX; tileY+=differanceY; release=true;
         }
     });
}
void clearCurrentLocation(){displayLocation=false;
}

    void drawImage(Graphics g, int x, int y){x+=40; y+=65;
    int temp=(int)(256*scale);
        g.drawImage(images[0][0], x-temp, y-temp,temp,temp); g.drawImage(images[1][0], x, y-temp,temp,temp); g.drawImage(images[2][0], x+temp, y-temp,temp,temp);
        g.drawImage(images[0][1], x-temp, y,temp,temp);
        g.drawImage(images[1][1], x, y, temp,temp);
        g.drawImage(images[2][1], x+temp, y,temp,temp);
        g.drawImage(images[0][2], x-temp, y+temp,temp,temp); g.drawImage(images[1][2], x, y+temp,temp,temp); g.drawImage(images[2][2], x+temp, y+temp,temp,temp);
        g.setColor(ColorUtil.BLACK);
        for(int i=0;i<movement.sizeRecords;i++){//System.out.println("-----/"+movement.z1.get(i)+" "+movement.z2.get(i)+" "+movement.z3.get(i)+" "+movement.z4.get(i));
            g.drawLine((int)((movement.z1.get(i)*scale)+x),(int)((movement.z2.get(i)*scale)+y),(int)((movement.z3.get(i)*scale+x)),(int)((movement.z4.get(i)*scale+y)));}
        if(displayLocation){x+=(int)(currentLocationX*scale); y+=(int)(currentLocationY*scale); System.out.println("hello: "+x+" "+y);
            g.drawLine(x, y, x, y-(int)(40*scale));
            g.drawLine(x, y, x+(int)(15*scale), y-(int)(15*scale));
            g.drawLine(x, y, x-(int)(15*scale), y-(int)(15*scale));
        }
    }

void drawImage2(Graphics g, int x, int y){x+=40; y+=65;
g.drawImage(images[0][0], x-256, y-256); g.drawImage(images[1][0], x, y-256); g.drawImage(images[2][0], x+256, y-256);
g.drawImage(images[0][1], x-256, y); 
g.drawImage(images[1][1], x, y);
g.drawImage(images[2][1], x+256, y);
g.drawImage(images[0][2], x-256, y+256); g.drawImage(images[1][2], x, y+256); g.drawImage(images[2][2], x+256, y+256);
g.setColor(ColorUtil.BLACK);
for(int i=0;i<movement.sizeRecords;i++){//System.out.println("-----/"+movement.z1.get(i)+" "+movement.z2.get(i)+" "+movement.z3.get(i)+" "+movement.z4.get(i));
    g.drawLine(movement.z1.get(i)+x,movement.z2.get(i)+y,movement.z3.get(i)+x,movement.z4.get(i)+y);}
if(displayLocation){x+=currentLocationX; y+=currentLocationY; System.out.println("hello: "+x+" "+y);
    g.drawLine(x, y, x, y-40);
        g.drawLine(x, y, x+15, y-15);
        g.drawLine(x, y, x-15, y-15);
}
}

void paint(int x, int y){
        hi.add(BorderLayout.CENTER, new Component() {
    @Override
    public void paint(Graphics g) {drawImage(g,(int)(x*scale),(int)(y*scale));
    }
});
        hi.show();
}
   
   void draw(){hi = new Form("", new BorderLayout());
menu(); setPainting();
motionListenerClick(); motionListenererDrag(); motionListererRelease();
paint(0,0); movement.searchLoop(startLat,startLong,zoom);
hi.show();
   }
    
}
