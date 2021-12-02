package com.revature.scottbank.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.scottbank.exceptions.AuthenticationException;
import com.revature.scottbank.exceptions.InvalidRequestException;
import com.revature.scottbank.models.AppUser;
import com.revature.scottbank.services.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteUserServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper mapper;

    public DeleteUserServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
//            DeleteUser deleteUser = mapper.readValue(req.getInputStream(),
//                    DeleteUser.class);
            HttpSession currentSession = req.getSession(false);

            if (currentSession == null) {
                throw new AuthenticationException();
            }

            AppUser authUserAttribute = (AppUser) currentSession.getAttribute("authUser");

            if (authUserAttribute == null) {
                throw new InvalidRequestException("Unexpected type in session attribute");
            }
            userService.deleteUser(authUserAttribute);
            req.getSession().invalidate();
            resp.setStatus(200);
            String payload = mapper.writeValueAsString("So long!");
            resp.getWriter().write(payload);
        } catch (InvalidRequestException | UnrecognizedPropertyException e) {
            resp.setStatus(400);
        } catch (AuthenticationException e) {
            resp.setStatus(401);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}
