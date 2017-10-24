package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserData {
    //string property is a java abstract class //
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty DOB;
    private final StringProperty radio1;
    private final StringProperty contact;
    private final StringProperty emergency;
    private final StringProperty postal;
    private final StringProperty email;
    private final StringProperty ID;

    public UserData(String ID,String firstName, String lastName, String email,String DOB,String radio1, String contact, String emergency, String postal) {
        this.firstName =  new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.DOB = new SimpleStringProperty(DOB);
        this.radio1 = new SimpleStringProperty(radio1);
        this.contact = new SimpleStringProperty(contact);
        this.emergency = new SimpleStringProperty(emergency);
        this.postal = new SimpleStringProperty(postal);
        this.email = new SimpleStringProperty(email);
        this.ID = new SimpleStringProperty(ID);
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

    public String getDOB() {
        return DOB.get();
    }

    public StringProperty DOBProperty() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB.set(DOB);
    }

    public String getRadio1() {
        return radio1.get();
    }

    public StringProperty radio1Property() {
        return radio1;
    }

    public void setRadio1(String radio1) {
        this.radio1.set(radio1);
    }


    public String getContact() {
        return contact.get();
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public String getEmergency() {
        return emergency.get();
    }

    public StringProperty emergencyProperty() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency.set(emergency);
    }

    public String getPostal() {
        return postal.get();
    }

    public StringProperty postalProperty() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal.set(postal);
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
}
