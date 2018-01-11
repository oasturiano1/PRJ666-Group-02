package com.foodrive.myapp;

import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;

public class userLanded extends Form {
    final Resources res;

    public userLanded(Resources res){
        //super("MENU");
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        this.setUIID("MenuForm");
        this.res = res;
        this.setScrollableY(true);
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        // FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        Image icon = res.getImage("backButton.png");
        //res.getImage("backButton.png");
        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new Login(UIManager.initFirstTheme("/theme")).show());
        this.getToolbar().addCommandToOverflowMenu("Overflow", icon, (e) -> Log.p("Clicked"));
        // this.getToolbar().addCommandToSideMenu("Sidemenu", icon, (e) -> Log.p("Clicked"));

        setLayout(new BorderLayout());
        showUser();

    }

    public void showUser(){
        Container north = BoxLayout.encloseY(
                // welcome,
                //profilePicLabel,,
                BorderLayout.centerAbsolute(new Label(" ")),
                BorderLayout.centerAbsolute(new Label(" ")),
                BorderLayout.centerAbsolute(new Label(" ")),
                BorderLayout.centerAbsolute(new Label(" "))
        );
        add(BorderLayout.NORTH,north);




        //Label field1 = new Label("View Volunteers");
        //field1.setUIID("userUpLable");
        Button ViewVolunteers = new Button("View Volunteers");

        //Label field2 = new Label("Search Volunteers");
        //field2.setUIID("userUpLable");
        Button SearchVolunteers = new Button("Search Volunteers");

//        Label field3 = new Label("Create Drive");
//        field3.setUIID("userUpLable");

        Button CreateDrive = new Button("Create Drive");

        Button signOut = new Button("LOGOUT");
        signOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Login(UIManager.initFirstTheme("/theme")).show();
            }
        });
        //getStarted.setGap(getStarted.getStyle().getFont().getHeight());
        signOut.setUIID("LogOutButton");

        //getStarted.setTextPosition(Component.CENTER);
        Container center = BoxLayout.encloseY(
                // welcome,
                //profilePicLabel,,
                BorderLayout.centerAbsolute(CreateDrive),
                BorderLayout.centerAbsolute(ViewVolunteers),
                BorderLayout.centerAbsolute(SearchVolunteers)
        );
        center.setScrollableY(true);
        add(BorderLayout.CENTER, center);

        this.addComponent(BorderLayout.SOUTH, signOut);

        SearchVolunteers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                   //new SearchVolunteers(res).show();
                }
        });
        ViewVolunteers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //new ViewVolunteers(res).show();
            }
        });
        CreateDrive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new CreateDrive(res).show();
            }
        });

    }

      /*  Container north = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        north.setUIID("SignUpCenter");
        north.setUIID("menuNorth");

        Label viewAllVolunteers = new Label("VIEW ALL","viewAll");
        viewAllVolunteers.setUIID("VAV");
        north.addComponent(viewAllVolunteers);
        this.addComponent(BorderLayout.NORTH, north);


        Container centersouth = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label time = new Label("Time","viewAll");
        time.setUIID("VAV2");
        centersouth.addComponent(time);
        this.addComponent(BorderLayout.SOUTH,centersouth);*/


    }



