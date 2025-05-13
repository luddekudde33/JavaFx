package com.example.demo2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffDao {
    /**
     * Returnerar true om e-post och lösenord matchar en personal i databasen.
     */
    public boolean authenticate(String email, String password) {
        String sql = """
            SELECT 1
              FROM Staff
             WHERE staffEmail = ? AND staffPassword = ?
             LIMIT 1
        """;
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                // Om vi får minst en rad finns användaren
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
