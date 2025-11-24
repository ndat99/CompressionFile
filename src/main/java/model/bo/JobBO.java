package model.bo;
import java.util.ArrayList;
import model.bean.CompressionJob;
import model.dao.JobDAO;

public class JobBO {
    JobDAO jobDAO = new JobDAO();

    public void insertJob(int userId, String fileName, String filePath) {
        jobDAO.insertJob(userId, fileName, filePath);
    }

    public ArrayList<CompressionJob> getJobsByUserId(int userId) {
        return jobDAO.getJobsByUserId(userId);
    }
    
    public String getCompressedPath(int jobId) {
        return jobDAO.getCompressedPath(jobId);
    }
}