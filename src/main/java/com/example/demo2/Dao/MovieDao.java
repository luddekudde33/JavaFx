package com.example.demo2.Dao;

import com.example.demo2.Model.Movie;
import com.example.demo2.Sql.DbUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDao {
    public List<Movie> getAllMovies() {
        String sql = "SELECT * FROM Movie";
        List<Movie> list = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Movie(
                        rs.getInt("movieID"),
                        rs.getString("title"),
                        rs.getString("mainCharacter"),
                        rs.getString("barcode"),
                        rs.getString("physicalLocation")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addMovie(Movie movie) {
        String sql = "INSERT INTO Movie(title, mainCharacter, barcode, physicalLocation) VALUES(?,?,?,?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getMainCharacter());
            ps.setString(3, movie.getBarcode());
            ps.setString(4, movie.getPhysicalLocation());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMovie(Movie movie) {
        String sql = "UPDATE Movie SET title=?, mainCharacter=?, barcode=?, physicalLocation=? WHERE movieID=?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getMainCharacter());
            ps.setString(3, movie.getBarcode());
            ps.setString(4, movie.getPhysicalLocation());
            ps.setInt(5, movie.getMovieId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMovie(int movieId) {
        String sql = "DELETE FROM Movie WHERE movieID=?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, movieId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}