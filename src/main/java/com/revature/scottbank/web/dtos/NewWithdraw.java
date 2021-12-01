package com.revature.scottbank.web.dtos;

public class NewWithdraw {

    private String withdraw;

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    @Override
    public String toString() {
        return "NewWithdraw{" +
                "withdraw='" + withdraw + '\'' +
                '}';
    }
}
