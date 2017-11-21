package volunteerdays;

import database.dbConnection;
import database.userObject;
import drivedays.driveDays;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class volunteerDays   extends Application implements Initializable {

    @FXML
    Labeled drivestart;

    @FXML
    ListView<String> volList;

    @FXML
    Labeled fullname;

    @FXML
    Labeled email;

    @FXML
    Labeled pnumber;

    @FXML
    Labeled hours;

    String opDay;

    drive selectD;

    /*@FXML
    ListView<String> drivedateslist;

    @FXML
    ListView<String> studentcontributionlist;*/

    dbConnection db;
    List<userObject> records = new ArrayList();



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("drives.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DRIVES");
        primaryStage.show();


        volList.setEditable(true);
        //drivedateslist.setEditable(true);

        //dateFormat.format(drives.get(i).start);



    }

    @FXML
    public void addVolunteer() throws IOException {
        System.out.println("PRESSING!");
        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        root = (Pane) loader.load(getClass().getResource("/volunteerdays/addVolunteers.fxml").openStream());
        addVolunteers ac = (addVolunteers) loader.getController();
        ac.setDB(db, opDay, selectD);
        //ac.setDrive(selectD);

        Scene scene = new Scene(root);
        userStage.setScene(scene);
        userStage.setTitle("Drive Days");
        userStage.show();

    }

    public void setDB (dbConnection db, String op, drive sd){
        opDay = op;
        selectD = sd;
        this.db = db;
        drivestart.setText("Volunteers for "  + opDay);

        records = db.getVolunteersByDate(opDay);
        volList.getItems().clear();
        for(int i = 0; i < records.size(); i++){

            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            //Date d = drives.get(i).start;
            //System.out.println("Working?");
            //System.out.println(dateFormat.format(d));

            //drives.get(i);

            volList.getItems().add(records.get(i).fname + " " + records.get(i).lname);
            //System.out.println(records.get(i).operationDayDate);



            //drivesList.get

        }

        volList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + volList.getSelectionModel().getSelectedItem());
                System.out.println(records.get(volList.getSelectionModel().getSelectedIndex()).fname);
                userObject sel = new userObject();

                sel = records.get(volList.getSelectionModel().getSelectedIndex());

                fullname.setText("Full name:" + sel.fname + " " + sel.lname);
                email.setText("Email:" + sel.email);
                pnumber.setText("Phone number:" + sel.phone);
                hours.setText("Hours Contributed:" + sel.hoursCon);

            }
        });
    }

    @FXML
    public void loadData(){
        records = db.getVolunteersByDate(opDay);
        volList.getItems().clear();
        for(int i = 0; i < records.size(); i++){
            volList.getItems().add(records.get(i).fname + " " + records.get(i).lname);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static void main(String[] args){
        launch(args);
    }


}
