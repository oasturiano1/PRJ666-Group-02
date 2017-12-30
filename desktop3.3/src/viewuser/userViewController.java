package viewuser;

import database.dbConnection;
import database.userObject;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

public class userViewController extends Application {

    @FXML Labeled fullname;
    @FXML Labeled temail;
    @FXML Labeled tnumber;
    @FXML Labeled ttotalhours;
    @FXML Labeled ttotalsigned;
    @FXML Labeled cname;
    @FXML Labeled cnumber;

    dbConnection db;

    userObject user;

    //private Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("userView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("USER PROFILE");
        primaryStage.show();

        //stage = primaryStage;
    }

    public void setDB (dbConnection db){
        this.db = db;
    }

    public void setUser(userObject userobj){
        user = userobj;
        System.out.print("User set!");
        temail.setText(user.email);
        tnumber.setText(user.phone);
        cname.setText(user.ename);
        cnumber.setText(user.ephone);
        ttotalhours.setText(user.hourstotal);
        ttotalsigned.setText(user.hourssigned);
    }
}
