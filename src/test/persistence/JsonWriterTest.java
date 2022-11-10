// Credit to JsonSerializationDemo from the CPSC 210 phase 2 example for saving and loading from json files.
// JsonSerializationDemo - https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests the persistence.JsonWriter class for functionality
 */
public class JsonWriterTest extends JsonTest {

    private Account acc;

    @BeforeEach
    void setup() {
        acc = new Account();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my/imbroken;':[]}{)_+/.file.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccount() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json");
            acc = reader.read();
            assertEquals(BigDecimal.ZERO, acc.getBalance());
            assertEquals(BigDecimal.ZERO, acc.getSavingsPercentGoal());
            assertEquals(0, acc.getSources().size());
            assertEquals(BigDecimal.ZERO, acc.getSavingsBal());
            assertEquals(0, acc.getDebts().size());
            assertEquals(0, acc.getReceipts().size());
        } catch (IOException e) {
            fail("Unexpected exception was thrown");
        }
    }

    @Test
    public void testWriterGenericAccount() {
        try {
            acc.depositBalance(BigDecimal.valueOf(5000.55));
            acc.setSavingsPercentGoal(BigDecimal.valueOf(.5));
            acc.addSource("Work", BigDecimal.valueOf(5000));
            acc.addSource("Bills", BigDecimal.valueOf(-1394.59));
            acc.getSavings().addValue(BigDecimal.valueOf(135903.39));
            acc.getSavings().setInterest(BigDecimal.valueOf(.01));
            acc.addDebt("Loan", BigDecimal.valueOf(300000), BigDecimal.valueOf(.07));
            acc.getReceipts().add("This is a receipt");

            JsonWriter writer = new JsonWriter("./data/testWriterGenericAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGenericAccount.json");
            acc = reader.read();
            assertEquals(1, acc.getMonthTracker());
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
            fail("Unexpected exception was thrown");
        }
    }
}
