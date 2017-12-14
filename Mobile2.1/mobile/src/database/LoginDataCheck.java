package database;

public class LoginDataCheck {
    private String email;
    private String password;
    private String division;

    public LoginDataCheck(String email, String password, String division) {
        this.email = email;
        this.password = password;
        this.division = division;
    }
    public LoginDataCheck(){

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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
}
