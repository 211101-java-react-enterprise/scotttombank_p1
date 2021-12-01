package com.revature.scottbank.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.scottbank.exceptions.AuthenticationException;
import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.models.AppUser;
import com.revature.scottbank.services.UserService;
import com.revature.scottbank.web.dtos.NewWithdraw;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class WithdrawServlet  extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper mapper;


    public WithdrawServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    //withdraw
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            NewWithdraw withdrawAmount = mapper.readValue(req.getInputStream(),
                    NewWithdraw.class);
            try {
                HttpSession currentSession = req.getSession(false);
                if (currentSession == null) {
                    throw new AuthenticationException();
                }

                AppUser authUserAttribute = (AppUser) currentSession.getAttribute("authUser");

                if (authUserAttribute == null) {
                    throw new InvalidRequestException("Unexpected type in session attribute");
                }
                userService.makeWithdrawal(authUserAttribute, withdrawAmount.getWithdraw());
                resp.setStatus(201);
                String payload = mapper.writeValueAsString(authUserAttribute);
                resp.getWriter().write(payload);
            } catch (InvalidRequestException | UnrecognizedPropertyException e) {
                resp.setStatus(400);
            } catch (AuthenticationException e) {
                resp.setStatus(401);
            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(500);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
