package com.example.demo2.Dao;

import com.example.demo2.Model.Loan;
import com.example.demo2.Sql.DbUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDao {
    public boolean addLoan(Loan loan) {
        String sql = "INSERT INTO Loan(loanDate, dueDate, status, userID, bookID, movieID) VALUES(?,?,?,?,?,?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(loan.getLoanDate()));
            ps.setDate(2, Date.valueOf(loan.getDueDate()));
            ps.setInt(3, loan.getStatus());
            ps.setInt(4, loan.getUserId());
            if (loan.getBookId() != null) ps.setInt(5, loan.getBookId()); else ps.setNull(5, Types.INTEGER);
            if (loan.getMovieId() != null) ps.setInt(6, loan.getMovieId()); else ps.setNull(6, Types.INTEGER);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean returnLoan(int loanId) {
        String sql = "UPDATE Loan SET status = ? WHERE loanID = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 2);
            ps.setInt(2, loanId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Loan> getOverdueLoans() {
        String sql = "SELECT loanID, userID, bookID, movieID, status, loanDate, dueDate FROM Loan WHERE dueDate < CURDATE() AND status = 1";
        List<Loan> list = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Loan(
                        rs.getInt("loanID"),
                        rs.getInt("userID"),
                        rs.getObject("bookID") != null ? rs.getInt("bookID") : null,
                        rs.getObject("movieID") != null ? rs.getInt("movieID") : null,
                        rs.getInt("status"),
                        rs.getDate("loanDate").toLocalDate(),
                        rs.getDate("dueDate").toLocalDate()
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Loan> getLoansByUser(int userId) {
        String sql = "SELECT loanID, userID, bookID, movieID, status, loanDate, dueDate FROM Loan WHERE userID = ? AND status = 1";
        List<Loan> list = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Loan(
                            rs.getInt("loanID"),
                            rs.getInt("userID"),
                            rs.getObject("bookID") != null ? rs.getInt("bookID") : null,
                            rs.getObject("movieID") != null ? rs.getInt("movieID") : null,
                            rs.getInt("status"),
                            rs.getDate("loanDate").toLocalDate(),
                            rs.getDate("dueDate").toLocalDate()
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
