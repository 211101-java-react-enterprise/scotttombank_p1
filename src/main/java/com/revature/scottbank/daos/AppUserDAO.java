package com.revature.scottbank.daos;

import com.revature.scottbank.models.AppUser;
import com.revature.scottbank.orm.annotations.Table;
import com.revature.scottbank.orm.SQLMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AppUserDAO {

    SQLMapper sqlMapper = new SQLMapper();

    public AppUser save(AppUser newUser) {
        newUser.setId(UUID.randomUUID().toString());
        try {
            if (sqlMapper.create(newUser)) return newUser;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean findByEmail(String email) {
        try {
            String className = AppUser.class.getAnnotation(Table.class).name();
            String[] identifier = {"email", email};
            ResultSet rs = sqlMapper.read(className, identifier);
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public AppUser findByEmailAndPassword(String email, String password) {
        try {
            String className = AppUser.class.getAnnotation(Table.class).name();
            String[] identifier1 = {"email", email};
            String[] identifier2 = {"password", password};
            ResultSet rs = sqlMapper.read(className, identifier1, identifier2);
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

    //TODO implement update balance with SQLMapper
    public boolean updateBalance(AppUser appUser) {
        return false;
    }

    public boolean removeById(String id) { return false; }

}
