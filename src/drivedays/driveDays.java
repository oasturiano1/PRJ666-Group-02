package drivedays;

import database.dbConnection;
import drives.addDrive;
import drives.drive;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class driveDays  extends Application implements Initializable {

    @FXML
    Labeled drivestart;

    @FXML
    ListView<String> daysList;

    drive selectD;

    String opDay = "";

    /*@FXML
    ListView<String> drivedateslist;

    @FXML
    ListView<String> studentcontributionlist;*/

    dbConnection db;
    List<record> records = new ArrayList();



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

    @FXML
    public void addDay()throws IOException{
        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        root = (Pane) loader.load(getClass().getResource("/drivedays/addDay.fxml").openStream());
        addDay ac = (addDay) loader.getController();
        ac.setDrive(selectD);
        //ac.setDrive(selectD);

        Scene scene = new Scene(root);
        userStage.setScene(scene);
        userStage.setTitle("Add Drive");
        userStage.show();
    }

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
        selectD = sd;
        drivestart.setText("Drive Days for "  +selectD.start + " drive");
        db = new dbConnection();



        try {
            Connection connection = db.connect();
            records = db.getRecordsByDrive(selectD.start);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        daysList.getItems().clear();
        for(int i = 0; i < records.size(); i++){

            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            //Date d = drives.get(i).start;
            //System.out.println("Working?");
            //System.out.println(dateFormat.format(d));

            //drives.get(i);

            daysList.getItems().add(records.get(i).operationDayDate);
            System.out.println(records.get(i).operationDayDate);

            //drivesList.get

        }

        daysList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + daysList.getSelectionModel().getSelectedItem());
                opDay = daysList.getSelectionModel().getSelectedItem();
            }
        });

    }

    /*public void setDB (dbConnection db, drive sd){
        selectD = sd;
        this.db = db;
        drivestart.setText("Drive Days for "  +selectD.start + " drive");

        records = db.getRecordsByDrive(selectD.start);
        daysList.getItems().clear();
        for(int i = 0; i < records.size(); i++){

            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            //Date d = drives.get(i).start;
            //System.out.println("Working?");
            //System.out.println(dateFormat.format(d));

            //drives.get(i);

            daysList.getItems().add(records.get(i).operationDayDate);
            System.out.println(records.get(i).operationDayDate);

            //drivesList.get

        }

        daysList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + daysList.getSelectionModel().getSelectedItem());
                opDay = daysList.getSelectionModel().getSelectedItem();
            }
        });

    }*/

    @FXML
    public void loadData(){

        records = db.getRecordsByDrive(selectD.start);
        daysList.getItems().clear();
        for(int i = 0; i < records.size(); i++){
            daysList.getItems().add(records.get(i).operationDayDate);
            System.out.println(records.get(i).operationDayDate);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }

    public static void main(String[] args){
        launch(args);
    }


}
