package se.antoneliasson.attendance.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import se.antoneliasson.attendance.models.Registry;

public class Attendance extends JFrame {
    
    public Attendance(Registry registry) {
        super("Attendance");
        
        setLayout(new BorderLayout());
        
        PersonTableModel tableModel = new PersonTableModel(registry);
        PersonTable personTable = new PersonTable(tableModel);
        add(personTable);
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
