package com.revature.scottbank.services;

import com.revature.scottbank.daos.AppUserDAO;
import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.exceptions.ResourcePersistenceException;
import com.revature.scottbank.models.AppUser;
import org.junit.*;

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
    public void testCaseCleanUp() { sut = null; }

    @Test
    public void test_logout_returnNull() {
         sut.logout();
         AppUser actualResult = sut.getSessionUser();

         Assert.assertNull("Expected sessionUser to be null", actualResult);

    }

    @Test
    public void test_isSessionActive_returnsFalse() {
        boolean actualResult = sut.isSessionActive();

        Assert.assertFalse("Expected sessionActive to be false", actualResult);
    }

    @Test
    public void test_getSessionUser_returnsTrue_givenSessionUserIsNull() {

        AppUser actualResult = sut.getSessionUser();

        Assert.assertNull("Expected sessionUser to be considered null", actualResult);
    }

    @Test
    public void test_isUserValid_returnsTrue_givenValidUser() {
        // Arrange
        AppUser validUser = new AppUser("valid", "valid", "valid@valid.com", "valid");

        // Act
        boolean actualResult = sut.isUserValid(validUser);

        // Assert
        Assert.assertTrue("Expected user to be considered valid", actualResult);
    }

    @Test
    public void test_isUserValid_returnsFalse_givenInvalidFirstName() {

        // Arrange
        AppUser invalidUser_1 = new AppUser(null, "valid", "valid", "valid");
        AppUser invalidUser_2 = new AppUser("", "valid", "valid", "valid");
        AppUser invalidUser_3 = new AppUser("    ", "valid", "valid", "valid");
        AppUser invalidUser_4 = new AppUser("valid", "valid", "valid", "valid");
        AppUser invalidUser_5 = new AppUser("valid", "", "valid", "valid");


        // Act
        boolean actualResult_1 = sut.isUserValid(invalidUser_1);
        boolean actualResult_2 = sut.isUserValid(invalidUser_2);
        boolean actualResult_3 = sut.isUserValid(invalidUser_3);
        boolean actualResult_4 = sut.isUserValid(invalidUser_4);
        boolean actualResult_5 = sut.isUserValid(invalidUser_5);

        // Assert
        Assert.assertFalse("Expected user to be considered invalid",
                actualResult_1);
        Assert.assertFalse("Expected user to be considered invalid",
                actualResult_2);
        Assert.assertFalse("Expected user to be considered invalid",
                actualResult_3);
        Assert.assertFalse("Expected user to be considered invalid",
                actualResult_4);
        Assert.assertFalse("Expected user to be considered invalid",
                actualResult_5);
    }

    @Ignore //(expected = ResourcePersistenceException.class)
    public void test_registerNewUser_throwsResourcePersistenceException_givenNullUser() {
        AppUser nullUser = new AppUser("valid", "valid", "valid@gmail.com","valid");
        when(mockUserDAO.findByEmail(nullUser.getEmail())).thenReturn(null);
        when(mockUserDAO.save(nullUser)).thenReturn(null);

        try {
            boolean actualResult = sut.registerNewUser(nullUser);
        } finally {
            verify(mockUserDAO, times(0)).save(nullUser);;
        }
    }

    @Test
    public void test_registerNewUser_returnsTrue_givenValidUser() {

        // Arrange
        AppUser validUser = new AppUser("valid", "valid", "valid@valid.com", "valid");
        when(mockUserDAO.findByEmail(validUser.getEmail())).thenReturn(null);
        when(mockUserDAO.save(validUser)).thenReturn(validUser);

        // Act
        boolean actualResult = sut.registerNewUser(validUser);

        // Assert
        Assert.assertTrue("Expected result to be true with valid user " +
                        "provided", actualResult);
        verify(mockUserDAO, times(1)).save(validUser);

    }

    @Test(expected = ResourcePersistenceException.class)
    public void test_registerNewUser_throwsResourcePersistenceException_givenValidUserWithTakenEmail() {

        // Arrange
        AppUser validUser = new AppUser("valid", "valid", "valid@valid.com", "valid");
        when(mockUserDAO.findByEmail(validUser.getEmail())).thenReturn(new AppUser());
        when(mockUserDAO.save(validUser)).thenReturn(validUser);

        // Act
        try {
            boolean actualResult = sut.registerNewUser(validUser);
        } finally {
            // Assert
            verify(mockUserDAO, times(0)).save(validUser);
        }

    }

    @Test(expected = InvalidRequestException.class)
    public void test_registerNewUser_throwsInvalidRequestException_givenInvalidUser() {

        // Arrange
        AppUser invalidUser = new AppUser("", "valid", "valid@valid.com", "valid");
        when(mockUserDAO.findByEmail(invalidUser.getEmail())).thenReturn(null);
        when(mockUserDAO.save(invalidUser)).thenReturn(invalidUser);

        // Act
        try {
            boolean actualResult = sut.registerNewUser(invalidUser);
        } finally {
            // Assert
            verify(mockUserDAO, times(0)).save(invalidUser);
        }

    }

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

}
