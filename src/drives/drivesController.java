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
        ac.setDB(db);
        //ac.setDrive(selectD);

        Scene scene = new Scene(root);
        userStage.setScene(scene);
        userStage.setTitle("Add Drive");
        userStage.show();
    }

    @FXML
    public void viewDriveDays() throws IOException {
        System.out.println("PRESSING!");
        if(sDate.getText() != "---") {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = null;
            root = (Pane) loader.load(getClass().getResource("/drivedays/driveDays.fxml").openStream());
            driveDays ac = (driveDays) loader.getController();
            ac.setDB(db, selectD);
            //ac.setDrive(selectD);

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Drive Days");
            userStage.show();
        }
    }

    public void setDB (dbConnection db){
        this.db = db;
        drives = db.getDrives();

        for(int i = 0; i < drives.size(); i++){
            drivesList.getItems().add(drives.get(i).start);
            System.out.println(drives.get(i).start);

        }

        drivesList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + drivesList.getSelectionModel().getSelectedItem());
                String start = drivesList.getSelectionModel().getSelectedItem();
                selectD = new drive();
                selectD = db.getDrive(start);

                sDate.setText(selectD.start);
                eDate.setText(selectD.end);
            }
        });


    }

    @FXML
    public void loadData(){
        drivesList.getItems().clear();
        drives = db.getDrives();
        for(int i = 0; i < drives.size(); i++){
            drivesList.getItems().add(drives.get(i).start);
            System.out.println(drives.get(i).start);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static void main(String[] args){
        launch(args);
    }


}
