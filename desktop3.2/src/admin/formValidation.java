package admin;

import java.util.regex.Pattern;

public class formValidation {

    public static Boolean isPassCor(String name){//TODO DETERMINE PASSWORD RESTRICTIONS
        return true;
    }

    public static Boolean isNameCor(String name){
        String regex = "[a-zA-Z]+";

        if(regex.length() > 25){
            return false;
        }

        if(name.matches(regex)){
            return true;
        }else if(name.isEmpty()){
            return false;
        }
        else{
            return false;
        }
    }
    public static Boolean isDate(String d){
        //String regex = "\\d{2}-\\d{2}-\\d{4}";
        String regex = "\\d{4}-\\d{2}-\\d{2}";

        if(d.matches(regex)){
            return true;
        }else if(d.isEmpty()){
            return false;
        }
        else{
            return false;
        }
    }
    public static Boolean isPhone(String dob){
        String regex = "\\d{3}-\\d{3}-\\d{4}";
        String regex2 = "\\d{10}";

        if(dob.matches(regex) || dob.matches(regex2)){
            return true;
        }else if(dob.isEmpty()){
            return false;
        }
        else{
            return false;
        }
    }
    public static Boolean isPost(String post){
        String regex = "\\w{1}\\d{1}\\w{1}\\d{1}\\w{1}\\d{1}";

        if(post.matches(regex)){
            return true;
        }else if(post.isEmpty()){
            return false;
        }
        else{
            return false;
        }
    }
    public static Boolean isMail(String post){
        String regex =   "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        /*String regex = "[a-zA-Z0-9][a-zA-Z0-9.]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+";*/
        if(post.matches(regex)){
            return true;
        }else if(post.isEmpty()){
            return false;
        }
        else{
            return false;
        }
    }

}
