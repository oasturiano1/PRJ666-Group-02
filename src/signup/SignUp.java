package signup;

import database.dbConnection;
import database.loginType;
import database.userObject;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user.userController;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUp extends Application implements Initializable{

    dbConnection db;

    @FXML
    private Labeled error;

    @FXML
    private javafx.scene.control.TextField fname;

    @FXML
    private javafx.scene.control.TextField lname;

    @FXML
    private javafx.scene.control.TextField email;

    @FXML
    private javafx.scene.control.TextField number;

    @FXML
    private javafx.scene.control.TextField pass;

    @FXML
    private javafx.scene.control.TextField passconf;

    @FXML
    private javafx.scene.control.TextField emname;

    @FXML
    private javafx.scene.control.TextField emnumber;

    @FXML
    private Button signupbtn;

    public void start(Stage stage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("signup.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SIGN UP");
        stage.show();
    }

    public void signUpMethod() throws SQLException {
        userObject newUser = new userObject();

        newUser.fname = fname.getText();
        newUser.lname = lname.getText();
        newUser.email = email.getText();
        newUser.phone = number.getText();
        newUser.pass = pass.getText();
        newUser.ename = emname.getText();
        newUser.ephone = emnumber.getText();
        Connection connection = db.connect();
        //need to add more constraints
        if(newUser.fname.isEmpty()||
                newUser.lname.isEmpty()||
                newUser.email.isEmpty()||
                newUser.phone.isEmpty()||
                newUser.phone.isEmpty()||
                newUser.pass.isEmpty()||
                newUser.ename.isEmpty()||
                newUser.ephone.isEmpty()){
            this.error.setText("All fields are required!");
        }else if(!newUser.checkEmail()) {
            this.error.setText("Email format is wrong!");
        }else if(!newUser.checkPhone()) {
            this.error.setText("Phone Number format is wrong!");
        }else if(!(pass.getText().equals(passconf.getText()))){//validation
            this.error.setText("Passwords do not match!");
        }else if(!newUser.checkContactPhone()) {
            this.error.setText("Contact Phone Number format is wrong!");
        }else{//if all is valid
            if(db.emailExists(email.getText())){
                this.error.setText("email alraedy exists");
            }else{
                System.out.println("email is valid");
                //String[] name = fname.getText().split(" ");
                System.out.println(fname+" "+lname);

                boolean success = db.newUser(newUser);


                if(success){
                    try {
                        Stage userStage = new Stage();
                        FXMLLoader loader = new FXMLLoader();
                        Pane root = (Pane)loader.load(getClass().getResource("/user/userFXML.fxml").openStream());


                        //attach usercontroller to user fxml
                        userController uc =(userController)loader.getController();
                        uc.setUser(newUser);//sets user object in next scene
                        //uc.setDB(db);// IMPORTANT- NEED TO CLOSE

                        Scene scene = new Scene(root);
                        scene.getStylesheets().add("admin/adminStyle.css");
                        userStage.setScene(scene);
                        userStage.setTitle("User DashBoard");
                        userStage.show();


                        Stage stage = (Stage) signupbtn.getScene().getWindow();
                        stage.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //db.newUser(name[0],name[1],email.getText(),number.getText(),pass.getText(),emname.getText(),emnumber.getText());
                this.error.setText("User created!");

            }
            connection.close();
            //this.error.setText("");
        }
    }

    public static void main(String[] args){
        launch(args);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db = new dbConnection();
    }
}
