package com.revature.scottbank.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.scottbank.exceptions.AuthenticationException;
import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.exceptions.ResourcePersistenceException;
import com.revature.scottbank.models.AppUser;
import com.revature.scottbank.services.UserService;
import com.revature.scottbank.web.dtos.ErrorResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

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
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        try {
            AppUser newUser = mapper.readValue(req.getInputStream(), AppUser.class);
            boolean wasRegistered = userService.registerNewUser(newUser);
            if (wasRegistered) {
                System.out.println("User successfully registered");
                resp.setStatus(201);
                String payload = mapper.writeValueAsString(newUser);
                resp.getWriter().write(payload);
            }
        } catch (InvalidRequestException | UnrecognizedPropertyException | ResourcePersistenceException e) {
            resp.setStatus(400);
            ErrorResponse errorResp = new ErrorResponse(400, e);
            String payload = mapper.writeValueAsString(errorResp);
            respWriter.write(payload);
        } catch (AuthenticationException e) {
            resp.setStatus(401);
            ErrorResponse errorResp = new ErrorResponse(401, e);
            String payload = mapper.writeValueAsString(errorResp);
            respWriter.write(payload);
        } catch (Throwable t) {
            resp.setStatus(500);
            System.out.println(Arrays.toString(t.getStackTrace()));
            ErrorResponse errorResp = new ErrorResponse(500, "An unexpected exception occurred. Please check the server logs", t);
            String payload = mapper.writeValueAsString(errorResp);
            respWriter.write(payload);
        }
    }
}
