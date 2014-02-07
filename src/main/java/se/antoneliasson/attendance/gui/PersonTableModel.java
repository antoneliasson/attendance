package se.antoneliasson.attendance.gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.apache.logging.log4j.LogManager;
import se.antoneliasson.attendance.models.Database;
import se.antoneliasson.attendance.models.Person;

public class PersonTableModel extends AbstractTableModel {

    private final Database db;
    private final List<Person> persons;
    private final String[] columnNames = {"Namn", "Telefonnummer", "E-postadress", "Kön", "Medlemstyp", "Betalningsdatum"};

    public PersonTableModel(Database db) {
        this.db = db;
        this.persons = new ArrayList<>();
        
        filter("");
    }
    
    public void filter(String filter) {
        LogManager.getLogger().debug("Filter changed to '{}'", filter);
        persons.clear();
        persons.addAll(db.find(filter));
        fireTableDataChanged(); // Efficiency? What efficiency?
    }

    @Override
    public int getRowCount() {
        return persons.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Person p = persons.get(row);
        switch (column) {
            case 0:
                return p.getName();
            case 1:
                return p.getPhone();
            case 2:
                return p.getEmail();
            case 3:
                return p.getGender();
            case 4:
                return p.getMembership();
            case 5:
                return p.getPayment();
            default:
                assert false;
                return null;
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    public Person get(int row) {
        return persons.get(row);
    }
}
