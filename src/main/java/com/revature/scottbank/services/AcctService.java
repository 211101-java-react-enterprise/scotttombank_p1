package com.revature.scottbank.services;

import com.revature.scottbank.daos.AcctDAO;
import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.exceptions.ResourcePersistenceException;
import com.revature.scottbank.models.Account;
import com.revature.scottbank.models.AppUser;

public class AcctService {

    private final AcctDAO acctDAO;
    private Account sessionAcct;
    private final UserService userService;

    public AcctService(AcctDAO acctDAO, UserService userService) {
        this.acctDAO = acctDAO;
        this.userService = userService;
        this.sessionAcct = null;
    }

    public Account findMyAcct() {
        sessionAcct = acctDAO.findByHolderId(userService.getSessionUser().getId());
        return sessionAcct;
    }

    public void openNewAcct(AppUser user) {
        Account newAcct = new Account();
        Account savedAcct = acctDAO.save(newAcct, user);
        if (savedAcct == null) {
            throw new ResourcePersistenceException("The account could not be " +
                    "persisted to the datasource");
        }
    }

    public void makeDeposit(String deposit) {
        double amount = formatAmount(deposit);
        double newBalance = amount + sessionAcct.getBalance();
        Account acct = new Account();
        acct.setId(sessionAcct.getId());
        acct.setBalance(newBalance);
        if (!acctDAO.update(acct)) {
            throw new ResourcePersistenceException("The deposit was " +
                    "unsuccessful");
        }
    }

    public void makeWithdrawal(String withdrawal) {
        double amount = formatAmount(withdrawal);
        double newBalance = sessionAcct.getBalance() - amount;
        if (newBalance >= 0) {
            Account acct = new Account();
            acct.setId(sessionAcct.getId());
            acct.setBalance(newBalance);
            if (!acctDAO.update(acct)) {
                throw new ResourcePersistenceException("The withdrawal was " +
                        "unsuccessful");
            }
        } else {
            throw new InvalidRequestException("Withdrawal must not be more " +
                    "than your account balance");
        }
    }

    public boolean isAmountValid(double amount) {
        if (amount <= 0) {
            throw new InvalidRequestException("Invalid Input");
        }
        return true;
    }

    public double formatAmount(String deposit) {
        if (deposit == null || deposit.equals("")) {
            throw new InvalidRequestException("No deposit amount entered");
        }
        double amount;
        try {
            amount = Double.parseDouble(deposit);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Invalid Input");
        } catch (NullPointerException e) {
            throw new InvalidRequestException("No deposit amount entered");
        }
        if (isAmountValid(amount)) {
            amount = (Math.round(amount * 100)) / 100.00f;
        }
        return amount;
    }

 }
