package com.revature.scottbank.screens;

import com.revature.scottbank.exceptions.AuthenticationException;
import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.services.UserService;
import com.revature.scottbank.util.ScreenRouter;

import java.io.BufferedReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LoginScreen extends Screen {

    private final UserService userService;

    public LoginScreen(BufferedReader consoleReader, ScreenRouter router,
                       UserService userService) {
        super("/login", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {
        System.out.println("\nLog in to see your ScottBank account details\n");
        System.out.print("Email: ");
        String email = consoleReader.readLine();
        System.out.print("Password: ");
        String password = consoleReader.readLine();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern(
                "E, MMM dd yyyy"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern(
                "hh:mm:ss a"));
        try {
            userService.authUser(email, password);
            String msg = "Successful authentication of email " + email +
                    " on " + date + " at " + time;
            logger.log(msg);
            router.navigate("/dashboard");
        } catch (InvalidRequestException | AuthenticationException e) {
            String msg = email + " on " + date + " at " + time + " -- " +
                    e.getMessage();
            System.out.println(e.getMessage());
            logger.log(msg);
        }
    }

}
