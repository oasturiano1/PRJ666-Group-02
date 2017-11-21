package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginApp extends Application {
    private Session session;
    private Controller controller;

    public static void main(String[] args){
        launch(args);
    }

    //stage is a top level container that host a scene
    public void start(Stage stage) throws Exception {
        session = new Session();
        controller = new Controller();

        //if(session.loggedin()){
        //   try {
        //        controller.adminLogin();
        //    } catch (IOException e) {
        //        e.printStackTrace();
        //    }
        //} else{
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("login.css");
            stage.setScene(scene);
            stage.setTitle("LOGIN");
            stage.setResizable(false);
            stage.show();
        //}
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        // Save file
    }


}
