package signup;

import admin.formValidation;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DAO;
import database.dbConnection;
import database.loginType;
import database.userObject;
import gui.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class SignUp extends Application implements Initializable{

    DAO dao;

    dbConnection db;

    @FXML
    private Labeled error;

    @FXML
    private JFXTextField fname;

    @FXML
    private JFXTextField lname;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField number;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private JFXPasswordField passconf;

    @FXML
    private JFXTextField emname;

    @FXML
    private JFXTextField emnumber;

    @FXML
    private Button signupbtn;

    @FXML private javafx.scene.control.Button cancel;

    private Boolean errCatch = false;

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
        newUser.hourstotal = "0.0";
        newUser.hourssigned = "0.0";
        newUser.ename = emname.getText();
        newUser.ephone = emnumber.getText();
        Connection connection = db.connect();

        if(newUser.fname.isEmpty()||
                newUser.lname.isEmpty()||
                newUser.email.isEmpty()||
                newUser.phone.isEmpty()||
                newUser.phone.isEmpty()||
                newUser.pass.isEmpty()||
                newUser.ename.isEmpty()||
                newUser.ephone.isEmpty()){
            errCatch = false;
        }


        if (dao.phpEmailExists(email.getText())) {
            email.setFocusColor(RED);
            email.setPromptText("Invalid - Email Already Used");
            errCatch = false;
        }

        //need to add more constraints

        if(errCatch){

            //boolean success = db.newUser(newUser);
            boolean success = dao.phpAddData(newUser);
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
        }
        connection.close();
    }

    public void ErrTester(JFXTextField textField) {
        errCatch = formValidation.isNameCor(textField.getText());
        if(errCatch == false){
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Name");
        } else{
            textField.setFocusColor(GREEN);
            textField.setPromptText("Name");
        }

        /*textField.setOnKeyTyped(event -> {
            int max = 15;
            if ((fname.getText().length() >= max)) {
                textField.setFocusColor(RED);
                textField.setPromptText("Invalid Name");
                 event.consume();

            } else {
                textField.setFocusColor(GREEN);
                textField.setPromptText("Name");
            }
        });*/
    }

    public void dobTester(JFXTextField textField) {
        errCatch = formValidation.isDate(textField.getText());
        if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Date");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("YYYY-MM-DD");
        }

    }

    public void phoneTester(JFXTextField textField) {
        errCatch = formValidation.isPhone(textField.getText());
        if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Phone Number");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("XXX-XXX-XXXX or XXXXXXXXXX");
        }

    }

    public void postTester(JFXTextField textField) {
        errCatch = formValidation.isPost(textField.getText());
        if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("l6a34");
        }

    }

    public void mailTester(JFXTextField textField){
        errCatch = formValidation.isMail(textField.getText());

        //Connection connection = db.connect();
        //boolean emailExists = db.emailExists(email.getText());
        //connection.close();

        if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Email Address");
        }else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("me@someMall.com");
        }

    }

    public void passTester(JFXPasswordField textField, JFXPasswordField originConf) {
        errCatch = formValidation.isPassCor(textField.getText());
        String o1 = textField.getText().toString();
        String o2 = originConf.getText().toString();

        if(o1.compareTo(o2) != 0){
            originConf.setFocusColor(RED);
            originConf.setPromptText("Invalid - Password does not match!");
        }
        if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Password");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("Password");
        }

    }

    public void passConfTester(JFXPasswordField origin, JFXPasswordField textField) {
        errCatch = formValidation.isPassCor(textField.getText());
        String o1 = origin.getText().toString();
        String o2 = textField.getText().toString();

        //errCatch = formValidation.isPassCor(textField.getText());

        if(o1.compareTo(o2) != 0){
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid - Password does not match!");
        }else if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Password");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("Password");
        }

    }

    public static void main(String[] args){
        launch(args);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = new DAO();
        db = new dbConnection();
        fname.setLabelFloat(true);
        lname.setLabelFloat(true);
        email.setLabelFloat(true);
        number.setLabelFloat(true);
        pass.setLabelFloat(true);
        passconf.setLabelFloat(true);
        emname.setLabelFloat(true);
        emnumber.setLabelFloat(true);

        fname.textProperty().addListener(e -> ErrTester(fname));

       fname.setOnKeyTyped(event -> {
            int maxChar = 10;
            if(fname.getText().length() >= maxChar){
                event.consume();
                fname.setFocusColor(RED);
                fname.setPromptText("Wroung Value Length");
            }
        });
        lname.textProperty().addListener(e -> ErrTester(lname));
        lname.setOnKeyTyped(event -> {
            int maxChar = 10;
            if(lname.getText().length() >= maxChar){
                event.consume();
                lname.setFocusColor(RED);
                lname.setPromptText("Wroung Value Length");
            }
        });
        email.textProperty().addListener(M -> mailTester(email));
        //email.textProperty().addListener(e -> {//This contnains a db query to check if email already exists
        //    try {
        //        mailTester(email);
         //   } catch (SQLException e1) {
         //       e1.printStackTrace();
         //   }
        //});
        number.textProperty().addListener(e -> phoneTester(number));
        pass.textProperty().addListener(e -> passTester(pass,passconf));
        //passconf.textProperty().addListener(e -> passTester(passconf));
        passconf.textProperty().addListener(e -> passConfTester(pass,passconf));
        emname.textProperty().addListener(e -> ErrTester(emname));
        emnumber.textProperty().addListener(e -> phoneTester(emnumber));
    }

    @FXML
    public void close() throws IOException {

        //OPEN LOGIN HERE

        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = (Pane) loader.load(getClass().getResource("/gui/login.fxml").openStream());
        Controller ac =(Controller) loader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("login.css");
        userStage.setScene(scene);
        //userStage.setTitle("EDIT PROFILE");
        userStage.show();

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
