package database;

public class people {
    private String lname;
    private String fname;
    private String email;
    private String Dob;
    private String gender;
    private String phone;
    private String emergency;
    private String postal;

    public people(String fname, String lname, String email, String dob, String gender, String phone, String emergency, String postal) {
        this.lname = lname;
        this.fname = fname;
        this.email = email;
        Dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.emergency = emergency;
        this.postal = postal;
    }



    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }
}
