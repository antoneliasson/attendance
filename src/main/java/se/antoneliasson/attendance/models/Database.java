package se.antoneliasson.attendance.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Database {
    private final Logger log;
    private Connection connection;

    public Database(String dbName) throws ClassNotFoundException {
        log = LogManager.getLogger();
        Class.forName("org.sqlite.JDBC");
        String dbURL = "jdbc:sqlite:" + dbName;
        try {
            log.info("Connecting to {}.", dbURL);
            connection = DriverManager.getConnection(dbURL); // ex: nybörjare.db
            initTables();
        } catch (SQLException e) {
            log.fatal("Failed to open database", e);
            System.exit(1);
        }
    }

    private void initTables() throws SQLException {
        log.debug("Initialising tables");
        Statement statement = connection.createStatement();
        statement.executeUpdate(Person.getSchema());
        statement.executeUpdate(Occasion.getSchema());
        statement.executeUpdate(Participation.getSchema());
    }
    
    /**
     * Inserts a prepared statement.
     * 
     * TODO: use a transaction here somewhere
     * 
     * @param tablename database table name
     * @param query a prepared query string containing ?-placeholders
     * @param values array of values to populate query with
     * @return primary key of inserted record
     */
    private int insert(String tablename, String query, String[] values) {
        log.debug("Query: {}", query);
        log.debug("Values: {}", Arrays.toString(values));
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                stmt.setString(i+1, values[i]);
            }
            int rowCount = stmt.executeUpdate();
            assert rowCount == 1;
        } catch (SQLException ex) {
            log.fatal(ex);
            System.exit(1);
        }
        try (Statement stmt = connection.createStatement()){
            ResultSet keys = stmt.executeQuery("SELECT last_insert_rowid()");
            int key = keys.getInt(1);
            log.debug("Primary key of inserted row: {}", key);
            
            return key;
        } catch (SQLException ex) {
            log.fatal(ex);
            System.exit(1);
        }
        return 0;
    }
    
    
    /**
     * Inserts a new Person into the registry.
     * 
     * @param fields 
     * @return  
     */
    public int insertPerson(Map<String, String> fields) {
        final String tablename="person";
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(tablename);
        query.append(" (timestamp, name, phone, email, gender, membership) VALUES (?, ?, ?, ?, ?, ?)");
        
        String[] values = {fields.get("timestamp"), fields.get("name"), fields.get("phone"),
            fields.get("email"), fields.get("gender"), fields.get("membership")};
        int id = insert(tablename, query.toString(), values);

        log.debug("Inserted new person with id {} into the database", id);
        return id;
    }
    
    /***
     * Typically a "SELECT id FROM tablename WHERE x LIKE ?" combined with a
     * [filter%] argument. Currently basically everything is hardcoded.
     * TODO: correct.
     * @param query
     * @param values
     * @return
     */
    private List<Person> search(String query, String[] values) {
        log.debug("Query: {}", query);
        log.debug("Values: {}", Arrays.toString(values));
        List<Person> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                stmt.setString(i+1, values[i]);
            }
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                result.add(new Person(this, res.getInt("id")));
            }
        } catch (SQLException ex) {
            log.fatal(ex);
            System.exit(1);
        }
        log.debug("Found {} matching person(s)", result.size());
        return result;
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
        List<Person> result = search(query, new String[] {filter + "%"});
        return result;
    }
    
    public String getString(String tablename, int id, String field) {
        String query = String.format("SELECT %s from %s WHERE id = ?", field, tablename);
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            return result.getString(1);
        } catch (SQLException ex) {
            log.error(ex);
        }
        assert false;
        return null;
    }
    
    public void setString(String tablename, int id, String field, String value) {
        String query = String.format("UPDATE %s SET %s=? WHERE id = ?", tablename, field);
        log.debug("Query: {}", query);
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, value);
            stmt.setInt(2, id);
            int rowsChanged = stmt.executeUpdate();
            assert rowsChanged == 1;
        } catch (SQLException ex) {
            log.error(ex);
        }
    }

    public List<Occasion> listOccasions() {
        final String tablename = "occasion";
        String query = String.format("SELECT id FROM %s ORDER BY date", tablename);
        List<Occasion> result = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                result.add(new Occasion(this, res.getInt("id")));
            }
        } catch (SQLException ex) {
            log.fatal(ex);
            System.exit(1);
        }
        log.debug("Found {} matching occasion(s)", result.size());
        return result;
    }
    
    public boolean getAttendance(int person, int occasion) {
        String query = "SELECT 1 FROM participation WHERE person = ? AND occasion = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, person);
            stmt.setInt(2, occasion);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                return true;
            }
        } catch (SQLException ex) {
            log.fatal(ex);
            System.exit(1);
        }
        return false;
    }
    
    public void setAttendance(int person, int occasion, boolean attends) {
        String query;
        if (attends) {
            query = "INSERT INTO participation (person, occasion) values (?, ?)";
        } else {
            query = "DELETE FROM participation WHERE person=? AND occasion=?";
        }
        log.debug("Query: {}", query);
        log.debug("Person={}, occasion={}", person, occasion);
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, person);
            stmt.setInt(2, occasion);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.fatal(ex);
            System.exit(1);
        }
    }
}
