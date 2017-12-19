package drives;

import com.jfoenix.controls.JFXDatePicker;
import database.DAO;
import database.dbConnection;
import database.userObject;
import drivedays.driveDays;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import records.record;
import viewuser.userViewController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class drivesController extends Application implements Initializable {

    @FXML
    ListView<String> drivesList;

    @FXML
    Labeled eDateLabel;

    @FXML
    Labeled sDateLabel;


    public drive selectD = new drive();

    @FXML
    Labeled errmsg;

    @FXML
    JFXDatePicker sDatePicker;

    @FXML
    JFXDatePicker eDatePicker;

    @FXML
    Button viewDays;

    private LocalDate sDate;
    private LocalDate eDate;

    boolean errCatch = true;


    DAO dao;
    dbConnection db;
    List<drive> drives = new ArrayList();



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("drives.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DRIVES");
        primaryStage.show();


        drivesList.setEditable(true);
        //drivedateslist.setEditable(true);

        //dateFormat.format(drives.get(i).start);



    }

    @FXML
    public void viewDriveDays() throws IOException {
        System.out.println("PRESSING!");
        if(selectD.start.compareTo("") != 0 || selectD.end.compareTo("") != 0) {
            Stage driveStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = null;
            root = (Pane) loader.load(getClass().getResource("/drivedays/driveDays.fxml").openStream());
            driveDays ac = (driveDays) loader.getController();
            ac.setDrive(selectD);
            //ac.setDrive(selectD);

            Scene scene = new Scene(root);
            driveStage.setScene(scene);
            driveStage.setTitle("Drive Days");
            driveStage.show();

            Stage stage = (Stage) viewDays.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void addDrive() throws SQLException {
        dateCompare(errmsg);



        if(errCatch){

            String startDate = sDate.toString();
            String endDate = eDate.toString();

            drive d = new drive();
            d.setDrive(startDate,endDate);

            //Connection connection = db.connect();
            System.out.println("DATES ARE OKAY");
            boolean success = dao.phpAddDrive(d);
            //connection.close();
            if(success){
                errmsg.setText("Drive Added!");
                loadData();
            }else {
                errmsg.setText("Drive Could Not Be Added!");
            }


            System.out.println(startDate + " " + endDate);
        }
    }

    @FXML
    public void loadData() throws SQLException {
        //Connection connection = db.connect();
        drivesList.getItems().clear();
        drives = dao.getAllDrives();
        for(int i = 0; i < drives.size(); i++){
            drivesList.getItems().add(drives.get(i).start);
            System.out.println(drives.get(i).start);
        }
        //connection.close();
    }

    public void getDateStart(){
        sDate = sDatePicker.getValue();
    }

    public void getDateEnd(){
        eDate = eDatePicker.getValue();
    }

    public void dateCompare(Labeled sd) throws SQLException {
        errCatch = false;

        if(sDate == null || eDate == null){
            sd.setText("Both dates are required!");
        }else {
            drive r = new drive();
            r.start = sDate.toString();
            r.end = eDate.toString();
            //Connection connection = db.connect();
            boolean exists = dao.phpDriveExists(r);
            //connection.close();

            if (sDate.equals(eDate)) {
                sd.setText("Dates are identical!");
                errCatch = false;
            } else if (!sDate.isBefore(eDate)) {
                sd.setText("Date order is wrong!");
                errCatch = false;
            } else if (exists) {
                sd.setText("Drive already exists!");
                errCatch = false;
            } else {//check if dates already exists
                sd.setText("");
                errCatch = true;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = new DAO();
        db = new dbConnection();
        sDatePicker.setValue(LocalDate.now());
        eDatePicker.setValue(LocalDate.now());
        sDate = sDatePicker.getValue();
        eDate = eDatePicker.getValue();

        //try {
            //Connection connection = db.connect();
            drives = dao.getAllDrives();
            //connection.close();
        //} catch (SQLException e) {
        //    e.printStackTrace();
        //}

        if(drives != null) {
            drivesList.getItems().clear();
            for (int i = 0; i < drives.size(); i++) {
                drivesList.getItems().add(drives.get(i).start);
                System.out.println(drives.get(i).start);
            }
        }
        drivesList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                //try {
                    //Connection connection = db.connect();
                    System.out.println("clicked on " + drivesList.getSelectionModel().getSelectedItem());
                    String start = drivesList.getSelectionModel().getSelectedItem();
                    selectD = new drive();
                    selectD = drives.get(drivesList.getSelectionModel().getSelectedIndex());

                    sDateLabel.setText(selectD.start);
                    eDateLabel.setText(selectD.end);
                    //connection.close();
                //} catch (SQLException e) {
                //    e.printStackTrace();
                //}
            }
        });

    }

    public static void main(String[] args){
        launch(args);
    }


}
