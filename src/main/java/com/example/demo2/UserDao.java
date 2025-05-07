package com.example.demo2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    /** Returnerar true om e‑post + lösenord matchar en rad i tabellen `User`. */
    public boolean authenticate(String email, String password) {
        String sql = """
            SELECT 1 FROM `User`
            WHERE email = ? AND password = ?
            LIMIT 1
            """;

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();         // fanns rad ⇒ inloggning ok
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;                 // databasfel ⇒ neka inlogg
        }
    }

    /**
     * Sparar ny användare i tabellen `User`.
     * @return true om insättning lyckades
     */
    public boolean register(String name, String email, String password) {
        String sql = """
        INSERT INTO `User` (fullName, email, password)
        VALUES (?, ?, ?)
        """;

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
