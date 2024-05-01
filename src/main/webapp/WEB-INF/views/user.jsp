<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User Home</title>
<!-- Bootstrap CSS link -->
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<!-- Custom CSS for styling -->
<style>
/* Add this style to make the logo responsive */
.logo {
    max-width: 15%;
    height: auto;
    display: block;
    margin: 0 auto; /* Center the logo */
}

.logout:hover {
    background-color: red;
    cursor: pointer;
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
            <h3>User Dashboard</h3>
            <div>
                <a class="logout btn btn-light"
                    style="cursor: pointer; float: right; margin-top: 10px; padding: 8px 15px; border-radius: 5px;"
                    onclick="showLogoutConfirmation()">Logout</a>
            </div>
        </div>
    </nav>
    
    <div class="sidebar">
        <div class="first" style="margin-bottom:500px;">
            <ul>
                <li><a href="/StudentRegistration/students/">Add Student</a></li>
                <li><a href="/StudentRegistration/course/">View Courses</a></li>
                <li><a href="/StudentRegistration/studentsreports">Generate Report</a></li>
                <!-- Add more items as needed -->
            </ul>
        </div>
        <div class="second">
            <div style="width: 95%;" class="m-auto p-4 d-flex justify-content-between bg-light  shadow p-3 mb-2 rounded">
                <p>Welcome, ${loggedInUser.name}</p>
                <p>Email: ${loggedInUser.email}</p>
                <p>Current Date and Time: <span id="currentDateTime"></span></p>
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
                    <button type="button" class="btn btn-primary" onclick="logout()">Logout</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <!-- Bootstrap JS and Popper.js -->
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
        
        // Get the current date and time
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
