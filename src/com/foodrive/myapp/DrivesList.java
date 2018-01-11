package com.foodrive.myapp;

import com.codename1.components.MultiButton;
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

import java.util.ArrayList;

public class DrivesList extends Form {
    private final Resources res;

    public DrivesList(Resources res, ArrayList<drive> drives,String adminName) {


        super(new BorderLayout());
        //setUIID("viewAllVols");
        this.res = res;
        this.setScrollableY(true);

        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        // FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        //res.getImage("icon_navbar_arrow_back.png");

        //TODO MAKE THE POINT TO CORRECT PLACES
        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new adminLanded(res,adminName).show());
        this.getToolbar().addCommandToOverflowMenu("New Drive",icon, (e) -> new AddDrive(res, adminName).show());
        //this.getToolbar().addCommandToOverflowMenu("Edit", icon, (e) -> new UpdateForm(res, user).show());


        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        for(int iter = 0; iter<drives.size(); iter++){
            MultiButton mb = new MultiButton(drives.get(iter).name+" "+drives.get(iter).start);
            mb.setTextLine2("more...");
            FontImage.setMaterialIcon(mb,FontImage.MATERIAL_PERSON);
            int finalIter = iter;
            mb.addActionListener(evt -> {
                new DAO().getAllDriveDays(drives.get(finalIter).id,drives.get(finalIter).start, adminName);
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
