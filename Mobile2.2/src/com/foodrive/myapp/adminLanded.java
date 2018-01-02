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
import database.DAO;
import database.people;

import java.util.ArrayList;

public class adminLanded extends Form {
    final Resources res;
    ArrayList<database.people> people = new ArrayList<people>();

    public adminLanded(Resources res){
        //super("MENU");
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        this.setUIID("MenuForm");
        this.res = res;
        this.setScrollableY(true);
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
       // FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        //res.getImage("icon_navbar_arrow_back.png");
        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new Login(UIManager.initFirstTheme("/theme")).show());
        this.getToolbar().addCommandToOverflowMenu("Overflow", icon, (e) -> Log.p("Clicked"));
       // this.getToolbar().addCommandToSideMenu("Sidemenu", icon, (e) -> Log.p("Clicked"));

        setLayout(new BorderLayout());
        showAdmin();

    }

    public void showAdmin(){
        Container north = BoxLayout.encloseY(
                // welcome,
                //profilePicLabel,,
                BorderLayout.centerAbsolute(new Label(" ")),
                BorderLayout.centerAbsolute(new Label(" ")),
                BorderLayout.centerAbsolute(new Label(" ")),
                BorderLayout.centerAbsolute(new Label(" "))
        );
        add(BorderLayout.NORTH,north);




        Button field1 = new Button("View Volunteers");
        field1.setUIID("adminUpLable");
        field1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new DAO().getAll();
            }
        });



        Label field2 = new Label("Search Volunteers");
        field2.setUIID("adminUpLable");

        Label field3 = new Label("Create Drive");
        field3.setUIID("adminUpLable");

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
                BorderLayout.centerAbsolute(field1),
                //BorderLayout.centerAbsolute(field2),
                BorderLayout.centerAbsolute(field3)
        );
        center.setScrollableY(true);
        add(BorderLayout.CENTER, center);

        this.addComponent(BorderLayout.SOUTH, signOut);



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


}
