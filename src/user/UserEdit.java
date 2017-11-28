package user;

import com.sun.org.apache.xml.internal.security.Init;
import database.dbConnection;
import database.loginType;
import database.userObject;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserEdit extends Application implements Initializable{

    dbConnection db;

    userObject user;

    @FXML TextField temail;
    @FXML TextField tnumber;
    @FXML PasswordField pass;
    @FXML PasswordField passconf;
    @FXML TextField cname;
    @FXML TextField cnumber;


    @FXML private javafx.scene.control.Button cncl;



    /*public UserEdit(){
        db = new dbConnection();

    }*/

    public void setDB (dbConnection db){
        this.db = db;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        db = new dbConnection();
        //db.isDatabaseCon();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("userEdit.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("USER PROFILE EDIT");
        primaryStage.show();
    }

    @FXML
    public void close() throws IOException {
        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = (Pane) loader.load(getClass().getResource("/user/userFXML.fxml").openStream());
        userController ac =(userController) loader.getController();
        ac.setUser(user);

        Scene scene = new Scene(root);
        userStage.setScene(scene);
        userStage.setTitle("USER PROFILE");

        userStage.show();
        Stage stage = (Stage) cncl.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void save() throws SQLException {

        Connection connection = db.connect();

        userObject editUser = new userObject();

        editUser.email = temail.getText();
        editUser.phone = tnumber.getText();
        editUser.pass = pass.getText();
        editUser.ename = cname.getText();
        editUser.ephone = cnumber.getText();

        Boolean val  = true;

        if(pass.getText().compareTo(passconf.getText()) != 0){
            val = false;
        }

        if(user.email.compareTo(editUser.email) != 0){//if email is changed
            if(db.emailExists(editUser.email)){//if new email already exists do not change
                val = false;
            }
        }
        if(!user.checkContactPhone()){
            val= false;
        }


        if(val) {//if all is valid, change user
            db.editUser(editUser, user);
            user = editUser;//set user to new user
        }
        connection.close();
    }

    public void setUser(userObject userobj){
        user = userobj;
        System.out.print("User set!");
        temail.setText(user.email);
        tnumber.setText(user.phone);
        cname.setText(user.ename);
        cnumber.setText(user.ephone);
    }

}
