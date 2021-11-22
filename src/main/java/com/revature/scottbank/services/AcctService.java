package com.revature.scottbank.services;

import com.revature.scottbank.daos.AcctDAO;
import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.exceptions.ResourcePersistenceException;
import com.revature.scottbank.models.Account;
import com.revature.scottbank.models.AppUser;

public class AcctService {

    private final AcctDAO acctDAO;
    Account sessionAcct;
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
        acctDAO.save(newAcct, user);
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
