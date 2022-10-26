// Credit to JsonSerializationDemo from the CPSC 210 phase 2 example for saving and loading from json files.
// JsonSerializationDemo - https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    Account acc;

    @Test
    public void testReaderInvalidFile() {
        JsonReader reader = new JsonReader("./data/Idon'texist.json");
        try {
            acc = reader.read();
            fail("IOException was expected");
        } catch (IOException e) {
            // Pass
        }
    }

    @Test
    public void testReaderEmptyAccount() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccount.json");
        try {
            acc = reader.read();
            assertEquals(1, acc.getMonthTracker());
            assertEquals(BigDecimal.ZERO, acc.getBalance());
            assertEquals(BigDecimal.ZERO, acc.getSavingsPercentGoal());
            assertEquals(0, acc.getSources().size());
            assertEquals(BigDecimal.ZERO, acc.getSavingsBal());
            assertEquals(0, acc.getDebts().size());
            assertEquals(0, acc.getReceipts().size());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testReaderGenericAccount() {
        JsonReader reader = new JsonReader("./data/testReaderGenericAccount.json");
        try {
            acc = reader.read();
            assertEquals(2, acc.getMonthTracker());
            assertEquals(BigDecimal.valueOf(5000.55), acc.getBalance());
            assertEquals(BigDecimal.valueOf(.5), acc.getSavingsPercentGoal());
            assertEquals(2, acc.getSources().size());
            checkSource("Work", BigDecimal.valueOf(5000), acc.getSources().get(0));
            checkSource("Bills", BigDecimal.valueOf(-1394.59), acc.getSources().get(1));
            checkSavings(BigDecimal.valueOf(135903.39), BigDecimal.valueOf(.01), acc.getSavings());
            assertEquals(1, acc.getDebts().size());
            checkDebt("Loan", BigDecimal.valueOf(300000), BigDecimal.valueOf(.07), acc.getDebts().get(0));
            assertEquals(1, acc.getReceipts().size());
            assertEquals("This is a receipt", acc.getReceipts().get(0));
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }
}
