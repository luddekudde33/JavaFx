package com.example.demo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbUtil {


    private static final String URL =
            "jdbc:mariadb://127.0.0.1:3306/librarydb";

    private static final String USER     = "ludde";          // byt!
    private static final String PASSWORD = "hej";  // byt!

    private DbUtil() {}                // utility‑klass: ingen instans

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Kunde inte hitta MariaDB-drivrutinen", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

