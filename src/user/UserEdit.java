package user;

import admin.AlertBox;
import admin.formValidation;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DAO;
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

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class UserEdit extends Application implements Initializable{

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

    @FXML Button cancel;

    userObject user;

    private Boolean errCatch = true,errCatch0 = false, errCatch1 = false, errCatch2 = false, errCatch3 = false, errCatch4 = false, errCatch5 = false, errCatch6 = false, errCatch7 = false;

    public void start(Stage stage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("signup.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SIGN UP");
        stage.show();
    }

    public void updateMethod() throws SQLException {

        ErrTester(fname);
        ErrTester(lname);
        mailTester(email);
        phoneTester(number);
        passTester(pass,passconf);
        passConfTester(pass,passconf);
        ErrTester(emname);
        phoneTester(emnumber);



        userObject newUser = new userObject();
        newUser.id = user.id;
        newUser.fname = fname.getText();
        newUser.lname = lname.getText();
        newUser.email = email.getText();
        newUser.phone = number.getText();
        newUser.pass = pass.getText();
        newUser.ename = emname.getText();
        newUser.ephone = emnumber.getText();
        newUser.hourstotal = user.hourstotal;
        newUser.hourssigned = user.hourssigned;
        //Connection connection = db.connect();

        if(newUser.fname.isEmpty()||
                newUser.lname.isEmpty()||
                newUser.email.isEmpty()||
                newUser.phone.isEmpty()||
                newUser.phone.isEmpty()||
                newUser.pass.isEmpty()||
                newUser.ename.isEmpty()||
                newUser.ephone.isEmpty()){
            errCatch = false;
            AlertBox.display("ERROR", "All fields are required!");
        }

        if(user.email.compareTo(email.getText())!=0) {//if email has changed
            /*if (dao.phpEmailExists(email.getText())) {
                email.setFocusColor(RED);
                email.setPromptText("Invalid - New Email Already Used");
                errCatch = false;
            }*/
            if (errCatch&&dao.phpEmailExists(email.getText())) {
                email.setFocusColor(RED);
                String t = email.getText().toString();
                if(email.getText().toString().compareTo("")!=0)
                    email.setPromptText("Invalid - Email Already Used");
                errCatch = false;
            }
        }
        //need to add more constraints

        if(errCatch&&errCatch0&&errCatch1&&errCatch2&&errCatch3&&errCatch4&&errCatch5&&errCatch6&&errCatch7){
            boolean success = dao.phpUpdateData(newUser);
            //boolean success = db.editUser(newUser, user);
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


                    Stage stage = (Stage) cancel.getScene().getWindow();
                    stage.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //connection.close();
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
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void reset(){
        fname.setText(user.fname);
        lname.setText(user.lname);
        email.setText(user.email);
        pass.setText(user.pass);
        passconf.setText(user.pass);
        number.setText(user.phone);
        emname.setText(user.ename);
        emnumber.setText(user.ephone);
    }

    public void setUser(userObject userobj){
        user = userobj;
        System.out.print("User set!");
        reset();
    }

    public void ErrTester(JFXTextField textField) { errCatch = formValidation.isNameCor(textField.getText());
        if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Name");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("Name");
        }

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

    public void fNameTester(JFXTextField textField) {
        errCatch0 = formValidation.isNameCor(textField.getText());
        if(errCatch0 == false){
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Name");
        } else{
            textField.setFocusColor(GREEN);
            textField.setPromptText("Name");
        }
    }

    public void lNameTester(JFXTextField textField) {
        errCatch1 = formValidation.isNameCor(textField.getText());
        if(errCatch1 == false){
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Name");
        } else{
            textField.setFocusColor(GREEN);
            textField.setPromptText("Name");
        }
    }


    public void mailTester(JFXTextField textField) throws SQLException {
        errCatch2 = formValidation.isMail(textField.getText());

        if(user.email.compareTo(email.getText().toString()) != 0){
            if (errCatch == false) {
                textField.setFocusColor(RED);
                textField.setPromptText("Invalid Email Address");
            }else {
                textField.setFocusColor(GREEN);
                textField.setPromptText("me@someMall.com");
            }
        }
    }

    public void phoneTester(JFXTextField textField) {
        errCatch3 = formValidation.isPhone(textField.getText());
        if (errCatch3 == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Phone Number");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("XXX-XXX-XXXX or XXXXXXXXXX");
        }

    }
    public void passTester(JFXPasswordField textField, JFXPasswordField originConf) {
        errCatch4 = formValidation.isPassCor(textField.getText());
        String o1 = textField.getText().toString();
        String o2 = originConf.getText().toString();

        if (errCatch4 == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Password");
            //errCatch4 = false;

        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("Password");

        }

        if(o1.compareTo(o2) != 0){
            originConf.setFocusColor(RED);
            originConf.setPromptText("Invalid - Password does not match!");
            errCatch4 = false;
        }else {
            originConf.setFocusColor(GREEN);
            originConf.setPromptText("Password");
            errCatch5 = true;
        }


    }

    public void passConfTester(JFXPasswordField origin, JFXPasswordField textField) {
        errCatch5 = formValidation.isPassCor(textField.getText());
        String o1 = origin.getText().toString();
        String o2 = textField.getText().toString();

        //errCatch = formValidation.isPassCor(textField.getText());

        if(o1.compareTo(o2) != 0){
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid - Password does not match!");
            errCatch5 = false;
        }else if (errCatch5 == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Password");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("Password");
            errCatch4 = true;
            //passTester(origin,textField);
        }

    }


    public void eNameTester(JFXTextField textField) {
        errCatch6 = formValidation.isNameCor(textField.getText());
        if(errCatch6 == false){
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid Name");
        } else{
            textField.setFocusColor(GREEN);
            textField.setPromptText("Name");
        }
    }

    public void ePhoneTester(JFXTextField textField) {
        errCatch7 = formValidation.isPhone(textField.getText());
        if (errCatch7 == false) {
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

        fname.textProperty().addListener(e -> fNameTester(fname));
        lname.textProperty().addListener(e -> lNameTester(lname));
        email.textProperty().addListener(e -> {//This contnains a db query to check if email already exists
            try {
                mailTester(email);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        number.textProperty().addListener(e -> phoneTester(number));
        pass.textProperty().addListener(e -> passTester(pass,passconf));
        //passconf.textProperty().addListener(e -> passTester(passconf));
        passconf.textProperty().addListener(e -> passConfTester(pass,passconf));
        emname.textProperty().addListener(e -> eNameTester(emname));
        emnumber.textProperty().addListener(e -> ePhoneTester(emnumber));
    }
}
