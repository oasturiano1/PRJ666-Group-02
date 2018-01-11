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

public class AddVolunteerForm extends Form {
    final Resources res;

    public AddVolunteerForm(Resources res, String adminName) {
        super("Add Volunteer");
        this.setUIID("SignUpForm");
        this.res = res;
        this.setScrollableY(true);
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        //res.getImage("icon_navbar_arrow_back.png")
        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new adminLanded(res,adminName).show());
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
        firstName.getHintLabel().setUIID("SignUpFieldHint");
        firstName.setRows(3);

        TextField lastName = new TextField();
        lastName.setUIID("SignUpField");
        lastName.setHint("Last Name");
        lastName.getHintLabel().setUIID("SignUpFieldHint");
        lastName.setRows(3);


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
        password.getHintLabel().setUIID("SignUpFieldHint");
        center.addComponent(password);
        password.setRows(3);

        TextField email = new TextField();
        email.setUIID("SignUpField");
        center.addComponent(email);
        email.setHint("Email Address");
        email.getHintLabel().setUIID("SignUpFieldHint");
        email.setRows(3);

       /* TextField Name = new TextField();
        Name.setUIID("SignUpField");
        Name.setHint("Contact Name");
        Name.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(Name);
        Name.setRows(3);*/

        TextField contactName = new TextField();
        contactName.setUIID("SignUpField");
        contactName.setHint("Contact Name");
        contactName.getHintLabel().setUIID("SignUpFieldHint");
        center.addComponent(contactName);
        contactName.setRows(3);

        TextField contactNumber = new TextField();
        contactNumber.setUIID("SignUpField");
        contactNumber.setHint("Contact number");
        contactNumber.getHintLabel().setUIID("SignUpFieldHint");
        center.addComponent(contactNumber);
        contactNumber.setRows(3);


        Container row4 = new Container(new BorderLayout());
        Label code = new Label("+1");
        code.setUIID("SignUpLable");
        row4.addComponent(BorderLayout.WEST, code);

        TextField phoneNumber = new TextField();
        phoneNumber.setUIID("SignUpField");
        phoneNumber.setHint("Phone Number");
        phoneNumber.getHintLabel().setUIID("SignUpFieldHint");
        row4.addComponent(BorderLayout.CENTER, phoneNumber);
        phoneNumber.setRows(3);

        center.addComponent(row4);

        this.addComponent(BorderLayout.CENTER, center);

        Button getStarted = new Button("Add Volunteer", res.getImage("icon_arrow_forward copy.png"));
        getStarted.setGap(getStarted.getStyle().getFont().getHeight());
        getStarted.setUIID("SignUpButton");
        getStarted.setTextPosition(Component.LEFT);


        this.addComponent(BorderLayout.SOUTH, getStarted);
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


        getStarted.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(checkNullData(firstName.getText(), lastName.getText(),email.getText(),phoneNumber.getText(),password.getText(),contactName.getText(), contactNumber.getText())){
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

                                new DAO().addVolunteer(fn,ln,em,ph,passWord,contactNum,contactNames);
                                ToastBar.Status error = ToastBar.getInstance().createStatus();
                                error.setMessage("Volunteer Added!");
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



                    //new DAO().getAll(adminName);
                } /*else{

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

    public boolean checkNullData(String text, String lastNameText, String emailText, String phoneNumberText, String passwordText, String contactNumberText, String contactNameText){
        boolean valid = true;
        if(text == null || text.isEmpty())
            valid = false;
        else if(lastNameText == null || lastNameText.isEmpty())
            valid = false;


        return valid;
    }
}