<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">

<title>New Password</title>
<style>
.custom-alert {
	margin-top: 50px;
	position: fixed;
	top: 20px;
	left: 50%;
	transform: translateX(-50%);
	padding: 10px;
	border-radius: 5px;
	color: white;
	font-weight: bold;
	padding-top: 50px;
	padding-bottom: 50px;
}

.success {
	background-color: #272829;
}

.error {
	background-color: #272829;
	color: #CD1818;
}
</style>
</head>
<body style="background-color: #D8D9DA;">

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4" style="padding-top: 120px; color: #272829;">
				<div
					style="text-align: center; background-color: #FFF6E0; border: 2px solid #272829;">

					<form:form action="/StudentRegistration/newpassprogress"
						method="post" modelAttribute="nbean" style="color:black;">

						<label style="color: #272829;">Enter New Password</label>

						<br>
						<form:input type="text" path="password" id="password"
							style="width:90%; border:2px solid #272829;" />
							<button type="button" id="togglePassword" class="btn btn-outline-secondary" onclick="togglePasswordVisibility()">Show</button>
						<br>
						<br>



						<button
							style="background-color: #272829; color: #D8D9DA; width: 90%;">Submit</button>
						<br>
						<br>

					</form:form>

				</div>


			</div>
			<div class="col-md-4"></div>
		</div>
	</div>
	<div id="custom-alert-container"></div>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

	<script>
    function showCustomAlert(message, type) {
        var alertContainer = document.getElementById('custom-alert-container');
        
        var alertElement = document.createElement('div');
        alertElement.className = 'custom-alert ' + type;
        alertElement.textContent = message;
        
        alertContainer.appendChild(alertElement);
        
        setTimeout(function() {
            alertContainer.removeChild(alertElement);
        }, 5000); // Remove the alert after 5 seconds
    }

    $(document).ready(function() {
        var successMessage = '${success}';
        var errorMessage = '${error}';
        
        if (successMessage) {
            showCustomAlert(successMessage, 'success');
        }
        
        if (errorMessage) {
            showCustomAlert(errorMessage, 'error');
        }
    });
    
    document.getElementById("password").addEventListener("input", validatePassword);
    document.getElementById("confirm_password").addEventListener("input", validatePassword);
    
    function togglePasswordVisibility() {
        var passwordInput = document.getElementById("password");
        var toggleButton = document.getElementById("togglePassword");

        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            toggleButton.textContent = "Hide";
        } else {
            passwordInput.type = "password";
            toggleButton.textContent = "Show";
        }
    }
</script>
</body>
</html>