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

    /*public adminLanded(Resources res){
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

    }*/

    public adminLanded(Resources res, String adaminName){
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
        showAdminWithName(adaminName);

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
        //field1.addActionListener(new ActionListener() {
        //    @Override
        //    public void actionPerformed(ActionEvent evt) {
        //        new DAO().getAll();
        //    }
        //});
        Button field3 = new Button("Records");
        field3.setUIID("adminUpLable");
        field3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //new DAO().getAllDrives();//TODO FIX THIS
                //new DAO().getAll();
            }
        });
        Container center = BoxLayout.encloseY(
                // welcome,
                //profilePicLabel,,
                BorderLayout.centerAbsolute(field1),
                //BorderLayout.centerAbsolute(field2),
                BorderLayout.centerAbsolute(field3)
        );
        center.setScrollableY(true);
        add(BorderLayout.CENTER, center);
    }

    public void showAdminWithName(String adminName){
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
                new DAO().getAll(adminName);
            }
        });
        Button field3 = new Button("Records");
        field3.setUIID("adminUpLable");
        field3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new DAO().getAllDrives(adminName);
                //new DAO().getAll();
            }
        });
        Container center = BoxLayout.encloseY(
                // welcome,
                //profilePicLabel,,
                BorderLayout.centerAbsolute(field1),
                //BorderLayout.centerAbsolute(field2),
                BorderLayout.centerAbsolute(field3)
        );
        center.setScrollableY(true);
        add(BorderLayout.CENTER, center);
    }


}
