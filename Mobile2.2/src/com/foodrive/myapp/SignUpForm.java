package com.foodrive.myapp;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import database.DAO;

public class SignUpForm extends Form {
    final Resources res;

    public SignUpForm(Resources res) {
        super("Sign Up");
        this.setUIID("SignUpForm");
        this.res = res;
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        //res.getImage("icon_navbar_arrow_back.png")
        this.getToolbar().addCommandToLeftBar("",icon, (e) -> new Login(UIManager.initFirstTheme("/theme")).show());
        /*this.getToolbar().addCommandToOverflowMenu("Overflow", icon, (e) -> Log.p("Clicked"));
        this.getToolbar().addCommandToSideMenu("Sidemenu", icon, (e) -> Log.p("Clicked"));*/

        setLayout(new BorderLayout());
        Container north = new Container(new FlowLayout(Component.CENTER));
        north.setUIID("SignUpNorth");




        Button photoButton = new Button(res.getImage("camera.png"));
        photoButton.setUIID("PhotoButton");
        //north.addComponent(photoButton);


        this.addComponent(BorderLayout.NORTH, north);

        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        center.setUIID("SignUpCenter");

        Container row1 = new Container(new GridLayout(1,2));
        TextField firstName = new TextField();
        firstName.setUIID("SignUpField");
        firstName.setHint("First Name");
        firstName.getHintLabel().setUIID("SignupFieldHint");
        firstName.setRows(3);

        TextField lastName = new TextField();
        lastName.setUIID("SignUpField");
        lastName.setHint("Last Name");
        lastName.getHintLabel().setUIID("SignupFieldHint");
        lastName.setRows(3);


        row1.addComponent(firstName);
        row1.addComponent(lastName);
        center.addComponent(row1);
        center.setScrollableY(true);

      /*  TextField ephone = new TextField();
        ephone.setUIID("SignUpField");
        ephone.setHint("Emrgenc Number");
        ephone.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(ephone);
        ephone.setRows(3);*/

        TextField password = new TextField();
        password.setUIID("SignUpField");
        password.setConstraint(TextField.PASSWORD);
        password.setHint("Password");
        password.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(password);
        password.setRows(3);

        TextField email = new TextField();
        email.setUIID("SignUpField");
        center.addComponent(email);
        email.setHint("Email Address");
        email.getHintLabel().setUIID("SignupFieldHint");
        email.setRows(3);

       /* TextField Name = new TextField();
        Name.setUIID("SignUpField");
        Name.setHint("Contact Name");
        Name.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(Name);
        Name.setRows(3);*/

        TextField contactName = new TextField();
        contactName.setUIID("SignUpField");
        contactName.setHint("Contact Name");
        contactName.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(contactName);
        contactName.setRows(3);

        TextField contactNumber = new TextField();
        contactNumber.setUIID("SignUpField");
        contactNumber.setHint("Contact number");
        contactNumber.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(contactNumber);
        contactNumber.setRows(3);


        Container row4 = new Container(new BorderLayout());
        Label code = new Label("+1");
        code.setUIID("SignUpLabel");
        row4.addComponent(BorderLayout.WEST, code);

        TextField phoneNumber = new TextField();
        phoneNumber.setUIID("SignUpField");
        phoneNumber.setHint("Phone Number");
        phoneNumber.getHintLabel().setUIID("SignupFieldHint");
        row4.addComponent(BorderLayout.CENTER, phoneNumber);
        phoneNumber.setRows(3);

        center.addComponent(row4);

        this.addComponent(BorderLayout.CENTER, center);

        Button getStarted = new Button("Get Started", res.getImage("icon_arrow_forward copy.png"));
        getStarted.setGap(getStarted.getStyle().getFont().getHeight());
        getStarted.setUIID("SignUpButton");
        getStarted.setTextPosition(Component.LEFT);


        this.addComponent(BorderLayout.SOUTH, getStarted);
        this.addCommand(new Command("Done") {

            @Override
            public void actionPerformed(ActionEvent evt) {

            }

        });

        this.setBackCommand(new Command("", res.getImage("icon_navbar_arrow_back.png")) {

            @Override
            public void actionPerformed(ActionEvent evt) {

            }

        });


       getStarted.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent evt) {
               if(checkNullData(firstName.getText(), lastName.getText(),email.getText(),phoneNumber.getText(),password.getText(),contactNumber.getText(), contactName.getText())){
                   String fn = firstName.getText();
                   String ln = lastName.getText();
                   String em = email.getText();
                   String ph = phoneNumber.getText();
                   String passWord = password.getText();
                   // String name = Name.getText();
                   //String gen = HoursSigned.getText();
                   //String ep = ephone.getText();
                   String contactNum = contactNumber.getText();
                   String contactNames = contactName.getText();

                   new DAO().addVolunteer(fn,ln,em,ph,passWord,contactNum,contactNames);
                   new DAO().addLogin(em,passWord,"USER");
               } /*else{

                   Dialog d = new Dialog("Add to my people shelf");
                   TextArea popupBody = new TextArea("people successfully added");
                   popupBody.setUIID("PopupBody");
                   popupBody.setEditable(false);
                   d.setLayout(new BorderLayout());
                   d.addVolunteer(BorderLayout.CENTER, popupBody);
                   d.showDialog();
               }*/
           }
       });


    }

    public boolean checkNullData(String text, String lastNameText, String emailText, String phoneNumberText, String passwordText, String contactNumberText, String contactNameText){
        boolean valid = true;
        if(text == null || text.isEmpty())
            valid = false;
        else if(lastNameText == null || lastNameText.isEmpty())
            valid = false;


        return valid;
    }
}