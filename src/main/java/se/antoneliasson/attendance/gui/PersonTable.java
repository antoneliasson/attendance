package se.antoneliasson.attendance.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersonTable extends JPanel implements ListSelectionListener, TableModelListener {
    private final Logger log;
    private final JTable table;
    private final PersonTableModel model;
    private final PersonPanel personPanel; // TODO: you do not belong in this class
    
    public PersonTable(PersonTableModel tableModel, PersonPanel personPanel) {
        super(new GridLayout(1, 0));
        log = LogManager.getLogger();
        log.trace("PersonTable constructor");
        this.model = tableModel;
        model.addTableModelListener(this);
        this.personPanel = personPanel;
        
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(800, 200));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(this);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (lse.getValueIsAdjusting()) {
            return;
        }
        ListSelectionModel lsm = (ListSelectionModel) lse.getSource();

        if (lsm.isSelectionEmpty()) {
            log.debug("Person table de-selected");
            personPanel.refresh(null);
        } else {
            // Find out which indexes are selected.
            int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
            if (maxIndex > minIndex) {
                assert false;
                throw new UnsupportedOperationException("Multiple selection currently not supported");
            }
            log.debug("Row {} selected", minIndex);
            // assume that the rows in the view maps to the same rows in the model
            personPanel.refresh(model.get(minIndex));
        }
    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        if (model.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
        } // else leave it deselected
    }
}
