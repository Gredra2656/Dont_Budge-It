package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class DebtAccTest {
    DebtAcc testDebt;

    private final static int INIT_VAL = 5000;
    private final static double INIT_INT = .05;

    @BeforeEach
    public void setup() {
        testDebt = new DebtAcc("Credit Card", BigDecimal.valueOf(INIT_VAL), BigDecimal.valueOf(INIT_INT));
    }

    @Test
    public void testCalculateInterestOnDebt() {
        testDebt.calculateInterest();
        assertEquals(BigDecimal.valueOf(INIT_VAL * (1 + INIT_INT)).stripTrailingZeros(),
                testDebt.getValue().stripTrailingZeros());
    }

    @Test
    public void testPayDebt() {
        assertFalse(testDebt.subValue(BigDecimal.valueOf(4000)));
        assertEquals(BigDecimal.valueOf(1000), testDebt.getValue());
    }

    @Test
    public void testPayDebtFully() {
        assertTrue(testDebt.subValue(BigDecimal.valueOf(5000)));
    }

    @Test
    public void testAddDebtValue() {
        testDebt.addValue(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(5050), testDebt.getValue());
    }
}
