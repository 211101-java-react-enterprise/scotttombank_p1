package com.revature.scottbank.screens;

import com.revature.scottbank.util.ScreenRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public abstract class Screen {

    protected String route;
    protected BufferedReader consoleReader;
    protected ScreenRouter router;
    protected Logger logger = LogManager.getLogger();

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
