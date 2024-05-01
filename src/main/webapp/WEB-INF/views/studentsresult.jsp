<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Report</title>
    <!-- Include Bootstrap CSS -->
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <!-- Include DataTables CSS -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">
    <!-- Include Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
	
	<style>
    .logout:hover{
        background-color:red;
        cursor:pointer;
    }
    .form-control {
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        transition: box-shadow 0.3s ease;
    }
    .form-control:focus {
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.3), 0 0 10px rgba(70, 130, 180, 0.6);
    }
	</style>
</head>
<body>

	<nav>
        <div class="dadhoboard">
            <div>
                <h3>Admin Dashboard</h3>
            </div>
            <div>
                <a class="logout btn btn-light" style="cursor:pointer; float: right; margin-top: 10px;  padding: 8px 15px; border-radius: 5px; " onclick="showLogoutConfirmation()">Logout</a>
            </div>
        </div>
    </nav>
    
 <div class="sidebar">
	<div class="first">
            <ul>
                <li><a href="/StudentRegistration/admin/">Dashboard</a></li>
                <li><a href="/StudentRegistration/users/">User Management</a></li>
            	<li><a href="/StudentRegistration/courses/">Course Management</a></li>
            	<li><a href="/StudentRegistration/student/">View Students</a></li>
                <li><a href="/ConvenienceManagementSystem/details">Manage Report</a></li>
            </ul>
        </div>
    <div class="second">
	<div class="container mt-4">
    	<h3 class="mb-3 shadow bg-white p-3 rounded">Details Reports</h3>
    	<table id="salesTable" class="table table-striped">
        	<thead>
        		<tr>
            		<th>Student Id</th>
            		<th>Student Image</th>
            		<th>Join Date</th>
            		<th>Student Name</th>
            		<th>Date Of Birth</th>
            		<th>Gender</th>
            		<th>Phone</th>
            		<th>Education</th>
            		<th>Attend</th>
        		</tr>
        	</thead>
        	<tbody>
        		<c:set var="totalAmountSum" value="0" />
        		<c:forEach items="${studentsList}" var="list">
            		<tr>
                		<td>${list.student_code}</td>
                		<td><img alt="ProfileImage" src="data:image/jpeg;base64,${list.photo}" style="width:100px;height:60px;" /></td>
						<td>${list.name}</td>
						<td>${list.date}</td>
						<td>${list.dob}</td>
						<td>${list.gender}</td>
						<td>${list.phone}</td>
						<td>${list.education}</td>
						<td>
    						<c:forEach var="course" items="${list.course_names}" varStatus="loop">
        						${course}<c:if test="${not loop.last}">,</c:if>
    						</c:forEach>
						</td>
						
            		</tr>
        		</c:forEach>
        	</tbody>
    	</table>
	</div>
	</div>
</div>

<!-- Sale Details Modal -->
<div class="modal fade" id="saleDetailsModal" tabindex="-1" role="dialog" aria-labelledby="saleDetailsModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="saleDetailsModalLabel">Sale Details</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="saleDetailsContent">
                <!-- Sale details content will be loaded here dynamically -->
            </div>
        </div>
    </div>
</div>

<div class="modal" tabindex="-1" role="dialog" id="logoutModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Logout Confirmation</h5>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to logout?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="cancelDelete()">Cancel</button>
                <a href="#" class="btn btn-primary" onclick="logout()">Logout</a>
            </div>
        </div>
    </div>
</div>

<!-- jQuery, Bootstrap JS, DataTables JS -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>

<script>
    $(document).ready(function () {
        $('#salesTable').DataTable();
    });

    function showLogoutConfirmation() {
        $('#logoutModal').modal('show');
    }
    
    function logout() {
        window.location.href = "/StudentRegistration/logout/";
    }

    function cancelDelete() {
        $('#logoutModal').modal('hide');
    }

</script>

</body>
</html>
