package com.revature.scottbank.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.scottbank.exceptions.AuthenticationException;
import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.models.AppUser;
import com.revature.scottbank.services.UserService;
import com.revature.scottbank.web.dtos.Credentials;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper mapper;

    public AuthServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    // login
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Credentials creds = mapper.readValue(req.getInputStream(),
                    Credentials.class);
            AppUser authUser = userService.authUser(creds.getEmail(),
                    creds.getPassword());
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("authUser", authUser.toString());
            resp.setStatus(204);
        } catch (InvalidRequestException | UnrecognizedPropertyException e) {
            resp.setStatus(400);
        } catch (AuthenticationException e) {
            resp.setStatus(401);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }

    }

    // logout
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
    }

}
