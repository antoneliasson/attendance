package se.antoneliasson.attendance.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import se.antoneliasson.attendance.controllers.JsonExporter;

public class ExportMenu extends JMenuItem implements ActionListener {
    private final JFrame parent;
    private final JsonExporter exporter;

    public ExportMenu(JFrame parent, JsonExporter exporter) {
        super("Exportera till JSON");
        this.parent = parent;
        this.exporter = exporter;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showOpenDialog(parent);

        if (choice == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            exporter.jsonExport(f.getPath());
        }
    }

}
