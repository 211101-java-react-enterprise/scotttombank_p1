package com.revature.scottbank.web.dtos;

public class DeleteUser {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DeleteUserServlet{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
