package com.foodrive.myapp;


import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import database.DAO;
import database.userObject;

import java.util.ArrayList;

public class SelectVolunteer extends Form {
    private final Resources res;


    private void picked(Resources res, ArrayList<userObject> recs, int driveId, String start, userObject pick, String adminName, String datePicked, String hours){

        AddRecord page = new AddRecord(res,recs,driveId,start,pick,adminName,datePicked,hours);

        page.show();

    }


    public SelectVolunteer(Resources res, ArrayList<userObject> recs, int driveId, String start, String adminName, String datePicked, String hours) {

        super(new BorderLayout());
        this.res = res;
        this.setScrollableY(true);

        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        // FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        //res.getImage("icon_navbar_arrow_back.png");

        //TODO MAKE THE POINT TO CORRECT PLACES
        //this.getToolbar().addCommandToLeftBar("",icon, (e) -> new DAO().getAllDriveDays(driveId,start));
        //this.getToolbar().addCommandToOverflowMenu("Edit", icon, (e) -> new UpdateForm(res, user).show());



        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);

        for(int iter = 0; iter<recs.size(); iter++){
            MultiButton mb = new MultiButton(recs.get(iter).fname + " " + recs.get(iter).lname);
            //mb.setTextLine2("more...");
            FontImage.setMaterialIcon(mb,FontImage.MATERIAL_PERSON);
            int finalIter = iter;
            mb.addActionListener(evt -> {
                //TODO WHEN CLICKED
                picked(res,recs,driveId,start,recs.get(finalIter),adminName,datePicked,hours);
            });
            list.add(mb);
        }
        getContentPane().animateLayout(150);
        add(BorderLayout.CENTER,list);


        this.getToolbar().addSearchCommand(evt -> {
            String text = (String)evt.getSource();
            if(text == null || text.length() == 0){
                for(Component cmp: this.getContentPane()){
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                    list.removeAll();
                    for(int iter = 0; iter<recs.size(); iter++){
                        MultiButton mb = new MultiButton(recs.get(iter).fname + " " + recs.get(iter).lname);
                        //mb.setTextLine2("more...");
                        FontImage.setMaterialIcon(mb,FontImage.MATERIAL_PERSON);
                        int finalIter = iter;
                        mb.addActionListener(et -> {
                            //TODO WHEN CLICKED
                            picked(res,recs,driveId,start,recs.get(finalIter),adminName,datePicked,hours);
                        });
                        list.add(mb);
                    }
                }
                this.getContentPane().animateLayout(150);
            } else{
                text = text.toLowerCase();
                list.removeAll();
                for(int iter = 0; iter<recs.size(); iter++){

                    //String tests1 =FN.get(iter).toLowerCase();
                    //String tests2 =LN.get(iter).toLowerCase();
                    //boolean test1 = FN.get(iter).toLowerCase().contains(text);
                    //boolean test2 = LN.get(iter).toLowerCase().contains(text);
                    if(recs.get(iter).fname.toLowerCase().contains(text) || recs.get(iter).lname.toLowerCase().contains(text)){
                        MultiButton mb = new MultiButton(recs.get(iter).fname+" "+recs.get(iter).lname);
                        mb.setTextLine2("more...");
                        FontImage.setMaterialIcon(mb,FontImage.MATERIAL_PERSON);
                        int finalIter = iter;
                        mb.addActionListener(evtF -> {
                            ToastBar.showMessage("Clicked: ",FontImage.MATERIAL_PERSON);
                            //TODO WHEN CLICKED
                            picked(res,recs,driveId,start,recs.get(finalIter),adminName,datePicked,hours);
                        });

                        list.add(mb);
                    }
                }
                this.getContentPane().animateLayout(150);
            }
        },4);

    }
}
