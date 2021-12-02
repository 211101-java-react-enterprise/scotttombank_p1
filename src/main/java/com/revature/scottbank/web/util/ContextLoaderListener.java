package com.revature.scottbank.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.scottbank.daos.AppUserDAO;
import com.revature.scottbank.services.UserService;
import com.revature.scottbank.web.servlets.AuthServlet;
import com.revature.scottbank.web.servlets.DepositServlet;
import com.revature.scottbank.web.servlets.UserServlet;
import com.revature.scottbank.web.servlets.WithdrawServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("\nInitializing app...");
//        logger.info("Initializing app...");

        ObjectMapper mapper = new ObjectMapper();

        AppUserDAO userDAO = new AppUserDAO();
        UserService userService = new UserService(userDAO);

        UserServlet userServlet = new UserServlet(userService, mapper);
        AuthServlet authServlet = new AuthServlet(userService, mapper);
        WithdrawServlet withdrawServlet = new WithdrawServlet(userService, mapper);
        DepositServlet depositServlet = new DepositServlet(userService, mapper);

        ServletContext context = sce.getServletContext();
        context.addServlet("UserServlet", userServlet).addMapping("/users");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
        context.addServlet("WithdrawServlet", withdrawServlet).addMapping(
                "/withdraw");
        context.addServlet("DepositServlet", depositServlet).addMapping(
                "/deposit");

        System.out.println("App initialized");
//        logger.info("App initialized");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Shutting down ScottTomBank web app!");
    }
}
