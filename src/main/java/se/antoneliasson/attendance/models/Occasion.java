package se.antoneliasson.attendance.models;

import java.util.Date;
import java.util.List;

public class Occasion {
    private static final String tablename = "occasion";
    
    private final Database db;
    private final int id;
    
    public Occasion(Database db, int id) {
        this.db = db;
        this.id = id;
    }
    
    public static String getSchema() {
        return String.format("CREATE TABLE IF NOT EXISTS %s ("
                +"id INTEGER, "
                +"date STRING, "
                +"PRIMARY KEY(id)"
                +")", tablename);
    }
    
    public int getId() {
        return id;
    }

    public String getDate() {
        return db.getString(tablename, id, "date");
    }
}
