package com.revature.scottbank.daos;

import com.revature.scottbank.models.Account;
import com.revature.scottbank.models.AppUser;
import com.revature.scottbank.util.ConnectionFactory;
import com.revature.scottbank.util.collections.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AcctDAO implements CrudDAO<Account> {

    public Account save(Account newAcct, AppUser user) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            newAcct.setId(UUID.randomUUID().toString());
            newAcct.setHolderId(user.getId());
            String sql = "insert into accounts (acct_id, balance, holder_id) " +
                    "values (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newAcct.getId());
            pstmt.setDouble(2, newAcct.getBalance());
            pstmt.setString(3, newAcct.getHolderId());
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 0) {
                return newAcct;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account findByHolderId(String holderId) {
        Account acct = new Account();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from accounts where holder_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, holderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                acct.setId(rs.getString("acct_id"));
                acct.setBalance(rs.getDouble("balance"));
                acct.setHolderId(rs.getString("holder_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acct;
    }

    @Override
    public Account save(Account newAcct) { return null; }

    @Override
    public List<Account> findAll() { return null; }

    @Override
    public Account findById(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from accounts where acct_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Account acct = new Account();
                AppUser holder = new AppUser();
                holder.setId(rs.getString("user_id"));
                holder.setFirstName(rs.getString("first_name"));
                holder.setLastName(rs.getString("last_name"));
                holder.setEmail(rs.getString("email"));

                acct.setId(rs.getString("acct_id"));
                acct.setBalance(rs.getDouble("balance"));
                acct.setHolderId(holder.getId());
                return acct;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Account updatedObj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "update accounts set balance = ? where acct_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, updatedObj.getBalance());
            pstmt.setString(2, updatedObj.getId());
            if (pstmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeById(String id) { return false; }

}
