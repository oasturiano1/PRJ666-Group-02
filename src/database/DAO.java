package database;

        import com.codename1.components.InfiniteProgress;
        import com.codename1.components.MultiButton;
        import com.codename1.components.ToastBar;
        import com.codename1.io.*;
        import com.codename1.ui.*;
        import com.codename1.ui.events.ActionEvent;
        import com.codename1.ui.events.ActionListener;
        import com.codename1.ui.layouts.BorderLayout;
        import com.codename1.ui.layouts.BoxLayout;
        import com.codename1.ui.plaf.Style;
        import com.codename1.ui.plaf.UIManager;
        import com.foodrive.myapp.*;

        import javax.annotation.Resources;
        import java.io.*;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URISyntaxException;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Map;

        import static com.sun.org.apache.xalan.internal.xsltc.compiler.sym.error;



public class DAO {
    private ConnectionRequest connectionRequest;
    public static Form hi;
    public boolean fit;
    private HttpURLConnection phpCon = null;
    private java.net.URL url;
    private LoginDataCheck log;
    private LoginDataCheck loginUser;
    private userObject user = new userObject();
    private boolean ret = false;


    public void phpIsLogin(String e, String p){
        ArrayList<LoginDataCheck> people = new ArrayList<LoginDataCheck>();

        if(checkNullData(e,p)){

            connectionRequest = new ConnectionRequest(){
                protected void readResponse(InputStream in) throws IOException {

                    JSONParser json = new JSONParser();

                    Reader reader = new InputStreamReader(in, "UTF-8");

                    Map<String, Object> data = json.parseJSON(reader);
                    List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");
                    people.clear();



                    for (Map<String, Object> obj : content) {
                        people.add(log = new LoginDataCheck((String) obj.get("email"),(String) obj.get("password"),(String) obj.get("division")));
                        loginUser= new LoginDataCheck((String) obj.get("email"),(String) obj.get("password"),(String) obj.get("division"));
                        if(e.equals((String)obj.get("email")) && p.equals((String)obj.get("password"))){
                            fit = true;
                            break;
                        }else{
                            fit = false;
                            break;
                        }
                        //System.out.println((String)obj.get("email")+" "+(String)obj.get("password")+" "+(String)obj.get("division"));
                    }
                }


                protected void postResponse() {
                    if(log != null){
                        com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                        //System.out.println("DAO:"+log.getEmail()+" "+log.getPassword()+" "+log.getDivision());
                       if(log.getDivision().equals("ADMIN"))
                           new adminLanded(LoginRes, loginUser.getEmail()).show();
                       else if(log.getDivision().equals("USER"))
                           phpGetUser(log.getEmail(),log.getPassword());
                       else{
                           ToastBar.Status error = ToastBar.getInstance().createStatus();
                           error.setMessage("Username or Password invalid!");
                           error.setExpires(3000);
                           error.show();
                       }

                    }else{
                        ToastBar.Status error = ToastBar.getInstance().createStatus();
                        error.setMessage("Username or Password invalid!");
                        error.setExpires(3000);
                        error.show();
                    }

                }
            };
            connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/mobileloginType.php?email="+ e + "&password="+ p);
            connectionRequest.setPost(false);
       /* InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        connectionRequest.setDisposeOnCompletion(dlg);*/
            NetworkManager.getInstance().addToQueue(connectionRequest);

        }


        //ewhite7@myseneaca.ca
    }

    public boolean phpUserExists(String e, String p){
        ArrayList<LoginDataCheck> people = new ArrayList<LoginDataCheck>();
        ret = false;
        if(checkNullData(e,p)){

            connectionRequest = new ConnectionRequest(){
                protected void readResponse(InputStream in) throws IOException {

                    JSONParser json = new JSONParser();

                    Reader reader = new InputStreamReader(in, "UTF-8");

                    Map<String, Object> data = json.parseJSON(reader);
                    List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");
                    people.clear();



                    for (Map<String, Object> obj : content) {
                        people.add(log = new LoginDataCheck((String) obj.get("email"),(String) obj.get("password"),(String) obj.get("division"))
                        );
                        if(e.equals((String)obj.get("email")) && p.equals((String)obj.get("password"))){
                            fit = true;
                            break;
                        }else{
                            fit = false;
                            break;
                        }
                        //System.out.println((String)obj.get("email")+" "+(String)obj.get("password")+" "+(String)obj.get("division"));
                    }
                }


                protected void postResponse() {
                    //if(checkNullData(log.getEmail(),log.getPassword())){
                        //com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                        //System.out.println("DAO:"+log.getEmail()+" "+log.getPassword()+" "+log.getDivision());
                        if(log.getDivision().equals("ADMIN")||log.getDivision().equals("USER"))
                            ret = true;
                   // }

                }
            };
            connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/mobileloginExists.php?email="+ e);
            connectionRequest.setPost(false);
       /* InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        connectionRequest.setDisposeOnCompletion(dlg);*/
            NetworkManager.getInstance().addToQueue(connectionRequest);

        }


        return ret;
    }

    public userObject phpGetUser(String email, String password){

        connectionRequest = new ConnectionRequest(){
            protected void readResponse(InputStream in) throws IOException {

                JSONParser json = new JSONParser();

                Reader reader = new InputStreamReader(in, "UTF-8");

                Map<String, Object> data = json.parseJSON(reader);
                List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");



                for (Map<String, Object> obj : content) {
                    //(String) obj.get("email"),(String) obj.get("password"),(String) obj.get("division")
                    user = new userObject();

                    //user.id = (int)obj.get("id");
                    user.fname = (String)obj.get("fname");
                    user.lname = (String)obj.get("lname");
                    user.email = (String)obj.get("email");
                    user.phone = (String)obj.get("phoneNumber");
                    user.pass = (String)obj.get("password");
                    user.hourstotal = (String)obj.get("hoursTotal");
                    user.hourssigned = (String)obj.get("hoursSigned");
                    user.ename = (String)obj.get("contactName");
                    user.ephone = (String)obj.get("contactPhone");

                    if(((String) obj.get("email")).compareTo(email) == 0 &&((String) obj.get("password")).compareTo(email) == 0){
                        fit = true;
                        break;
                    }else{
                        fit = false;
                        break;
                    }
                    //System.out.println((String)obj.get("email")+" "+(String)obj.get("password")+" "+(String)obj.get("division"));
                }
            }


            protected void postResponse() {
                if(checkNullData(log.getEmail(),log.getPassword())){
                    com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                    //System.out.println("DAO:"+log.getEmail()+" "+log.getPassword()+" "+log.getDivision());
                    //if(log.getDivision().equals("ADMIN"))
                    //    new adminLanded(LoginRes).show();
                   // else if(log.getDivision().equals("USER"))

                        //new userLanded(LoginRes).show();//TODO GET VOLUNTEER INFO
                            /*com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");*/
                            new ViewProfile(LoginRes,user.fname,user.lname,user.email,user.hourstotal,
                                    user.hourssigned,user.phone,user.ename,user.ephone,
                                    user.pass).show();

                   // else
                     //   System.out.println("User needed");
                }

            }
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/mobileGetUser.php?email="+ email + "&password="+ password);
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/mobileGetUser.php?email="+ email + "&password="+ password);
        connectionRequest.setPost(false);
       /* InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        connectionRequest.setDisposeOnCompletion(dlg);*/
        NetworkManager.getInstance().addToQueue(connectionRequest);


        return user;
    }




    public void addVolunteer(String fn, String ln, String email, String phone, String pass, String Contactname, String ContactNumber){

        connectionRequest=new ConnectionRequest(){
           /*    @Override
               protected void postResponse() {
                  *//* Dialog d = new Dialog("Add");
                   TextArea popupBody = new TextArea("Book successfully added");
                   popupBody.setUIID("PopupBody");
                   popupBody.setEditable(false);
                   d.setLayout(new BorderLayout());
                   d.addVolunteer(BorderLayout.CENTER, popupBody);
                   d.showDialog();*//*
               }*/
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/insertAll.php?fname=" + fn + "&lname=" + ln+"&email="+email+"&phoneNumber="+phone+"&password="+pass+"&contactName="+Contactname+"&contactPhone="+ContactNumber);
        connectionRequest.setPost(false);
      /*  InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        connectionRequest.setDisposeOnCompletion(dlg);*/
        NetworkManager.getInstance().addToQueue(connectionRequest);


    }

    public void addDrive(String date, String name){
        connectionRequest=new ConnectionRequest(){
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/insertDrives.php?sdate=" + date +"&name="+ name);
        connectionRequest.setPost(false);
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

    public void delVolunteer(String email){

        connectionRequest=new ConnectionRequest(){
              /* @Override
               protected void postResponse() {
                   getAll();
               }*/
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/deleteFields.php?email=" + email);
        connectionRequest.setPost(false);
      /*  InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        connectionRequest.setDisposeOnCompletion(dlg);*/
        NetworkManager.getInstance().addToQueue(connectionRequest);


    }


    public void delRecord(int recId,int userId){

        connectionRequest=new ConnectionRequest(){
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/deleteRecord.php?id="+recId+"&userid="+userId);
        connectionRequest.setPost(false);
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

    public void delDrive(int did){

        connectionRequest=new ConnectionRequest(){
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/deleteDrive.php?id="+did);
        connectionRequest.setPost(false);
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

    public void updateVolunteer(String fn, String ln, String email, String phone, String pass, String Contactname, String ContactNumber, String hoursTot, String hoursSign){

        connectionRequest=new ConnectionRequest(){
           /*    @Override
               protected void postResponse() {
                  *//* Dialog d = new Dialog("Add");
                   TextArea popupBody = new TextArea("Book successfully added");
                   popupBody.setUIID("PopupBody");
                   popupBody.setEditable(false);
                   d.setLayout(new BorderLayout());
                   d.addVolunteer(BorderLayout.CENTER, popupBody);
                   d.showDialog();*//*
               }*/
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/updateData.php?fname=" + fn + "&lname=" + ln+"&email="+email+"&phoneNumber="+phone+"&password="+pass+"&contactName="+Contactname+"&contactPhone="+ContactNumber+"&hoursTotal="+hoursTot+"&hoursSigned="+hoursSign);
        connectionRequest.setPost(false);
      /*  InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        connectionRequest.setDisposeOnCompletion(dlg);*/
        NetworkManager.getInstance().addToQueue(connectionRequest);


    }

    public void addLogin(String emailText,String passText,String divText){
        connectionRequest=new ConnectionRequest(){
            @Override
            protected void postResponse() {
                new Login(UIManager.initFirstTheme("/theme")).show();
            }
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/addLogin.php?email=" + emailText + "&password=" + passText+"&division="+divText);
        connectionRequest.setPost(false);
        /*InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        connectionRequest.setDisposeOnCompletion(dlg);*/
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

    public void addRecord(newRecord rec, String name){
        connectionRequest=new ConnectionRequest(){
            @Override
            protected void postResponse() {
                //new Login(UIManager.initFirstTheme("/theme")).show();
                //TODO TOAST IF SUCCESS
                ToastBar.Status success = ToastBar.getInstance().createStatus();
                success.setMessage("Record Updated!");
                success.setExpires(3000);
                success.show();
            }
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/insertRecord.php?driveid="+rec.driveId+
                "&volid="+rec.volunteerId+
                "&hourscon="+rec.hoursContributed+
                "&opdate="+rec.operationDayDate+
                "&dsdate="+rec.driveStartDate+
                "&super="+name);
        connectionRequest.setPost(false);
        /*InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        connectionRequest.setDisposeOnCompletion(dlg);*/
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

    public void updateRecord(record rec, String name,double driveId, String start,String opDate){
        connectionRequest=new ConnectionRequest(){
            @Override
            protected void postResponse() {
                //new Login(UIManager.initFirstTheme("/theme")).show();
                //TODO TOAST IF SUCCESS
                ToastBar.Status success = ToastBar.getInstance().createStatus();
                success.setMessage("Record Updated!");
                success.setExpires(3000);
                success.show();
            }
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/updateRecord.php?driveid="+driveId+
                "&volid="+rec.id+
                "&hourscon="+rec.hoursCon+
                "&opdate="+opDate+
                "&dsdate="+start+
                "&super="+name);
        connectionRequest.setPost(false);
        /*InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        connectionRequest.setDisposeOnCompletion(dlg);*/
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

    public void getAll(String adminName){
        ArrayList<people> people = new ArrayList<people>();
        connectionRequest = new ConnectionRequest(){


            protected void readResponse(InputStream in) throws IOException {
                JSONParser json = new JSONParser();

                Reader reader = new InputStreamReader(in, "UTF-8");

                Map<String, Object> data = json.parseJSON(reader);
                List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");
                people.clear();

                for (Map<String, Object> obj : content) {
                    people.add(new people((String) obj.get("lname"),(String) obj.get("fname"),(String) obj.get("phoneNumber"),(String)obj.get("hoursTotal"),(String) obj.get("hoursSigned"),(String) obj.get("contactName"),(String) obj.get("contactPhone"),(String) obj.get("email"),(String) obj.get("password"))

                    );
                }
            }

            //creating the list layout with data
            @Override
            protected void postResponse() {
                com.codename1.ui.util.Resources res = UIManager.initFirstTheme("/theme");
                new VolunteerList(res, people, adminName).show();
/*
                //System.out.println(libs.size());
                hi = new Form("",new BorderLayout());
                Container list = new Container(BoxLayout.y());
                list.setScrollableY(true);
                ArrayList<String> FN = new ArrayList<>();
                ArrayList<String> LN = new ArrayList<>();
                ArrayList<String> EM = new ArrayList<>();
                ArrayList<String> HRT = new ArrayList<>();
                ArrayList<String> HRS = new ArrayList<>();
                ArrayList<String> PN = new ArrayList<>();
                ArrayList<String> CN = new ArrayList<>();
                ArrayList<String> CPN = new ArrayList<>();
                ArrayList<String> PW = new ArrayList<>();
                for(people l : people){
                    FN.add(l.getFname());
                    LN.add(l.getLname());
                    EM.add(l.getEmail());
                    HRT.add(l.getTotalHrs());
                    HRS.add(l.getHrsSigned());
                    PN.add(l.getPhone());
                    CN.add(l.getEmergency());
                    CPN.add(l.getErNumber());
                    PW.add(l.getPassword());
                }
                for(int iter = 0; iter<FN.size(); iter++){
                    MultiButton mb = new MultiButton(people.get(iter).getLname()+" "+people.get(iter).getFname());
                    mb.setTextLine2("more...");
                    FontImage.setMaterialIcon(mb,FontImage.MATERIAL_PERSON);
                    int finalIter = iter;
                    mb.addActionListener(evt -> {
                        //ToastBar.showMessage("Clicked: ",FontImage.MATERIAL_PERSON);
                        com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                        new ViewVolunteers(LoginRes,FN.get(finalIter),LN.get(finalIter),EM.get(finalIter),
                        HRT.get(finalIter),HRS.get(finalIter),PN.get(finalIter),CN.get(finalIter),
                                CPN.get(finalIter),PW.get(finalIter)).show();
                    });
                    list.add(mb);
                }
                com.codename1.ui.util.Resources res = UIManager.initFirstTheme("/theme");
                Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
                FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
                hi.add(BorderLayout.CENTER,list);
                hi.getToolbar().addCommandToLeftBar("",icon, (e) -> new adminLanded(res).show());
                hi.getToolbar().addSearchCommand(evt -> {
                    String text = (String)evt.getSource();
                    if(text == null || text.length() == 0){
                        for(Component cmp: hi.getContentPane()){
                            cmp.setHidden(false);
                            cmp.setVisible(true);
                            list.removeAll();
                            for(int iter = 0; iter<FN.size(); iter++){

                                //String tests1 =FN.get(iter).toLowerCase();
                                //String tests2 =LN.get(iter).toLowerCase();
                                //boolean test1 = FN.get(iter).toLowerCase().contains(text);
                                //boolean test2 = LN.get(iter).toLowerCase().contains(text);

                                    MultiButton mb = new MultiButton(people.get(iter).getFname()+" "+people.get(iter).getLname());
                                    mb.setTextLine2("more...");
                                    FontImage.setMaterialIcon(mb,FontImage.MATERIAL_PERSON);
                                    int finalIter = iter;
                                    mb.addActionListener(evtF -> {
                                        //ToastBar.showMessage("Clicked: ",FontImage.MATERIAL_PERSON);
                                        com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                                        new ViewVolunteers(LoginRes,FN.get(finalIter),LN.get(finalIter),EM.get(finalIter),
                                                HRT.get(finalIter),HRS.get(finalIter),PN.get(finalIter),CN.get(finalIter),
                                                CPN.get(finalIter),PW.get(finalIter)).show();
                                    });

                                    list.add(mb);


                            }
                        }
                        hi.getContentPane().animateLayout(150);
                    } else{
                        text = text.toLowerCase();
                        list.removeAll();
                        for(int iter = 0; iter<FN.size(); iter++){

                            //String tests1 =FN.get(iter).toLowerCase();
                            //String tests2 =LN.get(iter).toLowerCase();
                            //boolean test1 = FN.get(iter).toLowerCase().contains(text);
                            //boolean test2 = LN.get(iter).toLowerCase().contains(text);
                           if(FN.get(iter).toLowerCase().contains(text) || LN.get(iter).toLowerCase().contains(text)){
                               MultiButton mb = new MultiButton(people.get(iter).getFname()+" "+people.get(iter).getLname());
                               mb.setTextLine2("more...");
                               FontImage.setMaterialIcon(mb,FontImage.MATERIAL_PERSON);
                               int finalIter = iter;
                               mb.addActionListener(evtF -> {
                                   //ToastBar.showMessage("Clicked: ",FontImage.MATERIAL_PERSON);
                                   com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                                   new ViewVolunteers(LoginRes,FN.get(finalIter),LN.get(finalIter),EM.get(finalIter),
                                           HRT.get(finalIter),HRS.get(finalIter),PN.get(finalIter),CN.get(finalIter),
                                           CPN.get(finalIter),PW.get(finalIter)).show();
                               });

                               list.add(mb);
                           }
                        }
                        hi.getContentPane().animateLayout(150);
                    }
                },4);
                hi.show();*/
               /* com.codename1.ui.List uiLibsList = new com.codename1.ui.List();
                ArrayList<String> libsNoms = new ArrayList<>();
                for(people l : people){
                    libsNoms.add(l.getFname());
                }
                com.codename1.ui.list.DefaultListModel<String> listModel = new com.codename1.ui.list.DefaultListModel<>(libsNoms);
                uiLibsList.setModel(listModel);
                uiLibsList.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        people currentPeople = people.get(uiLibsList.getCurrentSelected());
                        System.out.println();
                        //new people(currentPeople.getTitle(),currentPeople.getAuthor(),currentPeople.getCategory(),currentPeople.getIsbn()).show();
                    }
                });
                list.setLayout(new BorderLayout());
                list.add(BorderLayout.NORTH,uiLibsList);
                //list.addVolunteer(BorderLayout.SOUTH,Statics.createBackBtn());
                list.show();*/
            }
            //-----------------------//
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/getAll.php");
        connectionRequest.setPost(false);
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

    /*public void getAllDrives(){//TEMPLATE
        ArrayList<drive> drives = new ArrayList<drive>();
        connectionRequest = new ConnectionRequest(){


            protected void readResponse(InputStream in) throws IOException {
                JSONParser json = new JSONParser();

                Reader reader = new InputStreamReader(in, "UTF-8");

                Map<String, Object> data = json.parseJSON(reader);
                List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");
                drives.clear();

                for (Map<String, Object> obj : content) {
                    drives.add(new drive((String)obj.get("id"),(String) obj.get("start"),(String) obj.get("name")));
                }
            }

            //creating the list layout with data
            @Override
            protected void postResponse() {
            }
            //-----------------------//
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/mobileGetAllDrives.php");
        connectionRequest.setPost(false);
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }*/

    public void getAllDrives(String adminName){
        ArrayList<drive> drives = new ArrayList<drive>();
        connectionRequest = new ConnectionRequest(){


            protected void readResponse(InputStream in) throws IOException {
                JSONParser json = new JSONParser();

                Reader reader = new InputStreamReader(in, "UTF-8");

                Map<String, Object> data = json.parseJSON(reader);
                List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");
                drives.clear();

                for (Map<String, Object> obj : content) {
                    drives.add(new drive((String)obj.get("id"),(String) obj.get("start"),(String) obj.get("name")));
                }
            }

            //creating the list layout with data
            @Override
            protected void postResponse() {
                com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                new DrivesList(LoginRes,drives,adminName).show();
            }
            //-----------------------//
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/mobileGetAllDrives.php");
        connectionRequest.setPost(false);
        NetworkManager.getInstance().addToQueue(connectionRequest);

    }

    public void getAllDriveDays(int id, String start, String adminName){
        ArrayList<String> days = new ArrayList<String>();
        connectionRequest = new ConnectionRequest(){


            protected void readResponse(InputStream in) throws IOException {
                JSONParser json = new JSONParser();

                Reader reader = new InputStreamReader(in, "UTF-8");

                Map<String, Object> data = json.parseJSON(reader);
                List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");
                days.clear();

                for (Map<String, Object> obj : content) {
                    days.add((String)obj.get("date"));
                }
            }

            //creating the list layout with data
            @Override
            protected void postResponse() {
                com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                new DaysList(LoginRes,days ,id, start, adminName).show();
            }
            //-----------------------//
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/mobileGetAllDriveDays.php?id="+Integer.toString(id));
        connectionRequest.setPost(false);
        NetworkManager.getInstance().addToQueue(connectionRequest);

    }

    public void getAllDriveDayRecords(String odate, int driveId, String start, String adminName){
        ArrayList<record> recs = new ArrayList<record>();
        connectionRequest = new ConnectionRequest(){


            protected void readResponse(InputStream in) throws IOException {
                JSONParser json = new JSONParser();

                Reader reader = new InputStreamReader(in, "UTF-8");

                Map<String, Object> data = json.parseJSON(reader);
                List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");
                recs.clear();

                for (Map<String, Object> obj : content) {
                    recs.add(new record (Integer.parseInt(obj.get("id").toString()),
                            Integer.parseInt(obj.get("oprecid").toString()),
                            (String)obj.get("fname"),
                            (String)obj.get("lname"),
                            (String)obj.get("email"),
                            (String)obj.get("phone"),
                            (String)obj.get("super"),
                            (String)obj.get("cname"),
                            (String)obj.get("cphone"),
                            Double.parseDouble(obj.get("hourscon").toString())
                            )
                    );
                }
            }

            //creating the list layout with data
            @Override
            protected void postResponse() {
                com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                new RecordsList(LoginRes,recs, odate,driveId, start,adminName).show();
            }
            //-----------------------//
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/mobileGetAllDriveDayRecords.php?odate="+odate);
        connectionRequest.setPost(false);
        NetworkManager.getInstance().addToQueue(connectionRequest);

    }

    public void getAllUsersForAdd(int driveId, String start,String adminName){
        ArrayList<userObject> recs = new ArrayList<userObject>();
        connectionRequest = new ConnectionRequest(){


            protected void readResponse(InputStream in) throws IOException {
                JSONParser json = new JSONParser();

                Reader reader = new InputStreamReader(in, "UTF-8");

                Map<String, Object> data = json.parseJSON(reader);
                List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");
                recs.clear();

                for (Map<String, Object> obj : content) {

                    userObject user = new userObject();

                    user.id =Integer.parseInt((String)obj.get("id"));
                    user.lname =(String)obj.get("lname");
                    user.fname =(String)obj.get("fname");
                    recs.add(user);

                }
            }

            //creating the list layout with data
            @Override
            protected void postResponse() {
                com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                new AddRecord(LoginRes,recs,driveId,start,new userObject(), adminName,"","").show();
            }
            //-----------------------//
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/getAll.php");
        connectionRequest.setPost(false);
        NetworkManager.getInstance().addToQueue(connectionRequest);

    }



    public boolean checkNullData(String emailText, String passwordTex){
        boolean valid = true;
        if(emailText == null || emailText.isEmpty())
            valid = false;
        else if(passwordTex== null || passwordTex.isEmpty())
            valid = false;


        return valid;
    }

}

