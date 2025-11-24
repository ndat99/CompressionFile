<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload File</title>
    <link rel="stylesheet" type="text/css" href="index.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container-box">
        <h3>Tải lên file hoặc kéo thả</h3>
        <% if(request.getAttribute("error") != null) { %>
            <div class="error-msg"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="FileControllerServlet" method="POST" enctype="multipart/form-data">
            <input type="file" name="file" required multiple>
            <br>
            <input type="submit" name="upload" value="Nén tất cả 🗜">
        </form>
        
        <br><br>
        <a href="FileControllerServlet" class="btn btn-secondary">Xem Lịch sử nén >></a>
    </div>
</body>
</html>