package drives;

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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class drivesController extends Application implements Initializable {

    @FXML
    ListView<String> drivesList;

    @FXML
    Labeled sDate;

    @FXML
    Labeled eDate;


    public drive selectD = new drive();

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
    public void addDrive()throws IOException{
        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        root = (Pane) loader.load(getClass().getResource("/drives/addDrive.fxml").openStream());
        addDrive ac = (addDrive) loader.getController();
        //ac.setDB(db);
        //ac.setDrive(selectD);

        Scene scene = new Scene(root);
        userStage.setScene(scene);
        userStage.setTitle("Add Drive");
        userStage.show();
    }

    @FXML
    public void viewDriveDays() throws IOException {
        System.out.println("PRESSING!");
        if(selectD.id != 0) {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = null;
            root = (Pane) loader.load(getClass().getResource("/drivedays/driveDays.fxml").openStream());
            driveDays ac = (driveDays) loader.getController();
            ac.setDrive(selectD);
            //ac.setDrive(selectD);

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Drive Days");
            userStage.show();
        }
    }

    @FXML
    public void loadData() throws SQLException {
        Connection connection = db.connect();
        drivesList.getItems().clear();
        drives = db.getDrives();
        for(int i = 0; i < drives.size(); i++){
            drivesList.getItems().add(drives.get(i).start);
            System.out.println(drives.get(i).start);
        }
        connection.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db = new dbConnection();

        try {
            Connection connection = db.connect();
            drives = db.getDrives();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        drivesList.getItems().clear();
        for(int i = 0; i < drives.size(); i++){
            drivesList.getItems().add(drives.get(i).start);
            System.out.println(drives.get(i).start);
        }

        drivesList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    Connection connection = db.connect();
                    System.out.println("clicked on " + drivesList.getSelectionModel().getSelectedItem());
                    String start = drivesList.getSelectionModel().getSelectedItem();
                    selectD = new drive();
                    selectD = db.getDrive(start);

                    sDate.setText(selectD.start);
                    eDate.setText(selectD.end);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void main(String[] args){
        launch(args);
    }


}
