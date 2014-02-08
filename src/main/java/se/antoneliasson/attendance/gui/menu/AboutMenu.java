package se.antoneliasson.attendance.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import se.antoneliasson.attendance.gui.AboutDialog;

// Note to future me, if I ever question the existence of this class, here's
// how this would be done with an anonymous class:
//
// JMenuItem aboutMenu = new JMenuItem("About");
//         aboutMenu.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 new AboutDialog(Attendance.this);
//             }});
//         helpMenu.add(aboutMenu);
//
// I think I'd rather have a few extra lines of boilerplate code.

public class AboutMenu extends JMenuItem implements ActionListener {
    
    private final JFrame parent;
    
    public AboutMenu(JFrame parent) {
        super("About");
        this.parent = parent;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new AboutDialog(parent);
    }
}
