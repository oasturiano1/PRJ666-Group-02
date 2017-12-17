package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserData {
    //string property is a java abstract class //
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty phoneNumber;
    private final StringProperty password;
    private final StringProperty totalHours;
    private final StringProperty totalSigned;
    private final StringProperty contactName;
    private final StringProperty email;
    private final StringProperty ID;
    private final StringProperty contactNum;

    public UserData(String ID,String firstName, String lastName, String email,String phoneNumber,String password, String totalHours, String totalSigned, String contactName, String contactNum) {
        this.firstName =  new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.password = new SimpleStringProperty(password);
        this.totalHours = new SimpleStringProperty(totalHours);
        this.totalSigned = new SimpleStringProperty(totalSigned);
        this.contactName = new SimpleStringProperty(contactName);
        this.email = new SimpleStringProperty(email);
        this.ID = new SimpleStringProperty(ID);
        this.contactNum = new SimpleStringProperty(contactNum);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }


    public String getTotalHours() {
        return totalHours.get();
    }

    public StringProperty totalHoursProperty() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours.set(totalHours);
    }

    public String getTotalSigned() {
        return totalSigned.get();
    }

    public StringProperty totalSignedProperty() {
        return totalSigned;
    }

    public void setTotalSigned(String totalSigned) {
        this.totalSigned.set(totalSigned);
    }

    public String getContactName() {
        return contactName.get();
    }

    public StringProperty contactNameProperty() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName.set(contactName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getcontactNum() {
        return contactNum.get();
    }

    public StringProperty contactNumProperty() {
        return contactNum;
    }

    public void setcontactNum(String contactNum) {
        this.contactNum.set(contactNum);
    }
}
