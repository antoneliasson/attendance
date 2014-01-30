package se.antoneliasson.attendance.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        // This will likely change to a Map<ID, Person> in the future, but right
        // now solid indices are needed for the TableModel.
        this.persons = new HashMap<>();
        
        loadFromDb();
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
    
    /**
     * Updates an existing person with new data.
     * 
     * @param p 
     */
    public void update(Person p) {

    }
    
    private Person makePerson(Map<String, String> fields) {
        // Java is like Python, or was it the other way around?
        Person p = new Person();
        p.email = fields.get("email");
        p.gender = fields.get("gender");
        p.membership = fields.get("membership");
        p.name = fields.get("name");
        p.phone = fields.get("phone");
        p.timestamp = fields.get("timestamp");
        
        return p;
    }
    
    /**
     * Inserts a new Person into the registry.
     * 
     * @param fields 
     */
    public void insert(Map<String, String> fields) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(tablename);
        query.append(" (timestamp, name, phone, email, gender, membership) VALUES (?, ?, ?, ?, ?, ?)");
        
        String[] values = {fields.get("timestamp"), fields.get("name"), fields.get("phone"),
            fields.get("email"), fields.get("gender"), fields.get("membership")};
        int id = db.insert(tablename, query.toString(), values);

        Person p = makePerson(fields);
        p.id = id;

        persons.put(id, p);
        log.debug("Inserted new person into the registry: {}", p);
    }
    
    private void loadFromDb() {
        List<Person> result = db.getAllPersons();
        log.info("Loaded {} people from database", result.size());
        
        for (Person p : result) {
            persons.put(p.id, p);
        }
    }
    
    /**
     * Searches the database for Person's filtered by name.
     * 
     * TODO: @param filter whitespace separated words to match the Persons' names for
     * @param filter string to match against the beginning of Persons' names
     * @return 
     */
    public List<Person> find(String filter) {
        log.debug("Searching for people using filter '{}'", filter);
        String query = "SELECT id FROM person WHERE name LIKE ?";
        List<Integer> ids = db.search(query, new String[] {filter + "%"});
        List<Person> matches = new ArrayList<>();
        for (int id : ids) {
            Person p = persons.get(id);
            assert p != null;
            matches.add(p);
        }
        log.debug("Found {} matching person(s)", matches.size());
        return matches;
    }
}
