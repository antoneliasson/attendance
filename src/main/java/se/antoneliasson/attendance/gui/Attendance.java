package se.antoneliasson.attendance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import se.antoneliasson.attendance.gui.menu.OpenMenu;
import se.antoneliasson.attendance.models.Database;

public class Attendance extends JFrame {
    
    public Attendance(Database db) {
        super("Attendance - " + db.getName());
        
        setLayout(new BorderLayout());
        
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        JMenu fileMenu = new JMenu("Arkiv");
        menubar.add(fileMenu);
        fileMenu.add(new OpenMenu(this));
        
        PersonPanel personPanel = new PersonPanel(db);
        personPanel.setPreferredSize(new Dimension(200, 0));
        add(personPanel, BorderLayout.EAST);
        
        PersonTableModel tableModel = new PersonTableModel(db);
        PersonTable personTable = new PersonTable(tableModel, personPanel);
        add(personTable);
        
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.add(new JLabel("Filter: "), BorderLayout.WEST);
        FilterField filterField = new FilterField();
        Filterer filterer = new Filterer();
        filterer.addObserver(tableModel);
        filterField.getDocument().addDocumentListener(filterer);
        filterPanel.add(filterField);
        add(filterPanel, BorderLayout.NORTH);
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
