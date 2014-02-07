package se.antoneliasson.attendance.gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.apache.logging.log4j.LogManager;

public class Filterer implements DocumentListener {
    private final PersonTableModel model;
    
    public Filterer(PersonTableModel model) {
        this.model = model;
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        tellEveryone(de);
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        tellEveryone(de);
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        tellEveryone(de);
    }

    private void tellEveryone(DocumentEvent de) {
        Document document = de.getDocument();
        int length = document.getLength();
        try {
            model.filter(document.getText(0, length));
        } catch (BadLocationException ex) {
            LogManager.getLogger().error(ex);
        }
    }
}
