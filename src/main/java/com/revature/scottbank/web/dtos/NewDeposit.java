package com.revature.scottbank.web.dtos;

public class NewDeposit {

    private String deposit;

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    @Override
    public String toString() {
        return "NewDeposit{" +
                "deposit='" + deposit + '\'' +
                '}';
    }

}
