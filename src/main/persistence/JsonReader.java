package persistence;

import model.Account;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read source string
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account from file and returns it, throwing IOException if file cannot be read
    public Account read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    // EFFECTS: Reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private Account parseAccount(JSONObject jsonObject) {
        Account acc = new Account();
        addSources(acc, jsonObject);
        addSavings(acc, jsonObject);
        addDebt(acc, jsonObject);
        addReceipts(acc, jsonObject);
        addSavingsPercentGoal(acc, jsonObject);
        return acc;
    }


    private void addSources(Account acc, JSONObject jsonObject) {
    }

    private void addSavings(Account acc, JSONObject jsonObject) {
        
    }

    private void addDebt(Account acc, JSONObject jsonObject) {
        
    }

    private void addReceipts(Account acc, JSONObject jsonObject) {

    }

    private void addSavingsPercentGoal(Account acc, JSONObject jsonObject) {
    }
}
