package com.revature.scottbank.screens;

import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.exceptions.ResourcePersistenceException;
import com.revature.scottbank.services.AcctService;
import com.revature.scottbank.util.ScreenRouter;

import java.io.BufferedReader;

public class WithdrawScreen extends Screen {

    private final AcctService acctService;

    public WithdrawScreen(BufferedReader consoleReader, ScreenRouter router,
                          AcctService acctService) {
        super("/withdraw", consoleReader, router);
        this.acctService = acctService;
    }

    @Override
    public void render() throws Exception {
        System.out.print("\nEnter the amount you would like to withdraw\n" +
                "> $");
        String withdrawal = consoleReader.readLine();
        try {
            acctService.makeWithdrawal(withdrawal);
            System.out.println("Withdrawal was successful");
            router.navigate("/dashboard");
        } catch (InvalidRequestException | ResourcePersistenceException e) {
            System.out.println(e.getMessage());
        } finally {
            router.navigate("/dashboard");
        }
    }

}
