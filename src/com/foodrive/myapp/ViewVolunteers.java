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

import javax.swing.text.LabelView;

public class ViewVolunteers extends Form{
    private final Resources res;

    private void deleteFunction(String email, String adminName){
        new DAO().delVolunteer(email);
        new DAO().getAll(adminName);
    }

    public ViewVolunteers(Resources res, String fn,String ln,String em,String hrt,String hrs,String pn,String cn,String cpn,String PW, String adminName) {
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



        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new DAO().getAll(adminName));
        this.getToolbar().addCommandToOverflowMenu("Edit", icon, (e) -> new AdminUpdateForm(res, user,adminName).show());
        this.getToolbar().addCommandToOverflowMenu("Delete", icon, (e) -> deleteFunction(user.email,adminName));

        final Button ok = new Button("OK");
        final Dialog[] d = new Dialog[1];

        Label firsName = new Label(fn+" "+ln);//HERE
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



        Label spaceLabel = new Label(" ");
        Container by = BoxLayout.encloseY(
                // welcome,
                //profilePicLabel,
                spaceLabel,
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
    }

}