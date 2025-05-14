package com.example.demo2;

import com.example.demo2.Item;
import com.example.demo2.DbUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {
    public List<Item> getAllItems() {
        String sql = "SELECT * FROM Item";
        List<Item> list = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Item(
                        rs.getInt("itemID"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getString("authorOrArtist"),
                        rs.getString("publisher"),
                        rs.getString("barcode"),
                        rs.getString("classification"),
                        rs.getString("physicalLocation")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addItem(Item item) {
        String sql = "INSERT INTO Item(title, category, authorOrArtist, publisher, barcode, classification, physicalLocation) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getTitle());
            ps.setString(2, item.getCategory());
            ps.setString(3, item.getAuthorOrArtist());
            ps.setString(4, item.getPublisher());
            ps.setString(5, item.getBarcode());
            ps.setString(6, item.getClassification());
            ps.setString(7, item.getPhysicalLocation());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateItem(Item item) {
        String sql = "UPDATE Item SET title=?, category=?, authorOrArtist=?, publisher=?, barcode=?, classification=?, physicalLocation=? WHERE itemID=?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getTitle());
            ps.setString(2, item.getCategory());
            ps.setString(3, item.getAuthorOrArtist());
            ps.setString(4, item.getPublisher());
            ps.setString(5, item.getBarcode());
            ps.setString(6, item.getClassification());
            ps.setString(7, item.getPhysicalLocation());
            ps.setInt(8, item.getItemId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteItem(int itemId) {
        String sql = "DELETE FROM Item WHERE itemID=?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}