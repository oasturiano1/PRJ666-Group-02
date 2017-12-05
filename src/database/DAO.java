package database;

import admin.AlertBox;
import admin.UserData;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DAO {
    private static  final String USERNAME = "root";
    private static  final String PASS = "";
    private static  final String URLLocal = "jdbc:mysql://localhost/school";
    private static final String liveUrl = "jdbc:mysql://mysql12.000webhost.com/id3775275_redlight";
    //mysql xampp//

    private static final String sqliteCon = "jdbc:sqlite:C:\\Users\\B85M-ECSM\\Documents\\SCHOOL WORK\\Sem7\\PRJ666\\foodDrive\\redlight.db";
    private Connection con =null;
    private HttpURLConnection phpCon = null;
    private URL url;

    public DAO(){ }

    public Connection connect() throws SQLException {


        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Class.forName("org.sqlite.JDBC");
            //con =  DriverManager.getConnection(sqliteCon);
            con = DriverManager.getConnection(liveUrl,"id3775275_student","password123");
            return con;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // con =  DriverManager.getConnection(sqliteCon);
        //con =DriverManager.getConnection(URL,"root","");
        return null;
    }

    /*public Connection connect() throws SQLException {


        try {
            // Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.sqlite.JDBC");
            con =  DriverManager.getConnection(sqliteCon);
            return con;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        con =  DriverManager.getConnection(sqliteCon);
        //con =DriverManager.getConnection(URL,"root","");
        return null;
    }*/


    /*   PHP   */
    public Boolean phpConnection(){
        int num = 0;
        try {
            url = new URL("http://rose-wood-food-drive.000webhostapp.com/connect.php");
            phpCon =(HttpURLConnection)url.openConnection();
            num = phpCon.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return num == 200;
    }

    public boolean phpIsLogin(String email,String pass,String opt){
        BufferedReader in;
        String inputLine;
        StringBuilder sb = new StringBuilder();



        if(phpConnection()){
            try {
                URL connectURL = new URL("http://rose-wood-food-drive.000webhostapp.com/loginType.php?email=" + email + "&password="+ pass+"&division="+opt);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));
                while((inputLine = in.readLine()) != null){
                    //System.out.println(inputLine);
                    sb.append(inputLine+"\n");
                }

                System.out.println("***"+ " "+sb);
                if(sb.toString().equals("")) {
                    return false;
                }


                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public List<String> getAll(){
        BufferedReader in;
        String inputLine;
        StringBuilder sb = new StringBuilder();
        int i =0;
        List<String> ls = new ArrayList<>();



        if(phpConnection()){
            try {
                URL connectURL = new URL("http://rose-wood-food-drive.000webhostapp.com/desktopGetAll.php");
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    //System.out.println(inputLine);
                    //sb.append(inputLine+"\n");
                    ls.add(inputLine) ;

                }

               /* System.out.println(ls.get(0));
                System.out.println(ls.get(1));
                System.out.println(ls.get(2));*/

                /*for(String n: ls){
                    System.out.println(n);
                }*/



                // String nohtml = sb.toString().replaceAll("\\<.*?>","");
                /*System.out.println("*****");
                String[] strArray2 = sb.toString().split(Pattern.quote(" "));
                System.out.println(strArray2[0]);
                System.out.println(strArray2[1]);
                System.out.println(strArray2[2]);
                System.out.println(strArray2[3]);
                System.out.println(strArray2[4]);
                System.out.println(strArray2[5]);
                System.out.println(strArray2[6]);
                System.out.println(strArray2[7]);
                System.out.println(strArray2[8]);
                System.out.println(strArray2[9]);*/






               /* StringTokenizer st = new StringTokenizer(nohtml,":");
                String line = st.nextToken();
                String line2 = st.nextToken();
                System.out.println(line+" "+line2);*/


               /* String[] strArray2 = sb.toString().split(Pattern.quote("[{"));
                System.out.println(strArray2[0]);
                System.out.println(strArray2[1])*/;
                //System.out.println(sb.toString());
                //System.out.println(nohtml);

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ls;
    }

    @FXML
    public boolean phpAddData(userObject user){
        BufferedReader in;

        String input = "http://rose-wood-food-drive.000webhostapp.com/updateData.php?fname=" + user.fname +"&lname="+ user.lname+"&email="+user.email+
                "&phoneNumber="+user.phone+ "&password="+user.pass+
                "&hoursTotal="+user.hourstotal+"&hoursSigned="+user.hourssigned+
                "&contactName="+user.ename+"&contactPhone="+user.ephone;
        StringBuilder sb = new StringBuilder();
        String inputLine;

            if(phpConnection()){
                try {
                    URL connectURL = new URL(input);
                    in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                    while((inputLine = in.readLine()) != null){
                        //System.out.println(inputLine);
                        sb.append(inputLine+"\n");
                    }
                    System.out.println(sb);
                    return true;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
            AlertBox.display("INSERT", "Could not Insert" + "\nPlease Check Form Input");
            return false;
            }
    }

    @FXML
    public boolean phpEmailExists(String email) {
        String searchKey = email;
        BufferedReader in;
        List<String> ls = new ArrayList<>();
        String inputLine;
        StringBuilder sb = new StringBuilder();

        if(phpConnection()){
            URL connectURL = null;
            try {
                connectURL = new URL("http://rose-wood-food-drive.000webhostapp.com/emailExists.php?email="+searchKey);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    sb.append(inputLine);
                    ls.add(inputLine) ;
                }
                System.out.println("$*$ "+sb);
                if(!ls.isEmpty())
                    return true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }
        return false;
    }

    @FXML
    public userObject phpGetUser(String email) {
        String searchKey = email;
        BufferedReader in;
        List<String> ls = new ArrayList<>();
        String inputLine;
        StringBuilder sb = new StringBuilder();
        String[] str;
        userObject user = new userObject();

        if(phpConnection()){
            URL connectURL = null;
            try {
                connectURL = new URL("http://rose-wood-food-drive.000webhostapp.com/emailExists.php?email="+searchKey);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    sb.append(inputLine);
                    ls.add(inputLine) ;
                }
                System.out.println("$*$ "+sb);

                for(String info:ls){
                    str =  info.split(Pattern.quote(" "));
                    user.fname = str[1];
                    user.lname = str[2];
                    user.email = str[3];
                    user.phone = str[4];
                    user.pass = str[5];
                    user.hourstotal = str[6];
                    user.hourssigned = str[7];
                    user.ename = str[8];
                    user.ephone = str[9];
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
        return user;
    }

     /*   PHP   */


}
