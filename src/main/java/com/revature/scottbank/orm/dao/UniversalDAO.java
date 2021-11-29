package com.revature.scottbank.orm.dao;

import com.revature.scottbank.daos.CrudDAO;
import com.revature.scottbank.models.Account;
import com.revature.scottbank.models.AppUser;
import com.revature.scottbank.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class UniversalDAO<T> {

    //CREATE (saving)

    public boolean create(T t) {

        return false;
    }


//    public AppUser save(AppUser newUser) {
//        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
//            newUser.setId(UUID.randomUUID().toString());
//            String sql = "insert into app_users (user_id, first_name, " +
//                    "last_name, email, password) values (?, ?, ?, ?, ?)";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, newUser.getId());
//            pstmt.setString(2, newUser.getFirstName());
//            pstmt.setString(3, newUser.getLastName());
//            pstmt.setString(4, newUser.getEmail());
//            pstmt.setString(5, newUser.getPassword());
//            int rowsInserted = pstmt.executeUpdate();
//            if (rowsInserted != 0) {
//                return newUser;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    //READ  (grab info from database)

    public Class<T> read(T... t) {
        return null;
    }

    public AppUser findByEmail(String email) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from app_users where email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                AppUser user = new AppUser();
                user.setId(rs.getString("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //UPDATE

    public Class<T> update(T... t) {
        // orm.update("this field", "
        return null;
    }


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


    //DELETE  (remove from database)

    public Class<T> delete(T... t) {
        return null;
    }

}
