package model.dao;
import java.sql.*;
import java.util.ArrayList;
import model.bean.CompressionJob;

public class JobDAO {
    public void insertJob(int userId, String fileName, String filePath) {
        String sql = "INSERT INTO compression_jobs (user_id, original_filename, file_path, status) VALUES (?, ?, ?, 'PENDING')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, fileName);
            stmt.setString(3, filePath);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ArrayList<CompressionJob> getJobsByUserId(int userId) {
        ArrayList<CompressionJob> list = new ArrayList<>();
        String sql = "SELECT * FROM compression_jobs WHERE user_id = ? ORDER BY id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CompressionJob job = new CompressionJob();
                    job.setId(rs.getInt("id"));
                    job.setOriginalFilename(rs.getString("original_filename"));
                    job.setStatus(rs.getString("status"));
                    job.setCreatedAt(rs.getTimestamp("created_at")); 
                    list.add(job);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public CompressionJob getPendingJob() {
        try (Connection conn = DatabaseConnection.getConnection();
        	//lấy ra 1 việc chưa làm (đang pending)
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM compression_jobs WHERE status = 'PENDING' LIMIT 1")) {
             ResultSet rs = stmt.executeQuery();
             if (rs.next()) {
                 CompressionJob job = new CompressionJob();
                 job.setId(rs.getInt("id"));
                 job.setUserId(rs.getInt("user_id"));
                 return job;
             }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public String getFilePath(int jobId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT file_path FROM compression_jobs WHERE id=?")) {
             stmt.setInt(1, jobId);
             ResultSet rs = stmt.executeQuery();
             if(rs.next()) return rs.getString("file_path");
        } catch(Exception e) {}
        return null;
    }
    
    public String getCompressedPath(int jobId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT compressed_path FROM compression_jobs WHERE id=?")) {
             stmt.setInt(1, jobId);
             ResultSet rs = stmt.executeQuery();
             if(rs.next()) return rs.getString("compressed_path");
        } catch(Exception e) {}
        return null;
    }

    //update status của job và đường dẫn file zip kết quả
    public void updateJobStatus(int id, String status, String compressedPath) {
        String sql = "UPDATE compression_jobs SET status = ?, compressed_path = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, compressedPath);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}