package database;

import drives.drive;
import records.record;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class dbConnection {
    //mysql xampp//
    private static  final String USERNAME = "root";
    private static  final String PASS = "";
    private static  final String URL = "jdbc:mysql://localhost/school";
    //mysql xampp//

    private static final String sqliteCon = "jdbc:sqlite:C:\\Users\\B85M-ECSM\\Documents\\SCHOOL WORK\\Sem7\\PRJ666\\foodDrive\\redlight.db";
    private Connection con =null;

    public Connection connect() throws SQLException {


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
    }

    public boolean isDatabaseCon(){
        try {
            connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.con != null;
    }

    public boolean isLogin(String name,String pass,String opt) throws SQLException {
        PreparedStatement checkStmt = null;
        ResultSet checkRs = null;
        String checksql = "SELECT * FROM login where email = ? and password = ? and division = ?";

        if(opt == "ADMIN"){
            checksql = "SELECT * FROM login where email = ? and password = ? and division = ?";;
        }else{
            checksql = "SELECT * FROM volunteer WHERE email = ? AND password = ?";
        }

        try {
            checkStmt = con.prepareStatement(checksql);

            if(opt == "ADMIN"){
            checkStmt.setString(1,name);
            checkStmt.setString(2,pass);
            checkStmt.setString(3,opt);
            }else{
                checkStmt.setString(1,name);
                checkStmt.setString(2,pass);
            }
            checkRs = checkStmt.executeQuery();

            if(checkRs.next()){
                return  true;
            }
            return false;

        } catch (SQLException e) {
           return  false;
        } finally {
            {
                checkStmt.close();
                checkRs.close();
            }
        }

    }

    //ORLANDSON
    public boolean emailExists(String email){//checks if email already exists in db
        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;
        checksql = "SELECT * FROM volunteer WHERE email = '" + email + "'";
        try {

            stmt = con.createStatement();
            resultSet = stmt.executeQuery(checksql);

            if(resultSet.next() == true){
                System.out.println("already exists");
                return  true;
            }
            System.out.println("is acceptable");
            return false;

        } catch (SQLException e) {
            System.out.println(e);
            return  false;
        }
    }

    public boolean updateUser(userObject user){
        String checksql = "UPDATE volunteer SET " +
                "fname = '" + user.fname + "', " +
                "lname = '" + user.lname + "', " +
                "email = '" + user.email + "', " +
                "hoursTotal = '" + user.hourstotal + "', " +
                "hoursSigned = '" + user.hourssigned + "', " +
                "phoneNumber = '" + user.phone + "', " +
                "password = '" + user.pass + "', " +
                "contactName = '" + user.ename + "', " +
                "contactPhone = '" + user.ephone + "' " +
                "WHERE email = '" + user.email + "'";

        Statement stmt = null;
        ResultSet resultSet = null;
        try {

            stmt = con.createStatement();
            stmt.executeUpdate(checksql);

            System.out.println("SUCCESS");
            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return  false;
        }
    }

    public boolean editUser(userObject user, userObject originalUser){

        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;


        checksql =
                "UPDATE volunteer SET " +
                        "email = '" + user.email + "', " +
                        "phoneNumber = '" + user.phone + "', " +
                        "password = '" + user.pass + "', " +
                        "contactName = '" + user.ename + "', " +
                        "contactPhone = '" + user.ephone + "' " +
                        "WHERE email = '" + originalUser.email + "'";

        if(user.pass == null || user.pass.compareTo("") == 0){
            checksql =
                    "UPDATE volunteer SET " +
                            "email = '" + user.email + "', "+
                            "phoneNumber = '" + user.phone + "', "+
                            "contactName = '" + user.ename + "', "+
                            "contactPhone = '" + user.ephone + "' " +
                            "WHERE email = '" + originalUser.email +"'";
        }

        System.out.println(checksql);
        try {

            stmt = con.createStatement();
            stmt.executeQuery(checksql);

            System.out.println("SUCCESS");
            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return  false;
        }
    }

    //creates new user wiht parameters given
    public boolean newUser(userObject newUser){
        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;
        checksql = "INSERT INTO volunteer (fname, lname, email, phoneNumber, password, contactName, contactPhone) VALUES('" + newUser.fname + "', '" + newUser.lname + "', '" + newUser.email + "', '" + newUser.phone + "', '" + newUser.pass + "', '"+ newUser.ename + "', '"+ newUser.ephone +"')";

        try {

            stmt = con.createStatement();
            stmt.executeUpdate(checksql);

            System.out.println("SUCCESS");
            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return  false;
        }
    }

    public userObject getUser(String email){
        userObject user = new userObject();
        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;
        checksql = "SELECT * FROM volunteer WHERE email = '" + email + "'";
        try {

            stmt = con.createStatement();
            resultSet = stmt.executeQuery(checksql);

            user.fname = resultSet.getString("fname");
            user.lname = resultSet.getString("lname");
            user.email = resultSet.getString("email");
            user.phone = resultSet.getString("phoneNumber");
            user.pass = resultSet.getString("password");
            user.hourstotal = resultSet.getString("hoursTotal");
            user.hourssigned = resultSet.getString("hoursSigned");
            user.ename = resultSet.getString("contactName");
            user.ephone = resultSet.getString("contactPhone");


            /*if(resultSet.next() == true){


                System.out.println("user found");
                return  user;
            }*/

            System.out.println("User Found");
            return user;

        } catch (SQLException e) {
            System.out.println(e);
            return  user;
        }
    }


    //get records by drive id and operationDayDate (all volunteers for that date, use volunteer ids)
    public List<userObject> getVolunteersByDate(String odate){
        List<userObject> users = new ArrayList();



        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;

        checksql = "SELECT operationDayRecords.hoursContributed, volunteer.fname ,volunteer.lname, volunteer.email, volunteer.phoneNumber, volunteer.contactName, volunteer.contactPhone FROM operationDayRecords " +
                "INNER JOIN volunteer ON operationDayRecords.volunteerId=volunteer.id " +
                "WHERE operationDayRecords.operationDayDate = '" + odate + "'";

        //checksql = "SELECT * FROM volunteer WHERE id IN (SELECT volunteerId FROM operationDayRecords WHERE operationDayDate = '" + odate + "')";
        try {
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(checksql);

            while (resultSet.next()){
                userObject user = new userObject();
                user.email = resultSet.getString("email");
                user.fname = resultSet.getString("fname");
                user.lname = resultSet.getString("lname");
                user.phone = resultSet.getString("phoneNumber");
                user.hoursCon = resultSet.getString("hoursContributed");
                user.ename =  resultSet.getString("contactName");
                user.ephone =  resultSet.getString("contactPhone");
                /*user.hourssigned = resultSet.getString("hoursTotal");
                user.hourstotal = resultSet.getString("hoursSigned");
                user.ename = resultSet.getString("contactName");
                user.ephone = resultSet.getString("contactPhone");*/
                System.out.println("User Found");
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            System.out.println(e);
            return  users;
        }
    }

    //get records by drive id and operationDayDate (all volunteers for that date, use volunteer ids)
    public List<record> getRecordsByDriveAndDate(String sdate, String odate){
        List<record> recs = new ArrayList();



        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;
        checksql = "SELECT * FROM operationDayRecords WHERE driveStartDate IS '" + sdate + "' AND operationDayDate IS '" + odate + "'";
        try {
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(checksql);

            while (resultSet.next()){
                record r = new record();
                r.operationRecordId = resultSet.getInt("operationRecordId");
                r.driveStartDate = resultSet.getString("driveStartDate");
                r.driveId = resultSet.getInt("driveId");
                r.volunteerId = resultSet.getInt("volunteerId");
                r.operationDayDate = resultSet.getString("operationDayDate");
                r.hoursContributed = resultSet.getInt("hoursContributed");
                System.out.println("User Found");
                recs.add(r);
            }
            return recs;

        } catch (SQLException e) {
            System.out.println(e);
            return  recs;
        }
    }



    public List<drive> getDrives(){

        List<drive> drives = new ArrayList();



        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;
        checksql = "SELECT * FROM drives";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            PreparedStatement checkStmt;
            checkStmt = con.prepareStatement(checksql);
            //stmt = con.createStatement();
            resultSet = checkStmt.executeQuery();
            //resultSet = stmt.executeQuery(checksql);

            while (resultSet.next()){

                drive dr = new drive();

                dr.id = resultSet.getInt("driveId");
                //String st = dateFormat.format(resultSet.getDate("startDate"));
                //String en = dateFormat.format(resultSet.getDate("endDate"));

                String st = resultSet.getString("startDate");
                String en = resultSet.getString("endDate");

                System.out.println(st);

                dr.start = st;
                dr.end = en;

                System.out.println("User Found");
                drives.add(dr);
            }

            return drives;

        } catch (SQLException e) {
            System.out.println(e);
            return  drives;
        }
    }

    public List<userObject> searchVols(String fn, String ln){

        List<userObject> vols = new ArrayList();

        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;
        checksql = "SELECT * FROM volunteer WHERE fname LIKE '%" + fn +"%' OR lname LIKE '%" + ln +"%'";

        try {
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(checksql);

            while (resultSet.next()){

                userObject vol = new userObject();

                vol.id = resultSet.getInt("id");
                vol.fname = resultSet.getString("fname");
                vol.lname = resultSet.getString("lname");
                //vol.hoursCon = resultSet.getString("hoursContributed");

                System.out.println("User Found");
                vols.add(vol);
            }

            return vols;

        } catch (SQLException e) {
            System.out.println(e);
            return  vols;
        }
    }

    public List<userObject> getVols(){

        List<userObject> vols = new ArrayList();

        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;
        checksql = "SELECT * FROM volunteer";

        try {
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(checksql);

            while (resultSet.next()){

                userObject vol = new userObject();

                vol.id = resultSet.getInt("id");
                vol.fname = resultSet.getString("fname");
                vol.lname = resultSet.getString("lname");
                //vol.hoursCon = resultSet.getString("hoursContributed");

                System.out.println("User Found");
                vols.add(vol);
            }

            return vols;

        } catch (SQLException e) {
            System.out.println(e);
            return  vols;
        }
    }

    public drive getDrive(String beg){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        drive dr = new drive();
        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;
        checksql = "SELECT * FROM drives WHERE startDate = '" + beg + "'";
        try {

            stmt = con.createStatement();
            resultSet = stmt.executeQuery(checksql);

            dr.id = resultSet.getInt("driveId");
            dr.start = resultSet.getString("startDate");
            dr.end = resultSet.getString("endDate");

            System.out.println("User Found");
            return dr;

        } catch (SQLException e) {
            System.out.println(e);
            return  dr;
        }
    }

    public boolean addDrive(String beg, String end){

        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;


        checksql = "INSERT INTO drives (startDate, endDate)" +
                "VALUES('"+beg+"','"+end+"')";

        System.out.println(checksql);
        try {

            stmt = con.createStatement();
            stmt.executeUpdate(checksql);

            System.out.println("SUCCESS");
            return true;

        } catch (SQLException e) {
            System.out.println(e);
            return  false;
        }
    }

    //get records by driveid - gets all records in a drive (all unique days)
    public List<record> getRecordsByDrive(String date){
        List<record> recs = new ArrayList();



        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;
        checksql = "SELECT DISTINCT operationDayDate FROM operationDayRecords WHERE driveStartDate IS '" + date + "'";
        try {
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(checksql);

            while (resultSet.next()){
                record r = new record();
                //r.operationRecordId = resultSet.getInt("operationRecordId");
                r.driveStartDate = date;
                r.operationDayDate = resultSet.getString("operationDayDate");
                //r.driveId = resultSet.getInt("driveId");
                //r.volunteerId = resultSet.getInt("volunteerId");
                //r.operationDayDate = resultSet.getString("operationDayDate");
                //r.hoursContributed = resultSet.getInt("hoursContributed");
                recs.add(r);
            }
            return recs;

        } catch (SQLException e) {
            System.out.println(e);
            return  recs;
        }
    }

    public boolean recordExists(record r){

        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;
        checksql = "SELECT * FROM operationDayRecords WHERE volunteerId = " + r.volunteerId + " AND driveId = " + r.driveId;

        /*checksql = "INSERT INTO operationDayRecords (driveId, volunteerId, hoursContributed, operationDayDate, driveStartDate)" +
                "VALUES("+rec.driveId+"," +
                rec.volunteerId+"," +
                rec.hoursContributed+",'" +
                rec.operationDayDate+"','" +
                rec.driveStartDate+"')";*/

        try {
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(checksql);

            if(resultSet.next()){
                return true;
            }else {
                return false;
            }


        } catch (SQLException e) {
            System.out.println(e);
            return  false;
        }
    }

    public boolean addRecord(record rec) {
        String checksql;
        Statement stmt = null;
        ResultSet resultSet = null;


        boolean exists = false;

        checksql = "SELECT * FROM operationDayRecords WHERE volunteerId = " + rec.volunteerId + " AND driveId = " + rec.driveId + " AND operationDayDate = '" + rec.operationDayDate +"'";

        try {
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(checksql);

            if (resultSet.next()) {//if record already exists
                double prev = resultSet.getInt("hoursContributed");

                double diff = rec.hoursContributed - prev;//get difference


                checksql = "UPDATE operationDayRecords SET hoursContributed = " + rec.hoursContributed + //apply change
                        " WHERE volunteerId = " + rec.volunteerId + " AND driveId = " + rec.driveId + " AND operationDayDate = '" + rec.operationDayDate +"'";
                stmt.executeUpdate(checksql);

                checksql = "SELECT hoursTotal FROM volunteer WHERE id = " + rec.volunteerId + "";//change total
                resultSet = stmt.executeQuery(checksql);
                double ht = resultSet.getDouble("hoursTotal");
                ht = ht + diff;
                checksql = "UPDATE volunteer SET hoursTotal = " + ht + " WHERE id = " + rec.volunteerId;
                stmt.executeUpdate(checksql);


                return true;
            } else {// if new record

                checksql = "INSERT INTO operationDayRecords (driveId, volunteerId, hoursContributed, operationDayDate, driveStartDate)" +
                        "VALUES(" + rec.driveId + "," +
                        rec.volunteerId + "," +
                        rec.hoursContributed + ",'" +
                        rec.operationDayDate + "','" +
                        rec.driveStartDate + "')";
                stmt.executeUpdate(checksql);

                checksql = "SELECT hoursTotal FROM volunteer WHERE id = " + rec.volunteerId + "";
                resultSet = stmt.executeQuery(checksql);

                double ht = resultSet.getDouble("hoursTotal");

                ht = ht + rec.hoursContributed;

                checksql = "UPDATE volunteer SET hoursTotal = " + ht + " WHERE id = " + rec.volunteerId;
                stmt.executeUpdate(checksql);
                return true;
            }


        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
}
