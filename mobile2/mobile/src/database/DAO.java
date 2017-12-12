package database;

        import com.codename1.components.InfiniteProgress;
        import com.codename1.io.*;
        import com.codename1.ui.Dialog;
        import com.codename1.ui.Form;
        import com.codename1.ui.TextArea;
        import com.codename1.ui.events.ActionEvent;
        import com.codename1.ui.events.ActionListener;
        import com.codename1.ui.layouts.BorderLayout;
        import com.codename1.ui.plaf.UIManager;
        import com.foodrive.myapp.Login;
        import com.foodrive.myapp.adminLanded;

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
    public static Form list;
    public boolean fit;
    private HttpURLConnection phpCon = null;
    private java.net.URL url;
    private LoginDataCheck log;


    public void phpIsLogin(String e, String p, String d){
        ArrayList<LoginDataCheck> people = new ArrayList<LoginDataCheck>();

        if(checkNullData(e,p,d)){

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
                        if(e.equals((String)obj.get("email")) && p.equals((String)obj.get("password"))&& d.equals((String)obj.get("division"))){
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
                    if(checkNullData(log.getEmail(),log.getPassword(),log.getDivision())){
                        com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                        System.out.println("DAO:"+log.getEmail()+" "+log.getPassword()+" "+log.getDivision());
                        new adminLanded(LoginRes).show();
                    }

                }
            };
            connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/mobileloginType.php?email="+ e + "&password="+ p+"&division="+d);
            connectionRequest.setPost(false);
       /* InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        connectionRequest.setDisposeOnCompletion(dlg);*/
            NetworkManager.getInstance().addToQueue(connectionRequest);

        }


        //ewhite7@myseneaca.ca
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

    public void removeBook(){

    }

    public void updateBook(){

    }

    public void getAll(){
        ArrayList<people> people = new ArrayList<people>();
        connectionRequest = new ConnectionRequest(){


            protected void readResponse(InputStream in) throws IOException {
                JSONParser json = new JSONParser();

                Reader reader = new InputStreamReader(in, "UTF-8");

                Map<String, Object> data = json.parseJSON(reader);
                List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");
                people.clear();

                for (Map<String, Object> obj : content) {
                    people.add(new people((String) obj.get("fname"),(String) obj.get("lname"),(String) obj.get("email"),(String)obj.get("Dob"),(String) obj.get("gender"),(String) obj.get("phone"),(String) obj.get("emergency"),(String) obj.get("postal"))

                    );
                    //System.out.println((String)obj.get("fname"));
                }
            }

            //creating the list layout with data
            @Override
            protected void postResponse() {
                //System.out.println(libs.size());
                list = new Form();
                com.codename1.ui.List uiLibsList = new com.codename1.ui.List();
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
                        //new Abook(currentPeople.getTitle(),currentPeople.getAuthor(),currentPeople.getCategory(),currentPeople.getIsbn()).show();
                    }
                });
                list.setLayout(new BorderLayout());
                list.add(BorderLayout.NORTH,uiLibsList);
                //list.addVolunteer(BorderLayout.SOUTH,Statics.createBackBtn());
                list.show();
            }
            //-----------------------//
        };
        connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/getAll.php");
        connectionRequest.setPost(false);
        NetworkManager.getInstance().addToQueue(connectionRequest);



    }

    public boolean checkNullData(String emailText, String passwordTex, String divText){
        boolean valid = true;
        if(emailText == null || emailText.isEmpty())
            valid = false;
        else if(passwordTex== null || passwordTex.isEmpty())
            valid = false;
        else if(divText == null || divText.isEmpty())
            valid = false;


        return valid;
    }

}

