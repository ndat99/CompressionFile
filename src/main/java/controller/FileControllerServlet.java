package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import model.bean.User;
import model.bean.CompressionJob;
import model.bo.JobBO;

@WebServlet("/FileControllerServlet")
//cấu hình cho phép upload file
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 10,
    maxFileSize = 1024 * 1024 * 200,
    maxRequestSize = 1024 * 1024 * 500
)
public class FileControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //download file và hiện lịch sử
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currUser = (User) session.getAttribute("user");
        
        if (currUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        JobBO jobBO = new JobBO();
        
        if (request.getParameter("download") != null && request.getParameter("id") != null) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String filePath = jobBO.getCompressedPath(id); //lấy đường dẫn file zip từ database
                if (filePath != null) {
                    File downloadFile = new File(filePath);
                    if (downloadFile.exists()) {
                        response.setContentType("application/zip"); //báo trình duyệt biết đây là file zip
                        response.setHeader("Content-Disposition", "attachment; filename=\"zipmaster_" + id + ".zip\"");
                        try (FileInputStream in = new FileInputStream(downloadFile);
                             OutputStream out = response.getOutputStream()) { //ghi dữ liệu về trình duyệt
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = in.read(buffer)) != -1) {
                                out.write(buffer, 0, bytesRead); //gửi file từ server đến client
                            }
                        }
                        return;
                    }
                }
            }
            catch (Exception e) {
            	e.printStackTrace();
            }
        }
        
        ArrayList<CompressionJob> jobs = jobBO.getJobsByUserId(currUser.getId());
        request.setAttribute("jobs", jobs);
        RequestDispatcher rd = request.getRequestDispatcher("/history.jsp");
        rd.forward(request, response);
    }

    //upload file
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currUser = (User) session.getAttribute("user");
        
        if (currUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        if (request.getParameter("upload") != null) {
            try {
                //tạo thư mục chứa các file được upload
                String folderName = "Folder_" + System.currentTimeMillis();
                //đường dẫn thư mục chứa các folder được upload (lưu trên máy server)
                String serverDir = "D:\\Uploads";
                //đường dẫn folder chứa các file vừa được upload này
                String folderPath = serverDir + "\\" + folderName;
                
                File folderDir = new File(folderPath);
                if (!folderDir.exists()) folderDir.mkdirs(); //Tạo thư mục vói đường dẫn trên
                int fileCount = 0;
                
                					//lấy các file (part) được upload
                Collection<Part> parts = request.getParts();
                for (Part part : parts) {
                    //chỉ xử lý input có name="file"
                    if (part.getName().equals("file")) {
                        String fileName = part.getSubmittedFileName();
                        if (fileName != null && !fileName.isEmpty()) {
                            // Fix tên file
                            if (fileName.contains("\\")) fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                            fileName = fileName.replaceAll("[:\\\\/*?|<>]", "_");
                            
                            // Lưu file vào trong thư mục mới tạo
                            part.write(folderPath + "\\" + fileName);
                            fileCount++;
                        }
                    }
                }
                
                if (fileCount > 0) {
                    JobBO jobBO = new JobBO();
                    //tên hiển thị "Folder_... (n files)"
                    String displayName = folderName + " (" + fileCount + " files)";
                    //lưu tên folder và đường dẫn thư mục vào database
                    jobBO.insertJob(currUser.getId(), displayName, folderPath);
                }
                response.sendRedirect("FileControllerServlet");
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("error", "Lỗi upload: " + ex.getMessage());
                RequestDispatcher rd = request.getRequestDispatcher("/upload.jsp");
                rd.forward(request, response);
            }
        }
    }
}