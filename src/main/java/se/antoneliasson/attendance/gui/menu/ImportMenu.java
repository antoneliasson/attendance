package se.antoneliasson.attendance.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import se.antoneliasson.attendance.controllers.Importer;

public class ImportMenu extends JMenuItem implements ActionListener {
    private final JFrame parent;
    private final Importer importer;
    
    public ImportMenu(JFrame parent, Importer importer) {
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
            importer.csvImport(f.getPath());
        }
    }
    
}
