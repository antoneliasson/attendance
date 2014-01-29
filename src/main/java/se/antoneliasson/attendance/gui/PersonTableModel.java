package se.antoneliasson.attendance.gui;

import javax.swing.table.AbstractTableModel;
import se.antoneliasson.attendance.models.Person;
import se.antoneliasson.attendance.models.Registry;

public class PersonTableModel extends AbstractTableModel {

    private final Registry registry;
    private final String[] columnNames = {"Tidsstämpel", "Namn", "Telefonnummer", "E-postadress", "Kön", "Medlemstyp", "Betalningsdatum", "Leg kontrollerat"};

    public PersonTableModel(Registry registry) {
        this.registry = registry;
    }

    @Override
    public int getRowCount() {
        return registry.size();
    }

    @Override
    public int getColumnCount() {
        return 8; // number of fields in Person
    }

    @Override
    public Object getValueAt(int row, int column) {
        Person p = registry.get(row);
        switch (column) {
            case 0:
                return p.timestamp;
            case 1:
                return p.name;
            case 2:
                return p.phone;
            case 3:
                return p.email;
            case 4:
                return p.gender;
            case 5:
                return p.membership;
            case 6:
                return p.payment;
            case 7:
                return p.identificationChecked;
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
}
