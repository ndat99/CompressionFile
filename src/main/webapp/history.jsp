<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.bean.CompressionJob"%>
<%@ page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lịch sử nén file</title>
    <meta http-equiv="refresh" content="2"> <!--tự động làm mới trang sau mỗi 3 giây-->
    
    <link rel="stylesheet" type="text/css" href="index.css">
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container-wide">
        <div class="header-info">
		    <h3>Lịch sử nén file ⟲</h3>
		    <a href="upload.jsp" class="btn">+ Upload File Khác</a>
		</div>
        
        <table class="data-table">
            <thead>
                <tr>
                    <th width="40%">Tên File / Thư mục</th>
                    <th width="20%">Thời gian tạo</th>
                    <th width="15%">Trạng thái</th>
                    <th width="25%">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<CompressionJob> jobs = (ArrayList<CompressionJob>) request.getAttribute("jobs");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    
                    if (jobs == null || jobs.size() == 0) {
                %>
                    <tr>
                        <td colspan="4" style="text-align:center; color:#7f8c8d; padding: 30px;">
                            Chưa có dữ liệu. Hãy upload file đầu tiên!
                        </td>
                    </tr>
                <%
                    } else {
                        for (CompressionJob job : jobs) {
                %>
                    <tr>
                        <td><%= job.getOriginalFilename() %></td>
                        <td>
                            <%= (job.getCreatedAt() != null) ? sdf.format(job.getCreatedAt()) : "N/A" %>
                        </td>
                        
                        <td>
                            <% if ("PENDING".equals(job.getStatus())) { %>
                                <span class="status status-pending">Đang chờ...</span>
                            <% } else if ("PROCESSING".equals(job.getStatus())) { %>
                                <span class="status status-processing">Đang xử lý...</span>
                            <% } else { %>
                                <span class="status status-completed">Hoàn thành</span>
                            <% } %>
                        </td>
                        <td>
                            <% if ("COMPLETED".equals(job.getStatus())) { %>
                                <a href="FileControllerServlet?download=1&id=<%= job.getId() %>" class="btn" style="padding: 5px 10px; font-size: 12px;">
                                    Tải xuống ZIP 🡻
                                </a>
                            <% } else { %>
                                <span class="loading">Vui lòng đợi...</span>
                            <% } %>
                        </td>
                    </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>