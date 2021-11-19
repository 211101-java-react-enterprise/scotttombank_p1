package com.revature.scottbank.util;

import com.revature.scottbank.daos.AcctDAO;
import com.revature.scottbank.daos.AppUserDAO;
import com.revature.scottbank.screens.*;
import com.revature.scottbank.services.AcctService;
import com.revature.scottbank.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {

    private static boolean appRunning;
    private final ScreenRouter router;

    public AppState() {
        appRunning = true;
        router = new ScreenRouter();
        BufferedReader consoleReader =
                new BufferedReader(new InputStreamReader(System.in));

        AppUserDAO userDAO = new AppUserDAO();
        UserService userService = new UserService(userDAO);

        AcctDAO acctDAO = new AcctDAO();
        AcctService acctService = new AcctService(acctDAO, userService);

        Logger logger = Logger.getLogger(true);
        logger.log("Initializing app...");

        router.addScreen(new WelcomeScreen(consoleReader, router));
        router.addScreen(new NewAcctScreen(consoleReader, router, userService
                , acctService));
        router.addScreen(new LoginScreen(consoleReader, router, userService));
        router.addScreen(new DashboardScreen(consoleReader, router,
                userService, acctService));
        router.addScreen(new DepositScreen(consoleReader, router,
                acctService));
        router.addScreen(new WithdrawScreen(consoleReader, router,
                acctService));

        logger.log("App initialized");
    }

    public void startup() {
        try {
            while (appRunning) {
                router.navigate("/welcome");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shutdown() { appRunning = false; }

}
