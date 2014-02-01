package se.antoneliasson.attendance.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
            +"identification_checked STRING, " // boolean :3
            +"PRIMARY KEY(id)"
            +")", tablename);
    }

    public Person(Database db, int id) {
        this.id = id;
        this.db = db;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + getId() + ", timestamp=" + getTimestamp() + ", name=" + getName() + ", phone=" + getPhone() + ", email=" + getEmail() + ", gender=" + getGender() + ", membership=" + getMembership() + ", payment=" + getPayment() + ", identificationChecked=" + getIdentificationChecked() + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.db);
        hash = 53 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.db, other.db)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
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

    public boolean getIdentificationChecked() {
        // TODO: investigate the nice side effect that parseBoolean maps null to false
        return Boolean.parseBoolean(db.getString(tablename, id, "identification_checked"));
    }

    public void setIdentificationChecked(boolean selected) {
        db.setString(tablename, id, "identification_checked", Boolean.toString(selected));
    }

    public boolean getParticipation(Occasion o) {
        return db.getAttendance(id, o.getId());
    }
    
    public void setParticipation(Occasion o, boolean attends) {
        db.setAttendance(id, o.getId(), attends);
    }
}
