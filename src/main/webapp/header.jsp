<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.bean.User"%>
<%
    // Kiểm tra session ở đây luôn cho tiện
    User headerUser = (User) session.getAttribute("user");
    if (headerUser == null) {
        // Nếu chưa đăng nhập mà cố truy cập trang con -> đá về login
        response.sendRedirect("login.jsp");
        return; 
    }
%>

<div class="navbar">
    <a href="FileControllerServlet" class="navbar-title">⫘ ZipMaster ⫘</a>
    
    <div class="navbar-right">
        <span>Xin chào, <b><%= headerUser.getFullName() %></b></span>
        <!-- Nút đăng xuất -->
        <a href="login.jsp" class="btn-logout">Đăng xuất ➜]</a>
    </div>
</div>

