package com.example.demo2.Dao;

import com.example.demo2.Model.Book;
import com.example.demo2.Sql.DbUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM Book";
        List<Book> list = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("bookID"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getString("barcode"),
                        rs.getString("classification"),
                        rs.getString("physicalLocation"),
                        rs.getBoolean("isAvailable")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addBook(Book book) {
        String sql = "INSERT INTO Book(title, category, author, publisher, barcode, classification, physicalLocation, isAvailable) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getCategory());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getPublisher());
            ps.setString(5, book.getBarcode());
            ps.setString(6, book.getClassification());
            ps.setString(7, book.getPhysicalLocation());
            ps.setBoolean(8, book.isAvailable());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(Book book) {
        String sql = "UPDATE Book SET title=?, category=?, author=?, publisher=?, barcode=?, classification=?, physicalLocation=?, isAvailable=? WHERE bookID=?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getCategory());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getPublisher());
            ps.setString(5, book.getBarcode());
            ps.setString(6, book.getClassification());
            ps.setString(7, book.getPhysicalLocation());
            ps.setBoolean(8, book.isAvailable());
            ps.setInt(9, book.getBookId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(int bookId) {
        String sql = "DELETE FROM Book WHERE bookID=?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
