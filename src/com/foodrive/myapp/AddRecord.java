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

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;



public class AddRecord extends Form {
    final Resources res;
    Boolean errCatch = false, errCatch0 = false,errCatch1 = false,errCatch2 = false,errCatch3 = false,errCatch4 = true,errCatch5 = false,errCatch6 = false,errCatch7 = false;
    String errorList = "";
    String selectedDate = "";
    userObject picked = new userObject();

    /*private String convert(Picker datePicker) throws ParseException {
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

    public AddRecord(Resources res, ArrayList<userObject> recs, int driveId, String start, userObject pick,String adminName, String datePicked, String hours){
        super("New Record");
        this.setUIID("SignUpForm");
        this.res = res;
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        //res.getImage("icon_navbar_arrow_back.png")
        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new DAO().getAllDrives(adminName));
        /*this.getToolbar().addCommandToOverflowMenu("Overflow", icon, (e) -> Log.p("Clicked"));
        this.getToolbar().addCommandToSideMenu("Sidemenu", icon, (e) -> Log.p("Clicked"));*/

        newRecord rec = new newRecord();


        picked = pick;

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

        center.addComponent(row1);
        center.setScrollableY(true);


        Label calLabel = new Label();
        calLabel.setUIID("CreateRecordField");
        center.addComponent(calLabel);
        //if(datePicked.compareTo("")==0) {
            calLabel.setText("Record Date");
        //}else {
        //    calLabel.setText(datePicked);
        //    selectedDate= datePicked;
        //}


        /*Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        datePicker.setDate(new Date());
        center.addComponent(datePicker);*/

        //Calendar cld = new Calendar();
        //cld.addActionListener((e) -> Log.p("You picked: " + cld.getCurrentDate()));
        //center.addComponent(cld);


        Calendar datePicker = new Calendar();
        //datePicker.setType(Display.PICKER_TYPE_DATE);
        datePicker.setDate(new Date());
        center.addComponent(datePicker);
        datePicker.addActionListener((e) -> Log.p("You picked: " + datePicker.getDate()));

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

        Label selVolLabel = new Label();
        selVolLabel.setUIID("CreateRecordField");
        center.addComponent(selVolLabel);
        //selVolLabel.setText(picked.fname + " " + picked.lname);

        Button selVol = new Button(picked.fname + " " + picked.lname);
        selVol.setUIID("PhotoButton");

        if(picked.fname.compareTo("") == 0 ||picked.lname.compareTo("") == 0)
            selVol.setText("Select Volunteer");

        center.addComponent(selVol);



        Label nameLabel = new Label();
        nameLabel.setUIID("CreateRecordField");
        center.addComponent(nameLabel);
        nameLabel.setText("Hours Contributed");

        
        TextField hoursCon = new TextField();
        hoursCon.setUIID("CreateRecordField");
        hoursCon.setHint("---");
        hoursCon.getHintLabel().setUIID("CreateRecordFieldHint");
        center.addComponent(hoursCon);
        hoursCon.setText(hours);
        hoursCon.setRows(2);

        if(hours.compareTo("")==0)
            hoursCon.setText("");
        else
            hoursCon.setText(hours);


        selVol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new SelectVolunteer(res,recs,driveId,start,adminName,selectedDate,hoursCon.getText()).show();
            }
        });


        this.addComponent(BorderLayout.CENTER, center);

        Button updateButton = new Button("Create Record", res.getImage("icon_arrow_forward copy.png"));
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

                if (selectedDate.compareTo("") == 0 ||
                        pick.fname.compareTo("") == 0 ||
                        hoursCon.getText().compareTo("") == 0)
                {

                    if (selectedDate.compareTo("") == 0)
                        errorList += "Date required!\n";
                    if (pick.fname.compareTo("") == 0)
                        errorList += "Volunteer required!\n";
                    if( hoursCon.getText().compareTo("") == 0)
                        errorList += "Hours contributed required!\n";
                    errCatch = false;
                }else{
                    try{
                        Double i = Double.parseDouble(hoursCon.getText());

                        if(i > 24 || i < 0){
                            errCatch = false;
                            errorList += "Hours must be between 1 to 24!\n";
                        }else {
                            errCatch = true;

                        }
                    }catch (NumberFormatException e){
                        errCatch = false;
                        errorList += "Hours must be a valid number!\n";
                    }
                }




                if(errCatch){

                    rec.driveId = driveId;
                    rec.volunteerId = pick.id;
                    rec.hoursContributed = Double.parseDouble(hoursCon.getText());
                    rec.driveStartDate = start;
                    rec.operationDayDate = selectedDate;
                    String supervisor = adminName;

                    //THIS IS TO CHECK IF RECORD ALREADY EXISTS
                    ArrayList<newRecord> reco = new ArrayList<newRecord>();
                    boolean exists = false;
                    ConnectionRequest connectionRequest = new ConnectionRequest() {


                        protected void readResponse(InputStream in) throws IOException {
                            JSONParser json = new JSONParser();

                            Reader reader = new InputStreamReader(in, "UTF-8");

                            Map<String, Object> data = json.parseJSON(reader);
                            java.util.List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");
                            reco.clear();


                            for (Map<String, Object> obj : content) {
                                newRecord r = new newRecord();

                                r.driveStartDate = (String) obj.get("start");

                                reco.add(r);
                            }
                        }

                        //creating the list layout with data
                        @Override
                        protected void postResponse() {
                            //Resources LoginRes = UIManager.initFirstTheme("/theme");
                            //new DrivesList(LoginRes, drives, adminName).show();
                            if (reco.size() == 0) {//If doesnt exist
                                new DAO().addRecord(rec, supervisor);
                                ToastBar.Status error = ToastBar.getInstance().createStatus();
                                error.setMessage("Record Added!");
                                error.setExpires(5000);
                                error.show();
                            } else {//If does, display error
                                ToastBar.Status error = ToastBar.getInstance().createStatus();
                                error.setMessage("Record Already Exists!");
                                error.setExpires(5000);
                                error.show();
                            }
                        }
                        //-----------------------//
                    };
                    connectionRequest.setUrl("http://myvmlab.senecacollege.ca:5936/phpmyadmin/DAO/mobileRecordExists.php?volid=" + rec.volunteerId + "&driveid=" + rec.driveId + "&opday=" + rec.operationDayDate);
                    connectionRequest.setPost(false);
                    NetworkManager.getInstance().addToQueue(connectionRequest);
                }else{
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