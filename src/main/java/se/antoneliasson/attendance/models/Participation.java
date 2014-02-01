package se.antoneliasson.attendance.models;

public class Participation {
    public Participation() {
        // not sure if this should be a class
    }
    
    public static String getSchema() {
        return "CREATE TABLE IF NOT EXISTS participation ("
                +"id INTEGER, "
                +"person INTEGER, "
                +"occasion INTEGER, "
                +"PRIMARY KEY(id), "
                +"FOREIGN KEY(person) REFERENCES person(id), "
                +"FOREIGN KEY(occasion) REFERENCES occasion(id)"
                +")";
    }
}
