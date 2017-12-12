package volunteerdays;

import database.DAO;
import database.dbConnection;
import database.userObject;
import drives.drive;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import records.record;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class addVolunteers extends Application {

    @FXML
    ListView<String> volunteerList;

    @FXML
    Labeled range;

    List<userObject> vols = new ArrayList();

    String opDay;

    public static final Pattern valDate =
            Pattern.compile("[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}");

    dbConnection db;

    DAO dao;

    @FXML
    private javafx.scene.control.TextField hoursCont;

    //@FXML
    //private javafx.scene.control.TextField dayDate;

    userObject sel = new userObject();

    @FXML
    private javafx.scene.control.TextField volSearch;

    @FXML
    Labeled error;

    int did;

    drive selectD;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("addVolunteers.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ADD DAY");
        primaryStage.show();

        volunteerList.setEditable(true);
    }

    public void setAddVol(String select, drive sd){
        dao = new DAO();
        db = new dbConnection();
        opDay = select;
        selectD = sd;
        this.db = db;
        //try {
            //Connection connection = db.connect();
            vols = dao.phpgetAllVolunteers();
            //connection.close();
        //} catch (SQLException e) {
        //    e.printStackTrace();
        //}
        range.setText("Add volunteers to operation day " + opDay);

        for(int i = 0; i < vols.size(); i++){

            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            //Date d = drives.get(i).start;
            //System.out.println("Working?");
            //System.out.println(dateFormat.format(d));

            //drives.get(i);
            volunteerList.getItems().add(vols.get(i).fname + " " + vols.get(i).lname);




            //drivesList.get

        }

        volunteerList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + volunteerList.getSelectionModel().getSelectedItem());
                sel = vols.get(volunteerList.getSelectionModel().getSelectedIndex());
            }
        });

    }

    @FXML
    public void search(){

        volunteerList.getItems().clear();
        vols.clear();
        //try {
            //Connection connection = db.connect();
            if(volSearch.getText().compareTo("") == 0){
                vols = dao.phpgetAllVolunteers();
                for(int i = 0; i < vols.size(); i++){
                    volunteerList.getItems().add(vols.get(i).fname + " " + vols.get(i).lname);
                }
            }else{

                String name = volSearch.getText();

                String[] sp = new String[2];

                sp = name.split(" ");

                if(sp.length == 1) {
                    vols = db.searchVols(sp[0], " ");
                }else if(sp.length == 2){
                    vols = db.searchVols(sp[0], sp[1]);
                }

                for(int i = 0; i < vols.size(); i++){
                    volunteerList.getItems().add(vols.get(i).fname + " " + vols.get(i).lname);
                }
            }
            //connection.close();
        //} catch (SQLException e) {
        //    e.printStackTrace();
        //}
    }

    @FXML
    public void addVols(){
        //String date = "";

       // date = dayDate.getText();

        if(hoursCont.getText().compareTo("") != 0){
            boolean smatch = true;
            boolean ematch = true;

            //Matcher matcher = valDate.matcher(date);

            //if( matcher.matches()){// MAKE SURE DATES ARE WITHIN BOUNDS AND THERE ARE NO DUPLICATES

                record r = new record();
                System.out.println("DATES ARE OKAY");


                String dstartd = selectD.start;
                //String daydate = dayDate.getText().toString();
                //sel;
                r.driveStartDate = selectD.start;
                r.operationDayDate = opDay;
                r.driveId = selectD.id;
                r.volunteerId = sel.id;
                r.hoursContributed = Integer.parseInt(hoursCont.getText().toString());

                //if(db.recordExists(r)) {
                   //record already exists
                //}else{
                //try {
                    //Connection connection = db.connect();
                    boolean success = dao.phpAddRecord(r);
                    if(success){
                        error.setText("Drive Added!");
                    }else {
                        error.setText("Drive Could Not Be Added!");
                    }
                    //connection.close();
                //} catch (SQLException e) {
                //    e.printStackTrace();
                //}
                //}
            //}

            //System.out.println(date);

        }
    }
}