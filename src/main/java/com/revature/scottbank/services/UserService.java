package com.revature.scottbank.services;

import com.revature.scottbank.daos.AppUserDAO;
import com.revature.scottbank.exceptions.AuthenticationException;
import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.exceptions.ResourcePersistenceException;
import com.revature.scottbank.models.AppUser;

import java.sql.SQLException;

public class UserService {

    private final AppUserDAO userDAO;

    public UserService(AppUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean registerNewUser(AppUser newUser) {
        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Please provide all " +
                    "valid user information");
        }
        boolean emailAvailable = userDAO.findByEmail(newUser.getEmail());
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

    public AppUser authUser(String email, String password) {
        if (email == null || email.trim().equals("") || password == null ||
                password.trim().equals("")) {
            throw new InvalidRequestException("Email and password are required for logging " +
                    "in");
        }
        AppUser authUser = userDAO.findByEmailAndPassword(email, password);
        if (authUser == null) {
            throw new AuthenticationException();
        }
        return authUser;
    }

    public boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals(
                "")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals(
                "")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("") || !user.getEmail().contains("@") || !user.getEmail().contains("."))
            return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }

    public boolean makeDeposit(String deposit) {
        double amount = formatAmount(deposit);
        double newBalance = amount + sessionAcct.getBalance();
        Account acct = new Account();
        acct.setId(sessionAcct.getId());
        acct.setBalance(newBalance);
        acctDAO.update(acct);
        return true;
    }

    public boolean makeWithdrawal(String withdrawal) {
        double amount = formatAmount(withdrawal);
        double newBalance = sessionAcct.getBalance() - amount;
        if (newBalance >= 0) {
            Account acct = new Account();
            acct.setId(sessionAcct.getId());
            acct.setBalance(newBalance);
            acctDAO.update(acct);
        } else {
            throw new InvalidRequestException("Withdrawal must not be more " +
                    "than your account balance");
        }
        return true;
    }

    public boolean isAmountValid(double amount) {
        return !(amount <= 0);
    }

    public double formatAmount(String deposit) {
        if (deposit.equals("")) {
            throw new InvalidRequestException("No deposit amount entered");
        }
        double amount;
        try {
            amount = Double.parseDouble(deposit);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Invalid Input");
        }
        if (isAmountValid(amount)) {
            amount = (Math.round(amount * 100)) / 100.00d;
        } else {
            throw new InvalidRequestException("Amount must be a positive " +
                    "number");
        }
        return amount;
    }
}
