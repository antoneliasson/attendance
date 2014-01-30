package se.antoneliasson.attendance.gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import se.antoneliasson.attendance.models.Registry;

public class Attendance extends JFrame {
    
    public Attendance(Registry registry) {
        super("Attendance");
        
        setLayout(new BorderLayout());
        
        PersonTableModel tableModel = new PersonTableModel(registry);
        PersonTable personTable = new PersonTable(tableModel);
        add(personTable);
        
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.add(new JLabel("Filter: "), BorderLayout.WEST);
        JTextField filterField = new JTextField();
        Filterer filterer = new Filterer();
        filterer.addObserver(tableModel);
        filterField.getDocument().addDocumentListener(filterer);
        filterPanel.add(filterField);
        add(filterPanel, BorderLayout.NORTH);
        
        add(new JButton("Placeholder"), BorderLayout.EAST);
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
