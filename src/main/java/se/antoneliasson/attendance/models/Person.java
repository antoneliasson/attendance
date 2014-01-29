package se.antoneliasson.attendance.models;

public class Person {
    public int id;
    public String timestamp;
    public String name;
    public String phone;
    public String email;
    public String gender;
    public String membership;
    public String payment;
    public String identificationChecked;

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", timestamp=" + timestamp + ", name=" + name + ", phone=" + phone + ", email=" + email + ", gender=" + gender + ", membership=" + membership + ", payment=" + payment + ", identificationChecked=" + identificationChecked + '}';
    }
    
}
