package com.revature.scottbank.services;

import com.revature.scottbank.daos.AppUserDAO;
import com.revature.scottbank.exceptions.AuthenticationException;
import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.exceptions.ResourcePersistenceException;
import com.revature.scottbank.models.AppUser;

public class UserService {

    private final AppUserDAO userDAO;
    private AppUser sessionUser;

    public UserService(AppUserDAO userDAO) {
        this.userDAO = userDAO;
        this.sessionUser = null;
    }

    public AppUser getSessionUser() { return sessionUser; }

    public boolean registerNewUser(AppUser newUser) {
        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Please provide all " +
                    "requested information");
        }
        boolean emailAvailable =
                userDAO.findByEmail(newUser.getEmail()) == null;
        if (!emailAvailable) {
            throw new ResourcePersistenceException("There is already an " +
                    "account using this email address");
        }
        AppUser registeredUser = userDAO.save(newUser);
        if (registeredUser == null) {
            throw new ResourcePersistenceException("The user could not be " +
                    "persisted to the datasource");
        }
        return true;
    }

    public void authUser(String email, String password) {
        if (email == null || email.trim().equals("") || password == null ||
                password.trim().equals("")) {
            throw new InvalidRequestException("Email and password are required for logging " +
                    "in");
        }
        AppUser authUser = userDAO.findByEmailAndPassword(email, password);
        if (authUser == null) {
            throw new AuthenticationException();
        }
        sessionUser = authUser;
    }

    public boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals(
                "")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals(
                "")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals(
                "")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }

    public boolean isSessionActive() { return sessionUser != null; }

    public void logout() { sessionUser = null; }

}
