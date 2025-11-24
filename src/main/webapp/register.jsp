<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký tài khoản</title>
    <link rel="stylesheet" type="text/css" href="index.css">
</head>
<body>
    <div class="container-box">
        <h2>ĐĂNG KÝ TÀI KHOẢN</h2>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-msg"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="RegisterServlet" method="post">
            <label for="username">Tên đăng nhập:</label>
            <input type="text" id="username" name="username"
                   placeholder="Tên đăng nhập" required>

            <label for="password">Mật khẩu:</label>
            <input type="password" id="password" name="password"
                   placeholder="Mật khẩu" required>

            <label for="confirmPassword">Nhập lại mật khẩu:</label>
            <input type="password" id="confirmPassword" name="confirmPassword"
                   placeholder="Nhập lại mật khẩu" required>

            <label for="fullname">Họ và tên đầy đủ:</label>
            <input type="text" id="fullname" name="fullname"
                   placeholder="Họ và tên đầy đủ" required>

            <br>
            <input type="submit" value="Đăng ký ngay">
        </form>

        <br>
        <a href="login.jsp">Đã có tài khoản? Đăng nhập</a>
    </div>
</body>
</html>