package com.foodrive.myapp;

import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;

public class adminLanded extends Form {
    final Resources res;

    public adminLanded(Resources res){
        //super("MENU");
        this.setUIID("MenuForm");
        this.res = res;
        this.setScrollableY(true);
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        //res.getImage("icon_navbar_arrow_back.png")
        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new Login(UIManager.initFirstTheme("/theme")).show());
        //this.getToolbar().addCommandToOverflowMenu("Overflow", icon, (e) -> Log.p("Clicked"));
        this.getToolbar().addCommandToSideMenu("Sidemenu", icon, (e) -> Log.p("Clicked"));
        setLayout(new BorderLayout());
        showAdmin();

    }

    public void showAdmin(){
        Container north = new Container(new BoxLayout(BoxLayout.Y_AXIS));
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
        this.addComponent(BorderLayout.SOUTH,centersouth);


    }


}
