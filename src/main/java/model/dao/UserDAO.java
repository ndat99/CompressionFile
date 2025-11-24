package model.dao;
import java.sql.*;
import model.bean.User;

public class UserDAO {
    public User checkLogin(String username, String password) {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setFullName(rs.getString("full_name")); 
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return user;
    }
    
    public boolean checkUserExist(String username) { //kiểm tra user đã tồn tại chưa để đăng ký tk mới
        boolean exist = false;
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) exist = true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return exist;
    }
    
    public boolean insertUser(String username, String password, String fullname) {
        String sql = "INSERT INTO users (username, password, full_name) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, fullname);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}