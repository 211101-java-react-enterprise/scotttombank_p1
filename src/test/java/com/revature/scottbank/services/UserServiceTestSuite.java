package com.revature.scottbank.services;

import com.revature.scottbank.daos.AppUserDAO;
import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.exceptions.ResourcePersistenceException;
import com.revature.scottbank.models.AppUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import static org.mockito.Mockito.*;

public class UserServiceTestSuite {

    UserService sut;
    AppUserDAO mockUserDAO;

    @Before
    public void testCaseSetup() {
        mockUserDAO = mock(AppUserDAO.class);
        sut = new UserService(mockUserDAO);
    }

    @After
    public void testCaseCleanUp() {
        sut = null;
    }

    @Test
    public void test_isUserValid_returnsTrue_givenValidUser() {
        AppUser validUser = new AppUser("valid", "valid", "valid@gmail.com", "valid");

        boolean actualResult = sut.isUserValid(validUser);

        Assert.assertTrue("Expected user to be considered valid", actualResult);
    }

    @Test
    public void test_isUserValid_returnFalse_givenNullUser() {
        AppUser nullUser = null;

        boolean actualResult = sut.isUserValid(nullUser);

        Assert.assertFalse("Expected user to be considered null", actualResult);
    }

    @Test
    public void test_isUserValid_returnFalse_givenFirstNameNull() {
        AppUser nullFirstNameUser = new AppUser(null, "valid", "valid@gmail.com", "valid");

        boolean actualResult = sut.isUserValid(nullFirstNameUser);

        Assert.assertFalse("Expected first name to be considered null", actualResult);
    }

    @Test
    public void test_isUserValid_returnFalse_givenLastNameNull() {
        AppUser nullLastNameUser = new AppUser("valid", null, "valid@gmail.com", "valid");

        boolean actualResult = sut.isUserValid(nullLastNameUser);

        Assert.assertFalse("Expected first name to be considered null", actualResult);
    }

    @Test
    public void test_isUserValid_returnFalse_givenEmailNull() {
        AppUser nullEmailUser = new AppUser("valid", "valid", null, "valid");

        boolean actualResult = sut.isUserValid(nullEmailUser);

        Assert.assertFalse("Expected first name to be considered null", actualResult);
    }

    @Test
    public void test_isUserValid_returnFalse_givenPasswordNull() {
        AppUser nullPasswordUser = new AppUser("valid", "valid", "valid", null);

        boolean actualResult = sut.isUserValid(nullPasswordUser);

        Assert.assertFalse("Expected first name to be considered null", actualResult);
    }

    @Test
    public void test_registerNewUser_returnsTrue_givenValidUser() {
        AppUser validUser = new AppUser("valid", "valid", "valid@gmail.com", "valid");
        when(mockUserDAO.findByEmail(validUser.getEmail())).thenReturn(true);
        when(mockUserDAO.save(validUser)).thenReturn(validUser);

        boolean actualResult = sut.registerNewUser(validUser);

        Assert.assertTrue("Expected result to be true with valid user " +
                "provided", actualResult);
        verify(mockUserDAO, times(1)).save(validUser);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_registerNewUser_throwsInvalidRequestException_givenInvalidUser() {
        AppUser invalidUser = new AppUser(null, "valid", "valid@gmail.com", "valid");
        when(mockUserDAO.findByEmail(invalidUser.getEmail())).thenReturn(false);
        when(mockUserDAO.save(invalidUser)).thenReturn(invalidUser);

        boolean actualResult = sut.registerNewUser(invalidUser);

        Assert.assertTrue("Expected result to be false with invalid user " +
                "provided", actualResult);
        verify(mockUserDAO, times(1)).save(invalidUser);
    }

    @Test(expected = ResourcePersistenceException.class)
    public void test_registerNewUser_throwsResourcePersistenceException_givenTakenEmail() {
        AppUser invalidUser = new AppUser("valid", "valid", "valid@gmail.com", "valid");
        when(mockUserDAO.findByEmail(invalidUser.getEmail())).thenReturn(false);
        when(mockUserDAO.save(invalidUser)).thenReturn(invalidUser);

        boolean actualResult = sut.registerNewUser(invalidUser);

        Assert.assertTrue("Expected result to be false with invalid user " +
                "provided", actualResult);
        verify(mockUserDAO, times(1)).save(invalidUser);
    }

    @Test(expected = ResourcePersistenceException.class)
    public void test_registerNewUser_throwsResourcePersistenceException_givenNullUser() {
        AppUser nullUser = new AppUser("valid", "valid", "valid@gmail.com", "valid");

        when(mockUserDAO.findByEmail(nullUser.getEmail())).thenReturn(false);
        when(mockUserDAO.save(nullUser)).thenReturn(null);

        boolean actualResult = sut.registerNewUser(nullUser);

        Assert.assertTrue("Expected result to be false with null user " +
                "provided", actualResult);
        verify(mockUserDAO, times(1)).save(nullUser);
    }

//    @Test
//    public void test_authUser_throwsInvalidException_givenNullEmail() {
//        String email = null;
//        String password = null;
//
//        when(mockUserDAO.findByEmailAndPassword(email, password)).thenThrow(InvalidRequestException.class);
//        AppUser actualResult = sut.authUser(email, password);
//    }

    @Test
    public void test_isAmountValid_returnsTrue_givenValidAmount() {
        double validAmount = 1.11d;

        boolean actualResult = sut.isAmountValid(validAmount);

        Assert.assertTrue("Expected amount to be considered valid",
                actualResult);
    }

    @Test
    public void test_isAmountValid_returnsFalse_givenInvalidAmount() {
        double invalidAmount_1 = -1.11d;

        boolean actualResult_1 = sut.isAmountValid(invalidAmount_1);

        Assert.assertFalse("Expected amount to be considered invalid",
                actualResult_1);

    }

    @Test
    public void test_formatAmount_returnsADouble_givenValidAmount() {

        String validAmount = "1.11";

        double actualResult = sut.formatAmount(validAmount);

        Assert.assertEquals(1.11d, actualResult, 0.0);
    }

    @Test
    public void test_formatAmount_throwsInvalidRequestException_givenInvalidNumberFormat() {

        String invalidAmount_1 = "abc";
        String invalidAmount_2 = "";
        String invalidAmount_3 = "-1.11";

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
        String validAmount = "1.11";
        AppUser appUser = new AppUser("valid", "valid", "new@gmail.com", "valid");
        when(mockUserDAO.updateBalance(appUser)).thenReturn(true);

        boolean actualResult = sut.makeDeposit(appUser ,validAmount);

        Assert.assertTrue("Expected amount to be considered valid",
                actualResult);

    }

    @Test
    public void test_makeWithdrawal_returnsTrue_givenValidAmount() {
        String validAmount = "1.11";
        AppUser appUser = new AppUser("valid", "valid", "new@gmail.com", "valid");
        when(mockUserDAO.updateBalance(appUser)).thenReturn(true);

        sut.makeDeposit(appUser, "100.00");
        boolean actualResult = sut.makeWithdrawal(appUser, validAmount);

        Assert.assertTrue("Expected amount to be considered valid",
                actualResult);
    }

    @Test
    public void test_deleteUser_returnsTrue_givenValidUser() {
        AppUser appUser = new AppUser("valid", "valid", "new@gmail.com", "valid");
        when(mockUserDAO.removeById(appUser.getId())).thenReturn(true);

        boolean actualResult = sut.deleteUser(appUser);

        Assert.assertTrue("Expected user to be valid for deletion", actualResult);

    }

}






//    @Test
//    public void test_isUserValid_returnsTrue_givenValidUser() {
//        // Arrange
//        AppUser validUser = new AppUser("valid", "valid", "valid@gmail.com", "valid");
//
//        // Act
//        boolean actualResult = sut.isUserValid(validUser);
//
//        // Assert
//        Assert.assertTrue("Expected user to be considered valid", actualResult);
//    }
//
//    @Test
//    public void test_isUserValid_returnsFalse_givenInvalidFirstName() {
//
//        // Arrange
//        AppUser invalidUser_1 = new AppUser(null, "valid", "valid", "valid");
//        AppUser invalidUser_2 = new AppUser("", "valid", "valid", "valid");
//        AppUser invalidUser_3 = new AppUser("    ", "valid", "valid", "valid");
//
//        // Act
//        boolean actualResult_1 = sut.isUserValid(invalidUser_1);
//        boolean actualResult_2 = sut.isUserValid(invalidUser_2);
//        boolean actualResult_3 = sut.isUserValid(invalidUser_3);
//
//        // Assert
//        Assert.assertFalse("Expected user to be considered invalid",
//                actualResult_1);
//        Assert.assertFalse("Expected user to be considered invalid",
//                actualResult_2);
//        Assert.assertFalse("Expected user to be considered invalid",
//                actualResult_3);
//
//    }
//
//    @Test
//    public void test_registerNewUser_returnsTrue_givenValidUser() {
//
//        // Arrange
//        AppUser validUser = new AppUser("valid", "valid", "valid@gmail.com", "valid");
//        when(mockUserDAO.findByEmail(validUser.getEmail())).thenReturn(true);
//        when(mockUserDAO.save(validUser)).thenReturn(validUser);
//
//        // Act
//        boolean actualResult = sut.registerNewUser(validUser);
//
//        // Assert
//        Assert.assertTrue("Expected result to be true with valid user " +
//                        "provided", actualResult);
//        verify(mockUserDAO, times(1)).save(validUser);
//
//    }
//
//    @Test(expected = ResourcePersistenceException.class)
//    public void test_registerNewUser_throwsResourcePersistenceException_givenValidUserWithTakenEmail() {
//
//        // Arrange
//        AppUser validUser = new AppUser("valid", "valid", "valid@gmail.com", "valid");
//        when(mockUserDAO.findByEmail(validUser.getEmail())).thenReturn(false);
//        when(mockUserDAO.save(validUser)).thenReturn(validUser);
//
//        // Act
//        try {
//            boolean actualResult = sut.registerNewUser(validUser);
//        } finally {
//            // Assert
//            verify(mockUserDAO, times(0)).save(validUser);
//        }
//
//    }
//
//    @Test(expected = InvalidRequestException.class)
//    public void test_registerNewUser_throwsInvalidRequestException_givenInvalidUser() {
//
//        // Arrange
//        AppUser invalidUser = new AppUser("", "valid", "valid@gmail.com", "valid");
//        when(mockUserDAO.findByEmail(invalidUser.getEmail())).thenReturn(false);
//        when(mockUserDAO.save(invalidUser)).thenReturn(invalidUser);
//
//        // Act
//        try {
//            boolean actualResult = sut.registerNewUser(invalidUser);
//        } finally {
//            // Assert
//            verify(mockUserDAO, times(0)).save(invalidUser);
//        }
//    @Test
//    public void test_returns_given() {
//
//        // Arrange
//
//
//        // Act
//
//
//        // Assert
//
//
//    }
//
//    @Test
//    public void test_returns_given() {
//
//        // Arrange
//
//
//        // Act
//
//
//        // Assert
//
//
//    }


