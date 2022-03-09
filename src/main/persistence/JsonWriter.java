package persistence;

import model.*;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Referenced from JSON Serialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a writer that writes JSON representation of the User and Day to a data file
public class JsonWriter {
    private static final int TAB = 4;
    private String destination;
    private PrintWriter writer;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination
    //          file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of User to file
    public void write(User user) {
        JSONObject json = user.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Day to file
    public void write(Day day) {
        JSONObject json = day.toJson();
        saveToFile(json.toString(TAB));
    }

    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes given string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
