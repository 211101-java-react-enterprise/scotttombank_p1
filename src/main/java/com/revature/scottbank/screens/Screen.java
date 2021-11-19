package com.revature.scottbank.screens;

import com.revature.scottbank.util.Logger;
import com.revature.scottbank.util.ScreenRouter;

import java.io.BufferedReader;

public abstract class Screen {

    String route;
    BufferedReader consoleReader;
    ScreenRouter router;
    Logger logger = Logger.getLogger(false);

    public Screen(String route, BufferedReader consoleReader,
                  ScreenRouter router) {
        this.route = route;
        this.consoleReader = consoleReader;
        this.router = router;
    }

    public String getRoute() {
        return route;
    }

    public abstract void render() throws Exception;

}
