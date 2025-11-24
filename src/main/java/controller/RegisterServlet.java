package controller;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.bo.UserBO;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
    	String u = request.getParameter("username");
        String p = request.getParameter("password");
        String cp = request.getParameter("confirmPassword");
        String f = request.getParameter("fullname");
        
        if (!p.equals(cp)) {
            request.setAttribute("error", "Mật khẩu nhập lại không khớp!");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        UserBO userBO = new UserBO();
        
        if (userBO.checkUserExist(u)) {
            request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            if (userBO.registerUser(u, p, f)) {
                String msg = "Đăng ký thành công! Bạn đã có thể đăng nhập.";
                String encodedMsg = URLEncoder.encode(msg, "UTF-8");
                response.sendRedirect("login.jsp?error=" + encodedMsg);
            } else {
                request.setAttribute("error", "Lỗi hệ thống, vui lòng thử lại sau.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        }
    }
}