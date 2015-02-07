package se.antoneliasson.attendance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import se.antoneliasson.attendance.controllers.Importer;
import se.antoneliasson.attendance.controllers.JsonExporter;
import se.antoneliasson.attendance.gui.menu.AboutMenu;
import se.antoneliasson.attendance.gui.menu.ExportMenu;
import se.antoneliasson.attendance.gui.menu.ImportMenu;
import se.antoneliasson.attendance.gui.menu.OpenMenu;
import se.antoneliasson.attendance.models.Database;

public class Attendance extends JFrame {
    
    public Attendance(Database db) {
        super("Attendance - " + db.getName());
        
        setLayout(new BorderLayout());
        
        Importer importer = new Importer(db);
        //JsonExporter exporter = new JsonExporter(db);
        
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        JMenu fileMenu = new JMenu("Arkiv");
        menubar.add(fileMenu);
        fileMenu.add(new OpenMenu(this));
        fileMenu.add(new ImportMenu(this, importer));
        //fileMenu.add(new ExportMenu(this, exporter));
        JMenu helpMenu = new JMenu("Help");
        menubar.add(helpMenu);
        helpMenu.add(new AboutMenu(this));
        
        PersonPanel personPanel = new PersonPanel(db);
        personPanel.setPreferredSize(new Dimension(200, 0));
        add(personPanel, BorderLayout.EAST);
        
        PersonTableModel tableModel = new PersonTableModel(db);
        db.addObserver(tableModel);
        PersonTable personTable = new PersonTable(tableModel, personPanel);
        add(personTable);
        
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.add(new JLabel("Filter: "), BorderLayout.WEST);
        FilterField filterField = new FilterField();
        Filterer filterer = new Filterer(tableModel);
        filterField.getDocument().addDocumentListener(filterer);
        filterPanel.add(filterField);
        add(filterPanel, BorderLayout.NORTH);
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
