package se.antoneliasson.attendance.controllers;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.antoneliasson.attendance.models.Database;
import se.antoneliasson.attendance.models.Person;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class Importer {
    private final Logger log;
    private final Database db;
    private final Map<String, Integer> columnIndices;
    
    public Importer(Database db) {
        log = LogManager.getLogger();
        this.db = db;
        columnIndices = new HashMap<>();
    }
    
    public void csvImport(String filename) {
        try {
            log.info("Importing from file \"{}\"", filename);
            int inserted = 0, updated = 0;
            db.beginTransaction();
            try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
                String[] line = reader.readNext();
                log.debug("Parsing header: {}", Arrays.toString(line));
                parseHeader(line);

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

    private void parseHeader(String[] header) {
        int flags = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.CANON_EQ;
        Map<String, String> regexps = new HashMap<>();
        // The regular expressions must match the entire field
        regexps.put("timestamp", "tidstämpel|tidsstämpel|timestamp");
        regexps.put("name", "namn");
        regexps.put("phone", "telefon(nummer)?");
        regexps.put("email", "e-post(adress)?");
        regexps.put("gender", "kön|.*man.*|.*kvinna.*|.*förare.*|.*följare.*"); // This changes every other day
        regexps.put("membership", "medlem.*"); // Ex: Medlemstyp | Medlem i Teknologkåren?
        regexps.put("payment", "betal.*"); // Ex: Betalat | Betalning | Betaldatum

        log.debug("Columns are indexed from 0");
        for (Map.Entry<String, String> kv : regexps.entrySet()) {
            String fieldName = kv.getKey();
            String regexp = kv.getValue();
            Pattern p = Pattern.compile(regexp, flags);
            int index = matchIndex(p, header);
            log.debug("Found '{}' column at index {}", fieldName, index);
            columnIndices.put(fieldName, index);
        }
    }

    private int matchIndex(Pattern p, String[] fields) {
        for (int index = 0; index < fields.length; index++) {
            if (p.matcher(fields[index]).matches()) {
                return index;
            }
        }
        throw new NoSuchElementException(String.format("Pattern '%s' did not match any field", p.toString()));
    }

    private Map<String, String> map(String[] csvFields) {
        Map<String, String> dbFields = new HashMap<>();
        for (Map.Entry<String, Integer> kv : columnIndices.entrySet()) {
            String fieldName = kv.getKey();
            int index = kv.getValue();
            dbFields.put(fieldName, csvFields[index]);
        }
        return dbFields;
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
}
