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
        assertFalse(testAcc.getSources().isEmpty());
        assertEquals(0, testAcc.getSavings().getBal());
        assertEquals(0, testAcc.getSavings().getInterest());
        assertTrue(testAcc.getDebts().isEmpty());
    }

    @Test
    public void testAddSource() {
        testAcc.addSource("Temp Work",  10000);
        assertEquals(5, testAcc.getSources().size());
    }

    @Test
    public void testRemoveSource() {
        assertTrue(testAcc.removeSource("test source"));
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
        assertEquals(0, testAcc.calculateIncome());
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
        double income = testAcc.calculateIncome();
        double expense = testAcc.calculateExpenses();
        double surplus = testAcc.calculateSurplus();
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
        testAcc.addDebt("Credit Card", 1000, .2);
        testAcc.getSavings().setInterest(.01);
        testAcc.depositSavings(500);

        testAcc.computeNextPeriod();

        assertEquals(5000+5000+500-360-1, testAcc.getBalance());
        assertEquals(1000 * 1.2, testAcc.getDebts().get(0).getValue());
        assertEquals(500 * 1.01, testAcc.getSavings().getBal());
        assertEquals(1, testAcc.getReceipts().size());

        /*List<DebtAcc> debts = testAcc.getDebts();
        SavingsAcc savings = testAcc.getSavings();
        List<Source> sources = testAcc.getSources();

        for (int d = 0; d < debts.size(); d++) {
            DebtAcc dSpec = debts.get(d);
            dSpec.calculateInterest();
        }
        savings.calculateInterest();

        int surplus = testAcc.calculateSurplus();*/
    }

    @Test
    public void testAddDebt() {
        testAcc.addDebt("Credit Card2", 100, .2);
        assertEquals(1, testAcc.getDebts().size());
    }

    @Test
    public void testDepositSavings() {
        testAcc.depositSavings(10000);
        assertEquals(10000, testAcc.getSavingsBal());
    }

    @Test
    public void testGetSavingsBal() {
        assertEquals(0, testAcc.getSavingsBal());
    }

    @Test
    public void testSuggestSavings() {
        testAcc.setSavingsPercentGoal(.5);
        assertEquals(testAcc.getSavingsPercentGoal() * testAcc.calculateSurplus(),
                testAcc.suggestSavings(testAcc.calculateSurplus()));
    }
}
