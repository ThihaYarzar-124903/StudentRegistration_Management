<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Page</title>
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>
    
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
    		<li><a href="/StudentRegistration/users/">User Management</a></li>
            <li><a href="/StudentRegistration/courses/">Course Management</a></li>
            <li><a href="/StudentRegistration/student/">View Students</a></li>
            <li><a href="/StudentRegistration/details">Manage Report</a></li>
            <!-- Add more items as needed -->
        </ul>
    </div>
    <div class="second">
        <div class="bg-white pb-4 mt-3 m-auto" style="width: 95%;">   
            <div>   
                <nav style="width: 95%;" class="m-auto p-4 d-flex justify-content-between bg-light  shadow p-3 mb-2 rounded">   
                    <p>Welcome, ${loggedInUser.name}</p>
                	<p>Email: ${loggedInUser.email}</p>
                	<p>Current Date and Time: <span id="currentDateTime"></span></p>
                </nav>  
            </div>  
            <div class="d-flex mt-4 justify-content-around text-center">    
                <div class="flex-column rounded p-2 d-flex bg-white shadow justify-content-between w-25" style="height: 150px;">   
                    <h3 class="">Total Courses</h3>    
                    <h2>${courseCount}</h2>       
                    <a href="/StudentRegistration/courses/" class="pt-2 pb-2 text-bg-info" style="text-decoration:none;">View Courses</a>    
                </div>
                <div class="flex-column p-2 rounded d-flex bg-white bg-white shadow justify-content-between w-25 " style="height: 150px;">
                    <h3>Total Students</h3>
                    <h2>${studentCount}</h2>
                    <a href="/StudentRegistration/student/" class="pt-2 pb-2 text-bg-info" style="text-decoration:none;">View Students</a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Logout confirmation modal -->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="logoutModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="logoutModalLabel">Logout Confirmation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure you want to logout?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="cancelDelete()">Cancel</button>
                <button type="button" class="btn btn-primary" onclick="logout()">Logout</button>
            </div>
        </div>
    </div>
</div>

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
    
    var currentDate = new Date();
    var day = currentDate.getDate();
    var month = currentDate.getMonth() + 1; // Months are zero-based
    var year = currentDate.getFullYear();
    var hours = currentDate.getHours();
    var minutes = currentDate.getMinutes();
    var seconds = currentDate.getSeconds();

    // Format the date and time
    var formattedDateTime = day + '/' + month + '/' + year + ' ' + hours + ':' + minutes + ':' + seconds;

    // Display the formatted date and time in the span element
    document.getElementById('currentDateTime').textContent = formattedDateTime;
</script>
</body>
</html>
