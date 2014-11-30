package se.antoneliasson.attendance.models;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PersonSerializer implements JsonSerializer<Person> {

    @Override
    public JsonElement serialize(Person person, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        obj.add("id", new JsonPrimitive(person.getId()));
        obj.add("timestamp", new JsonPrimitive(person.getTimestamp()));
        obj.add("name", new JsonPrimitive(person.getName()));
        obj.add("phone", new JsonPrimitive(person.getPhone()));
        obj.add("email", new JsonPrimitive(person.getEmail()));
        obj.add("gender", new JsonPrimitive(person.getGender()));
        obj.add("membership", new JsonPrimitive(person.getMembership()));
        obj.add("payment", new JsonPrimitive(person.getPayment()));
        obj.add("identification_checked", new JsonPrimitive(person.getIdentificationChecked()));
        return obj;
    }
}
