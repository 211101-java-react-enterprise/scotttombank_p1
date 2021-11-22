package com.revature.scottbank.services;

import com.revature.scottbank.daos.AcctDAO;
import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.models.Account;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import static org.mockito.Mockito.*;

public class AcctServiceTests {

    AcctService sut;
    AcctDAO mockAcctDAO;
    UserService mockUserService;

    @Before
    public void testCaseSetup() {
        mockAcctDAO = mock(AcctDAO.class);
        sut = new AcctService(mockAcctDAO, mockUserService);
        sut.sessionAcct = new Account("1", 1.11d);
    }

    @After
    public void testCaseCleanUp() { sut = null; }

    @Test
    public void test_isAmountValid_returnsTrue_givenValidAmount() {

        // Arrange
        double validAmount = 1.11d;

        // Act
        boolean actualResult = sut.isAmountValid(validAmount);

        // Assert
        Assert.assertTrue("Expected amount to be considered valid",
                actualResult);

    }

    @Test
    public void test_isAmountValid_returnsFalse_givenInvalidAmount() {

        // Arrange
        double invalidAmount_1 = -1.11d;

        // Act
        boolean actualResult_1 = sut.isAmountValid(invalidAmount_1);

        // Assert
        Assert.assertFalse("Expected amount to be considered invalid",
                actualResult_1);

    }

    @Test
    public void test_formatAmount_returnsADouble_givenValidAmount() {

        // Arrange
        String validAmount = "1.11";

        // Act
        double actualResult = sut.formatAmount(validAmount);

        // Assert
        Assert.assertEquals(1.11d, actualResult, 0.0);

    }

    @Test
    public void test_formatAmount_throwsInvalidRequestException_givenInvalidNumberFormat() {

        // Arrange
        String invalidAmount_1 = "abc";
        String invalidAmount_2 = "";
        String invalidAmount_3 = "-1.11";

        // Act
        final ThrowingRunnable throwingRunnable_1 =
                () -> sut.formatAmount(invalidAmount_1);
        final ThrowingRunnable throwingRunnable_2 =
                () -> sut.formatAmount(invalidAmount_2);
        final ThrowingRunnable throwingRunnable_3 =
                () -> sut.formatAmount(invalidAmount_3);

        Assert.assertThrows("Invalid Input", InvalidRequestException.class,
                throwingRunnable_1);
        Assert.assertThrows("No deposit amount entered", InvalidRequestException.class,
                throwingRunnable_2);
        Assert.assertThrows("Amount must be a positive number",
                InvalidRequestException.class,
                throwingRunnable_3);

    }

    @Test
    public void test_makeDeposit_returnsTrue_givenValidAmount() {

        // Arrange
        String validAmount = "1.11";
        Account acct = sut.sessionAcct;
        when(mockAcctDAO.update(acct)).thenReturn(true);

        // Act
        boolean actualResult = sut.makeDeposit(validAmount);

        // Assert
        Assert.assertTrue("Expected amount to be considered valid",
                actualResult);

    }

    @Test
    public void test_makeWithdrawal_returnsTrue_givenValidAmount() {

        // Arrange
        String validAmount = "1.11";
        Account acct = sut.sessionAcct;
        when(mockAcctDAO.update(acct)).thenReturn(true);

        // Act
        boolean actualResult = sut.makeWithdrawal(validAmount);

        // Assert
        Assert.assertTrue("Expected amount to be considered valid",
                actualResult);

    }

    @Test
    public void test_makeWithdrawal_throwsInvalidRequestException_givenInvalidAmount() {

        // Arrange
        String invalidAmount = "1.12";

        // Act
        final ThrowingRunnable throwingRunnable =
                () -> sut.makeWithdrawal(invalidAmount);

        // Assert
        Assert.assertThrows("Withdrawal must not be more than your account balance", InvalidRequestException.class,
                throwingRunnable);

    }

}
