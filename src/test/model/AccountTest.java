package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    Account testAcc;

    @BeforeEach
    public void setup() {
        testAcc = new Account();
        testAcc.addSource("test source", BigDecimal.valueOf(-1));
        testAcc.addSource("Work", BigDecimal.valueOf(5000));
        testAcc.addSource("Lawn Mowing", BigDecimal.valueOf(500));
        testAcc.addSource("Bills", BigDecimal.valueOf(-360));
    }

    @Test
    public void testAccountConstructor() {
        assertEquals(BigDecimal.ZERO ,testAcc.getBalance());
        assertEquals(BigDecimal.ZERO, testAcc.getSavingsPercentGoal());
        assertTrue(testAcc.getReceipts().isEmpty());
        assertFalse(testAcc.getSources().isEmpty());
        assertEquals(BigDecimal.ZERO, testAcc.getSavings().getBal());
        assertEquals(BigDecimal.ZERO, testAcc.getSavings().getInterest());
        assertTrue(testAcc.getDebts().isEmpty());
    }

    @Test
    public void testAddSource() {
        testAcc.addSource("Temp Work",  BigDecimal.valueOf(10000));
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
        assertEquals(BigDecimal.valueOf(5000+500-1-360), testAcc.calculateSurplus());
    }

    @Test
    public void testCalculateSurplusNeg() {
        testAcc.addSource("Temp Bill", BigDecimal.valueOf(-6000));
        assertEquals(BigDecimal.valueOf(5000+500-1-360-6000), testAcc.calculateSurplus());
    }

    @Test
    public void testCalculateIncome() {
        assertEquals(BigDecimal.valueOf(5000+500), testAcc.calculateIncome());
    }

    @Test
    public void testCalculateNoIncome() {
        testAcc = new Account();
        assertEquals(BigDecimal.ZERO, testAcc.calculateIncome());
    }

    @Test
    public void testCalculateExpenses() {
        assertEquals(BigDecimal.valueOf(-1-360), testAcc.calculateExpenses());
    }

    @Test
    public void testCalculateNoExpenses() {
        testAcc = new Account();
        assertEquals(BigDecimal.ZERO, testAcc.calculateExpenses());
    }

    @Test
    public void testReturnReceipt() {
        BigDecimal income = testAcc.calculateIncome();
        BigDecimal expense = testAcc.calculateExpenses();
        BigDecimal surplus = testAcc.calculateSurplus();
        BigDecimal spg = testAcc.getSavingsPercentGoal();
        String receipt;

        receipt = "Income: "+ income +
                "\nExpenses: " + expense + "\n------" + "\nTotal: " + surplus + "\nRecommended Savings: "
        + testAcc.calculateSurplus().multiply(spg);
        assertEquals(receipt, testAcc.returnReceipt());
    }

    @Test
    public void testAddBalance() {
        testAcc.addBalance(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(500), testAcc.getBalance());
    }

    @Test
    public void testWithdrawBalance() {
        testAcc.addBalance(BigDecimal.valueOf(1000));
        testAcc.withdrawBalance(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(500), testAcc.getBalance());
    }

    @Test
    public void testUpdateBalance() {
        testAcc.updateBalance(BigDecimal.valueOf(5345));
        assertEquals(BigDecimal.valueOf(5345), testAcc.getBalance());
    }

    @Test
    public void testComputeNextPeriod() {
        testAcc.addBalance(BigDecimal.valueOf(5000));
        testAcc.addDebt("Credit Card", BigDecimal.valueOf(1000), BigDecimal.valueOf(.2));
        testAcc.getSavings().setInterest(BigDecimal.valueOf(.01));
        testAcc.depositSavings(BigDecimal.valueOf(500));

        testAcc.computeNextPeriod();

        assertEquals(BigDecimal.valueOf(5000+5000+500-500-360-1), testAcc.getBalance());
        assertEquals(BigDecimal.valueOf(1000 * 1.2), testAcc.getDebts().get(0).getValue());
        assertEquals(BigDecimal.valueOf(500 * 1.01).stripTrailingZeros(),
                testAcc.getSavings().getBal().stripTrailingZeros());
        assertEquals(1, testAcc.getReceipts().size());
    }

    @Test
    public void testAddDebt() {
        testAcc.addDebt("Credit Card2", BigDecimal.valueOf(100), BigDecimal.valueOf(.2));
        assertEquals(1, testAcc.getDebts().size());
    }

    @Test
    public void removeDebt() {
        testAcc.addDebt("Credit Card", BigDecimal.valueOf(20000), BigDecimal.valueOf(.2));
        assertTrue(testAcc.removeDebt("Credit Card"));
    }

    /*
    @Test
    public void payDebt() {
        testAcc.addBalance(BigDecimal.valueOf(501));
        testAcc.addDebt("Credit Card", BigDecimal.valueOf(2000), BigDecimal.valueOf(.1));
        assertTrue(testAcc.payDebt("Credit Card", BigDecimal.valueOf(500)));
        assertTrue(testAcc.debts.get(0).getValue().equals(BigDecimal.valueOf(1500)));
        assertEquals(BigDecimal.valueOf(1), testAcc.getBalance());
    }

    @Test
    public void payNonExistentDebt() {
        assertFalse(testAcc.payDebt("Credit Card", BigDecimal.valueOf(50)));
    }
     */

    @Test

    public void removeNonExistentDebt() {
        testAcc.addDebt("Credit Card", BigDecimal.valueOf(20000), BigDecimal.valueOf(.2));
        assertFalse(testAcc.removeDebt("WienerSchnitzel"));
    }

    @Test
    public void testDepositSavings() {
        testAcc.depositSavings(BigDecimal.valueOf(10000));
        assertEquals(BigDecimal.valueOf(10000), testAcc.getSavingsBal());
    }

    @Test
    public void testWithdrawSavings() {
        testAcc.withdrawSavings(BigDecimal.valueOf(200));
    }

    @Test
    public void testGetSavingsBal() {
        assertEquals(BigDecimal.ZERO, testAcc.getSavingsBal());
    }

    @Test
    public void testSuggestSavings() {
        testAcc.setSavingsPercentGoal(BigDecimal.valueOf(.5));
        assertEquals(testAcc.getSavingsPercentGoal().multiply(testAcc.calculateSurplus()),
                testAcc.suggestSavings(testAcc.calculateSurplus()));
    }
}
