package com.revature.scottbank.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.scottbank.exceptions.AuthenticationException;
import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.models.AppUser;
import com.revature.scottbank.services.UserService;
import com.revature.scottbank.web.dtos.ErrorResponse;
import com.revature.scottbank.web.dtos.NewWithdraw;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class WithdrawServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper mapper;

    public WithdrawServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        try {
            NewWithdraw withdrawAmount = mapper.readValue(req.getInputStream(),
                    NewWithdraw.class);
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
