package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    Account testAcc;

    @BeforeEach
    public void setup() {
        testAcc = new Account();
        testAcc.addSource("test source", -1);
        testAcc.addSource("Work", 5000);
        testAcc.addSource("Lawn Mowing", 500);
        testAcc.addSource("Bills", -360);
    }

    @Test
    public void testAccountConstructor() {
        assertEquals(0 ,testAcc.getBalance());
        assertEquals(0, testAcc.getSavingsPercentGoal());
        assertTrue(testAcc.getReceipts().isEmpty());
        assertTrue(testAcc.getSources().isEmpty());
        assertEquals(0, testAcc.getSavings().getBal());
        assertEquals(.01, testAcc.getSavings().getInterest());
        assertTrue(testAcc.getDebts().isEmpty());
    }

    @Test
    public void testAddSource() {
        testAcc.addSource("Temp Work",  10000);
    }

    @Test
    public void testRemoveSource() {
        testAcc.removeSource("test source");
    }

    @Test
    public void testRemoveNonExistentSource() {
        assertFalse(testAcc.removeSource("president of belarus"));
    }

    @Test
    public void testCalculateSurplus() {
        assertEquals(5000+500-1-360, testAcc.calculateSurplus());
    }

    @Test
    public void testCalculateSurplusNeg() {
        testAcc.addSource("Temp Bill", -6000);
        assertEquals(5000+500-1-360-6000, testAcc.calculateSurplus());
    }

    @Test
    public void testCalculateIncome() {
        assertEquals(5000+500, testAcc.calculateIncome());
    }

    @Test
    public void testCalculateNoIncome() {
        testAcc = new Account();
        assertEquals(0, testAcc);
    }

    @Test
    public void testCalculateExpenses() {
        assertEquals(-1-360, testAcc.calculateExpenses());
    }

    @Test
    public void testCalculateNoExpenses() {
        testAcc = new Account();
        assertEquals(0, testAcc.calculateExpenses());
    }

    @Test
    public void testReturnReceipt() {
        int income = testAcc.calculateIncome();
        int expense = testAcc.calculateExpenses();
        int surplus = testAcc.calculateSurplus();
        String receipt;

        receipt = "Income: "+ income +
                "\nExpenses: " + expense + "\n------" + "\nTotal: " + surplus;
        assertEquals(receipt, testAcc.returnReceipt());
    }

    @Test
    public void testAddBalance() {
        testAcc.addBalance(500);
        assertEquals(500, testAcc.getBalance());
    }

    @Test
    public void testWithdrawBalance() {
        testAcc.addBalance(1000);
        testAcc.withdrawBalance(500);
        assertEquals(500, testAcc.getBalance());
    }

    @Test
    public void testComputeNextPeriod() {
        testAcc.addBalance(5000);

        // START HERE TODO
    }
}
