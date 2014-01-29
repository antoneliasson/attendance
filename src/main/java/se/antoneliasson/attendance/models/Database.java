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
        statement.executeUpdate(Registry.getSchema());
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
    public int insert(String tablename, String query, String[] values) {
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
    
    /***
     * Typically a "SELECT id FROM tablename WHERE x LIKE ?" combined with a
     * [filter%] argument. Currently basically everything is hardcoded.
     * @param query
     * @param values
     * @return 
     */
    public List<Integer> search(String query, String[] values) {
        log.debug("Query: {}", query);
        log.debug("Values: {}", Arrays.toString(values));
        List<Integer> ids = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                stmt.setString(i+1, values[i]);
            }
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                ids.add(res.getInt("id"));
            }
        } catch (SQLException ex) {
            log.fatal(ex);
            System.exit(1);
        }
        log.debug("Found matching ID's: {}", ids);
        return ids;
    }
    
    /**
     * Yes, this is a terrible workaround.
     * 
     * @return 
     */
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet res = stmt.executeQuery("SELECT * FROM person");
            while (res.next()) {
                Person p = new Person();
                p.id = res.getInt("id");
                p.timestamp = res.getString("timestamp");
                p.name = res.getString("name");
                p.phone = res.getString("phone");
                p.email = res.getString("email");
                p.gender = res.getString("gender");
                p.membership = res.getString("membership");
                
                persons.add(p);
            }
        } catch (SQLException ex) {
            log.fatal(ex);
            System.exit(1);
        }
        return persons;
    }
}
