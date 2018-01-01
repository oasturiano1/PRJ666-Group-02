package drivedays;

import com.jfoenix.controls.JFXDatePicker;
import database.DAO;
import database.dbConnection;
import database.userObject;
import drives.drive;
import drives.drivesController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class addDay extends Application implements Initializable{

    @FXML
    ListView<String> volunteerList;

    @FXML
    Labeled range;

    List<userObject> vols = new ArrayList();

    drive selectD;

    public static final Pattern valDate =
            Pattern.compile("[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}");

    dbConnection db;

    DAO dao;

    @FXML
    private javafx.scene.control.TextField hoursCont;

    @FXML
    private javafx.scene.control.TextField dayDate;

    @FXML
    JFXDatePicker dateDayPicker;

    @FXML
    private javafx.scene.control.TextField volSearch;

    @FXML
    private Labeled error;

    @FXML
    private Labeled error2;

    private LocalDate sDate;

    private LocalDate eDate;

    @FXML
    private Button back;

    userObject sel = new userObject();

    private LocalDate day;

    boolean errCatch = false;

    int did;

    private String adminName;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("addDay.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ADD DAY");
        primaryStage.show();
        volunteerList.setEditable(true);




    }

    public void setDrive(drive selectDrive, LocalDate selectDay, userObject selectUser, String con, String adminName){
        int selectedVolIndex = 0;
        dao = new DAO();
        db = new dbConnection();
        sel = selectUser;
        selectD = selectDrive;
        this.adminName = adminName;

        hoursCont.setText(con);

        if(selectDay != null)
            dateDayPicker.setValue(selectDay);
        else
            dateDayPicker.setValue(LocalDate.now());
        day = dateDayPicker.getValue();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //formatter = formatter.withLocale( Locale.CANADA );
        sDate = LocalDate.parse(selectD.start, formatter);
        //eDate = LocalDate.parse(selectD.end, formatter);

        //try {
            //Connection connection = db.connect();

            vols = dao.phpgetAllVolunteers();
            //connection.close();
        //} catch (SQLException e) {
        //    e.printStackTrace();
        //}
        ObservableList<String> list = FXCollections.observableArrayList();
        //volunteerList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        range.setText("Create new record for " + selectD.name + " " + selectD.start);

        for(int i = 0; i < vols.size(); i++){

            //Focus on selected user
            if(selectUser != null){
                if(vols.get(i).email.equals(selectUser.email)){
                    selectedVolIndex = i;


                }
            }


            volunteerList.getItems().add(vols.get(i).fname + " " + vols.get(i).lname);

        }
        if(selectedVolIndex != 0){
            volunteerList.getSelectionModel().select(selectedVolIndex);
            volunteerList.scrollTo(selectedVolIndex);
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
                    vols = dao.phpgetVolunteer(sp[0], " ");
                }else if(sp.length == 2){
                    vols = dao.phpgetVolunteer(sp[0], sp[1]);
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

    public void getDay(){
        day = dateDayPicker.getValue();
    }

    @FXML
    public void addNewDay() throws SQLException {

        dateCompare(error);
        //numberChecker(error2);


        if(errCatch) {
            String date = "";
            date = day.toString();
            //Connection connection = db.connect();
            if (date.compareTo("") != 0) {
                boolean smatch = true;
                boolean ematch = true;

                Matcher matcher = valDate.matcher(date);

                if (matcher.matches()) {// MAKE SURE DATES ARE WITHIN BOUNDS AND THERE ARE NO DUPLICATES

                    record r = new record();
                    System.out.println("DATES ARE OKAY");


                    String dstartd = selectD.start;
                    String daydate = dateDayPicker.getValue().toString();
                    //sel;
                    //r.operationRecordId = selectD.
                    r.driveStartDate = selectD.start;
                    r.operationDayDate = daydate;
                    r.driveId = selectD.id;
                    r.volunteerId = sel.id;
                    r.hoursContributed = Double.parseDouble(hoursCont.getText().toString());


                    record recExists = dao.getRecord(r);




                    if(recExists != null){

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Overwrite existing record?", ButtonType.YES, ButtonType.NO);
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {//if overwrite
                            double prev = recExists.hoursContributed;
                            double diff = r.hoursContributed - prev;

                            userObject prevRec = new userObject();
                            prevRec = dao.phpGetUserById(recExists.volunteerId);

                            double ht = Double.parseDouble(prevRec.hourstotal) + diff;



                            //u = dao.get

                            //double newTotal = r.hoursContributed + diff;



                            boolean success = dao.phpUpdateRecord(r,adminName);
                            dao.phpUpdateHoursTotal(r.volunteerId,ht);
                            if (success) {
                                error.setText("Record Updated!");
                            } else {
                                error.setText("Record Could Not Be Updated!");
                            }
                        }

//TODO THIS


                    }else {
                        boolean success = dao.phpAddRecord(r,adminName);
                        if (success) {
                            error.setText("Record Added!");
                        } else {
                            error.setText("Record Could Not Be Added!");
                        }
                    }

                }

                System.out.println(date);

            }
            //connection.close();
        }


    }


    public void dateCompare(Labeled sd) throws SQLException {//TODO check if works
        errCatch = false;
        //Connection connection = db.connect();
        //connection.close();

        if(day == null){
            sd.setText("Date is required!!");
        }else if(day.isBefore(sDate)) {
            sd.setText("Date selected is out of drive scope!");
            errCatch = false;
        }else {//check if dates already exists
            sd.setText("");
            errCatch = true;
        }

    }

    public void numberChecker(Labeled sd){
        errCatch = false;
        if(hoursCont.getText().compareTo("") != 0){
            try{
                Double i = Double.parseDouble(hoursCont.getText());

                if(i > 24 || i < 0){
                    errCatch = false;
                    sd.setText("Number must be between 0 and 24!");
                }else {
                    errCatch = true;
                    sd.setText("");
                }
            }catch (NumberFormatException e){
                errCatch = false;
                sd.setText("Not a valid number!");
            }
        }else{
            sd.setText("Hours is required!");
        }
    }

    @FXML public void back() throws IOException {
        System.out.print("PRESSED!");
        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = (Pane) loader.load(getClass().getResource("/drives/drives.fxml").openStream());
        drivesController ac =(drivesController) loader.getController();

        Scene scene = new Scene(root);
        userStage.setScene(scene);
        userStage.setTitle("Drives");
        userStage.show();

        close();
    }

    public void close(){

        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*dateDayPicker.valueProperty().addListener(E -> {
            try {
                dateCompare(error);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });*/
        hoursCont.textProperty().addListener(E -> numberChecker(error2));



    }
}