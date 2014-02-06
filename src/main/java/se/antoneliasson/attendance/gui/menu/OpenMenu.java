package se.antoneliasson.attendance.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.antoneliasson.attendance.gui.Attendance;
import se.antoneliasson.attendance.models.Database;

public class OpenMenu extends JMenuItem implements ActionListener {
    private final Logger log;
    private final JFrame parent;
    
    public OpenMenu(JFrame parent) {
        // TODO: make up a generic name for the thing this application manages,
        // instead of 'course'
        super("Öppna kurs");
        log = LogManager.getLogger();
        this.parent = parent;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showOpenDialog(parent);
        
        if (choice == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            log.info("Opening database {}", f.getPath());
            Database db = new Database(f.getPath());
            // TODO: maybe check if this is the one that is already open?
            new Attendance(db);
        }
    }
}
