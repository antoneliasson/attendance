package se.antoneliasson.attendance.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class FilterField extends JTextField {
    private static final String CANCEL_ACTION = "cancel-search";
    
    public FilterField() {
        super();
        
        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();
        im.put(KeyStroke.getKeyStroke("ESCAPE"), CANCEL_ACTION);
        am.put(CANCEL_ACTION, new CancelAction());
        
    }
    
    class CancelAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent ev) {
            requestFocusInWindow();
            setText("");
        }
    }
}
