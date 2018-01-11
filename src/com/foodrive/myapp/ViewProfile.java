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
import database.userObject;
import map.proccess;

import javax.swing.text.LabelView;

public class ViewProfile extends Form{
    private final Resources res;

    public ViewProfile(Resources res, String fn,String ln,String em,String hrt,String hrs,String pn,String cn,String cpn,String PW) {


        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        //setUIID("viewAllVols");
        this.res = res;
        this.setScrollableY(true);

        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        // FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        //res.getImage("icon_navbar_arrow_back.png");

        userObject user = new userObject();

        user.fname = fn;
        user.lname = ln;
        user.email = em;
        user.hourstotal = hrt;
        user.hourssigned = hrs;
        user.phone = pn;
        user.ename = cn;
        user.ephone = cpn;
        user.pass = PW;

        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new Login(UIManager.initFirstTheme("/theme")).show());
        this.getToolbar().addCommandToOverflowMenu("Edit", icon, (e) -> new UpdateForm(res, user).show());



        final Button ok = new Button("OK");
        final Dialog[] d = new Dialog[1];

        Label firsName = new Label(fn + " " + ln);//HERE
        firsName.setUIID("adminUpLable");
        firsName.getAllStyles().setMargin(LEFT, 0);
        /*firsName.addActionListener(evt -> {
            d[0] = new Dialog("Editing");
            TextField tf = new TextField("New Name");
            ok.addActionListener(evt1 -> {

            });
            d[0].addComponent(tf);
            d[0].addComponent(ok);
            d[0].show();
            //d.show("Editing", "", "OK", "Cancel");
        });*/

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                d[0].dispose();
            }
        });




        Label phone = new Label(pn);
        phone.setUIID("adminUpLable");
        phone.getAllStyles().setMargin(LEFT, 0);

        Label totalHrs = new Label("Hours Contributed: "+hrt);
        totalHrs.setUIID("adminUpLable");
        totalHrs.getAllStyles().setMargin(LEFT, 0);

        Label signed = new Label("Hours Signed: "+hrs);
        signed.setUIID("adminUpLable");
        signed.getAllStyles().setMargin(LEFT, 0);

        Label email = new Label(em);
        email.setUIID("adminUpLable");
        email.getAllStyles().setMargin(LEFT, 0);

        Label contactName = new Label("Contact: "+cn);
        contactName.setUIID("adminUpLable");
        contactName.getAllStyles().setMargin(LEFT, 0);

        Label contactNumber = new Label("Contact #:"+cpn);
        contactNumber.setUIID("adminUpLable");
        contactNumber.getAllStyles().setMargin(LEFT, 0);
/*
        Button upd = new Button("UPDATE");
        upd.setUIID("adminUpLable");
        upd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //new DAO().updateVolunteer(fn,ln,em,pn,PW,cn,cpn);
                new DAO().getAll();
            }
        });*/

        Container south = new Container(new FlowLayout(Component.CENTER));
        //south.addComponent(upd);
        this.addComponent(BorderLayout.SOUTH, south);

        proccess henryStuff=new proccess();

        Button map = new Button("map");
        map.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //      new Login(UIManager.initFirstTheme("/theme")).show();
                System.out.println("hello");
                Form hi = new Form("Hi World", BoxLayout.y());
                // hi.getToolbar().hideToolbar();
                // hi.getToolbar().setUIID("Container");
                henryStuff.setForm(hi);
                henryStuff.addAllButtons();
            }
        });

        Label spaceLabel = new Label(" ");
        Container by = BoxLayout.encloseY(
                // welcome,
                //profilePicLabel,
                spaceLabel,
                BorderLayout.center(map),
                BorderLayout.center(firsName),
                BorderLayout.center(phone),
                BorderLayout.center(email),
                BorderLayout.center(contactName),
                BorderLayout.center(contactNumber),
                BorderLayout.center(totalHrs),
                BorderLayout.center(signed)
        );
        add(BorderLayout.NORTH, by);

        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);

       /* //super("View Volunteers");
        this.setUIID("ViewVolunteers");
        //this.res = res;
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        //res.getImage("icon_navbar_arrow_back.png")
        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new Login(UIManager.initFirstTheme("/theme")).show());
        *//*this.getToolbar().addCommandToOverflowMenu("Overflow", icon, (e) -> Log.p("Clicked"));
        this.getToolbar().addCommandToSideMenu("Sidemenu", icon, (e) -> Log.p("Clicked"));*//*

        setLayout(new BorderLayout());
        Container north = new Container(new FlowLayout(Component.CENTER));
        north.setUIID("ViewVolunteersNorth");

        Button photoButton = new Button(res.getImage("camera.png"));
        photoButton.setUIID("PhotoButton");
        //north.addComponent(photoButton);


        this.addComponent(BorderLayout.NORTH, north);

        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        center.setUIID("ViewVolunteersCenter");
        new DAO().getAll();
        this.addComponent(BorderLayout.CENTER, center)*/;


    }

}