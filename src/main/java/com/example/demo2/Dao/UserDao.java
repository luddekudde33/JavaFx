package com.example.demo2.Dao;

import com.example.demo2.Model.User;
import com.example.demo2.Sql.DbUtil;
import java.sql.*;

public class UserDao {
    /**
     * Returnerar User-objekt om e‑post + lösenord matchar, annars null.
     */
    public User authenticate(String email, String password) {
        String sql = "SELECT userID, email, password, fullName FROM `User` WHERE email = ? AND password = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("userID"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("fullName")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sparar ny användare i tabellen `User`.
     * @return true om insättning lyckades
     */
    public boolean register(String name, String email, String password) {
        String sql = "INSERT INTO `User` (fullName, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}