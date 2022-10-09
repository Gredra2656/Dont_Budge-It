package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DebtAccTest {
    DebtAcc testDebt;

    private final static int INIT_VAL = 5000;
    private final static double INIT_INT = .05;

    @BeforeEach
    public void setup() {
        testDebt = new DebtAcc("Credit Card", INIT_VAL, INIT_INT);
    }

    @Test
    public void testCalculateInterestOnDebt() {
        testDebt.calculateInterest();
        assertEquals((INIT_VAL * (1 + INIT_INT)), testDebt.getValue());
    }

    @Test
    public void testPayDebt() {
        assertFalse(testDebt.subValue(4000));
        assertEquals(1000, testDebt.getValue());
    }

    @Test
    public void testPayDebtFully() {
        assertTrue(testDebt.subValue(5000));
    }

    @Test
    public void testAddDebtValue() {
        testDebt.addValue(50);
        assertEquals(5050, testDebt.getValue());
    }
}
