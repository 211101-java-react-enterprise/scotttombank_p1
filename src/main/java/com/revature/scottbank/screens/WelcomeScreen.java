package com.revature.scottbank.screens;

import com.revature.scottbank.util.ScreenRouter;
import static com.revature.scottbank.util.AppState.shutdown;

import java.io.BufferedReader;

public class WelcomeScreen extends Screen {

    public WelcomeScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("/welcome", consoleReader, router);
    }

    @Override
    public void render() throws Exception {
        String menu = "\n\nScottBank\n\n" +
                "1) Log In\n" +
                "2) Open A New Account\n" +
                "3) Exit\n" +
                "> ";
        System.out.print(menu);
        String userSelection = consoleReader.readLine();

        switch (userSelection) {
            case "1":
                router.navigate("/login");
                break;
            case "2":
                router.navigate("/new-acct");
                break;
            case "3":
                System.out.println("\nExiting Application...\n\n");
                shutdown();
                break;
            case "throw exception":
                throw new RuntimeException();
            default:
                System.out.println("\nInvalid Selection. Please try again.\n");
        }
    }

}
