package se.antoneliasson.attendance.controllers;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.antoneliasson.attendance.models.Database;
import se.antoneliasson.attendance.models.Person;

public class Importer {
    private final Logger log;
    private final Database db;
    
    public Importer(Database db) {
        log = LogManager.getLogger();
        this.db = db;
    }
    
    public void csvImport(String filename) {
        try {
            log.info("Importing from file \"{}\"", filename);
            int inserted = 0, updated = 0;
            db.beginTransaction();
            try (CSVReader reader = new CSVReader(new FileReader(filename))) {
                String[] line = reader.readNext();
                log.debug("Discarding header: {}", Arrays.toString(line));

                while ((line = reader.readNext()) != null) {
                    Map<String, String> values = map(line);
                    Person p = db.getPerson(values.get("timestamp"));
                    if (p == null) {
                        db.insertPerson(values);
                        inserted++;
                    } else {
                        p.setPayment(values.get("payment"));
                        updated++;
                    }
                }
                if (confirm(inserted, updated)) {
                    db.commitTransaction();
                    log.info("Inserted {} and updated {} record(s) in the database.", inserted, updated);
                } else {
                    db.rollbackTransaction();
                    log.info("Import aborted by user. No data has been changed.");
                }
            } catch (IOException ioe) {
                log.error("Failed to read CSV file", ioe);
                db.rollbackTransaction();
                return;
            } catch (NoSuchElementException nsee) {
                log.error("Failed to parse CSV file header;", nsee);
                db.rollbackTransaction();
            }
        } catch (SQLException ex) {
            log.error("Database error during import: {}", ex);
            try {
                db.rollbackTransaction();
            } catch (SQLException e) {
                log.error("Failed to rollback transaction after failed import: ", e);
            }
        }
    }

    private boolean confirm(int inserted, int updated) {
        String[] options = {"Save changes", "Undo import"};
        String msg = String.format(
                "Created %d new person(s).\n"
                + "Updated %d existing person(s) with new data.\n\n"
                + "Continue?",
                inserted, updated);
        int answer = JOptionPane.showOptionDialog(null, msg, "Review import",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return answer == JOptionPane.YES_OPTION;
    }

    private Map<String, String> map(String[] csvFields) {
        Map<String, String> dbFields = new HashMap<>();
        dbFields.put("timestamp", csvFields[0]);
        dbFields.put("name", csvFields[1]);
        dbFields.put("phone", csvFields[2]);
        dbFields.put("email", csvFields[3]);
        dbFields.put("gender", csvFields[4]);
        dbFields.put("membership", csvFields[5]);
        dbFields.put("payment", csvFields[6]);
        return dbFields;
    }
}
