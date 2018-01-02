package database;

public class people {
    private String lname;
    private String fname;
    private String phone;
    private String totalHrs;
    private String hrsSigned;
    private String emergency;
    private String erNumber;
    private String email;
    private String password;

    public people(String lname, String fname, String phone, String totalHrs, String hrsSigned, String emergency, String erNumber, String email, String password) {
        this.lname = lname;
        this.fname = fname;
        this.phone = phone;
        this.totalHrs = totalHrs;
        this.hrsSigned = hrsSigned;
        this.emergency = emergency;
        this.erNumber = erNumber;
        this.email = email;
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTotalHrs() {
        return totalHrs;
    }

    public void setTotalHrs(String totalHrs) {
        this.totalHrs = totalHrs;
    }

    public String getHrsSigned() {
        return hrsSigned;
    }

    public void setHrsSigned(String hrsSigned) {
        this.hrsSigned = hrsSigned;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getErNumber() {
        return erNumber;
    }

    public void setErNumber(String erNumber) {
        this.erNumber = erNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
