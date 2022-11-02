// Credit to JsonSerializationDemo from the CPSC 210 phase 2 example for saving and loading from json files.
// JsonSerializationDemo - https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import model.DebtAcc;
import model.SavingsAcc;
import model.Source;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Used to supply common methods to the persistence.JsonReaderTest and persistence.JsonWriterTest classes
 */
public class JsonTest {

    protected void checkSource(String name, BigDecimal val, Source source) {
        assertEquals(name, source.getName());
        assertEquals(val, source.getValue());
    }

    protected void checkSavings(BigDecimal bal, BigDecimal interest, SavingsAcc savingsAcc) {
        assertEquals(bal, savingsAcc.getBal());
        assertEquals(interest, savingsAcc.getInterest());
    }

    protected void checkDebt(String name, BigDecimal bal, BigDecimal interest, DebtAcc debtAcc) {
        assertEquals(name, debtAcc.getName());
        assertEquals(bal, debtAcc.getValue());
        assertEquals(interest, debtAcc.getInterest());
    }
}
