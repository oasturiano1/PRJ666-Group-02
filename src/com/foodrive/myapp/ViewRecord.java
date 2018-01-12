package com.foodrive.myapp;


import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import database.DAO;
import database.userObject;

public class ViewRecord extends Form {
    private final Resources res;

    private void deleteFunction(String email,String adminName){
        new DAO().delVolunteer(email);
        new DAO().getAll(adminName);
    }

    public ViewRecord(Resources res, record rec, String odate, int driveId, String start, String adminName) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        //setUIID("viewAllVols");
        this.res = res;
        this.setScrollableY(true);

        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        // FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        //res.getImage("icon_navbar_arrow_back.png");


        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new DAO().getAllDriveDayRecords(odate,driveId, start,adminName));
        this.getToolbar().addCommandToOverflowMenu("Edit", icon, (e) -> new editRecord(res, rec,odate,driveId, start,adminName).show());
        this.getToolbar().addCommandToOverflowMenu("Delete",icon, (e) -> {
            new DAO().delRecord(rec.oprecid,rec.id);
            new DAO().getAllDrives(adminName);

        });
        //this.getToolbar().addCommandToOverflowMenu("Delete", icon, (e) -> deleteFunction(user.email));

        final Button ok = new Button("OK");
        final Dialog[] d = new Dialog[1];

        Label firsName = new Label(rec.fname+" "+rec.lname);//HERE
        firsName.setUIID("adminUpLable");
        firsName.getAllStyles().setMargin(LEFT, 0);

        Label email = new Label(rec.mail);
        email.setUIID("adminUpLable");
        email.getAllStyles().setMargin(LEFT, 0);

        Label phone = new Label(rec.phone);
        phone.setUIID("adminUpLable");
        phone.getAllStyles().setMargin(LEFT, 0);

        Label totalHrs = new Label("Hours Contributed: "+rec.hoursCon);
        totalHrs.setUIID("adminUpLable");
        totalHrs.getAllStyles().setMargin(LEFT, 0);

        Label contactName = new Label("Contact: "+rec.ename);
        contactName.setUIID("adminUpLable");
        contactName.getAllStyles().setMargin(LEFT, 0);

        Label contactNumber = new Label("Contact #:"+rec.ephone);
        contactNumber.setUIID("adminUpLable");
        contactNumber.getAllStyles().setMargin(LEFT, 0);

        Label superv = new Label("Supervisor:"+rec.supervisor);
        superv.setUIID("adminUpLable");
        superv.getAllStyles().setMargin(LEFT, 0);

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
                BorderLayout.center(superv)
        );
        add(BorderLayout.NORTH, by);

        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }

}
