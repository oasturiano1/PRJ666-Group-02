package com.foodrive.myapp;

import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import database.DAO;

import java.util.HashMap;
import java.util.Map;

public class Login extends Form {
    public String info;
    private Label white_;
    private Label blue_;
    private DAO dao;
    private Button loginButton;
    private TextField login;
    private TextField password;
    private Resources theme;
    public boolean check;
    public String radio_;


    public Login(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginFormNew");
        dao = new DAO();
        this.theme = theme;
        //Container north = new Container(new FlowLayout(Component.CENTER));

        Label photoButton = new Label(" ");
        Label blank = new Label(" ");
        Button btnStand = new Button("",theme.getImage("buttonstand.png"));
        photoButton.setUIID("photoBtn");
        Label message = new Label("MEMBER LOGIN ");
        message.setUIID("MSG");

       /* north.addComponent(photoButton);
        this.addComponent(BorderLayout.NORTH,north);*/
        Container north = BoxLayout.encloseY(
                // welcome,
                //profilePicLabel,
                blank,
                photoButton,
                BorderLayout.centerAbsolute(message)
        );
        add(BorderLayout.NORTH, north);





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
        login = new TextField() ;
        password = new TextField() ;
        //login.getAllStyles().setMargin(LEFT, 0);
        login.setHint("Email");
        login.setUIID("mainText");
        login.getHintLabel().setUIID("SignupFieldHint");
        login.setRows(2);
        login.setHintIcon(theme.getImage("icon1.png"));


       // password.getAllStyles().setMargin(LEFT, 0);
        password.setHint("Password");
        password.setRows(2);
        password.getHintLabel().setUIID("SignupFieldHint");
        password.setHintIcon(theme.getImage("lock.png"));
        password.setConstraint(TextField.PASSWORD);
        password.setUIID("mainText");

        Label frontBlank = new Label(" ");
        Label BlankBlank = new Label(" ");
        Label onekBlank = new Label(" ");

        /*ComboBox<Map<String, Object>> combo = new ComboBox<> (

                createListEntry("ADMIN"),
                createListEntry("USER")
        );
        combo.setRenderer(new GenericListCellRenderer<>(new MultiButton(), new MultiButton()));
        combo.setUIID("mainText");*/
       /* String[] characters = { "ADMIN", "USER"};
        AutoCompleteTextField act = new AutoCompleteTextField(characters);
        act.addActionListener(e -> ToastBar.showMessage("You picked " + act.getText(), FontImage.MATERIAL_INFO));
        Button down = new Button();
        FontImage.setMaterialIcon(down, FontImage.MATERIAL_KEYBOARD_ARROW_DOWN);*/



        //Lable
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        /*loginIcon.setUIID("FrontField");
        passwordIcon.setUIID("FrontField");*/





        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }

        TextField stand = new TextField("");
        stand.setRows(2);
        stand.setUIID("STAND");

   /*    Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        center.setUIID("SignUpCenter");
        center.addComponent(login);
        center.addComponent(password);
        this.addComponent(BorderLayout.CENTER, center);*/

        RadioButton cb1 = new RadioButton("ADMIN ");
        cb1.setSelected(false);
        cb1.setUIID("checker");
        cb1.setOppositeSide(true);
        cb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                radio_ = "ADMIN";
            }
        });

        RadioButton cb2 = new RadioButton("USER    ");
        cb2.setSelected(false);
        cb2.setUIID("checker");
        cb2.setOppositeSide(true);
        cb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                radio_ = "USER";
            }
        });

        new ButtonGroup(cb1,cb2);

         /*    Label buttonStand = new Label("");
        buttonStand.setUIID("buttonStand_");*/
        //Button

       /* if(cb1.isSelected()){
            cb1.setSelected(true);
            //cb2.setSelected(false);
            radio_ = "ADMIN";
        }
        else if(cb2.isSelected()){
            cb2.setSelected(true);
            radio_ = "USER";
        }
        else
            radio_ = "Bug";*/

        Button loginButton = new Button("LOGIN");
        loginButton.setUIID("LoginButton");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

          /*      String loginText = login.getText();
                String passwordText = password.getText();*/
               switch (radio_){
                   case "ADMIN":
                       dao.phpIsLogin(login.getText(),password.getText(),radio_);
                   case "USER":
                       System.out.println("USERS");
               }


            }
        });




        Button createNewAccount = new Button("CREATE NEW ACCOUNT");
        createNewAccount.setUIID("CreateNewAccountButton");
        createNewAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                new SignUpForm(theme).show();
            }
        });

        Container by = BoxLayout.encloseY(
                // welcome,
                //profilePicLabel,
                spaceLabel,
                BorderLayout.center(login),
                      /* addVolunteer(BorderLayout.EAST, loginIcon),*/

                BlankBlank,

                BorderLayout.center(password),
                       /*addVolunteer(BorderLayout.EAST, passwordIcon),*/

                onekBlank,

                BorderLayout.west(cb1),
                BorderLayout.west(cb2),

                BorderLayout.center(loginButton),
                createNewAccount
        );
        add(BorderLayout.CENTER, by);

        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
      /*  down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                act.showPopup();
            }
        });*/

    }

    private Map<String, Object> createListEntry(String name) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("Line1", name);
        return entry;
    }

    public void displayAdmin(){
        new adminLanded(theme).show();
    }

}


