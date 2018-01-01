package database;

import admin.AlertBox;
import admin.UserData;
import drives.drive;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import records.record;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DAO {
    private static  final String USERNAME = "root";
    private static  final String PASS = "";
    private static  final String URLLocal = "jdbc:mysql://localhost/school";
    private static final String liveUrl = "jdbc:mysql://mysql12.000webhost.com/id3775275_redlight";
    private static final String link = "http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/";

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
            url = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/connect.php");
            phpCon =(HttpURLConnection)url.openConnection();
            num = phpCon.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return num == 200;
    }

    public String[] phpIsLogin(String email,String pass){
        BufferedReader in;
        String inputLine;
        StringBuilder sb = new StringBuilder();



        if(phpConnection()){
            try {
                URL connectURL = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/loginType.php?email=" + email + "&password="+ pass);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));
                while((inputLine = in.readLine()) != null){
                    //System.out.println(inputLine);
                    sb.append(inputLine+"\n");
                }

                //System.out.println("*%*"+ " "+sb);
                String[] data = sb.toString().split(" ");
               // System.out.println(data[0]+" "+data[1]+" "+data[2]);

                if(!sb.toString().equals("")) {
                    return data;
                }


                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public List<String> getAll(){
        BufferedReader in;
        String inputLine;
        StringBuilder sb = new StringBuilder();
        int i =0;
        List<String> ls = new ArrayList<>();



        if(phpConnection()){
            try {
                URL connectURL = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/desktopGetAll.php");
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

    public List<userObject> phpgetAllVolunteers(){
        BufferedReader in;
        String inputLine;
        String str[];
        StringBuilder sb = new StringBuilder();
        int i =0;
        List<String> ls = new ArrayList<>();
        List<userObject> users = new ArrayList<>();


        if(phpConnection()){
            try {
                URL connectURL = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/desktopGetAll.php");
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    //System.out.println(inputLine);
                    //sb.append(inputLine+"\n");
                    ls.add(inputLine) ;

                }
                if(ls.size() != 0) {
                    for (String info : ls) {
                        userObject user = new userObject();
                        str = info.split(Pattern.quote(" "));
                        user.id = Integer.parseInt(str[0]);
                        user.fname = str[1];
                        user.lname = str[2];
                        user.email = str[3];
                        user.phone = str[4];
                        user.pass = str[5];
                        user.hourstotal = str[6];
                        user.hourssigned = str[7];
                        user.ename = str[8];
                        user.ephone = str[9];
                        users.add(user);
                    }
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    public List<userObject> phpgetVolunteer(String fn, String ln){
        BufferedReader in;
        String inputLine;
        String str[];
        StringBuilder sb = new StringBuilder();
        int i =0;
        List<String> ls = new ArrayList<>();
        List<userObject> users = new ArrayList<>();


        if(phpConnection()){
            try {
                URL connectURL = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/loadSelectedRow.php?fname="+fn+"&lname="+ln);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    //System.out.println(inputLine);
                    //sb.append(inputLine+"\n");
                    ls.add(inputLine) ;

                }
                if(ls.size() != 0) {
                    for (String info : ls) {
                        userObject user = new userObject();
                        str = info.split(Pattern.quote(" "));
                        user.fname = str[1];
                        user.lname = str[2];
                        user.email = str[3];
                        user.phone = str[4];
                        user.pass = str[5];
                        user.hourstotal = str[6];
                        user.hourssigned = str[7];
                        user.ename = str[8];
                        user.ephone = str[9];
                        users.add(user);
                    }
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @FXML
    public boolean phpAddData(userObject user){
        BufferedReader in;

        String input = "http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/insertAll.php?fname=" + user.fname +"&lname="+ user.lname+"&email="+user.email+
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
    public boolean phpUpdateData(userObject user){
        BufferedReader in;

        String input = "http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/updateData.php?fname=" + user.fname +"&lname="+ user.lname+"&email="+user.email+
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
                connectURL = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/emailExists.php?email="+searchKey);
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
                connectURL = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/emailExists.php?email="+searchKey);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    sb.append(inputLine);
                    ls.add(inputLine) ;
                }
                System.out.println("$*$ "+sb);

                for(String info:ls){
                    str =  info.split(Pattern.quote(" "));
                    user.id = Integer.parseInt(str[0]);
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

    @FXML
    public userObject phpGetUserById(int id) {
        String searchKey = Integer.toString(id);
        BufferedReader in;
        List<String> ls = new ArrayList<>();
        String inputLine;
        StringBuilder sb = new StringBuilder();
        String[] str;
        userObject user = new userObject();

        if(phpConnection()){
            URL connectURL = null;
            try {
                connectURL = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/getVolunteerById.php?id="+id);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    sb.append(inputLine);
                    ls.add(inputLine) ;
                }
                System.out.println("$*$ "+sb);

                for(String info:ls){
                    str =  info.split(Pattern.quote(" "));
                    user.id = Integer.parseInt(str[0]);
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

    public List<drive> getAllDrives(){
        BufferedReader in;
        String inputLine;
        StringBuilder sb = new StringBuilder();
        int i =0;
        String[] str;
        List<String> ls = new ArrayList<>();
        List<drive> drives = new ArrayList();


        if(phpConnection()){
            try {
                URL connectURL = new URL(link + "desktopGetAllDrives.php");
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
                    sb.append(inputLine+"\n");
                    ls.add(inputLine) ;
                }

                if(ls.size() != 0) {
                    for (String info : ls) {
                        str = info.split(Pattern.quote(" "));
                        drive d = new drive();
                        //d.setDrive(str[1], str[2]);
                        d.start = str[1];
                        d.name = str[2];
                        d.id = Integer.parseInt(str[0]);
                        drives.add(d);
                    }
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return drives;
    }

    public boolean phpUpdateHoursTotal(int id, Double ht) {
        BufferedReader in;

        String input = "http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/updateHoursTotal.php?id="+id+"&ht="+ht;
        StringBuilder sb = new StringBuilder();
        String inputLine;

        if(phpConnection()){
            try {
                URL connectURL = new URL(input);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
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
            AlertBox.display("UPDATE", "Could not update" + "\nPlease Check Form Input");
            return false;
        }
    }

    public List<record> getVolunteerContributions(int id){
        BufferedReader in;
        String inputLine;
        StringBuilder sb = new StringBuilder();
        int i =0;
        String[] str;
        List<String> ls = new ArrayList<>();
        List<record> recs = new ArrayList();


        if(phpConnection()){
            try {
                URL connectURL = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/getContributions.php?id=" + id);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
                    sb.append(inputLine+"\n");
                    ls.add(inputLine) ;
                }

                if(ls.size() != 0) {
                    for (String info : ls) {
                        str = info.split(Pattern.quote(" "));
                        record rec = new record();
                        rec.operationRecordId = Integer.parseInt(str[0]);
                        rec.driveStartDate = str[1];
                        rec.driveId = Integer.parseInt(str[2]);
                        rec.volunteerId = Integer.parseInt(str[3]);
                        rec.operationDayDate = str[4];
                        rec.hoursContributed = Double.parseDouble(str[5]);
                        recs.add(rec);
                    }
                }else{
                    return  null;
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return recs;
    }

    public record getRecord(record r){
        BufferedReader in;
        String inputLine;
        StringBuilder sb = new StringBuilder();
        int i =0;
        String[] str;
        List<String> ls = new ArrayList<>();
        record rec = new record();


        if(phpConnection()){
            try {
                URL connectURL = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/desktopGetRecord.php?volid=" + r.volunteerId + "&driveid=" +r.driveId +"&opdate="+r.operationDayDate);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
                    sb.append(inputLine+"\n");
                    ls.add(inputLine) ;
                }

                if(ls.size() != 0) {
                    for (String info : ls) {
                        str = info.split(Pattern.quote(" "));
                        rec.operationRecordId = Integer.parseInt(str[0]);
                        rec.driveStartDate = str[1];
                        rec.driveId = Integer.parseInt(str[2]);
                        rec.volunteerId = Integer.parseInt(str[3]);
                        rec.operationDayDate = str[4];
                        rec.hoursContributed = Integer.parseInt(str[5]);
                    }
                }else{
                    return  null;
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return rec;
    }

    @FXML
    public boolean phpAddDrive(drive d){
        BufferedReader in;

        String input = "http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/insertDrives.php?sdate=" + d.start +"&edate="+ d.end;
        StringBuilder sb = new StringBuilder();
        String inputLine;

        if(phpConnection()){
            try {
                URL connectURL = new URL(input);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
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


    public void phpDeleteDrive(int did){
        BufferedReader in;

        String input = link + "deleteDrive.php?id="+did;
        StringBuilder sb = new StringBuilder();
        String inputLine;

        if(phpConnection()){
            try {
                URL connectURL = new URL(input);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
                    sb.append(inputLine+"\n");
                }
                System.out.println(sb);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //AlertBox.display("INSERT", "Could not delete" + "\nPlease Check Form Input");
        }
    }

    public List<userObject> phpgetAllDriveRecords(int id){
        BufferedReader in;
        String inputLine;
        StringBuilder sb = new StringBuilder();
        int i =0;
        String[] str;
        List<String> ls = new ArrayList<>();
        List<userObject> recs = new ArrayList();


        if(phpConnection()){
            try {
                URL connectURL = new URL(link + "desktopGetAllDriveRecords.php?driveid="+ id);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
                    sb.append(inputLine+"\n");
                    ls.add(inputLine) ;
                }

                if(ls.size() != 0) {
                    for (String info : ls) {
                        str = info.split(Pattern.quote(" "));
                        userObject r = new userObject();
                        r.hoursCon = str[0];
                        r.fname = str[1];
                        r.lname = str[2];
                        r.email = str[3];
                        r.phone = str[4];
                        r.ename = str[5];
                        r.ephone = str[6];
                        r.date = str[7];
                        recs.add(r);
                    }
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return recs;
    }

    public List<record> phpgetAllDriveDays(drive d){
        BufferedReader in;
        String inputLine;
        StringBuilder sb = new StringBuilder();
        int i =0;
        String[] str;
        List<String> ls = new ArrayList<>();
        List<record> recs = new ArrayList();


        if(phpConnection()){
            try {
                URL connectURL = new URL(link + "desktopGetAllDriveDays.php?id="+ d.id);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
                    sb.append(inputLine+"\n");
                    ls.add(inputLine) ;
                }

                if(ls.size() != 0) {
                    for (String info : ls) {
                        str = info.split(Pattern.quote(" "));
                        record r = new record();
                        r.operationDayDate = (str[0]);
                        r.driveStartDate = d.start;
                        recs.add(r);
                    }
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return recs;
    }


    public List<userObject> phpDriveReport(int id){
        BufferedReader in;
        String inputLine;
        StringBuilder sb = new StringBuilder();
        int i =0;
        String[] str;
        List<String> ls = new ArrayList<>();
        List<userObject> recs = new ArrayList();


        if(phpConnection()){
            try {
                URL connectURL = new URL(link + "desktopGetReport.php?id="+ id);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
                    sb.append(inputLine+"\n");
                    ls.add(inputLine) ;
                }

                if(ls.size() != 0) {
                    for (String info : ls) {
                        str = info.split(Pattern.quote(" "));
                        userObject r = new userObject();

                        if(str[0].compareTo("") != 0) {
                            r.fname = str[0];
                            r.lname = str[1];
                            r.email = str[2];
                            r.phone = str[3];
                            r.hoursCon = str[4];
                            recs.add(r);
                        }
                    }
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return recs;
    }

    public List<userObject> phpgetAllDriveDayRecords(String odate){
        BufferedReader in;
        String inputLine;
        StringBuilder sb = new StringBuilder();
        int i =0;
        String[] str;
        List<String> ls = new ArrayList<>();
        List<userObject> recs = new ArrayList();


        if(phpConnection()){
            try {
                URL connectURL = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/desktopGetAllDriveDayRecords.php?odate="+ odate);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
                    sb.append(inputLine+"\n");
                    ls.add(inputLine) ;
                }

                if(ls.size() != 0) {
                    for (String info : ls) {
                        str = info.split(Pattern.quote(" "));
                        userObject r = new userObject();
                        r.hoursCon = str[0];
                        r.fname = str[1];
                        r.lname = str[2];
                        r.email = str[3];
                        r.phone = str[4];
                        r.ename = str[5];
                        r.ephone = str[6];
                        recs.add(r);
                    }
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return recs;
    }

    @FXML
    public boolean phpDriveExists(drive d){
        BufferedReader in;

        String input = "http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/driveExists.php?sdate=" + d.start +"&edate="+ d.end;
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
                if(sb.toString().compareTo("") != 0)
                    return true;
                else
                    return false;
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


    public void phpDeleteRecord(userObject user){
        BufferedReader in;

        String input = link + "deleteRecord.php?id="+user.recId+"&userid="+user.id;
        StringBuilder sb = new StringBuilder();
        String inputLine;

        if(phpConnection()){
            try {
                URL connectURL = new URL(input);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
                    sb.append(inputLine+"\n");
                }
                System.out.println(sb);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //AlertBox.display("INSERT", "Could not Insert" + "\nPlease Check Form Input");
        }
    }

    public boolean phpAddRecord(record rec, String name) {
        BufferedReader in;

        String input = "http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/insertRecord.php?driveid="+rec.driveId+"&volid="+rec.volunteerId+"&hourscon="+rec.hoursContributed+"&opdate="+rec.operationDayDate+"&dsdate="+rec.driveStartDate+"&super="+name;
        StringBuilder sb = new StringBuilder();
        String inputLine;

        if(phpConnection()){
            try {
                URL connectURL = new URL(input);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
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

    public boolean phpUpdateRecord(record rec, String name) {
        BufferedReader in;

        String input = "http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/updateRecord.php?driveid="+rec.driveId+"&volid="+rec.volunteerId+"&hourscon="+rec.hoursContributed+"&opdate="+rec.operationDayDate+"&dsdate="+rec.driveStartDate+"&super="+name;
        StringBuilder sb = new StringBuilder();
        String inputLine;

        if(phpConnection()){
            try {
                URL connectURL = new URL(input);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
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
            AlertBox.display("UPDATE", "Could not update" + "\nPlease Check Form Input");
            return false;
        }
    }

     /*   PHP   */


}
