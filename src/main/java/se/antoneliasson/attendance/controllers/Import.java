package se.antoneliasson.attendance.controllers;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.antoneliasson.attendance.models.Database;

public class Import {
    private final Logger log;
    private final Database db;
    
    public Import(Database db) {
        log = LogManager.getLogger();
        this.db = db;
    }
    
    public void simpleImport(String filename) {
        log.info("Importing from file \"{}\"", filename);
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            String[] line = reader.readNext();
            log.debug("Discarding header: {}", Arrays.toString(line));
            
            while( (line = reader.readNext()) != null ) {
                db.insertPerson(map(line));
            }
        } catch (IOException ioe) {
            log.error("Failed to read CSV file", ioe);
        }
    }
    
    private Map<String, String> map(String[] csvFields) {
        Map<String, String> dbFields = new HashMap<>();
        dbFields.put("timestamp", csvFields[0]);
        dbFields.put("name", csvFields[1]);
        dbFields.put("phone", csvFields[2]);
        dbFields.put("email", csvFields[3]);
        dbFields.put("gender", csvFields[4]);
        dbFields.put("membership", csvFields[5]);
        return dbFields;
    }
}
