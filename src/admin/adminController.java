package admin;

        import com.jfoenix.controls.JFXDialog;
        import com.jfoenix.controls.JFXDialogLayout;
        import com.jfoenix.controls.JFXTextField;
        import com.jfoenix.validation.RequiredFieldValidator;
        import com.sun.rowset.internal.Row;
        import database.dbConnection;
        import javafx.beans.value.ChangeListener;
        import javafx.beans.value.ObservableValue;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.event.Event;
        import javafx.event.EventHandler;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.control.cell.TextFieldTableCell;
        import javafx.scene.control.cell.TextFieldTreeTableCell;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.KeyCode;
        import javafx.scene.input.KeyEvent;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.text.Text;
        import javafx.stage.FileChooser;
        import org.apache.poi.xssf.usermodel.XSSFRow;
        import org.apache.poi.xssf.usermodel.XSSFSheet;
        import org.apache.poi.xssf.usermodel.XSSFWorkbook;

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.net.URL;
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ResourceBundle;

        import static javafx.scene.paint.Color.BLUE;
        import static javafx.scene.paint.Color.GREEN;
        import static javafx.scene.paint.Color.RED;


public class adminController implements Initializable {
    @FXML
    private JFXTextField fname;
    @FXML
    private JFXTextField lname;
    @FXML
    private JFXTextField contact;
    @FXML
    private JFXTextField emergency;
    @FXML
    private JFXTextField postCode;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField dob;
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
    private TableColumn<UserData, String> dobCol;
    @FXML
    private TableColumn<UserData, String> genderCol;
    @FXML
    private TableColumn<UserData, String> contactCol;
    @FXML
    private TableColumn<UserData, String> emergencyCol;
    @FXML
    private TableColumn<UserData, String> postCol;
    @FXML
    private TableColumn<UserData, String> emailCol;
    @FXML
    private TableColumn<UserData, String> idCol;
    //-----------------------------------------------------------//

    private dbConnection db;
    private ObservableList<UserData> observableList; //A list that enables listeners to track changes when they occur
    private RequiredFieldValidator validator;
    private Boolean errCatch;


    //interacting with fxml ids
    @Override
    public void initialize(URL url, ResourceBundle resources) {

        db = new dbConnection();
        /*userTable.setEditable(true);//delete,update dont really work*/
        idCol.setCellFactory(TextFieldTableCell.forTableColumn());
        loadData();

        /**************************validation**********************/
        fname.textProperty().addListener(e -> ErrTester(fname));
        lname.textProperty().addListener(e -> ErrTester(lname));
        dob.textProperty().addListener(E -> dobTester(dob));
        contact.textProperty().addListener(E -> phoneTester(contact));
        emergency.textProperty().addListener(E -> phoneTester(emergency));
        postCode.textProperty().addListener(P -> postTester(postCode));
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
        searchButton.setOnAction(event -> loadSelectedData(event));//this take cair of enter and button click



        addRecords.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addData();
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

                dob.setText(data.getDOB());
                dob.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                dob.setPromptText("");

                if (data.getRadio1() == "Male") {
                    Male.setSelected(true);
                } else {
                    Female.setSelected(true);
                }
                contact.setText(data.getContact());
                contact.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                contact.setPromptText("");

                emergency.setText(data.getEmergency());
                emergency.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                emergency.setPromptText("");

                postCode.setText(data.getPostal());
                postCode.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                postCode.setPromptText("");

                email.setText(data.getEmail());
                email.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
                email.setPromptText("");

                searchId.setText(fname.getText() + "," + lname.getText());
                searchId.setStyle("-fx-text-fill: green; -fx-font-size: 12;");

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
                        , resultSet.getString(7), resultSet.getString(8), resultSet.getString(9)));
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Load Database err " + e);
        }


        this.fnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.dobCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        this.genderCol.setCellValueFactory(new PropertyValueFactory<>("radio1"));
        this.contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        this.emergencyCol.setCellValueFactory(new PropertyValueFactory<>("emergency"));
        this.postCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        this.idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

        this.userTable.setItems(null);
        this.userTable.setItems(observableList);

    }

    @FXML
    public void addData() {

        if (errCatch && !fname.getText().isEmpty() && !lname.getText().isEmpty()) {

            String insertsql = "INSERT INTO volunteer(fname,lname,email,Dob,gender,phone,emergency,postal) VALUES (?,?,?,?,?,?,?,?)";
            String gender = "";
            if (Male.isSelected()) {
                gender += Male.getText();
            }
            if (Female.isSelected()) {
                gender += Female.getText();
            }

            try {
                Connection connection = db.connect();

                PreparedStatement stmt = connection.prepareStatement(insertsql);
                stmt.setString(1, this.fname.getText());
                stmt.setString(2, this.lname.getText());
                stmt.setString(3, this.email.getText());
                stmt.setString(4, this.dob.getText());
                stmt.setString(5, gender);
                stmt.setString(6, this.contact.getText());
                stmt.setString(7, this.emergency.getText());
                stmt.setString(8, this.postCode.getText());
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
        this.dob.setText("");
        this.dob.setPromptText("01-01-1901");
        this.Male.setSelected(false);
        this.Female.setSelected(false);
        this.contact.setText("");
        this.contact.setPromptText("555-555-5555");
        this.emergency.setText("");
        this.emergency.setPromptText("555-555-5555");
        this.postCode.setText("");
        this.postCode.setPromptText("l6a3a4");
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
            }
            loadData();
        } else {
            AlertBox.display("DELETE", "Could not Delete" + "\nPlease Contact Developer");
        }
    }

    @FXML
    private void loadSelectedData(ActionEvent event) {
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
                        , resultSet.getString(7), resultSet.getString(8), resultSet.getString(9)));
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Load Database err " + e);
        }


        this.fnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.dobCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        this.genderCol.setCellValueFactory(new PropertyValueFactory<>("radio1"));
        this.contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        this.emergencyCol.setCellValueFactory(new PropertyValueFactory<>("emergency"));
        this.postCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        this.idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

        this.userTable.setItems(null);
        this.userTable.setItems(observableList);

    }

    //Update Records Anonymous class


    public void manualLoadData(ActionEvent event) {
        loadData();
    }


    public void setUpdateRecords(ActionEvent event) {

        String searchKey = this.searchId.getText();
        String[] split = searchKey.split("\\s*,\\s*");

        if (errCatch) {

            try {

                String insertsql = "UPDATE volunteer set fname=?,lname=?,email=?,Dob=?,gender=?,phone=?,emergency=?,"
                        + "postal=? "
                        + "where fname='" + split[0] + "'" + "and lname='" + split[1] + "'";
                Connection connection = db.connect();
                PreparedStatement stmt = connection.prepareStatement(insertsql);
                stmt.setString(1, split[0]);
                stmt.setString(2, split[1]);

                String gender = "";
                if (Male.isSelected()) {
                    gender += Male.getText();
                }
                if (Female.isSelected()) {
                    gender += Female.getText();
                }

                stmt.setString(1, this.fname.getText());
                stmt.setString(2, this.lname.getText());
                stmt.setString(3, this.email.getText());
                stmt.setString(4, this.dob.getText());
                stmt.setString(5, gender);
                stmt.setString(6, this.contact.getText());
                stmt.setString(7, this.emergency.getText());
                stmt.setString(8, this.postCode.getText());

                stmt.execute();
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

    public void dobTester(JFXTextField textField) {
        errCatch = formValidation.isDob(textField.getText());
        if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("01-1-1901");
        }

    }

    public void phoneTester(JFXTextField textField) {
        errCatch = formValidation.isPhone(textField.getText());
        if (errCatch == false) {
            textField.setFocusColor(RED);
            textField.setPromptText("Invalid");
        } else {
            textField.setFocusColor(GREEN);
            textField.setPromptText("555-555-5555");
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

    public void ExportToExcel(ActionEvent event) {

        String sql = "SELECT * FROM volunteer";

        try {
            Connection connection = db.connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            //microsft 2007-
            XSSFWorkbook workbook = new XSSFWorkbook();//for eatlir version use HSSF
            XSSFSheet sheet = workbook.createSheet("Information");
            XSSFRow header = sheet.createRow(0);//first rwo
            header.createCell(0).setCellValue("ID");//first col
            header.createCell(1).setCellValue("First Name");
            header.createCell(2).setCellValue("Last Name");
            header.createCell(3).setCellValue("Email");
            header.createCell(4).setCellValue("DOB");
            header.createCell(5).setCellValue("Gender");
            header.createCell(6).setCellValue("Phone");
            header.createCell(7).setCellValue("Emergency");
            header.createCell(8).setCellValue("Postal");

            int index = 1;

            while(resultSet.next()){
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(resultSet.getString("ID"));
                row.createCell(0).setCellValue(resultSet.getString("First Name"));
                row.createCell(0).setCellValue(resultSet.getString("Last Name"));
                row.createCell(0).setCellValue(resultSet.getString("Email"));
                row.createCell(0).setCellValue(resultSet.getString("DOB"));
                row.createCell(0).setCellValue(resultSet.getString("Gender"));
                row.createCell(0).setCellValue(resultSet.getString("Phone"));
                row.createCell(0).setCellValue(resultSet.getString("Emergency"));
                row.createCell(0).setCellValue(resultSet.getString("Postal"));
                index++;
            }

            FileOutputStream fileOutputStream = new FileOutputStream("Details.xlsx");
            workbook.write(fileOutputStream);
            fileOutputStream.close();

            AlertBox.display("EXPORT", "All Good");

            resultSet.close();;
            stmt.close();

            connection.close();
        } catch (SQLException e) {
            System.err.println("Load Database err " + e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}














