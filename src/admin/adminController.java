package admin;

        import com.jfoenix.controls.JFXDialog;
        import com.jfoenix.controls.JFXDialogLayout;
        import com.jfoenix.controls.JFXTextField;
        import com.jfoenix.validation.RequiredFieldValidator;
        import com.sun.rowset.internal.Row;
        import database.DAO;
        import database.dbConnection;
        import database.userObject;
        import drives.drivesController;
        import gui.Controller;
        import gui.whoIsLoggedIn;
        import javafx.application.Application;
        import javafx.beans.value.ChangeListener;
        import javafx.beans.value.ObservableValue;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.event.Event;
        import javafx.event.EventHandler;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.control.cell.TextFieldTableCell;
        import javafx.scene.control.cell.TextFieldTreeTableCell;
        import javafx.scene.effect.Blend;
        import javafx.scene.effect.BoxBlur;
        import javafx.scene.effect.DropShadow;
        import javafx.scene.effect.MotionBlur;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.KeyCode;
        import javafx.scene.input.KeyEvent;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.Pane;
        import javafx.scene.text.Text;
        import javafx.stage.DirectoryChooser;
        import javafx.stage.FileChooser;
        import javafx.stage.Stage;
        import org.apache.poi.xssf.usermodel.XSSFRow;
        import org.apache.poi.xssf.usermodel.XSSFSheet;
        import org.apache.poi.xssf.usermodel.XSSFWorkbook;

        import java.io.*;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.ResourceBundle;
        import java.util.regex.Pattern;

        import static javafx.scene.paint.Color.*;


public class adminController extends Application implements Initializable {
    @FXML
    private JFXTextField fname;
    @FXML
    private JFXTextField lname;
    @FXML
    private JFXTextField totHours;
    @FXML
    private JFXTextField totSigned;
    @FXML
    private JFXTextField contactName;
    @FXML
    private JFXTextField contactNum;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField phoneNum;
    @FXML
    private JFXTextField passw;
    @FXML
    private TextField searchId;
    @FXML
    private Label nameErr;

    @FXML
    private Button addRecords;
    @FXML
    private Button updateRecords;
    @FXML
    private Button clearRecords;
    @FXML
    private Button deleteRecords;
    @FXML
    private Button excelButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button drivesView;
    @FXML
    private Button s;
    @FXML
    private RadioButton Female;
    @FXML
    private RadioButton Male;


    //----------------------------TABLE-------------------------//
    @FXML
    private TableView<UserData> userTable;
    @FXML
    private TableColumn<UserData, String> fnameCol;
    @FXML
    private TableColumn<UserData, String> lnameCol;
    @FXML
    private TableColumn<UserData, String> phoneNumCol;
    @FXML
    private TableColumn<UserData, String> passwordCol;
    @FXML
    private TableColumn<UserData, String> conNameCol;
    @FXML
    private TableColumn<UserData, String> totHoursCol;
    @FXML
    private TableColumn<UserData, String> totSignedCol;
    @FXML
    private TableColumn<UserData, String> conNumCol;
    @FXML
    private TableColumn<UserData, String> emailCol;
    @FXML
    private TableColumn<UserData, String> idCol;

    @FXML
    private TreeView<JFXTextField> treeView;

    //-----------------------------------------------------------//

    private DAO dao;
    private dbConnection db;
    private ObservableList<UserData> observableList; //A list that enables listeners to track changes when they occur
    private RequiredFieldValidator validator;
    private Boolean errCatch;
    private whoIsLoggedIn whoIsLoggedIn;
    private String newDel;
    private String adminName;

    public void setName(String name){
        adminName = name;
    }

    //interacting with fxml ids
    @Override
    public void initialize(URL url, ResourceBundle resources) {

        dao = new DAO();
        db = new dbConnection();
        whoIsLoggedIn = new whoIsLoggedIn().getWhoIsLoggedIn();

        /*userTable.setEditable(true);//delete,update dont really work*/
        idCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //loadData();
        loadphpData();

        /**************************validation**********************/
        fname.textProperty().addListener(e -> ErrTester(fname));
        lname.textProperty().addListener(e -> ErrTester(lname));
        phoneNum.textProperty().addListener(E -> phoneTester(phoneNum));
        totHours.setDisable(true);
        //totHours.textProperty().addListener(E -> phoneTester(totHours));
        totSigned.textProperty().addListener(E -> hrTester(totSigned));
        contactName.textProperty().addListener(P -> ErrTester(contactName));
        contactNum.textProperty().addListener(P -> phoneTester(contactNum));
        email.textProperty().addListener(M -> mailTester(email));
        /*fname.getValidators().add(validator);
        fname.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    validator.setMessage("                              field is empty");
                    fname.validate();
                }
                ErrTester();
            }
        });*/
        /**************************validation**********************/


        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("deleter"));

        content.setActions();
        //Update Records with lamda or annon

        /*searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadSelectedData(event);
            }
        })*/
        ;

       /* searchId.setOnAction(event -> loadSelectedData(event));*/
       // searchButton.setOnAction(event -> loadSelectedData(event));//this take cair of enter and button click
        searchButton.setOnAction(event -> phpLoadSelectedData(event));



        addRecords.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               // addData();
                phpAddData();
            }
        });


        //pass tabel data to textfield,for a more eficent update
        userTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                UserData data = userTable.getItems().get(userTable.getSelectionModel().getFocusedIndex());
                fname.setText(data.getFirstName());
                fname.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                fname.setPromptText("");

                lname.setText(data.getLastName());
                lname.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                lname.setPromptText("");

                phoneNum.setText(data.getPhoneNumber());
                phoneNum.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                phoneNum.setPromptText("");


                passw.setText(data.getPassword());
                passw.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                passw.setPromptText("");
                passw.setEffect(new MotionBlur());

               /* phoneNum.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                phoneNum.setPromptText("");*/

                /*if (data.getRadio1() == "Male") {
                    Male.setSelected(true);
                } else {
                    Female.setSelected(true);
                }*/
                totHours.setText(data.getTotalHours());
                totHours.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                totHours.setPromptText("");

                totSigned.setText(data.getTotalSigned());
                totSigned.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                totSigned.setPromptText("");

                contactName.setText(data.getContactName());
                contactName.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                contactName.setPromptText("");

                contactNum.setText(data.getcontactNum());
                contactNum.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                contactNum.setPromptText("");

                email.setText(data.getEmail());
                email.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                email.setPromptText("");

                searchId.setText(fname.getText() + "," + lname.getText());
                newDel = email.getText();
                searchId.setStyle("-fx-text-fill: green; -fx-font-size: 12;");

            }
        });

      /*  TreeItem<JFXTextField> root = new TreeItem<>();
        TreeItem<JFXTextField> nodeA = new TreeItem<>(passw);
        root.getChildren().add(nodeA);
        treeView.setRoot(root);*/
      passw.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
              passw.setEffect(new Blend());
              passw.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
          }
      });




    }


    @FXML
    private void loadData() {
        String sql = "SELECT * FROM volunteer";

        try {
            Connection connection = db.connect();
            this.observableList = FXCollections.observableArrayList();
            ResultSet resultSet = connection.createStatement().executeQuery(sql);

            //start from 1 because id is autoincrimenting
            while (resultSet.next()) {
                this.observableList.add(new UserData(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)
                        , resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)
                        , resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10)));
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Load Database err " + e);
        }


        this.fnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        this.passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        this.totHoursCol.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
        this.totSignedCol.setCellValueFactory(new PropertyValueFactory<>("totalSigned"));
        this.conNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        this.conNumCol.setCellValueFactory(new PropertyValueFactory<>("contactNum"));
        this.idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

        this.userTable.setItems(null);
        this.userTable.setItems(observableList);

    }

    @FXML
    public void addData() {

        if (errCatch && !fname.getText().isEmpty() && !lname.getText().isEmpty()) {

            String insertsql = "INSERT INTO volunteer(fname, lname, email, phoneNumber, password, contactName, contactPhone, hoursTotal, hoursSigned) VALUES (?,?,?,?,?,?,?,?,?)";
            /*String gender = "";
            if (Male.isSelected()) {
                gender += Male.getText();
            }
            if (Female.isSelected()) {
                gender += Female.getText();
            }*/

            try {
                Connection connection = db.connect();

                PreparedStatement stmt = connection.prepareStatement(insertsql);
                stmt.setString(1, this.fname.getText());
                stmt.setString(2, this.lname.getText());
                stmt.setString(3, this.email.getText());
                stmt.setString(4, this.phoneNum.getText());
                stmt.setString(5, this.passw.getText());
                stmt.setString(6, this.contactName.getText());
                stmt.setString(7, this.contactNum.getText());
                stmt.setString(8, this.totHours.getText());
                stmt.setString(9, this.totSigned.getText());
                stmt.execute();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            loadData();
        } else {
            AlertBox.display("INSERT", "Could not Insert" + "\nPlease Check Form Input");
        }

    }

    @FXML
    public void clearFields(ActionEvent event) {
        this.fname.setText("");
        this.fname.setPromptText("First Name");
        this.lname.setText("");
        this.lname.setPromptText("Last Name");
        this.email.setText("");
        this.email.setPromptText("me@someMall.com");
        this.phoneNum.setText("");
        this.phoneNum.setPromptText("XXX-XXX-XXXX");
        this.passw.setText("");
        this.totSigned.setText("");
        this.totHours.setText("");
        this.totHours.setPromptText("Signed Hours");
        this.totSigned.setText("0.0");
        this.totSigned.setPromptText("Signed Hours");
        this.contactName.setText("");
        this.contactName.setPromptText("l6a3a4");
        this.contactNum.setText("");
        this.contactNum.setPromptText("XXX-XXX-XXXX");
        this.searchId.setText("");
        this.searchId.setPromptText("first,last");
    }


    @FXML
    public void deleteFields(ActionEvent event) {


        String searchKey = this.searchId.getText();
        String[] split = searchKey.split("\\s*,\\s*");

        if (!split[0].equals("") && !split[1].equals("")) {

            String sql = "DELETE FROM volunteer where fname = ? and lname = ?";
            String gender;
            try {
                Connection connection = db.connect();
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, split[0]);
                stmt.setString(2, split[1]);
                stmt.executeUpdate();

               /* *//*ResultSet rs = stmt.executeQuery();
                this.fname.setText(rs.getString(1));
                this.lname.setText(rs.getString(2));
                this.email.setText(rs.getString(3));
                this.dob.setText(rs.getString(4));
                gender = rs.getString(5);
                this.contact.setText(rs.getString(6));
                this.emergency.setText(rs.getString(7));
                this.postCode.setText(rs.getString(8));*//*

                if (gender.equals("Male")) {
                    Male.setSelected(true);
                } else {
                    Female.setSelected(true);
                }*/


                connection.close();
            } catch (SQLException e) {
                System.err.println("Load Database err " + e);
                System.err.println("Load Database err " + e);
            }
            loadData();
        } else {
            AlertBox.display("DELETE", "Could not Delete" + "\nPlease Contact Developer");
        }
    }

    @FXML
    private void loadSelectedData() {
        String searchKey = this.searchId.getText();
        String[] split = searchKey.split("\\s*,\\s*");

        String sql = "SELECT * FROM volunteer where fname = ? and lname = ?";

        try {
            Connection connection = db.connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, split[0]);
            stmt.setString(2, split[1]);
            this.observableList = FXCollections.observableArrayList();
            ResultSet resultSet = stmt.executeQuery();


            //start from 1 because id is autoincrimenting
            while (resultSet.next()) {
                this.observableList.add(new UserData(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)
                        , resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)
                        , resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10)));
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Load Database err " + e);
        }


        this.fnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        this.passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        this.totHoursCol.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
        this.totSignedCol.setCellValueFactory(new PropertyValueFactory<>("totalSigned"));
        this.conNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        this.conNumCol.setCellValueFactory(new PropertyValueFactory<>("contactNum"));
        this.idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

        this.userTable.setItems(null);
        this.userTable.setItems(observableList);

    }

    //Update Records Anonymous class


    public void manualLoadData(ActionEvent event) {
        //loadData();
        loadphpData();
    }


    public void setUpdateRecords(ActionEvent event) {

        String searchKey = this.searchId.getText();
        String[] split = searchKey.split("\\s*,\\s*");

        if (errCatch) {

            try {

                //String insertsql = "UPDATE volunteer volunteer(fname = ?, lname = ?, email = ?, phoneNumber = ?, password = ?, contactName = ?, contactPhone = ?, hoursTotal = ?, hoursSigned = ?) WHERE email = ?";

                /*String insertsql = "UPDATE volunteer set fname=?,lname=?,email=?,Dob=?,gender=?,phone=?,emergency=?,"
                        + "postal=? "
                        + "where fname='" + split[0] + "'" + "and lname='" + split[1] + "'";*/
                Connection connection = db.connect();


                //PreparedStatement stmt = connection.prepareStatement(insertsql);
                //stmt.setString(1, split[0]);
                //stmt.setString(2, split[1]);

                /*String gender = "";
                if (Male.isSelected()) {
                    gender += Male.getText();
                }
                if (Female.isSelected()) {
                    gender += Female.getText();
                }*/

                userObject user = new userObject();

                user.fname = this.fname.getText();
                user.lname = this.lname.getText();
                user.email = this.email.getText();
                user.phone = this.phoneNum.getText();
                user.pass = this.passw.getText();
                user.hourstotal = this.totHours.getText();
                user.hourssigned = this.totSigned.getText();
                user.ename = this.contactName.getText();
                user.ephone = this.contactNum.getText();



                /*stmt.setString(1, this.fname.getText());
                stmt.setString(2, this.lname.getText());
                stmt.setString(3, this.email.getText());
                stmt.setString(4, this.phoneNum.getText());
                stmt.setString(5, this.passw.getText());
                stmt.setString(6, this.totHours.getText());
                stmt.setString(7, this.totSigned.getText());
                stmt.setString(8, this.contactName.getText());*/
                db.updateUser(user);
                //stmt.execute();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            loadData();

        } else {
            AlertBox.display("UPDATE", "Could not update" + "\nPlease Contact Developer");
        }


    }

    public void ErrTester(JFXTextField textField) {
        errCatch = formValidation.isNameCor(textField.getText());
        if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("Name");
        }

    }

    @FXML
    public void dobTester(JFXTextField textField) {
        errCatch = formValidation.isDate(textField.getText());
        if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("YYYY-MM-DD");
        }

    }

    @FXML
    public void phoneTester(JFXTextField textField) {
        errCatch = formValidation.isPhone(textField.getText());
        int max = 12;
        if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("XXX-XXX-XXXX or XXXXXXXXXX");
        }
        textField.setOnKeyTyped(event -> {
            if(textField.getText().length() > max){
                event.consume();
                textField.setFocusColor(PURPLE);
                textField.setPromptText("Invalid Length");
            }
        });

    }

    @FXML
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

    @FXML
    public void mailTester(JFXTextField textField) {
        errCatch = formValidation.isMail(textField.getText());
        if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("me@someMall.com");
        }

    }

    @FXML
    public void hrTester(JFXTextField textField) {
        errCatch = formValidation.isHoursSigned(textField.getText());
        int max = 4;

        if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("");
        }
        textField.setOnKeyTyped(event -> {
            if(textField.getText().length() > max){
                event.consume();
                textField.setFocusColor(PURPLE);
                textField.setPromptText("Invalid Length");
            }
        });

    }

    @FXML
    public void drivesView() throws IOException, SQLException {

        //Connection connection = db.connect();

        System.out.print("PRESSED!");
        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = (Pane) loader.load(getClass().getResource("/drives/drives.fxml").openStream());
        drivesController ac =(drivesController) loader.getController();
        ac.setName(adminName);
        Scene scene = new Scene(root);
        userStage.setScene(scene);
        userStage.setTitle("Drives");
        userStage.show();

        closeFull();
    }

    //this need the apache jar
    @FXML
    public void ExportToExcel(ActionEvent event) {

        String sql = "SELECT * FROM volunteer";

        try {
            Connection connection = db.connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            //microsft 2007-
            XSSFWorkbook workbook = new XSSFWorkbook();//for eatlir version use HSSF
            XSSFSheet sheet = workbook.createSheet("Information");


            sheet.setColumnWidth(0, 2560);
            sheet.setColumnWidth(1, 6560);
            sheet.setColumnWidth(2, 6560);
            sheet.setColumnWidth(3, 6560);
            sheet.setColumnWidth(4, 6560);
            sheet.setColumnWidth(5, 6560);
            sheet.setColumnWidth(6, 6560);
            sheet.setColumnWidth(7, 6560);
            sheet.setColumnWidth(8, 6560);
            sheet.setColumnWidth(9, 6560);

            XSSFRow header = sheet.createRow(0);//first rwo
            header.createCell(0).setCellValue("ID");//first col
            header.createCell(1).setCellValue("First Name");
            header.createCell(2).setCellValue("Last Name");
            header.createCell(3).setCellValue("Email");
            header.createCell(4).setCellValue("Phone");
            header.createCell(5).setCellValue("Password");
            header.createCell(6).setCellValue("hoursTotal");
            header.createCell(7).setCellValue("hoursSigned");
            header.createCell(8).setCellValue("contactName");
            header.createCell(9).setCellValue("contactPhone");

            int index = 1;

            while(resultSet.next()){
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(resultSet.getString("id"));
                row.createCell(1).setCellValue(resultSet.getString("fname"));
                row.createCell(2).setCellValue(resultSet.getString("lname"));
                row.createCell(3).setCellValue(resultSet.getString("email"));
                row.createCell(4).setCellValue(resultSet.getString("phoneNumber"));
                row.createCell(5).setCellValue(resultSet.getString("password"));
                row.createCell(6).setCellValue(resultSet.getString("hoursTotal"));
                row.createCell(7).setCellValue(resultSet.getString("hoursSigned"));
                row.createCell(8).setCellValue(resultSet.getString("contactName"));
                row.createCell(9).setCellValue(resultSet.getString("contactPhone"));
                index++;
            }

            //FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\dance\\Desktop\\Details.xlsx");
            FileOutputStream fileOutputStream = null;
            try {
                Stage stage = (Stage) searchButton.getScene().getWindow();
                DirectoryChooser dChooser = new DirectoryChooser();
                dChooser.setTitle("Save Food Drive Report");
                File selectedDirectory = dChooser.showDialog(stage);

                if (selectedDirectory != null) {
                    fileOutputStream = new FileOutputStream(new File(selectedDirectory.getAbsolutePath() + "\\Volunteer Report.xlsx"));
                    workbook.write(fileOutputStream);
                    fileOutputStream.close();
                } else {
                    AlertBox.display("EXPORT", "No folder Selected!");
                }



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AlertBox.display("EXPORT", "Export Successful");

            connection.close();
        } catch (SQLException e) {
            System.err.println("Load Database err " + e);
        }

    }

    @FXML
    public void signout(Event event) throws IOException {

        close();
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

        Stage stage = (Stage) searchButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void closeFull() throws IOException {
        Stage stage = (Stage) drivesView.getScene().getWindow();
        stage.close();
    }

    /* PHP*/

    @FXML
    public void loadphpData(){
        List<String> ls = new ArrayList<>();
        this.observableList = FXCollections.observableArrayList();
        String[] str;

        ls = dao.getAll();
        for(String info:ls){
           str =  info.split(Pattern.quote(" "));
            this.observableList.add(new UserData(str[0],  str[1],  str[2],  str[3],  str[4],  str[5],  str[6],  str[7],  str[8],str[9]));
        }




        //this.passw.setEffect(new BoxBlur());
        this.fnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        //this.passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        this.totHoursCol.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
        this.totSignedCol.setCellValueFactory(new PropertyValueFactory<>("totalSigned"));
        this.conNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        this.conNumCol.setCellValueFactory(new PropertyValueFactory<>("contactNum"));
        this.idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

        this.userTable.setItems(null);
        this.userTable.setItems(observableList);
    }

    @FXML
    public void phpAddData(){
        List<String> ls = new ArrayList<>();
        String[] str;
        Boolean opt = false;


        BufferedReader in;
        String input = "http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/insertAll.php?fname=" + this.fname.getText() +"&lname="+ this.lname.getText()+"&email="+this.email.getText()+
                "&phoneNumber="+this.phoneNum.getText()+ "&password="+this.passw.getText()+
                "&hoursTotal="+this.totHours.getText()+"&hoursSigned="+this.totSigned.getText()+
                "&contactName="+this.contactName.getText()+"&contactPhone="+this.contactNum.getText();
        StringBuilder sb = new StringBuilder();
        String inputLine;

        ls = dao.getAll();
        for(String info:ls){
            str =  info.split(Pattern.quote(" "));
            System.out.println(str[3]+str[4]);
            if(str[3].equals(email.getText()) || str[4].equals(phoneNum.getText())){
                opt = true;
                break;
            }
        }

        if(opt){
            AlertBox.display("INSERT", "Duplicate Records");
        } else{
            if (errCatch && dao.phpConnection()){

                if(dao.phpConnection()){
                    try {
                        URL connectURL = new URL(input);
                        in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                        while((inputLine = in.readLine()) != null){
                            //System.out.println(inputLine);
                            sb.append(inputLine+"\n");
                        }
                        System.out.println(sb);

                        loadphpData();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                AlertBox.display("INSERT", "Could not Insert" + "\nPlease Check Form Input");
            }
        }
    }

    @FXML
    public void phpDeleteFields(ActionEvent event){
        /*String searchKey = this.searchId.getText();
        String[] split = searchKey.split("\\s*,\\s*");*/
        BufferedReader in;
        StringBuilder sb = new StringBuilder();
        String inputLine;

        if(AlertBox.checkForDelete("DELETE","Are you sure you want to delete?")){
            if (!newDel.equals("")) {

                if(dao.phpConnection()){
                    try {
                        URL connectURL = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/deleteFields.php?email=" +newDel);
                        in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                        while((inputLine = in.readLine()) != null){
                            //System.out.println(inputLine);
                            sb.append(inputLine+"\n");
                        }
                        System.out.println(sb);

                        loadphpData();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                AlertBox.display("DELETE", "Could not Remove");
            }
        }
    }

    @FXML
    public void phpUpdateFields(ActionEvent event){
        String input = "http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/updateData.php?fname=" + this.fname.getText() +"&lname="+ this.lname.getText()+"&email="+newDel+
                "&phoneNumber="+this.phoneNum.getText()+ "&password="+this.passw.getText()+
                "&hoursTotal="+this.totHours.getText()+"&hoursSigned="+this.totSigned.getText()+
                "&contactName="+this.contactName.getText()+"&contactPhone="+this.contactNum.getText();
        /*String searchKey = this.searchId.getText();
        String[] split = searchKey.split("\\s*,\\s*");*/

        BufferedReader in;
        StringBuilder sb = new StringBuilder();
        String inputLine;
       ;

        if(errCatch && dao.phpConnection()) {

            try {
                URL connectURL = new URL(input);
                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    //System.out.println(inputLine);
                    sb.append(inputLine+"\n");
                }
                System.out.println(sb);

                loadphpData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            AlertBox.display("UPDATE", "Could not update" + "\nPlease Contact Developer");
        }



    }

    @FXML
    private void phpLoadSelectedData(ActionEvent event) {
        String searchKey = this.searchId.getText();
        BufferedReader in;
        String inputLine;
        String[] split = {"",""};
        split = searchKey.split("\\s*,\\s*");
        List<String> ls = new ArrayList<>();
        String[] str;
        this.observableList = FXCollections.observableArrayList();
        StringBuilder sb = new StringBuilder();



        if(dao.phpConnection()){
            URL connectURL = null;
            try {

                if(split.length == 1){
                    connectURL = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/loadSelectedRow.php?fname="+split[0]);
                }else {
                    connectURL = new URL("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/loadSelectedRow.php?fname=" + split[0] + "&lname=" + split[1]);
                }

                in = new BufferedReader(new InputStreamReader(connectURL.openStream()));

                while((inputLine = in.readLine()) != null){
                    sb.append(inputLine);
                    ls.add(inputLine) ;
                }
                System.out.println("$*$ "+sb);

                for(String info:ls){
                    str =  info.split(Pattern.quote(" "));
                    this.observableList.add(new UserData(str[0],  str[1],  str[2],  str[3],  str[4],  str[5],  str[6],  str[7],  str[8],str[9]));
                }

                //---------
                this.fnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
                this.lnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
                this.emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
                this.phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                //this.passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
                this.totHoursCol.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
                this.totSignedCol.setCellValueFactory(new PropertyValueFactory<>("totalSigned"));
                this.conNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
                this.conNumCol.setCellValueFactory(new PropertyValueFactory<>("contactNum"));
                this.idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

                this.userTable.setItems(null);
                this.userTable.setItems(observableList);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


   /* List<String> ls = new ArrayList<>();
        this.observableList = FXCollections.observableArrayList();
    String[] str;

    ls = dao.getAll();
        for(String info:ls){
        str =  info.split(Pattern.quote(" "));
        this.observableList.add(new UserData(str[0],  str[1],  str[2],  str[3],  str[4],  str[5],  str[6],  str[7],  str[8],str[9]));
    }*/
   public void phpExportToExcel(ActionEvent event) {
       List<String> ls = new ArrayList<>();
       this.observableList = FXCollections.observableArrayList();
       String[] str;

       ls = dao.getAll();
     /*  for(String info:ls){
           str =  info.split(Pattern.quote(" "));
           this.observableList.add(new UserData(str[0],  str[1],  str[2],  str[3],  str[4],  str[5],  str[6],  str[7],  str[8],str[9]));
       }
       */
       String createorName = new Controller().uName;
       //microsft 2007-
       XSSFWorkbook workbook = new XSSFWorkbook();//for eatlir version use HSSF
       XSSFSheet sheet = workbook.createSheet(createorName);
       XSSFRow header = sheet.createRow(0);//first rwo
       header.createCell(0).setCellValue("ID");//first col
       header.createCell(1).setCellValue("First Name");
       header.createCell(2).setCellValue("Last Name");
       header.createCell(3).setCellValue("Email");
       header.createCell(4).setCellValue("Phone");
       header.createCell(5).setCellValue("Password");
       header.createCell(6).setCellValue("hoursTotal");
       header.createCell(7).setCellValue("hoursSigned");
       header.createCell(8).setCellValue("contactName");
       header.createCell(8).setCellValue("contactPhone");

       int index = 1;

       for(String info:ls){
           str =  info.split(Pattern.quote(" "));
           XSSFRow row = sheet.createRow(index);
           row.createCell(0).setCellValue(str[0]);
           row.createCell(1).setCellValue(str[1]);
           row.createCell(2).setCellValue(str[2]);
           row.createCell(3).setCellValue(str[3]);
           row.createCell(4).setCellValue(str[4]);
           row.createCell(5).setCellValue(str[5]);
           row.createCell(6).setCellValue(str[6]);
           row.createCell(7).setCellValue(str[7]);
           row.createCell(8).setCellValue(str[8]);
           row.createCell(8).setCellValue(str[9]);
           index++;
       }

       FileOutputStream fileOutputStream = null;
       try {
           Stage stage = (Stage) searchButton.getScene().getWindow();
           DirectoryChooser dChooser = new DirectoryChooser();
           dChooser.setTitle("Select Volunteers Report Location");
           File selectedDirectory = dChooser.showDialog(stage);


           if(selectedDirectory != null) {
               fileOutputStream = new FileOutputStream(selectedDirectory.getAbsolutePath() + "\\Volunteer Report.xlsx");
               workbook.write(fileOutputStream);
           }else {
               AlertBox.display("EXPORT", "No folder Selected!");
           }
               fileOutputStream.close();


       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       AlertBox.display("EXPORT", "Export Successful");


   }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("newAdmin.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //primaryStage.setTitle("DRIVES");
        primaryStage.show();
    }
}














