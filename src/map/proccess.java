/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import com.codename1.components.WebBrowser;
import com.codename1.javascript.JSFunction;
import com.codename1.javascript.JSObject;
import com.codename1.javascript.JavascriptContext;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author henry
 */
public class proccess {
    public io i=new io(); public timer t;
    boolean timing=false;
    Sockets s=new Sockets();
    proccess proccesses=this;
    Painting paint;
    //String connectionName="myvmlab.senecacollege.ca";




    public proccess(){
    t=new timer(i); t.start(); i.setProccess(this); s.proccesses=this;
    }
    Button b; Button ss; boolean started=false;
    public void startTiming(){t.active(); timing=true; hi.add(ss); hi.removeComponent(b); hi.show(); started=true;}
    public void stopTiming(){if(timing){t.timerStop(); i.close(); timing=false; hi.add(b); hi.removeComponent(ss);  hi.show(); started=false;}}
   // public void fileTransfers(){stopTiming(); i=new io(); f=new fileTransfer(i); f.start();}
     public void fileTransfers(){stopTiming(); //i=new io(); 
     s.setIO(i);
     s.socketConnection("myvmlab.senecacollege.ca",5937,1); 
// s.socketConnection("localhost",5000,1); 
     }
     
   public void registerSocket(){ //i=new io(); 
     
     s.socketConnection("myvmlab.senecacollege.ca",5937,1); 
 //s.socketConnection("localhost",5000,1); 
     }
     
     public void recieveFiles(int state){
     //i=new io(); 
     s.setIO(i);
     s.socketConnection("myvmlab.senecacollege.ca",5937,state); 
  //   s.socketConnection("localhost",5000,state); 
     }
     
    Form hi; TextField text;
    public void setForm(Form input){hi=input;}
   public     void addAllButtons(){hi.removeAll(); hi.setLayout(BoxLayout.y());
                text = new TextField("", "", 20, TextArea.ANY);
       text.setUIID("InvalidTextField");
       hi.add(text);
        b = new Button("start"); b.addActionListener((e)->startTiming());
        ss = new Button("stop"); ss.addActionListener((e)->stopTiming());  //hi.add(ss);
        Button z = new Button("transfer"); z.addActionListener((e)->fileTransfers());  hi.add(z);
        Button r = new Button("input movement"); r.addActionListener((e)->recieveFiles(2));  hi.add(r);
        Button c = new Button("input map tiles"); c.addActionListener((e)->recieveFiles(3));  hi.add(c);
   //     Button dd = new Button("display map"); dd.addActionListener((e)->showMap());  hi.add(dd);
   //     Button load = new Button("load movement"); load.addActionListener((e)->loadMap());  hi.add(load);
        Button paint = new Button("display map");       paint.addActionListener((e)->paint());  hi.add(paint);

if(!started){hi.add(b);} else{hi.add(ss);}
 //      Form hi = new Form("Rich Text", BoxLayout.y());       hi.add(new RichTextView("This is plain text <b>this is bold</b> and <i>this is italic</i> and all of this breaks lines nicely as well...."));
hi.show();}








        void paint(){
        hi.removeAll(); paint = new Painting(hi); paint.setProccess(this);
        paint.draw();
        }
        
    movementFile movement=new movementFile();    
    void loadMap(){
    movement.setIo(i);
    movement.load();
   // getInRange(0,0,0,0);
    }
        
    
    //void showMap(){
    
    
    /*
       void getInRange(double latitude, double longitude, int scale){
        System.out.println("getting in range");
        movement.searchLoop(latitude,longitude,scale,tileX, tileY);
        System.out.println("done searching");
       }    
      */ 
     Double latA; Double longA;  int zoomA;  int tileX;  int tileY; 
    
                final WebBrowser d = new WebBrowser(){
                    movementFile move=new movementFile();
                    
    public void onLoad(String url){
        BrowserComponent c = (BrowserComponent)this.getInternal();
        JavascriptContext ctx = new JavascriptContext(c);
        JSObject window = (JSObject)ctx.get("window");
        
                window.set("addAsync", new JSFunction(){

            public void apply(JSObject self, final Object[] args) {
                Double a = (Double)args[0];
                Double b = (Double)args[1];
                JSObject callback = (JSObject)args[2];
System.out.println("--------------------");
                double result = a.doubleValue() + b.doubleValue();
                callback.call(new Object[]{new Double(result)});

            }

        });
                
                                window.set("login", new JSFunction(){

            public void apply(JSObject self, final Object[] args) {
             // String a=(String)ctx.get("document.body.email"); 
             
                 String a = args[0].toString(); 
                String b = args[1].toString();
                JSObject callback = (JSObject)args[2];
                s.username=a; s.password=b;
                s.proccesses=proccesses;
                s.socketConnection("localhost", 5000, 5);
                boolean status=s.returnLoggedIn;
System.out.println("username: "+a+" password: "+b);
                double result = 6;
                
               // if(status){    addAllButtons();}
             //   callback.call(new Object[]{new Double(result)});

            }

        });

      window.set("signup", new JSFunction(){

            public void apply(JSObject self, final Object[] args) {
             // String a=(String)ctx.get("document.body.email"); 
             
                 String a = args[0].toString(); 
                String b = args[1].toString();
                 String c = args[2].toString();
                  String d = args[3].toString();
                   String e = args[4].toString();
                    String f = args[5].toString();
                     String g = args[6].toString();
                      String h = args[7].toString();
                       String i = args[8].toString();
                        String j = args[9].toString();
                JSObject callback = (JSObject)args[10];
                String all=a+","+c+","+d+","+e+","+f+","+g+","+h+","+i+","+j;
                s.registerData=all;
                System.out.println(all);
                if(b.equals(c)){ s.socketConnection("localhost",5000,4); }
//System.out.println(a+" "+b+" "+c+" "+d+" "+e+" "+f+" "+g+" "+h+" "+i+" "+j);
                double result = 6;
                callback.call(new Object[]{new Double(result)});

            }

        });
                                
        window.set("startRegister", new JSFunction(){ public void apply(JSObject self, final Object[] args) {
        showSignUp();
        //showLogin();
        }});                       
                                
                

                        window.set("getImage", new JSFunction(){
            public void apply(JSObject self, final Object[] args) {// System.out.println("----------------------------------");
           int a = ((Double)args[1]).intValue();
           int b = ((Double)args[2]).intValue();  
           int c = ((Double)args[3]).intValue();                 //System.out.println("zzzzz "+a+" "+b+" "+c);
            JSObject callback = (JSObject)args[0];
//i=new io();
String returned;
     if(a==-1){returned=i.readImage("/map/plus.png");}
else if(a==-2){returned=i.readImage("/map/minus.png");}
          else{returned=i.readImage("/map/gm_"+a+"_"+b+"_"+c+".png");}
//System.out.println("returned is: "+returned);
             callback.call(new Object[]{returned}); 
            }

        });

//       window.set("getLat", new JSFunction(){ public void apply(JSObject self, final Object[] args) {
       //}});

window.set("getSize", new JSFunction(){public void apply(JSObject self, final Object[] args) {JSObject callback = (JSObject)args[0];
double result=movement.getSize();
result+=8;
callback.call(new Object[]{new Double(result)});}});
       
window.set("getMovementString", new JSFunction(){public void apply(JSObject self, final Object[] args) {JSObject callback = (JSObject)args[0];
String temp=movement.longString;
System.out.println(temp);
temp+="0,150,256,150,"+"0,190,256,190,";
callback.call(new Object[]{temp});}});
       
window.set("increment", new JSFunction(){ public void apply(JSObject self, final Object[] args) {
movement.currentPixel++;
}});
        /*
window.set("retrieveX1", new JSFunction(){public void apply(JSObject self, final Object[] args) {JSObject callback = (JSObject)args[0]; 
double result=movement.getX1(); System.out.print("-------------x1 is: "+result);callback.call(new Object[]{new Double(result)});}});
window.set("retrieveY1", new JSFunction(){public void apply(JSObject self, final Object[] args) {JSObject callback = (JSObject)args[0]; 
double result=movement.getY1(); System.out.print("---------------y1 is: "+result); callback.call(new Object[]{new Double(result)});}});
window.set("retrieveX2", new JSFunction(){public void apply(JSObject self, final Object[] args) {JSObject callback = (JSObject)args[0]; 
double result=movement.getX2(); System.out.print("------------------x2 is: "+result); callback.call(new Object[]{new Double(result)});}});
window.set("retrieveY2", new JSFunction(){public void apply(JSObject self, final Object[] args) {JSObject callback = (JSObject)args[0]; 
double result=movement.getY2(); System.out.println("-------------------y2 is: "+result);callback.call(new Object[]{new Double(result)});}});
*/
/*
       window.set("retrieveFileIncrement", new JSFunction(){ public void apply(JSObject self, final Object[] args) {
           movement.currentPixel++;
       }});
       window.set("retrieveX1", new JSFunction(){ public void apply(JSObject self, final Object[] args) {
                       JSObject callback = (JSObject)args[0]; 
            callback.call(new Object[]{movement.getX1()}); 
       }});
       window.set("retrieveY1", new JSFunction(){ public void apply(JSObject self, final Object[] args) {
                       JSObject callback = (JSObject)args[0]; 
            callback.call(new Object[]{movement.getY1()}); 
       }});
       window.set("retrieveX2", new JSFunction(){ public void apply(JSObject self, final Object[] args) {
                       JSObject callback = (JSObject)args[0]; 
            callback.call(new Object[]{movement.getX2()}); 
       }});
       window.set("retrieveY2", new JSFunction(){ public void apply(JSObject self, final Object[] args) {
                       JSObject callback = (JSObject)args[0]; 
            callback.call(new Object[]{movement.getY2()}); 
       }});
       */

        window.set("setLoopReturn", new JSFunction(){ public void apply(JSObject self, final Object[] args) {
            
                latA = (Double)args[0];
                longA = (Double)args[1];
                zoomA = ((Double)args[2]).intValue();
                
           movement.searchLoop(latA,longA,zoomA);
           
                JSObject callback = (JSObject)args[3];
                System.out.println("hello: "+latA+" "+longA+" "+zoomA);// getInRange(leftLong,rightLong,topLat,bottomLat);
        }});
                        
        window.set("getPlus", new JSFunction(){ public void apply(JSObject self, final Object[] args) {
        JSObject callback = (JSObject)args[0]; String returned; returned=i.readImage("/map/plus.png");
        callback.call(new Object[]{returned}); 
        }});
 
        window.set("getMinus", new JSFunction(){ public void apply(JSObject self, final Object[] args) {
        JSObject callback = (JSObject)args[0]; String returned; returned=i.readImage("/map/minus.png");
        callback.call(new Object[]{returned}); 
        }});
        
        window.set("addAsync2", new JSFunction(){

            public void apply(JSObject self, final Object[] args) {
                Double a = (Double)args[0];
                Double b = (Double)args[1];
                JSObject callback = (JSObject)args[2];
System.out.println("--------------------");
                double result = a.doubleValue() + b.doubleValue();
                //callback.call(new Object[]{new Double(result)});
              //  callback.call(new Object[]{"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"});
System.out.println("start");
i=new io(); 
                String returned=i.readImage("/map/gm_0_0_1.png");
//System.out.println("returned is: "+returned);
             callback.call(new Object[]{returned}); 
            }

        });
        
        window.set("back", new JSFunction(){ public void apply(JSObject self, final Object[] args) {
        addAllButtons();
        }});
    }
};

         void showSignUp(){
        d.setURL("jar:///signup.html"); 
        hi.removeAll();
        hi.setLayout(new BorderLayout());
        hi.addComponent(BorderLayout.CENTER,d);
                hi.getToolbar().hideToolbar();
        hi.getToolbar().setUIID("Container");
        hi.show();        
    }
                
     void showLogin(){
        d.setURL("jar:///login.html"); 
        hi.removeAll();
        hi.setLayout(new BorderLayout());
        hi.addComponent(BorderLayout.CENTER,d);
                hi.getToolbar().hideToolbar();
        hi.getToolbar().setUIID("Container");
        hi.show();        
    }
                
                
     void showMap(){
    //    d.setURL("jar:///CameraExample.html");
    loadMap();
        d.setURL("jar:///test4.html"); 
        hi.removeAll();
        hi.setLayout(new BorderLayout());
        hi.addComponent(BorderLayout.CENTER,d);
                hi.getToolbar().hideToolbar();
        hi.getToolbar().setUIID("Container");
        hi.show();        
    }

}

