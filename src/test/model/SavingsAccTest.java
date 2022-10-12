package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccTest {
    SavingsAcc testSave;

    @BeforeEach
    public void setup() {
        testSave = new SavingsAcc(BigDecimal.valueOf(500), BigDecimal.valueOf(.01));
    }

    @Test
    public void testSavingsAccConstructor() {
        assertEquals(BigDecimal.valueOf(500), testSave.getBal());
        assertEquals(BigDecimal.valueOf(.01), testSave.getInterest());
    }

    @Test
    public void testCalculateInterest() {
        testSave.calculateInterest();
        assertEquals(BigDecimal.valueOf(500 * 1.01).stripTrailingZeros(), testSave.getBal().stripTrailingZeros());
    }

    @Test
    public void testAddValue() {
        testSave.addValue(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(550), testSave.getBal());
    }

    @Test
    public void testSubValue() {
        assertFalse(testSave.subValue(BigDecimal.valueOf(50)));
        assertEquals(BigDecimal.valueOf(450), testSave.getBal());
    }

    @Test
    public void testSubValueCompletely() {
        assertTrue(testSave.subValue(BigDecimal.valueOf(500)));
        assertEquals(BigDecimal.valueOf(0), testSave.getBal());
    }
}
