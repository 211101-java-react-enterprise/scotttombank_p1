package com.revature.scottbank.models;

import com.revature.scottbank.orm.annotations.Column;
import com.revature.scottbank.orm.annotations.Table;

@Table(name = "app_users")
public class AppUser {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Double balance;

    public AppUser(String firstName, String lastName, String email,
                   String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
//        this.balance = 0.00d;
    }

    public AppUser() { super(); }

    @Column(name = "user_id")
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @Column(name = "first_name")
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    @Column(name = "last_name")
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    @Column(name = "email")
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Column(name = "password")
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Column(name = "balance")
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "AppUser{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }

/*
    Shows only the last 4 digits of acct # with 2 leading asterisks
    public String getFormattedId() {
        int idx = id.length() - 4;
        return "**" + id.substring(idx);
    }
*/

}
