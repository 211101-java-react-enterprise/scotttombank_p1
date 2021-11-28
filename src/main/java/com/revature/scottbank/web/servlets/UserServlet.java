package com.revature.scottbank.web.servlets;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.scottbank.models.AppUser;
import com.revature.scottbank.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper mapper;

    public UserServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");
/*
        BufferedReader reqReader =
                new BufferedReader(new InputStreamReader(req.getInputStream()));
        String line;
        while ((line = reqReader.readLine()) != null) {
            System.out.println(line);
        }
*/
        try {
            AppUser newUser = mapper.readValue(req.getInputStream(), AppUser.class);
            boolean wasRegistered = userService.registerNewUser(newUser);
            if (wasRegistered) {
                System.out.println("User successfully registered");
                resp.setStatus(201);
            } else {
                System.out.println("Could not persist user. Check logs");
                resp.setStatus(500);
            }
        } catch (JsonParseException e) {
            resp.setStatus(400);
        }


    }
}
