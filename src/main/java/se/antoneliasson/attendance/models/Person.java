package se.antoneliasson.attendance.models;

import java.util.HashMap;
import java.util.Map;

public class Person {
    private static final String tablename = "person";
    
    private final Database db;
    private final int id;

    public Person(Database db, String timestamp, String name, String phone, String email, String gender, String membership, String payment, String identificationChecked) {
        Map<String, String> fields = new HashMap<>();
        fields.put("timestamp", timestamp);
        fields.put("name", name);
        fields.put("phone", phone);
        fields.put("email", email);
        fields.put("gender", gender);
        fields.put("membership", membership);
        fields.put("payment", payment);
        fields.put("identificationChecked", identificationChecked);
        
        this.id = db.insertPerson(fields);
        this.db = db;
    }

    public static String getSchema() {
        return String.format("CREATE TABLE IF NOT EXISTS %s ("
            +"id INTEGER, "
            +"timestamp STRING, " // date+time
            +"name STRING, "
            +"phone STRING, "
            +"email STRING, "
            +"gender STRING, "
            +"membership STRING, "
            +"payment STRING, " // date
            +"identification_checked STRING, " // date. Maybe. Or a boolean.
            +"PRIMARY KEY(id)"
            +")", tablename);
    }

    Person(Database db, int id) {
        this.id = id;
        this.db = db;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + getId() + ", timestamp=" + getTimestamp() + ", name=" + getName() + ", phone=" + getPhone() + ", email=" + getEmail() + ", gender=" + getGender() + ", membership=" + getMembership() + ", payment=" + getPayment() + ", identificationChecked=" + getIdentificationChecked() + '}';
    }

    public int getId() {
        return id;
    }

    public String getTimestamp() {
        return db.getString(tablename, id, "timestamp");
    }

    public String getName() {
        return db.getString(tablename, id, "name");
    }

    public String getPhone() {
        return db.getString(tablename, id, "phone");
    }

    public String getEmail() {
        return db.getString(tablename, id, "email");
    }

    public String getGender() {
        return db.getString(tablename, id, "gender");
    }

    public String getMembership() {
        return db.getString(tablename, id, "membership");
    }

    public String getPayment() {
        return db.getString(tablename, id, "payment");
    }

    public String getIdentificationChecked() {
        return db.getString(tablename, id, "identification_checked");
    }
}
