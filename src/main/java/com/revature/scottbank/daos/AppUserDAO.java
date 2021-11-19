package com.revature.scottbank.daos;

import com.revature.scottbank.models.AppUser;
import com.revature.scottbank.util.ConnectionFactory;
import com.revature.scottbank.util.collections.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AppUserDAO implements CrudDAO<AppUser> {

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

    public AppUser findByEmailAndPassword(String email, String password) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from app_users where email = ? and " +
                    "password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
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

    @Override
    public AppUser save(AppUser newUser) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            newUser.setId(UUID.randomUUID().toString());
            String sql = "insert into app_users (user_id, first_name, " +
                    "last_name, email, password) values (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newUser.getId());
            pstmt.setString(2, newUser.getFirstName());
            pstmt.setString(3, newUser.getLastName());
            pstmt.setString(4, newUser.getEmail());
            pstmt.setString(5, newUser.getPassword());
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 0) {
                return newUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<AppUser> findAll() { return null; }

    @Override
    public AppUser findById(String id) { return null; }

    @Override
    public boolean update(AppUser updatedObj) { return false; }

    @Override
    public boolean removeById(String id) { return false; }

}
