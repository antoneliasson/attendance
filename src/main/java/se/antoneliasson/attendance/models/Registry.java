package se.antoneliasson.attendance.models;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Registry {
    private final Logger log;
    private final Database db;
    private final Map<Integer, Person> persons;
    private static final String tablename = "person";
    
    public Registry(Database db) {
        log = LogManager.getLogger();
        this.db = db;
        this.persons = new HashMap<>();
    }

    public static String getSchema() {
        return String.format("CREATE TABLE IF NOT EXISTS %s ("
            +"id INTEGER, "
            +"timestamp STRING, "
            +"name STRING, "
            +"phone STRING, "
            +"email STRING, "
            +"gender STRING, "
            +"membership STRING, "
            +"has_paid BOOLEAN NOT NULL DEFAULT 0, "
            +"identification_checked BOOLEAN NOT NULL DEFAULT 0, "
            +"PRIMARY KEY(id)"
            +")", tablename);
    }
    
    /**
     * Updates an existing person with new data.
     * 
     * @param p 
     */
    public void update(Person p) {

    }
    
    /**
     * Inserts a new Person into the registry.
     * 
     * @param fields 
     */
    public void insert(Map<String, String> fields) {
        // Java is like Python, or was it the other way around?
        Person p = new Person();
        p.email = fields.get("email");
        p.gender = fields.get("gender");
        p.membership = fields.get("membership");
        p.name = fields.get("name");
        p.phone = fields.get("phone");
        p.timestamp = fields.get("timestamp");
        
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(tablename);
        query.append(" (timestamp, name, phone, email, gender, membership) VALUES (?, ?, ?, ?, ?, ?)");
        
        String[] values = {fields.get("timestamp"), fields.get("name"), fields.get("phone"),
            fields.get("email"), fields.get("gender"), fields.get("membership")};
        int id = db.insert(tablename, query.toString(), values);

        p.id = id;

        persons.put(id, p);
        log.debug("Inserted new person into the registry: {}", p);
    }
}
