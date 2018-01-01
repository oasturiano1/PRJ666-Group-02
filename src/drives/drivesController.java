package drives;

import admin.AlertBox;
import admin.adminController;
import admin.formValidation;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import database.DAO;
import database.dbConnection;
import database.userObject;
import drivedays.addDay;
import drivedays.driveDays;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class drivesController extends Application implements Initializable {
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
    Labeled supervisor;

    @FXML
    Labeled drivestart;

    @FXML
    Labeled driveday;
    @FXML
    ListView<String> drivesList;

    @FXML
    ListView<String> volList;

    @FXML
    ListView<String> daysList;

    //@FXML
    //Labeled eDateLabel;

    //@FXML
    //Labeled sDateLabel;


    public drive selectD = new drive();

    @FXML
    Labeled errmsg;

    @FXML
    JFXDatePicker sDatePicker;

    //@FXML
    //JFXDatePicker eDatePicker;

    @FXML
    JFXTextField driveName;

    @FXML
    Button viewDays;

    @FXML
    Button back;

    @FXML
    Button addRecord;

    private LocalDate sDate;
    //private LocalDate eDate;

    private String opDay;

    private LocalDate selDay;

    private String contribution = "0";

    boolean errCatch = true;

    private String adminName;

    userObject selUser;
    DAO dao;
    dbConnection db;
    List<drive> drives = new ArrayList();
    List<userObject> volRecords = new ArrayList();
    List<record> records = new ArrayList();


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
        driveExists(errmsg);

        if(errCatch){

            String startDate = sDate.toString();
            String name = driveName.getText();

            drive d = new drive();
            d.start=startDate;
            d.name=name;


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


            //System.out.println(startDate + " " + endDate);
        }
    }

    @FXML
    public void deleteDrive() throws SQLException {
        if(selectD.id != 0) {


            List<userObject> check = new ArrayList();
            check = dao.phpDriveReport(selectD.id);

            if(check.size() == 0){//if drive is empty

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the " + selectD.name + " " + selectD.start + " drive?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {//delete
                    dao.phpDeleteDrive(selectD.id);
                    AlertBox.display("DELETE", "Drive deleted!");
                    loadData();
                }

            }else {
                AlertBox.display("ERROR", "Drive must be empty to delete!");
            }


        }else {
            AlertBox.display("ERROR", "Please select a drive to delete!");
            //Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a drive to add records!", ButtonType.OK);
            //alert.showAndWait();
        }
    }


    @FXML
    public void loadData() throws SQLException {//TODO HERE
        //Connection connection = db.connect();
        drivesList.getItems().clear();
        daysList.getItems().clear();
        volList.getItems().clear();
        drives = dao.getAllDrives();
        for(int i = 0; i < drives.size(); i++){
            drivesList.getItems().add(drives.get(i).name + " " +drives.get(i).start);
            System.out.println(drives.get(i).start);
        }

        //connection.close();
    }

    public void getDateStart(){
        sDate = sDatePicker.getValue();
    }

    //public void getDateEnd(){
     //   eDate = eDatePicker.getValue();
    //}

    public void driveExists(Labeled sd) throws SQLException {
        errCatch = false;

        if(sDate == null || driveName == null){
            sd.setText("Name and date are required!");
        }else {
            drive r = new drive();
            r.start = sDate.toString();
            r.name = driveName.getText();
            //Connection connection = db.connect();
            boolean exists = dao.phpDriveExists(r);
            //connection.close();

            if (exists) {
                sd.setText("Drive already exists!");
                errCatch = false;
            } else {//check if dates already exists
                sd.setText("");
                errCatch = true;
            }
        }
    }

    public void ErrTester(JFXTextField textField) {
        errCatch = formValidation.isNameCor(textField.getText());
        if(errCatch == false){
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Name");
        } else{
            textField.setFocusColor(GREEN);
            textField.setPromptText("Name");
        }
    }

    public void setName(String name){
        adminName = name;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = new DAO();
        db = new dbConnection();
        sDatePicker.setValue(LocalDate.now());
        //eDatePicker.setValue(LocalDate.now());
        sDate = sDatePicker.getValue();
        //eDate = eDatePicker.getValue();
        driveName.textProperty().addListener(e -> ErrTester(driveName));
        //try {
            //Connection connection = db.connect();
            //drives = dao.getAllDrives();
            //connection.close();
        //} catch (SQLException e) {
        //    e.printStackTrace();
        //}

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*if(drives != null) {
            drivesList.getItems().clear();
            for (int i = 0; i < drives.size(); i++) {
                drivesList.getItems().add(drives.get(i).start);
                System.out.println(drives.get(i).start);
            }
        }*/
        /*drivesList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                //try {
                    //Connection connection = db.connect();
                    System.out.println("clicked on " + drivesList.getSelectionModel().getSelectedItem());
                    String start = drivesList.getSelectionModel().getSelectedItem();
                    selectD = new drive();
                    selectD = drives.get(drivesList.getSelectionModel().getSelectedIndex());
                    //selectD = drives.get(drivesList.getSelectionModel().getSelectedIndex());

                    //sDateLabel.setText(selectD.start);
                    //eDateLabel.setText(selectD.end);
                    //connection.close();
                //} catch (SQLException e) {
                //    e.printStackTrace();
                //}
            }
        });*/


        drivesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                System.out.println("clicked on " + drivesList.getSelectionModel().getSelectedItem());
                String start = drivesList.getSelectionModel().getSelectedItem();
                selectD = new drive();
                selectD = drives.get(drivesList.getSelectionModel().getSelectedIndex());

                fullname.setText("---");
                email.setText("---");
                pnumber.setText("---");
                hours.setText("---");
                contactNa.setText("---");
                contactNu.setText("---");
                supervisor.setText("---");


                records = dao.phpgetAllDriveDays(selectD);
                //connection.close();
                //} catch (SQLException e) {
                //    e.printStackTrace();
                //}
                //}

                daysList.getItems().clear();
                volList.getItems().clear();
                for(int i = 0; i < records.size(); i++){
                    daysList.getItems().add(records.get(i).operationDayDate);
                    System.out.println(records.get(i).operationDayDate);
                }

                //volRecords = dao.phpgetAllDriveRecords(selectD.id);
                //connection.close();
                //volList.getItems().clear();
                //for(int i = 0; i < volRecords.size(); i++){
                //    volList.getItems().add(volRecords.get(i).date + " " +volRecords.get(i).fname + " " + volRecords.get(i).lname);
                //}
                //} catch (SQLException e) {
                //    e.printStackTrace();
                //}
            }
        });

        daysList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(daysList.getSelectionModel().getSelectedItem() != null) {
                    fullname.setText("---");
                    email.setText("---");
                    pnumber.setText("---");
                    hours.setText("---");
                    contactNa.setText("---");
                    contactNu.setText("---");
                    supervisor.setText("---");

                    System.out.println("clicked on " + daysList.getSelectionModel().getSelectedItem());
                    opDay = daysList.getSelectionModel().getSelectedItem();

                    //driveday.setText("Volunteers for "  +opDay);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    selDay = LocalDate.parse(opDay, formatter);

                    //try {
                    //Connection connection = db.connect();
                    volRecords = dao.phpgetAllDriveDayRecords(opDay);
                    //connection.close();
                    volList.getItems().clear();
                    for (int i = 0; i < volRecords.size(); i++) {
                        volList.getItems().add(volRecords.get(i).fname + " " + volRecords.get(i).lname);
                    }
                    //} catch (SQLException e) {
                    //    e.printStackTrace();
                    //}
                }
            }
        });

        volList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(volList.getSelectionModel().getSelectedItem() != null) {
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
                    supervisor.setText("Supervisor:" + selUser.supervisor);

                    contribution = selUser.hoursCon;
                }
            }
        });

    }

    @FXML
    public  void back() throws IOException {
        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = (Pane) loader.load(getClass().getClassLoader().getResource("admin/newAdmin.fxml"));

        //attach admincontroller to asmin fxml
        adminController ac =(adminController)loader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/admin/adminStyle.css");
        userStage.setScene(scene);
        // userStage.setTitle(username.getText()+"[ "+ "Admin"+ " ]");
        userStage.setTitle("ADMIN");
        userStage.setResizable(false);
        userStage.show();
        close();
    }

    @FXML
    public void addDay()throws IOException{

        if(selectD.id != 0) {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = null;
            root = (Pane) loader.load(getClass().getResource("/drivedays/addDay.fxml").openStream());
            addDay ac = (addDay) loader.getController();
            ac.setDrive(selectD, selDay, selUser, contribution, adminName);
            //ac.setDrive(selectD);

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Add Drive");
            userStage.show();
            close();
        }else {
            AlertBox.display("ERROR", "Please select a drive to add records!");
            //Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a drive to add records!", ButtonType.OK);
            //alert.showAndWait();
        }
    }

    @FXML
    public void delete() throws IOException, SQLException {

        if(selUser.id != 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete" + selUser.fname + " " + selUser.lname + "'s record?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {//delete
                dao.phpDeleteRecord(selUser);
                AlertBox.display("DELETE", "Record deleted!");
                loadData();
            }
        }else {AlertBox.display("ERROR", "Please select a record to delete!");

            //Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a drive to add records!", ButtonType.OK);
            //alert.showAndWait();
        }
    }

    public static void main(String[] args){
        launch(args);
    }

    public void close(){

        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    public void closeFOrAddDay(){

        Stage stage = (Stage) addRecord.getScene().getWindow();
        stage.close();
    }

}
