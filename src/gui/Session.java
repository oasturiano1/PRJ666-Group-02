package gui;

import java.util.prefs.Preferences;

public class Session {
    Preferences pref;

    public Session(){
        pref = Preferences.userNodeForPackage(Session.class);
    }

    public void setPref(boolean loggedin,String user,String email){
        pref.putBoolean("loggedInmode",loggedin);
        pref.put(user,"Users");
        pref.put(email,"Emails");
    }
    public Boolean loggedin(){
        return pref.getBoolean("loggedInmode",false);
    }

}
