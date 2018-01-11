package com.foodrive.myapp;

import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import database.DAO;
import database.people;
import database.userObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
public class UpdateForm extends Form {
    final Resources res;
    Boolean errCatch = false, errCatch0 = false,errCatch1 = false,errCatch2 = false,errCatch3 = false,errCatch4 = true,errCatch5 = false,errCatch6 = false,errCatch7 = false;
    String errorList = "";

    public UpdateForm(Resources res, userObject user) {
        super("Update");
        this.setUIID("SignUpForm");
        this.res = res;
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        //res.getImage("icon_navbar_arrow_back.png")
        this.getToolbar().addCommandToLeftBar("",icon, (e) ->new ViewProfile(res,user.fname,user.lname,user.email,user.hourstotal,
                user.hourssigned,user.phone,user.ename,user.ephone,
                user.pass).show());
        /*this.getToolbar().addCommandToOverflowMenu("Overflow", icon, (e) -> Log.p("Clicked"));
        this.getToolbar().addCommandToSideMenu("Sidemenu", icon, (e) -> Log.p("Clicked"));*/

        setLayout(new BorderLayout());
        Container north = new Container(new FlowLayout(Component.CENTER));
        north.setUIID("SignUpNorth");




        Button photoButton = new Button(res.getImage("camera.png"));
        photoButton.setUIID("PhotoButton");
        //north.addComponent(photoButton);


        this.addComponent(BorderLayout.NORTH, north);

        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        center.setUIID("SignUpCenter");

        Container row1 = new Container(new GridLayout(1,2));

        TextField firstName = new TextField();
        firstName.setUIID("SignUpField");
        firstName.setHint("First Name");
        firstName.getHintLabel().setUIID("SignupFieldHint");
        firstName.setRows(3);
        firstName.setText(user.fname);

        TextField lastName = new TextField();
        lastName.setUIID("SignUpField");
        lastName.setHint("Last Name");
        lastName.getHintLabel().setUIID("SignupFieldHint");
        lastName.setRows(3);
        lastName.setText(user.lname);

        row1.addComponent(firstName);
        row1.addComponent(lastName);
        center.addComponent(row1);
        center.setScrollableY(true);

      /*  TextField ephone = new TextField();
        ephone.setUIID("SignUpField");
        ephone.setHint("Emrgenc Number");
        ephone.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(ephone);
        ephone.setRows(3);*/

        TextField password = new TextField();
        password.setUIID("SignUpField");
        password.setConstraint(TextField.PASSWORD);
        password.setHint("Password");
        password.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(password);
        password.setRows(3);
        password.setText(user.pass);

        TextField email = new TextField();
        email.setUIID("SignUpField");
        center.addComponent(email);
        email.setHint("Email Address");
        email.getHintLabel().setUIID("SignupFieldHint");
        email.setRows(3);
        email.setText(user.email);

       /* TextField Name = new TextField();
        Name.setUIID("SignUpField");
        Name.setHint("Contact Name");
        Name.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(Name);
        Name.setRows(3);*/

        TextField phoneNumber = new TextField();
        phoneNumber.setUIID("SignUpField");
        phoneNumber.setHint("Phone Number");
        phoneNumber.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(phoneNumber);
        phoneNumber.setRows(3);
        phoneNumber.setText(user.phone);

        TextField contactName = new TextField();
        contactName.setUIID("SignUpField");
        contactName.setHint("Contact Name");
        contactName.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(contactName);
        contactName.setRows(3);
        contactName.setText(user.ename);

        TextField contactNumber = new TextField();
        contactNumber.setUIID("SignUpField");
        contactNumber.setHint("Contact number");
        contactNumber.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(contactNumber);
        contactNumber.setRows(3);
        contactNumber.setText(user.ephone);


        //Container row4 = new Container(new BorderLayout());
        //Label code = new Label("+1");
        //code.setUIID("SignUpLabel");
        //row4.addComponent(BorderLayout.WEST, code);

        /*TextField phoneNumber = new TextField();
        phoneNumber.setUIID("SignUpField");
        phoneNumber.setHint("Phone Number");
        phoneNumber.getHintLabel().setUIID("SignupFieldHint");
        row4.addComponent(BorderLayout.CENTER, phoneNumber);
        phoneNumber.setRows(3);*/

        //center.addComponent(row4);

        this.addComponent(BorderLayout.CENTER, center);

        Button updateButton = new Button("Update Profile", res.getImage("icon_arrow_forward copy.png"));
        updateButton.setGap(updateButton.getStyle().getFont().getHeight());
        updateButton.setUIID("SignUpButton");
        updateButton.setTextPosition(Component.LEFT);


        this.addComponent(BorderLayout.SOUTH, updateButton);
        this.addCommand(new Command("Done") {

            @Override
            public void actionPerformed(ActionEvent evt) {

            }

        });

        this.setBackCommand(new Command("", res.getImage("icon_navbar_arrow_back.png")) {

            @Override
            public void actionPerformed(ActionEvent evt) {

            }

        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                errorList = "";
                errCatch = true;

                if(firstName.getText().isEmpty() ||
                        lastName.getText().isEmpty()||
                        phoneNumber.getText().isEmpty()||
                        email.getText().isEmpty()||
                        contactName.getText().isEmpty()||
                        contactNumber.getText().isEmpty()||
                        password.getText().isEmpty()){
                    errCatch = false;//AlertBox.display("ERROR", "All fields are required!");
                    errorList +="All Fields are required!\n";
                }else {
                    fNameTester(firstName);
                    lNameTester(lastName);
                    phoneTester(phoneNumber);
                    mailTester(email);
                    eNameTester(contactName);
                    ePhoneTester(contactNumber);
                    passConfTester(password);
                }

                if(errCatch&&errCatch0&&errCatch1&&errCatch2&&errCatch3&&errCatch4&&errCatch5&&errCatch6&&errCatch7){
                    String fn = firstName.getText();
                    String ln = lastName.getText();
                    String em = email.getText();
                    String ph = phoneNumber.getText();
                    String passWord = password.getText();
                    // String name = Name.getText();
                    //String gen = HoursSigned.getText();
                    //String ep = ephone.getText();
                    String contactNum = contactNumber.getText();
                    String contactNames = contactName.getText();


                    //TODO UPDATE
                    user.fname = fn;
                    user.lname = ln;
                    user.email = em;
                    user.phone = ph;
                    user.pass = passWord;
                    user.ename = contactNames;
                    user.ephone = contactNum;

                    //THIS IS TO CHECK IF EMAIL ALREADY EXISTS
                    ArrayList<String> reco = new ArrayList<String>();
                    boolean exists = false;
                    ConnectionRequest connectionRequest = new ConnectionRequest() {


                        protected void readResponse(InputStream in) throws IOException {
                            JSONParser json = new JSONParser();

                            Reader reader = new InputStreamReader(in, "UTF-8");

                            Map<String, Object> data = json.parseJSON(reader);
                            java.util.List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");
                            reco.clear();


                            for (Map<String, Object> obj : content) {
                                String r = (String)obj.get("email");
                                reco.add(r);
                            }
                        }

                        //creating the list layout with data
                        @Override
                        protected void postResponse() {
                            //Resources LoginRes = UIManager.initFirstTheme("/theme");
                            //new DrivesList(LoginRes, drives, adminName).show();
                            if(reco.size() == 0){//If doesnt exist

                                new DAO().updateVolunteer(fn,ln,em,ph,passWord,contactNames,contactNum, user.hourstotal, user.hourssigned);
                                ToastBar.Status error = ToastBar.getInstance().createStatus();
                                error.setMessage("Profile Updated!");
                                error.setExpires(5000);
                                error.show();
                            }else {//If does, display error
                                ToastBar.Status error = ToastBar.getInstance().createStatus();
                                error.setMessage("Email Already Exists!");
                                error.setExpires(5000);
                                error.show();
                            }
                        }

                    };
                    connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/mobileEmailExists.php?email="+em);
                    connectionRequest.setPost(false);
                    NetworkManager.getInstance().addToQueue(connectionRequest);

                    //new DAO().addLogin(em,passWord,"USER");
                } else {
                    //Form f = new Form("Toast", BoxLayout.y());
                    ToastBar.Status error = ToastBar.getInstance().createStatus();
                    error.setMessage(errorList);
                    error.setExpires(5000);
                    error.show();
                }


               /*else{

                   Dialog d = new Dialog("Add to my people shelf");
                   TextArea popupBody = new TextArea("people successfully added");
                   popupBody.setUIID("PopupBody");
                   popupBody.setEditable(false);
                   d.setLayout(new BorderLayout());
                   d.addVolunteer(BorderLayout.CENTER, popupBody);
                   d.showDialog();
               }*/
            }
        });


    }

    public void fNameTester(TextField textField) {
        errCatch0 = formValidation.isNameCor(textField.getText());
        if(errCatch0 == false){
            errorList += "Invalid First Name!\n";
        }
    }

    public void lNameTester(TextField textField) {
        errCatch1 = formValidation.isNameCor(textField.getText());
        if(errCatch1 == false){
            errorList += "Invalid Last Name!\n";
        }
    }

    public void mailTester(TextField textField){
        errCatch2 = formValidation.isMail(textField.getText());

        //Connection connection = db.connect();
        //boolean emailExists = db.emailExists(email.getText());
        //connection.close();

        if (errCatch2 == false) {
            errorList += "Invalid Email Address!\n";
        }

    }

    public void phoneTester(TextField textField) {
        errCatch3 = formValidation.isPhone(textField.getText());
        if (errCatch3 == false) {
            errorList += "Invalid Phone Number!\n(XXX-XXX-XXXX or XXXXXXXXXX)\n";
        }

    }


    public void passConfTester(TextField origin) {
        errCatch5 = formValidation.isPassCor(origin.getText());
        //String o1 = origin.getText().toString();
        // String o2 = textField.getText().toString();

        //errCatch = formValidation.isPassCor(textField.getText());

        //if(o1.compareTo(o2) != 0){
        //    textField.setFocusColor(RED);
        //    textField.setPromptText("Invalid - Password does not match!");
        //   errCatch5 = false;
        //}else if (errCatch5 == false) {
        //    textField.setFocusColor(RED);
        //    textField.setPromptText("Invalid Password");
        //} else {
        //    textField.setFocusColor(GREEN);
        //    textField.setPromptText("Password");
        //    errCatch4 = true;
        //passTester(origin,textField);
        //}

    }

    public void eNameTester(TextField textField) {
        errCatch6 = formValidation.isNameCor(textField.getText());
        if(errCatch0 == false){
            errorList += "Invalid Emergency Contact Name!\n";
        }
    }

    public void ePhoneTester(TextField textField) {
        errCatch7 = formValidation.isPhone(textField.getText());
        if (errCatch7 == false) {
            errorList += "Invalid Emergency Contact Phone Number!\n(XXX-XXX-XXXX or XXXXXXXXXX)\n";
        }

    }

    public boolean checkNullData(String text, String lastNameText, String emailText, String phoneNumberText, String passwordText, String contactNumberText, String contactNameText){
        boolean valid = true;
        if(text == null || text.isEmpty())
            valid = false;
        else if(lastNameText == null || lastNameText.isEmpty())
            valid = false;


        return valid;
    }
}