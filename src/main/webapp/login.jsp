<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng nhập</title>
    <link rel="stylesheet" type="text/css" href="index.css">
</head>
<body>
    <div class="container-box">
        <h2>Đăng nhập</h2>
        
        <% if (request.getParameter("error") != null) { %>
            <div class="error-msg">
                <%= request.getParameter("error") %>
            </div>
        <% } %>

        <form action="CheckLoginServlet" method="post">
            <label style="float:left">Tài khoản:</label>
            <input type="text" name="username" placeholder="Nhập username" required>
            
            <label style="float:left">Mật khẩu:</label>
            <input type="password" name="password" placeholder="Nhập password" required>
            
            <input type="submit" value="Đăng nhập ngay">
        </form>
        
        <br>
        <a href="register.jsp">Chưa có tài khoản? Đăng ký ngay</a>
    </div>
</body>
</html>