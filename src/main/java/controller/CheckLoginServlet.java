package controller;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.bean.User;
import model.bo.UserBO;

@WebServlet("/CheckLoginServlet")
public class CheckLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String u = request.getParameter("username");
        String p = request.getParameter("password");
        
        UserBO userBO = new UserBO();
        User user = userBO.checkLogin(u, p);
        
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);             
            response.sendRedirect(request.getContextPath() + "/upload.jsp");
        } else {
            request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}