package com.foodrive.myapp;


import com.codename1.components.ToastBar;
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

import java.text.ParseException;
import java.util.Date;

public class editRecord extends Form {
    private final Resources res;
    String errorList = "";
    Boolean errCatch = false;

    private void deleteFunction(String email,String adminName){
        new DAO().delVolunteer(email);
        new DAO().getAll(adminName);
    }

    public editRecord(Resources res, record rec, String odate, int driveId, String start, String adminName) {

        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        //setUIID("viewAllVols");
        this.res = res;
        this.setScrollableY(true);
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);



        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new ViewRecord(res, rec,odate,driveId, start, adminName).show());


        setLayout(new BorderLayout());
        Container north = new Container(new FlowLayout(Component.CENTER));
        north.setUIID("SignUpNorth");



        Button photoButton = new Button(res.getImage("camera.png"));
        photoButton.setUIID("PhotoButton");
        //north.addComponent(photoButton);


        this.addComponent(BorderLayout.NORTH, north);

        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        center.setUIID("EditRecordCenter");

        Container row1 = new Container(new GridLayout(1,2));

        center.addComponent(row1);
        center.setScrollableY(true);

        Label firsName = new Label(rec.fname+" "+rec.lname);//HERE
        firsName.setUIID("EditRecordCenter");
        center.addComponent(firsName);


        Label totalHrs = new Label("Hours Contributed: "+rec.hoursCon);
        totalHrs.setUIID("EditRecordCenter");
        center.addComponent(totalHrs);



        Label newHrsLabel = new Label("New Hours Contributed: ");
        newHrsLabel.setUIID("EditRecordCenter");
        center.addComponent(newHrsLabel);

        TextField newHrs = new TextField(rec.hoursCon.toString());
        newHrs.setUIID("EditRecordCenter");
        newHrs.setHint("New Hours Contributed");
        newHrs.setText(rec.hoursCon.toString());
        center.addComponent(newHrs);
        newHrs.setRows(2);


        this.addComponent(BorderLayout.CENTER, center);

        Button updateButton = new Button("Update Record", res.getImage("icon_arrow_forward copy.png"));
        updateButton.setGap(updateButton.getStyle().getFont().getHeight());
        updateButton.setUIID("SignUpButton");
        updateButton.setTextPosition(Component.LEFT);


        this.addComponent(BorderLayout.SOUTH, updateButton);
        /*this.addCommand(new Command("Done") {

            @Override
            public void actionPerformed(ActionEvent evt) {

            }

        });

        this.setBackCommand(new Command("", res.getImage("icon_navbar_arrow_back.png")) {

            @Override
            public void actionPerformed(ActionEvent evt) {

            }

        });*/


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //TODO UPDATE
                errorList = "";
                errCatch = true;
                //rec.hoursCon = Double.parseDouble(newHrs.getText());

                if(newHrs.getText().compareTo("")==0){
                    errCatch = false;
                    errorList += "Hours is required!\n";
                } else{
                    try{
                        rec.hoursCon = Double.parseDouble(newHrs.getText());

                        if(rec.hoursCon > 24 || rec.hoursCon < 0){
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
                    new DAO().updateRecord(rec,adminName,driveId,start,odate);
                    ToastBar.Status error = ToastBar.getInstance().createStatus();
                    error.setMessage("Record Updated!");
                    error.setExpires(5000);
                    error.show();
                }else {
                    ToastBar.Status error = ToastBar.getInstance().createStatus();
                    error.setMessage(errorList);
                    error.setExpires(5000);
                    error.show();
                }

            }
        });


    }
}

