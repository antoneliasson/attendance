package se.antoneliasson.attendance;

import se.antoneliasson.attendance.gui.Attendance;
import se.antoneliasson.attendance.models.Database;

public class Launcher {
        public static void main(String[] args) throws ClassNotFoundException {
        Database db = new Database("example.db");
        new Attendance(db);
    }
}
