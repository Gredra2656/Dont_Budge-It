// Credit to JsonSerializationDemo from the CPSC 210 phase 2 example for saving and loading from json files.
// JsonSerializationDemo - https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// TODO: ADD REQ MOD EFF CLAUSE
public class JsonReader {
    private final String source;

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
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    private Account parseAccount(JSONObject jsonObject) {
        Account acc = new Account();
        acc.updateBalance(jsonObject.getBigDecimal("balance"));
        acc.setSavingsPercentGoal(jsonObject.getBigDecimal("spg"));
        addSources(acc, jsonObject);
        addSavings(acc, jsonObject);
        addDebts(acc, jsonObject);
        addReceipts(acc, jsonObject);
        return acc;
    }

    private void addSources(Account acc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sources");
        for (Object json : jsonArray) {
            JSONObject nextSource = (JSONObject) json;
            addSource(acc, nextSource);
        }
    }

    private void addSource(Account acc, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        BigDecimal val = jsonObject.getBigDecimal("value");
        acc.addSource(name, val);
    }

    private void addSavings(Account acc, JSONObject jsonObject) {
        JSONObject savings = jsonObject.getJSONObject("savings");
        BigDecimal balance = savings.getBigDecimal("balance");
        BigDecimal interest = savings.getBigDecimal("interest");
        acc.getSavings().setBal(balance);
        acc.getSavings().setInterest(interest);
    }

    private void addDebts(Account acc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("debts");
        for (Object json : jsonArray) {
            JSONObject nextDebt = (JSONObject) json;
            addDebt(acc, nextDebt);
        }
    }

    private void addDebt(Account acc, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        BigDecimal value = jsonObject.getBigDecimal("value");
        BigDecimal interest = jsonObject.getBigDecimal("interest");
        acc.addDebt(name, value, interest);

    }

    private void addReceipts(Account acc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("receipts");
        for (Object json : jsonArray) {
            JSONObject nextReceipt = (JSONObject) json;
            String receipt = (String) nextReceipt.get("receipt");
            acc.getReceipts().add(receipt);
        }
    }
}
