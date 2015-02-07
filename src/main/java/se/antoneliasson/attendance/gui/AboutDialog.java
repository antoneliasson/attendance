package se.antoneliasson.attendance.gui;

import javax.swing.*;

public class AboutDialog {

    private final String gpl
            = "This program is free software: you can redistribute it and/or modify\n"
            + "it under the terms of the GNU General Public License as published by\n"
            + "the Free Software Foundation, either version 3 of the License, or\n"
            + "(at your option) any later version.\n"
            + "\n"
            + "This program is distributed in the hope that it will be useful,\n"
            + "but WITHOUT ANY WARRANTY; without even the implied warranty of\n"
            + "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n"
            + "GNU General Public License for more details.\n"
            + "\n"
            + "You should have received a copy of the GNU General Public License\n"
            + "along with this program.  If not, see <http://www.gnu.org/licenses/>.";

    public AboutDialog(JFrame owner) {
        final String version = getClass().getPackage().getImplementationVersion();
        final String msg = String.format(
                "Attendance version %s.\n"
                        + "Copyright 2014 Anton Eliasson <anton@antoneliasson.se>\n\n"
                        + "%s\n\n"
                        + "Attendance uses parts from the following software projects, licensed under the Apache License 2.0:\n"
                        + "OpenCSV (http://opencsv.sourceforge.net/)\n"
                        + "Apache Log4j 2 (http://logging.apache.org/log4j)\n"
                        + "Xerial SQLite JDBC Driver (https://bitbucket.org/xerial/sqlite-jdbc)", version, gpl);
        JOptionPane optionPane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(owner, "About");
        dialog.setVisible(true);
    }
}
