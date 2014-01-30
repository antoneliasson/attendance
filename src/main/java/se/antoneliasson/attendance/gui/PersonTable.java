package se.antoneliasson.attendance.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersonTable extends JPanel {
    private final Logger log;
    
    public PersonTable(TableModel tableModel) {
        super(new GridLayout(1, 0));
        log = LogManager.getLogger();
        log.trace("PersonTable constructor");
        
        JTable table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(800, 200));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
}
