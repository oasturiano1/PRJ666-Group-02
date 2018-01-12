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

public class DaysList extends Form {
    private final Resources res;

    public DaysList(Resources res, ArrayList<String> dates, int driveId, String start,String adminName) {

        super(new BorderLayout());
        //setUIID("viewAllVols");
        this.res = res;
        this.setScrollableY(true);

        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        // FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);

        //res.getImage("icon_navbar_arrow_back.png");

        //TODO MAKE THE POINT TO CORRECT PLACES
        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new DAO().getAllDrives(adminName));
        this.getToolbar().addCommandToOverflowMenu("Add Record",icon, (e) -> new DAO().getAllUsersForAdd(driveId, start,adminName));
        this.getToolbar().addCommandToOverflowMenu("Delete",icon, (e) -> {

            if(dates.size() == 0){
                new DAO().delDrive(driveId);
                new DAO().getAllDrives(adminName);
            }else{
                ToastBar.Status error = ToastBar.getInstance().createStatus();
                error.setMessage("Drive must have no records to delete!");
                error.setExpires(5000);
                error.show();
            }

        });

        //this.getToolbar().addCommandToOverflowMenu("Edit", icon, (e) -> new UpdateForm(res, user).show());

        Label driveName = new Label();
        driveName.setUIID("driveLabel");
        driveName.setText("Drive Days");
        //add(BorderLayout.CENTER,driveName);

        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        list.add(driveName);//
        for(int iter = 0; iter<dates.size(); iter++){
            MultiButton mb = new MultiButton(dates.get(iter));
            mb.setTextLine2("more...");
            FontImage.setMaterialIcon(mb,FontImage.MATERIAL_PERSON);
            int finalIter = iter;
            mb.addActionListener(evt -> {
                new DAO().getAllDriveDayRecords(dates.get(finalIter),driveId,start,adminName);
                //new DAO().getAllDriveDays(dates.get(finalIter).toString());
                //ToastBar.showMessage("Clicked: ",FontImage.MATERIAL_PERSON);
                //com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                //new ViewVolunteers(LoginRes,FN.get(finalIter),LN.get(finalIter),EM.get(finalIter),
                //        HRT.get(finalIter),HRS.get(finalIter),PN.get(finalIter),CN.get(finalIter),
                //        CPN.get(finalIter),PW.get(finalIter)).show();
            });
            list.add(mb);
        }
        getContentPane().animateLayout(150);
        add(BorderLayout.CENTER,list);

    }
}
