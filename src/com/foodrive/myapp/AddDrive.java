package com.foodrive.myapp;

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

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;



public class AddDrive extends Form {
    final Resources res;
    Boolean errCatch = false, errCatch0 = false;
    String errorList = "";
    String selectedDate;
/*
    private String convert(Picker datePicker) throws ParseException {
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/mm/yy");
        SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-mm-dd");
        Date date = dt1.parse(datePicker.getText());
        String date2 = dt2.format(date);
        selectedDate = date2;
        return  date2;
    }*/

    private String convert(Calendar datePicker) throws ParseException {
        SimpleDateFormat dt2 = new SimpleDateFormat("YYYY-MM-DD");
        String date2 = dt2.format(datePicker.getDate());
        selectedDate = date2;
        Log.p("You picked: " + selectedDate);
        return  date2;
    }

    public AddDrive(Resources res, String adminName){
        super("New Drive");
        this.setUIID("SignUpForm");
        this.res = res;
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        //res.getImage("icon_navbar_arrow_back.png")
        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new DAO().getAllDrives(adminName));
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

        

        //row1.addComponent(firstName);
        //row1.addComponent(lastName);
        center.addComponent(row1);
        center.setScrollableY(true);

      /*  TextField ephone = new TextField();
        ephone.setUIID("CreateDriveField");
        ephone.setHint("Emrgenc Number");
        ephone.getHintLabel().setUIID("CreateDriveFieldHint");
        center.addComponent(ephone);
        ephone.setRows(3);*/

        Label nameLabel = new Label();
        nameLabel.setUIID("CreateDriveField");
        center.addComponent(nameLabel);
        nameLabel.setText("Drive Name");

        TextField driveName = new TextField();
        driveName.setUIID("CreateDriveField");
        driveName.setHint("---");
        driveName.getHintLabel().setUIID("CreateDriveFieldHint");
        center.addComponent(driveName);
        driveName.setRows(3);

        Label calLabel = new Label();
        calLabel.setUIID("CreateDriveField");
        center.addComponent(calLabel);
        calLabel.setText("Start Date");

        //Calendar cld = new Calendar();
        //cld.addActionListener((e) -> Log.p("You picked: " + new Date(cld.getSelectedDay())));
        //center.addComponent(cld);

        Calendar datePicker = new Calendar();
        //datePicker.setType(Display.PICKER_TYPE_DATE);
        datePicker.setDate(new Date());
        center.addComponent(datePicker);

        try {
            calLabel.setText(convert(datePicker));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }


        datePicker.addActionListener((e) -> {
            try {
                calLabel.setText(convert(datePicker));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });



        //Container row4 = new Container(new BorderLayout());
        //Label code = new Label("+1");
        //code.setUIID("SignUpLabel");
        //row4.addComponent(BorderLayout.WEST, code);

        /*TextField phoneNumber = new TextField();
        phoneNumber.setUIID("CreateDriveField");
        phoneNumber.setHint("Phone Number");
        phoneNumber.getHintLabel().setUIID("CreateDriveFieldHint");
        row4.addComponent(BorderLayout.CENTER, phoneNumber);
        phoneNumber.setRows(3);*/

        //center.addComponent(row4);

        this.addComponent(BorderLayout.CENTER, center);

        Button updateButton = new Button("Create Drive", res.getImage("icon_arrow_forward copy.png"));
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

                try {
                    calLabel.setText(convert(datePicker));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

                if(driveName.getText().isEmpty() ||
                        datePicker.getDate().toString().isEmpty()){
                    errCatch = false;//AlertBox.display("ERROR", "All fields are required!");
                    errorList +="All Fields are required!\n";
                }else {
                    nameTester(driveName);
                }

                if(errCatch&&errCatch0){

                    //THIS IS TO CHECK IF DRIVE ALREADY EXISTS
                    ArrayList<drive> drives = new ArrayList<drive>();
                    ConnectionRequest connectionRequest = new ConnectionRequest() {


                        protected void readResponse(InputStream in) throws IOException {
                            JSONParser json = new JSONParser();

                            Reader reader = new InputStreamReader(in, "UTF-8");

                            Map<String, Object> data = json.parseJSON(reader);
                            List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");
                            drives.clear();
                            for (Map<String, Object> obj : content) {
                                drives.add(new drive((String) obj.get("id"), (String) obj.get("start"), (String) obj.get("name")));
                            }
                        }

                        //creating the list layout with data
                        @Override
                        protected void postResponse() {
                            //Resources LoginRes = UIManager.initFirstTheme("/theme");
                            //new DrivesList(LoginRes, drives, adminName).show();
                            if(drives.size() == 0){//If doesnt exist
                                new DAO().addDrive(selectedDate,driveName.getText());
                                ToastBar.Status error = ToastBar.getInstance().createStatus();
                                error.setMessage("Drive Added!");
                                error.setExpires(5000);
                                error.show();
                            }else {//If does, display error
                                ToastBar.Status error = ToastBar.getInstance().createStatus();
                                error.setMessage("Drive Already Exists!");
                                error.setExpires(5000);
                                error.show();
                            }
                        }
                        //-----------------------//
                    };
                    connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/mobileDriveExists.php?sdate="+selectedDate);
                    connectionRequest.setPost(false);
                    NetworkManager.getInstance().addToQueue(connectionRequest);





                } else {
                    //Form f = new Form("Toast", BoxLayout.y());
                    ToastBar.Status error = ToastBar.getInstance().createStatus();
                    error.setMessage(errorList);
                    error.setExpires(5000);
                    error.show();
                }

            }
        });


    }

    public void nameTester(TextField textField) {
        errCatch0 = formValidation.isNameCor(textField.getText());
        if(errCatch0 == false){
            errorList += "Invalid First Name!\n";
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