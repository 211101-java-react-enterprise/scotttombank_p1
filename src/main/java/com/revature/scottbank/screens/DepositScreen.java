package com.revature.scottbank.screens;

import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.exceptions.ResourcePersistenceException;
import com.revature.scottbank.services.AcctService;
import com.revature.scottbank.util.ScreenRouter;

import java.io.BufferedReader;

public class DepositScreen extends Screen {

    private final AcctService acctService;

    public DepositScreen(BufferedReader consoleReader, ScreenRouter router,
                         AcctService acctService) {
        super("/deposit", consoleReader, router);
        this.acctService = acctService;
    }

    @Override
    public void render() throws Exception {
        System.out.print("\nEnter the amount you would like to deposit\n" +
                "> $");
        String deposit = consoleReader.readLine();
        try {
            acctService.makeDeposit(deposit);
            System.out.println("Deposit was successful");
            router.navigate("/dashboard");
        } catch (InvalidRequestException | ResourcePersistenceException e) {
            System.out.println(e.getMessage());
        } finally {
            router.navigate("/dashboard");
        }
    }

}
