package com.revature.scottbank.models;

public class Account extends Object {

    private String id;
    private double balance;
    private String holderId;

    public Account() {
        this.id = null;
        this.balance = 0.00d;
        this.holderId = null;
    }

//    public Account() { super(); }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getHolderId() { return holderId; }
    public void setHolderId(String id) { this.holderId = id; }

}
