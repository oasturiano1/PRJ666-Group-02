package drives;

import database.DAO;
import database.dbConnection;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class addDrive extends Application implements Initializable{

    public static final Pattern valDate =
            Pattern.compile("[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}");

    dbConnection db;

    DAO dao;

    @FXML
    private javafx.scene.control.TextField sDate;

    @FXML
    private javafx.scene.control.TextField eDate;

    @FXML
    private Labeled error;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("addDrive.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ADD DRIVES");
        primaryStage.show();
    }

    //public void setDB(dbConnection db){
    //    this.db = db;
    //}

    @FXML
    public void addDrive() throws SQLException {
        dao = new DAO();
        String sdate = "";
        String edate = "";
        sdate = sDate.getText();
        edate = eDate.getText();

        if(sdate.compareTo("") != 0 && edate.compareTo("") != 0){
            boolean smatch = true;
            boolean ematch = true;

            Matcher matcher = valDate.matcher(sdate);
            smatch = matcher.matches();
            matcher = valDate.matcher(edate);
            ematch = matcher.matches();

            if(smatch && ematch){// MAKE SURE DATES ARE NOT THE SAME AND IN THE RIGHT ORDER

                Connection connection = db.connect();
                System.out.println("DATES ARE OKAY");
                boolean success = db.addDrive(sdate,edate);
                connection.close();
                if(success){
                    error.setText("Drive Added!");
                }else {
                    error.setText("Drive Could Not Be Added!");
                }
            }

            System.out.println(sdate + " " + edate);

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db = new dbConnection();
    }
}
