package database;

import java.sql.*;

public class dbConnection {
    //mysql xampp//
    private static  final String USERNAME = "root";
    private static  final String PASS = "";
    private static  final String URL = "jdbc:mysql://localhost/school";
    //mysql xampp//

    private static final String sqliteCon = "C:\\Users\\B85M-ECSM\\Documents\\SCHOOL WORK\\Sem7\\PRJ666\\foodDrive\\redlight.db";
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

        try {
            checkStmt = con.prepareStatement(checksql);
            checkStmt.setString(1,name);
            checkStmt.setString(2,pass);
            checkStmt.setString(3,opt);
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
            stmt.executeQuery(checksql);

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

}
