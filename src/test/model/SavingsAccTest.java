package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccTest {
    SavingsAcc testSave;

    @BeforeEach
    public void setup() {
        testSave = new SavingsAcc(500, .01);
    }

    @Test
    public void testSavingsAccConstructor() {
        assertEquals(500, testSave.getBal());
        assertEquals(.01, testSave.getInterest());
    }

    @Test
    public void testComputeInterest() {
        testSave.computeInterest();
        assertEquals(500 * 1.01, testSave.getBal());
    }

    @Test
    public void testAddBal() {
        testSave.addBal(50);
        assertEquals(550, testSave.getBal());
    }

    @Test
    public void testWithdrawBal() {
        assertTrue(testSave.withdrawBal(50));
        assertEquals(450, testSave.getBal());
    }

    @Test
    public void testWithdrawBalFail() {
        assertFalse(testSave.withdrawBal(501));
        assertEquals(500, testSave.getBal());
    }
}
