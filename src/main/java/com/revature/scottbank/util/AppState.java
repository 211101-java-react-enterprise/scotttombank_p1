package com.revature.scottbank.util;

import com.revature.scottbank.daos.AcctDAO;
import com.revature.scottbank.daos.AppUserDAO;
import com.revature.scottbank.services.AcctService;
import com.revature.scottbank.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {

    private static boolean appRunning;

    public AppState() {
        appRunning = true;
        BufferedReader consoleReader =
                new BufferedReader(new InputStreamReader(System.in));

        AppUserDAO userDAO = new AppUserDAO();
        UserService userService = new UserService(userDAO);

        AcctDAO acctDAO = new AcctDAO();
        AcctService acctService = new AcctService(acctDAO, userService);

        Logger logger = Logger.getLogger(true);
        logger.log("Initializing app...");

        logger.log("App initialized");
    }

    public void startup() {
        try {
            while (appRunning) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shutdown() { appRunning = false; }

}
