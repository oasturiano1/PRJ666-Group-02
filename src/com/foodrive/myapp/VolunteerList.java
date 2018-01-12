package com.foodrive.myapp;

import com.codename1.components.MultiButton;
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
import database.people;

import java.util.ArrayList;

public class VolunteerList extends Form {
    private final Resources res;

    public VolunteerList(Resources res, ArrayList<people> people, String adminName) {

        super(new BorderLayout());
        //setUIID("viewAllVols");
        this.res = res;
        this.setScrollableY(true);

        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        ArrayList<String> FN = new ArrayList<>();
        ArrayList<String> LN = new ArrayList<>();
        ArrayList<String> EM = new ArrayList<>();
        ArrayList<String> HRT = new ArrayList<>();
        ArrayList<String> HRS = new ArrayList<>();
        ArrayList<String> PN = new ArrayList<>();
        ArrayList<String> CN = new ArrayList<>();
        ArrayList<String> CPN = new ArrayList<>();
        ArrayList<String> PW = new ArrayList<>();
        for (people l : people) {
            FN.add(l.getFname());
            LN.add(l.getLname());
            EM.add(l.getEmail());
            HRT.add(l.getTotalHrs());
            HRS.add(l.getHrsSigned());
            PN.add(l.getPhone());
            CN.add(l.getEmergency());
            CPN.add(l.getErNumber());
            PW.add(l.getPassword());
        }
        for (int iter = 0; iter < FN.size(); iter++) {
            MultiButton mb = new MultiButton(people.get(iter).getFname() + " " + people.get(iter).getLname());
            mb.setTextLine2("more...");
            FontImage.setMaterialIcon(mb, FontImage.MATERIAL_PERSON);
            int finalIter = iter;
            mb.addActionListener(evt -> {
                //ToastBar.showMessage("Clicked: ",FontImage.MATERIAL_PERSON);
                com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                new ViewVolunteers(LoginRes, FN.get(finalIter), LN.get(finalIter), EM.get(finalIter),
                        HRT.get(finalIter), HRS.get(finalIter), PN.get(finalIter), CN.get(finalIter),
                        CPN.get(finalIter), PW.get(finalIter),adminName).show();
            });
            list.add(mb);
        }
        com.codename1.ui.util.Resources res2 = UIManager.initFirstTheme("/theme");
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        this.add(BorderLayout.CENTER, list);
        this.getToolbar().addCommandToLeftBar("", icon, (e) -> new adminLanded(res2,adminName).show());
        this.getToolbar().addSearchCommand(evt -> {
            String text = (String) evt.getSource();
            if (text == null || text.length() == 0) {
                for (Component cmp : this.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                    list.removeAll();
                    for (int iter = 0; iter < FN.size(); iter++) {

                        //String tests1 =FN.get(iter).toLowerCase();
                        //String tests2 =LN.get(iter).toLowerCase();
                        //boolean test1 = FN.get(iter).toLowerCase().contains(text);
                        //boolean test2 = LN.get(iter).toLowerCase().contains(text);

                        MultiButton mb = new MultiButton(people.get(iter).getFname() + " " + people.get(iter).getLname());
                        mb.setTextLine2("more...");
                        FontImage.setMaterialIcon(mb, FontImage.MATERIAL_PERSON);
                        int finalIter = iter;
                        mb.addActionListener(evtF -> {
                            //ToastBar.showMessage("Clicked: ",FontImage.MATERIAL_PERSON);
                            com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                            new ViewVolunteers(LoginRes, FN.get(finalIter), LN.get(finalIter), EM.get(finalIter),
                                    HRT.get(finalIter), HRS.get(finalIter), PN.get(finalIter), CN.get(finalIter),
                                    CPN.get(finalIter), PW.get(finalIter),adminName).show();
                        });

                        list.add(mb);


                    }
                }
                this.getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                list.removeAll();
                for (int iter = 0; iter < FN.size(); iter++) {

                    //String tests1 =FN.get(iter).toLowerCase();
                    //String tests2 =LN.get(iter).toLowerCase();
                    //boolean test1 = FN.get(iter).toLowerCase().contains(text);
                    //boolean test2 = LN.get(iter).toLowerCase().contains(text);
                    if (FN.get(iter).toLowerCase().contains(text) || LN.get(iter).toLowerCase().contains(text)) {
                        MultiButton mb = new MultiButton(people.get(iter).getFname() + " " + people.get(iter).getLname());
                        mb.setTextLine2("more...");
                        FontImage.setMaterialIcon(mb, FontImage.MATERIAL_PERSON);
                        int finalIter = iter;
                        mb.addActionListener(evtF -> {
                            //ToastBar.showMessage("Clicked: ",FontImage.MATERIAL_PERSON);
                            com.codename1.ui.util.Resources LoginRes = UIManager.initFirstTheme("/theme");
                            new ViewVolunteers(LoginRes, FN.get(finalIter), LN.get(finalIter), EM.get(finalIter),
                                    HRT.get(finalIter), HRS.get(finalIter), PN.get(finalIter), CN.get(finalIter),
                                    CPN.get(finalIter), PW.get(finalIter),adminName).show();
                        });

                        list.add(mb);
                    }
                }
                this.getContentPane().animateLayout(150);
            }
        }, 4);
    }
}

