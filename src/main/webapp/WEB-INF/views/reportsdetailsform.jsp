<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reports Details</title>
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
	
 	<script>
        function showLogoutConfirmation() {
            $('#logoutModal').modal('show');
        }

        function logout() {
            window.location.href = "/StudentRegistration/logout/";
        }

        function cancelDelete() {
            $('#logoutModal').modal('hide');
        }
        
        $(document).ready(function() {
            $("form").submit(function(event) {
                var fromDate = new Date($("#fromDate").val());
                var toDate = new Date($("#toDate").val());

                if (toDate < fromDate) {
                    toastr.error("To Date cannot be before From Date");
                    event.preventDefault();
                }
            });
        });
    </script>
    
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
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
            <form class="bg-white mt-5 p-4 rounded shadow" action="/StudentRegistration/detail" method="POST">
            	<h2>Search Report Form</h2>
                <div class="form-group">
                    <label for="userId">User ID</label>
                    <input type="text" id="userId" class="form-control" name="userId" required="required" placeholder="Enter user id">
                </div>
                
                <div class="form-group">
        			<label for="fromDate">From Date</label>
        			<input type="date" id="fromDate" class="form-control" name="fromDate" required="required" placeholder="Enter from date">
    			</div>
    
    			<div class="form-group">
        			<label for="toDate">To Date</label>
        			<input type="date" id="toDate" class="form-control" name="toDate" required="required" placeholder="Enter to date">
    			</div>
    			
                <div class="mt-2 d-flex justify-content-end">
                	<button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
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

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>
