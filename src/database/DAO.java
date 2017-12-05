package database;

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

     /*   PHP   */


}
