<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Student</title>
	<link href="<c:url value="/resources/css/sucessful.css" />" rel="stylesheet">
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
    </script>
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
    	<div class="first">
    	<ul>
    		<li><a href="/StudentRegistration/user">Dashboard</a></li>
    		<li><a href="/StudentRegistration/students/">Add Student</a></li>
			<li><a href="/StudentRegistration/course/">View Courses</a></li>
			<li><a href="/StudentRegistration/studentsreports">Generate Report</a></li>
        <!-- Add more items as needed -->
       	</ul>
    	</div>
    		<div class="second">
    		<div class="row">
			<div class="col-md-4"></div> 
			<div class="col-md-4 mt-4 mb-4 rounded p-5 shadow bg-white"> 
		    <div class="text-danger">${error}</div>  
			<form:form action="/StudentRegistration/students/add" method="post" modelAttribute="student" enctype="multipart/form-data">
			<%-- <form:errors path="*" cssClass="text-danger" element="div"></form:errors> --%>
				<h2>Add Student Form</h2>
				
				<div class="mb-3">
					<c:if test="${not empty errs}">
						<div class="alert alert-danger">${errs}</div>
					</c:if>
				</div> 
				
				<div class="mb-3">
					<form:input type="hidden" path="delete" value="0" />
				</div>
				
				<div class="mb-3">
					<form:input type="hidden" path="user_id" value="${loggedInUser.id}" />
				</div>
				
				
				<div class="mb-3">
					<form:label path="student_code" class="form-label">Student Id</form:label>
					<form:input path="student_code" class="form-control" readonly="true"/>
					<form:errors path="student_code" cssClass="text-danger"></form:errors>
				</div>
				
				<div class="mb-3">
                	<form:label path="date" class="form-label">Join Date</form:label>
               		<form:input type="date" class="form-control" path="date" placeholder="Please choose join date" min="<%= new java.util.Date().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate() %>"/>
					<form:errors path="date" cssClass="text-danger"></form:errors>
            	</div>
				
				<div class="mb-3">
					<form:label path="name" maxlength="30" class="form-label">Name</form:label>
					<form:input path="name" class="form-control" placeholder="Please enter name"/>
					<form:errors path="name" cssClass="text-danger"></form:errors>
				</div>
				
				<div class="mb-3">
                	<form:label path="dob" class="form-label">Date Of Birth</form:label>
               		<form:input type="date" class="form-control" path="dob" placeholder="Please enter date of birth" max="<%= new java.util.Date().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate() %>"/>
					<form:errors path="dob" cssClass="text-danger"></form:errors>
            	</div>

            	<div class="mb-3">
                	<form:label path="gender" class="form-label">Gender</form:label>
                	<div>
                		<form:radiobutton path="gender" value="male" class="form-check-input"/> Male
                		<form:radiobutton path="gender" value="female" class="form-check-input"/> Female
                	</div>
                	<form:errors path="gender" cssClass="text-danger"></form:errors>
            	</div>
				
				<div class="mb-3">
					<form:label path="phone" class="form-label">Phone</form:label>
					<form:input class="form-control" path="phone" maxlength="11" type="text" onkeypress='return event.charCode >= 48 && event.charCode <= 57' placeholder="Please enter phone"/>
					<form:errors path="phone" cssClass="text-danger"></form:errors>
				</div>
				
				<div class="mb-3">
					<form:label path="education" class="form-label">Education</form:label>
					 <form:select path="education" class="form-select" aria-label="Education">
            			<form:option value="Bachelor of Information Technology">Bachelor of Information Technology</form:option>
            			<form:option value="Diploma in IT">Diploma in IT</form:option>
            			<form:option value="Bachelor of Computer Science">Bachelor of Computer Science</form:option>
        			</form:select>
				</div>
    			
    			<div class="mb-3">
    				<form:label path="courses" class="form-label">Attend</form:label>
    				<form:checkboxes items="${coursesOptionList}" path="courses" itemValue="id" itemLabel="name"/>
    				<form:errors path="courses" cssClass="text-danger"></form:errors>
				</div>


				<div class="mb-3">
                	<form:label path="multipart" class="form-label" >Student Image</form:label>
                	<form:input type="file" path="multipart" class="form-control" required="required" placeholder="Please choose image"/>
            	</div>
            	
				<div class="mb-3 d-flex justify-content-end">			
					<a class="btn btn-secondary" href="/StudentRegistration/students/" style="margin-right:10px;">Cancel</a>
					<input type="submit" value="Add Student" class="btn btn-primary" />
				</div>
			</form:form>
			<div class="popup" id="popup">
              	<img src="https://www.shutterstock.com/image-vector/green-check-mark-icon-circle-260nw-576516469.jpg">
              	<h2>Thank You!</h2>
              	<p>Add Student Successful</p>
              	<a href="/StudentRegistration/students/"><button type="button">Ok</button></a>
           </div> 
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
	
<script>
document.addEventListener("DOMContentLoaded", () => {
    const popup = document.getElementById("popup");

    var result = ${result};

    const openPopup = () => {
        popup.classList.add("open-popup");
    };

    if (result === 1) {
        openPopup();
    }

    console.log('phone-field script _');

    document.querySelector('.form-control[name="phone"]').addEventListener('keypress', (e) => {
        console.log('phone value', e.target.value.length);
        if (e.target.value.length > 10) {
            e.target.value = e.target.value.substr(0, 10);
        }
    });
});
</script>


</body>
</html>