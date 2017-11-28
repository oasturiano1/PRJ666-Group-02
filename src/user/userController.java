package user;

import database.dbConnection;
import database.userObject;
import drives.drivesController;
import gui.LoginApp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class userController extends Application implements Initializable{

    //dbConnection db;

    userObject user;

    @FXML Labeled temail;
    @FXML Labeled tnumber;
    @FXML Labeled ttotalhours;
    @FXML Labeled ttotalsigned;
    @FXML Labeled cname;
    @FXML Labeled cnumber;

    @FXML private javafx.scene.control.Button sgnout;

    //private Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception  {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("userFXML.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("USER PROFILE");
        primaryStage.show();

        //stage = primaryStage;
    }

    @FXML
    public void editUser() throws IOException {

        System.out.print("PRESSED!");
        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = (Pane) loader.load(getClass().getResource("/user/userEdit.fxml").openStream());
        UserEdit ac =(UserEdit) loader.getController();
        ac.setUser(user);
        //ac.setDB(db);

        Scene scene = new Scene(root);
        userStage.setScene(scene);
        userStage.setTitle("EDIT PROFILE");
        userStage.show();
        Stage stage = (Stage) sgnout.getScene().getWindow();
        stage.close();
        //Stage stage = (Stage) sgnout.getScene().getWindow();
        //stage.close();

        /*System.out.print("PRESSED!");
        try {
            System.out.print("PRESSED!");
            replaceScene("userEdit.fxml");
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }*/

    }


    public void setUser(userObject userobj){
        user = userobj;
        System.out.print("User set!");
        temail.setText(user.email);
        tnumber.setText(user.phone);
        ttotalhours.setText(user.hourstotal);
        ttotalsigned.setText(user.hourssigned);
        cname.setText(user.ename);
        cnumber.setText(user.ephone);
    }

    //public void setDB (dbConnection db){
    //    this.db = db;
    //}

    @FXML
    public void close() throws IOException {

        //OPEN LOGIN HERE
        /*
        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = (Pane) loader.load(getClass().getResource("/gui/login.fxml").openStream());
        LoginApp ac =(LoginApp) loader.getController();

        Scene scene = new Scene(root);
        userStage.setScene(scene);
        userStage.setTitle("EDIT PROFILE");
        userStage.show();*/

        Stage stage = (Stage) sgnout.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //db = new dbConnection();

    }

/*
    private Parent replaceScene(String fxml) throws Exception {

        Parent page = (Parent) FXMLLoader.load(this.getClass().getResource(fxml),null, new JavaFXBuilderFactory());
        Scene scene = stage.getScene();
        if(scene == null){
            //scene = new Scene(page, 700, 450);
            //scene.getStylesheets().add(this.getClass().getResource("demo.css").toExternalForm());
            //stage.setScene(scene);
        }else{
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }*/
}
