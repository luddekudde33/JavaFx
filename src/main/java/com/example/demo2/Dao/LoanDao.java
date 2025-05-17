package com.example.demo2.Dao;

import com.example.demo2.Model.Loan;
import com.example.demo2.Sql.DbUtil;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDao {
    public boolean addLoan(Loan loan) {
        String sql = "INSERT INTO Loan(loanDate, dueDate, status, userID, copyID) VALUES(?,?,?,?,?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(loan.getLoanDate()));
            ps.setDate(2, Date.valueOf(loan.getDueDate()));
            ps.setInt(3, loan.getStatus());
            ps.setInt(4, loan.getUserId());
            ps.setInt(5, loan.getCopyId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean returnLoan(int loanId, LocalDate returnDate) {
        String sql = "UPDATE Loan SET returnDate=?, status=? WHERE loanId=?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(returnDate));
            ps.setInt(2, 2);
            ps.setInt(3, loanId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Loan> getOverdueLoans() {
        String sql = "SELECT loanId, userID, copyID, status, loanDate, dueDate, returnDate FROM Loan WHERE dueDate < CURDATE() AND status = 1";
        List<Loan> out = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                out.add(new Loan(
                        rs.getInt("loanId"),
                        rs.getInt("userID"),
                        rs.getInt("copyID"),
                        rs.getInt("status"),
                        rs.getDate("loanDate").toLocalDate(),
                        rs.getDate("dueDate").toLocalDate(),
                        rs.getDate("returnDate") != null ? rs.getDate("returnDate").toLocalDate() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

    /**
     * Hämta alla lån för en specifik användare
     */
    public List<Loan> getLoansByUser(int userId) {
        // Hämta endast aktiva lån (status = 1)
        String sql = "SELECT loanId, userID, copyID, status, loanDate, dueDate, returnDate " +
                "FROM Loan WHERE userID = ? AND status = 1";
        List<Loan> out = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new Loan(
                            rs.getInt("loanId"),
                            rs.getInt("userID"),
                            rs.getInt("copyID"),
                            rs.getInt("status"),
                            rs.getDate("loanDate").toLocalDate(),
                            rs.getDate("dueDate").toLocalDate(),
                            // returnDate är null för aktiva lån
                            null
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }
}
