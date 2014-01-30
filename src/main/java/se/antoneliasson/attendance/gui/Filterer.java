package se.antoneliasson.attendance.gui;

import java.util.Observable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.apache.logging.log4j.LogManager;

public class Filterer extends Observable implements DocumentListener {

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
        setChanged();
        try {
            notifyObservers(document.getText(0, length));
        } catch (BadLocationException ex) {
            LogManager.getLogger().error(ex);
        }
    }
}
