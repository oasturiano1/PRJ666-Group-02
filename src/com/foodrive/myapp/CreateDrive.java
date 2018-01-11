package com.foodrive.myapp;


import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import database.DAO;

import java.util.Date;

public class CreateDrive extends Form {
    public CreateDrive(Resources res) {
        this.setUIID("CreateDrive");
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        //res.getImage("icon_navbar_arrow_back.png")
        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new Login(UIManager.initFirstTheme("/theme")).show());
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
        firstName.setHint("Name");
        firstName.getHintLabel().setUIID("SignupFieldHint");
        firstName.setRows(2);

        Date startDate = new Date();

        center.addComponent(row1);

        this.addComponent(BorderLayout.CENTER, center);

        Button createDriveButton = new Button("Create Drive");
        createDriveButton.setGap(createDriveButton.getStyle().getFont().getHeight());
        createDriveButton.setUIID("CreateDriveButton");
        createDriveButton.setTextPosition(Component.LEFT);


        this.addComponent(BorderLayout.SOUTH, createDriveButton);
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


        createDriveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });


    }
}
