package se.antoneliasson.attendance.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Arrays;
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
}
