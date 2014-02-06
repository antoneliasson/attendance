package se.antoneliasson.attendance;

import se.antoneliasson.attendance.gui.Attendance;
import se.antoneliasson.attendance.models.Database;

public class Launcher {

    public static void main(String[] args) {
        String dbFilename = "example.db";
        if (args.length == 1) {
            dbFilename = args[0];
        }
        Database db = new Database(dbFilename);
        new Attendance(db);
    }
}
