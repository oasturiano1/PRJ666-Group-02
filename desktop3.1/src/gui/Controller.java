package gui;

import admin.adminController;
import database.DAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import database.dbConnection;
import database.loginType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import signup.SignUp;
import user.userController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;



//fx:controller="gui.Controller"
public class Controller implements Initializable {
    private DAO dao;
    private dbConnection db;

    @FXML
    public TextField username;;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox combo;
    @FXML
    private Labeled status;
    @FXML
    private Button okbtn;
    @FXML
    private Button canbtn;
    @FXML
    private Labeled errorMsg;

    @FXML
    private Label signbtn;


    public Controller(){
        dao = new DAO();
       combo = new ComboBox();
    }

    private Session session;
    public static String uName;
    public static String pass;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        session = new Session();

        /* if(db.isDatabaseCon()){
            this.status.setText("Online");
         }
         else{
             this.status.setText("OffLine");
         }*/
        //db = new dbConnection();

        if(dao.phpConnection() || db.isDatabaseCon()){
            this.status.setText("Online");
            dao.getAll();
        }
        else{
            this.status.setText("OffLine");
        }

         combo.getItems().addAll(loginType.values());
         //combo.setValue("Admin/User");



         signbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 try {
                     signUpMethod();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         });
    }




    public void closeLoginScene(ActionEvent event){
        Platform.exit();
    }

    @FXML
    public void loginMethod(ActionEvent event){
        uName = this.username.getText();
        pass = this.password.getText();


        //String option = this.combo.getValue().toString();
        String[] datas = dao.phpIsLogin(uName,pass);
        //System.out.println(datas[0]+" "+datas[1]+" "+datas[2]);
        /*db.isLogin(username,password,option);*/
        //dao.phpIsLogin(username,password,option)


        try {
            if(datas[0].equals(uName) && datas[1].equals(pass)){
               session.setPref(true,uName,pass);


                Stage stage = (Stage)this.okbtn.getScene().getWindow();
                stage.close();


                switch (datas[2]){
                    case "ADMIN":
                        adminLogin();
                        break;
                    case "USER":
                        userLogin();
                        break;
                }
            } else{
                this.errorMsg.setText("Error");
                //System.out.print(username+" "+password+" "+option);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userLogin(){
        try {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("/user/userFXML.fxml").openStream());


            //Connection connection = db.connect();

            //attach usercontroller to user fxml


            userController uc =(userController)loader.getController();
            //uc.setUser(db.getUser(this.username.getText()));//sets user object in next scene
            uc.setUser(dao.phpGetUser(this.username.getText()));//sets user object in next scene
            //uc.setDB(db);// IMPORTANT- NEED TO CLOSE

            Scene scene = new Scene(root);
            scene.getStylesheets().add("admin/adminStyle.css");
            userStage.setScene(scene);
            userStage.setTitle("User DashBoard");
            userStage.show();
            //connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void adminLogin() throws IOException {
        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = (Pane) loader.load(getClass().getClassLoader().getResource("admin/newAdmin.fxml"));

        //attach admincontroller to asmin fxml
        adminController ac =(adminController)loader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/admin/adminStyle.css");
        userStage.setScene(scene);
       // userStage.setTitle(username.getText()+"[ "+ "Admin"+ " ]");
        userStage.setTitle("ADMIN");
        userStage.setResizable(false);
        userStage.show();
    }

    public void signUpMethod() throws IOException {
        System.out.print("PRESSED!");
        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = (Pane) loader.load(getClass().getResource("/signup/signup.fxml").openStream());
        SignUp ac =(SignUp) loader.getController();


        Scene scene = new Scene(root);
        scene.getStylesheets().add("/signup/signUpStyle.css");
        userStage.setScene(scene);
        userStage.setTitle("Sign up");
        userStage.show();

        //Stage stage = (Stage)this.si

        //.getScene().getWindow();
        Stage stage = (Stage)this.okbtn.getScene().getWindow();
        stage.close();

    }

    //ORLANDSON This closes the screen, call when moving to next window
    public void close(){

        Stage stage = (Stage) okbtn.getScene().getWindow();
        stage.close();
    }

}


