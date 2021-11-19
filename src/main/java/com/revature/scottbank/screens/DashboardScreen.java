package com.revature.scottbank.screens;

import com.revature.scottbank.models.Account;
import com.revature.scottbank.models.AppUser;
import com.revature.scottbank.services.AcctService;
import com.revature.scottbank.services.UserService;
import com.revature.scottbank.util.ScreenRouter;

import java.io.BufferedReader;

public class DashboardScreen extends Screen {

    private final UserService userService;
    private final AcctService acctService;

    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router,
                           UserService userService, AcctService acctService) {
        super("/dashboard", consoleReader, router);
        this.userService = userService;
        this.acctService = acctService;
    }

    @Override
    public void render() throws Exception {
        AppUser sessionUser = userService.getSessionUser();
        while (userService.isSessionActive()) {
            Account sessionAcct = acctService.findMyAcct();
            System.out.printf("\n%s %s\n", sessionUser.getFirstName(),
                    sessionUser.getLastName());
            System.out.printf("Account %s\n", sessionAcct.getId());
            double balance = (Math.round(sessionAcct.getBalance() * 100)) / 100.0f;
            System.out.printf("Balance: $%,8.2f\n", balance);
            String menu = "1) Deposit to Your Account\n" +
                    "2) Withdraw from Your Account\n" +
                    "3) Log Out\n" +
                    "> ";
            System.out.print(menu);
            String userSelection = consoleReader.readLine();

            switch (userSelection) {
                case "1":
                    router.navigate("/deposit");
                    break;
                case "2":
                    router.navigate("/withdraw");
                    break;
                case "3":
                    System.out.println("\nGoodbye. Come back soon.\n\n");
                    userService.logout();
                    break;
                default:
                    System.out.println("\nInvalid Selection. Please try again.\n");
                    router.navigate("/dashboard");
            }
        }
    }

}
