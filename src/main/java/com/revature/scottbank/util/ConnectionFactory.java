package com.revature.scottbank.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final ConnectionFactory connectionFactory =
            new ConnectionFactory();
    private Properties props = new Properties();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ConnectionFactory() {
        try {
            props.load(new FileReader("src/main/resources/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getInstance() { return connectionFactory; }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(props.getProperty("url"),
                    props.getProperty("username"), props.getProperty(
                            "password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
