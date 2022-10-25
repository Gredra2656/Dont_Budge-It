// Credit to JsonSerializationDemo from the CPSC 210 phase 2 example for saving and loading from json files.
// JsonSerializationDemo - https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;


import org.json.JSONObject;

public interface Writable {
    //EFFECTS: returns this as JSON object
    JSONObject toJson();
}
