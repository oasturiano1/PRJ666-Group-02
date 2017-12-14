package com.foodrive.myapp;


import com.codename1.ui.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;

import database.DAO;

import java.io.IOException;

public class Home {
    private Resources LoginRes;
    private Login log;
    private DAO dao;


    public void init(Object context) {
        dao = new DAO();
        //dao.checkConnection();

       LoginRes = UIManager.initFirstTheme("/theme");
     /*   try {
            LoginRes = Resources.openLayered("/theme.css");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //db = new dbConnection();
        log = new Login(LoginRes);
        log.show();

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);
    }
    
    public void start() {
       /* System.out.println("***"+db.isDatabaseCon()+"***");
        if(db.isDatabaseCon()){
            log.labelSetter(new Label("Welcome", "WelcomeBlue"));
        }else{
            log.labelSetter(new Label("Welcome", "WelcomeWhite"));
        }

        try {
            if(db.isLogin("e.com","Pass123","ADMIN")){
                System.out.println("LOGIN IN");
            }else{
                System.out.println("ERROR");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

      /*  Form f = new Form("HOME");
        f.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        Label text = new Label("Email");
        TextField email = new TextField(10);
        gc.weightx = 1; //weight for cell space
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;  //take all space
        gc.anchor = GridBagConstraints.CENTER; //move over to the right
        gc.insets = new Insets(0, 0, 0, 5);
        f .addComponent(gc,text);

        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;//move over to the left
        f.addComponent(gc,email);



        Button btn = new Button("ok");
        Button quiet = new Button("quite");

       *//* f.addComponent(text);
        f.addComponent(btn);
        f.addComponent(quiet);*//*
        f.show();*/

    }

    public void stop() {

    }
    
    public void destroy() {
    }

}
