package com.foodrive.myapp;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import database.DAO;

public class createAccount extends Form {
    public String info;
    private Label white_;
    private Label blue_;
    private DAO dao;


    public createAccount(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("SidemenuTop");
        dao = new DAO();





        /*if(new dbConnection().isDatabaseCon()){
            white_ = new Label("Unlock", "WelcomeWhite");
            System.out.println("connected");
        }else{
            white_ = new Label("Locked", "WelcomeBlue");
        }*/

        /*Container welcome = FlowLayout.encloseCenter(
                white_  = new Label("Unlock", "WelcomeWhite")
               // new Label("Jennifer", "WelcomeBlue")
        );*/

        getTitleArea().setUIID("Container");

      /*  Image profilePic = theme.getImage("user-picture.jpg");
        Image mask = theme.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
        profilePicLabel.setMask(mask.createMask());*/

        //TextField
        TextField login = new TextField("", "Login", 20, TextField.EMAILADDR) ;
        TextField password = new TextField("", "Password", 20, TextField.EMAILADDR) ;
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);


        //Lable
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);


        //Button
        Button loginButton = new Button("LOGIN");
        loginButton.setUIID("LoginButton");
        loginButton.addActionListener(e -> {
            Toolbar.setGlobalToolbar(false);
            // new WalkthruForm(theme).show();
            Toolbar.setGlobalToolbar(true);

            String loginText = login.getText();
            String passwordText = password.getText();
            if(loginText != null && password != null){
                dao.phpIsLogin(loginText,passwordText);
                if(dao.fit){
                    dao.getAll();
                }
            }
        });

        Button createNewAccount = new Button("CREATE NEW ACCOUNT");
        createNewAccount.setUIID("CreateNewAccountButton");
        createNewAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new createAccount(theme);
            }
        });

        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }


        Container by = BoxLayout.encloseY(
                // welcome,
                //profilePicLabel,
                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                loginButton,
                createNewAccount
        );
        add(BorderLayout.CENTER, by);

        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }

}


