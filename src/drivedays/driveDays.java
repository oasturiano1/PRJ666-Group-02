package drivedays;

import database.DAO;
import database.dbConnection;
import database.userObject;
import drives.drive;
import drives.drivesController;
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
import volunteerdays.volunteerDays;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class driveDays  extends Application implements Initializable {

    @FXML
    Labeled fullname;

    @FXML
    Labeled email;

    @FXML
    Labeled pnumber;

    @FXML
    Labeled hours;

    @FXML
    Labeled contactNa;

    @FXML
    Labeled contactNu;

    @FXML
    Labeled drivestart;

    @FXML
    Labeled driveday;

    @FXML
    ListView<String> daysList;

    @FXML
    ListView<String> volList;

    @FXML
    Button back;

    String opDay = "";

    drive selectD;

    userObject selUser;

    LocalDate selDay;

    /*@FXML
    ListView<String> drivedateslist;

    @FXML
    ListView<String> studentcontributionlist;*/

    DAO dao;
    dbConnection db;
    List<record> records = new ArrayList();
    List<userObject> volRecords = new ArrayList();



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("drives.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DRIVES");
        primaryStage.show();


        daysList.setEditable(true);
        //drivedateslist.setEditable(true);

        //dateFormat.format(drives.get(i).start);



    }
/*
    @FXML
    public void addDay()throws IOException{

        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        root = (Pane) loader.load(getClass().getResource("/drivedays/addDay.fxml").openStream());
        addDay ac = (addDay) loader.getController();
        ac.setDrive(selectD, selDay, selUser,"");
        //ac.setDrive(selectD);

        Scene scene = new Scene(root);
        userStage.setScene(scene);
        userStage.setTitle("Add Drive");
        userStage.show();
    }

    @FXML
    public void editDay()throws IOException{

        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        root = (Pane) loader.load(getClass().getResource("/drivedays/addDay.fxml").openStream());
        addDay ac = (addDay) loader.getController();
        ac.setDrive(selectD, selDay, selUser,"");
        //ac.setDrive(selectD);

        Scene scene = new Scene(root);
        userStage.setScene(scene);
        userStage.setTitle("Add Drive");
        userStage.show();
    }
*/
    @FXML
    public void viewDriveDays() throws IOException {
        System.out.println("PRESSING!");

        if(opDay != "") {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = null;
            root = (Pane) loader.load(getClass().getResource("/volunteerdays/volunteerDays.fxml").openStream());
            volunteerDays ac = (volunteerDays) loader.getController();
            ac.setDriveDay(opDay, selectD);
            //ac.setDrive(selectD);

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Drive Days");
            userStage.show();
        }

    }

    public void setDrive(drive sd){
        dao = new DAO();
        selectD = sd;
        selUser = new userObject();
        drivestart.setText("Drive Days for "  +selectD.start + " drive");
        db = new dbConnection();



        //try {
            //Connection connection = db.connect();
            records = dao.phpgetAllDriveDays(selectD);
            //connection.close();
        //} catch (SQLException e) {
        //    e.printStackTrace();
        //}

        daysList.getItems().clear();
        for(int i = 0; i < records.size(); i++){
            daysList.getItems().add(records.get(i).operationDayDate);
            System.out.println(records.get(i).operationDayDate);
        }

        daysList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                fullname.setText("---");
                email.setText("---");
                pnumber.setText("---");
                hours.setText("---");
                contactNa.setText("---");
                contactNu.setText("---");

                System.out.println("clicked on " + daysList.getSelectionModel().getSelectedItem());
                opDay = daysList.getSelectionModel().getSelectedItem();

                driveday.setText("Volunteers for "  +opDay);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                selDay = LocalDate.parse(opDay, formatter);

                //try {
                    //Connection connection = db.connect();
                    volRecords = dao.phpgetAllDriveDayRecords(opDay);
                    //connection.close();
                    volList.getItems().clear();
                    for(int i = 0; i < volRecords.size(); i++){
                        volList.getItems().add(volRecords.get(i).fname + " " + volRecords.get(i).lname);
                    }
                //} catch (SQLException e) {
                //    e.printStackTrace();
                //}
            }
        });


        volList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + volList.getSelectionModel().getSelectedItem());
                System.out.println(volRecords.get(volList.getSelectionModel().getSelectedIndex()).fname);
                selUser = new userObject();

                selUser = volRecords.get(volList.getSelectionModel().getSelectedIndex());

                fullname.setText("Full name:" + selUser.fname + " " + selUser.lname);
                email.setText("Email:" + selUser.email);
                pnumber.setText("Phone number:" + selUser.phone);
                hours.setText("Hours Contributed:" + selUser.hoursCon);
                contactNa.setText("Contact Name:" + selUser.ename);
                contactNu.setText("Contact Number:" + selUser.ephone);

            }
        });

    }

    @FXML
    public void back() throws IOException {

        System.out.print("PRESSED!");
        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = (Pane) loader.load(getClass().getResource("/drives/drives.fxml").openStream());
        drivesController ac =(drivesController) loader.getController();

        Scene scene = new Scene(root);
        userStage.setScene(scene);
        userStage.setTitle("Drives");
        userStage.show();

        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void loadData() {
        daysList.getItems().clear();
        //Connection connection = db.connect();
        records = dao.phpgetAllDriveDays(selectD);
        records = dao.phpgetAllDriveDays(selectD);
        //daysList.getItems().clear();
        for(int i = 0; i < records.size(); i++){
            daysList.getItems().add(records.get(i).operationDayDate);
            System.out.println(records.get(i).operationDayDate);
        }
        //connection.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }

    public static void main(String[] args){
        launch(args);
    }


}
