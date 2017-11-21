package database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class userObject {
    public String fname, lname, email, phone, pass, hourstotal, hourssigned, ename, ephone ="", hoursCon;
    public int id;

    public static final Pattern valEmail =
            Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public static final Pattern valPhone =
            Pattern.compile("\\d{10}");

    public userObject(){
        fname = "";
        lname = "";
        email = "";
        phone = "";
        pass = "";
        hourstotal = "";
        hourssigned = "";
        ename = "";
        ephone = "";
    }

    public boolean checkEmail(){
        Matcher matcher = valEmail.matcher(email);
        return matcher.matches();
    }

    public boolean checkPhone(){
        Matcher matcher = valPhone.matcher(phone);
        return matcher.matches();
    }

    public boolean checkContactPhone(){
        Matcher matcher = valPhone.matcher(ephone);
        return matcher.matches();
    }

}
