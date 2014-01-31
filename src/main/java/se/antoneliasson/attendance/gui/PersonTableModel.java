package se.antoneliasson.attendance.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;
import org.apache.logging.log4j.LogManager;
import se.antoneliasson.attendance.models.Database;
import se.antoneliasson.attendance.models.Person;

public class PersonTableModel extends AbstractTableModel implements Observer {

    private final Database db;
    private final List<Person> persons;
    private final String[] columnNames = {"Tidsstämpel", "Namn", "Telefonnummer", "E-postadress", "Kön", "Medlemstyp", "Betalningsdatum", "Leg kontrollerat"};

    public PersonTableModel(Database db) {
        this.db = db;
        this.persons = new ArrayList<>();
        
        filter("");
    }
    
    private void filter(String filter) {
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
        return 8; // number of fields in Person
    }

    @Override
    public Object getValueAt(int row, int column) {
        Person p = persons.get(row);
        switch (column) {
            case 0:
                return p.getTimestamp();
            case 1:
                return p.getName();
            case 2:
                return p.getPhone();
            case 3:
                return p.getEmail();
            case 4:
                return p.getGender();
            case 5:
                return p.getMembership();
            case 6:
                return p.getPayment();
            case 7:
                return p.getIdentificationChecked();
            default:
                assert false;
                return null;
        }
    }

//    @Override
//    public Class getColumnClass(int c) {
//        return getValueAt(0, c).getClass();
//    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public void update(Observable o, Object o1) {
        String filter = (String)o1;
        LogManager.getLogger().debug("Filter changed to '{}'", filter);
        filter(filter);
    }
    
    public Person get(int row) {
        return persons.get(row);
    }
}
