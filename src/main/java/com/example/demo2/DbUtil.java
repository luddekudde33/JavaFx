package com.example.demo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbUtil {


    private static final String URL =
            "jdbc:mariadb://127.0.0.1:3306/librarydb";

    private static final String USER     = "127.0.0.1";  //Ändra om de inte funkar
    private static final String PASSWORD = "";  // Ändra om de inte funkar

    private DbUtil() {}

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");  // är på familjedatorn, där av maria (mor) db //ludde
        } catch (ClassNotFoundException e) {
            throw new SQLException("Kunde inte hitta DB", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

