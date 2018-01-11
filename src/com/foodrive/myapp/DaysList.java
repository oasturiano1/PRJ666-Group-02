package com.foodrive.myapp;

import com.codename1.components.MultiButton;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import database.DAO;

import java.util.ArrayList;

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
        //this.getToolbar().addCommandToOverflowMenu("Edit", icon, (e) -> new UpdateForm(res, user).show());


        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
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
