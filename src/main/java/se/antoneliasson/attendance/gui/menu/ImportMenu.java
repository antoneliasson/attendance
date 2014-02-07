package se.antoneliasson.attendance.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import se.antoneliasson.attendance.controllers.Import;

public class ImportMenu extends JMenuItem implements ActionListener {
    private final JFrame parent;
    private final Import importer;
    
    public ImportMenu(JFrame parent, Import importer) {
        super("Importera från CSV");
        this.parent = parent;
        this.importer = importer;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showOpenDialog(parent);
        
        if (choice == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            importer.simpleImport(f.getPath());
        }
    }
    
}
