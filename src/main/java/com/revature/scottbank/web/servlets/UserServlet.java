package com.revature.scottbank.web.servlets;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.scottbank.models.AppUser;
import com.revature.scottbank.services.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper mapper;

    public UserServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    // register new user
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        try {
            AppUser newUser = mapper.readValue(req.getInputStream(), AppUser.class);
            boolean wasRegistered = userService.registerNewUser(newUser);
            if (wasRegistered) {
                System.out.println("User successfully registered");
                resp.setStatus(201);
                String payload = mapper.writeValueAsString(newUser);
                resp.getWriter().write(payload);
            } else {
                System.out.println("Could not persist user. Check logs");
                resp.setStatus(500);
            }
        } catch (JsonParseException e) {
            resp.setStatus(400);
        }

    }

}
