package com.revature.scottbank.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Logger {

    private static Logger logger;
    private final boolean printToConsole;

    private Logger(boolean printToConsole) {
        this.printToConsole = printToConsole;
    }

    public static Logger getLogger(boolean printToConsole) {
        if (logger == null) { logger = new Logger(printToConsole); }
        return logger;
    }

    public void info(String msg) {}
    public void warn(String msg) {}
    public void error(String msg) {}
    public void fatal(String msg) {}

    public void log(String msg, Object... args) {
        try (Writer logWriter = new FileWriter(
                "src/main/resources/logs/app.log", true)) {
            String formattedMsg = String.format(msg, args);
            logWriter.write(formattedMsg + "\n");
            if (printToConsole) {
                System.out.println("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
