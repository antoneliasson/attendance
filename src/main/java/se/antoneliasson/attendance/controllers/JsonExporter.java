package se.antoneliasson.attendance.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import se.antoneliasson.attendance.models.Database;
import se.antoneliasson.attendance.models.Person;
import se.antoneliasson.attendance.models.PersonSerializer;

public class JsonExporter {
    private final Logger log;
    private final Database db;
    private final Gson gson;

    public JsonExporter (Database db) {
        log = LogManager.getLogger();
        this.db = db;
        gson = new GsonBuilder().registerTypeAdapter(Person.class, new PersonSerializer())
                .setPrettyPrinting().create();
    }

    public void jsonExport(String filename) {
        String timestamp = "2014-01-20 17.10.21";
        Person p = db.getPerson(timestamp);
        System.out.println(gson.toJson(p));
    }
}
