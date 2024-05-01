<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>Display Users</title>
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/2.0.1/css/buttons.dataTables.min.css">
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/buttons/2.0.1/js/dataTables.buttons.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/buttons/2.0.1/js/buttons.html5.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.70/pdfmake.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.70/vfs_fonts.js"></script>
</head>
<style>
      .disabled-row {
         background-color: yellow; 
      } 
      
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
    
    .error {
        color: red;
    }
</style>
<body>
	<nav>
      <div class="dadhoboard">
      		<div>
            	<div>
            		<h3>Admin Dashboard</h3>
            	</div>
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
            <li><a href="/StudentRegistration/details">Manage Report</a></li>
        <!-- Add more items as needed -->
        </ul>
      </div>
    	<div class="second">
    		<p class="error">${error}</p>
    		
    		<div class="mb-3">
				<c:if test="${not empty errors}">
					<div class="alert alert-danger">${errors}</div>
				</c:if>
			</div>
    		
    		<div class="mt-4" style="width: 100%">
    			<div class="m-auto rounded shadow bg-light text-dark p-3  d-flex justify-content-between" style="width: 95%">
	    			<h4>Manage User</h4>
	    			<a  class="btn btn-success" href="add">Add User</a>
    			</div>
    	    </div>
 
    			<div class="mt-4" style="width: 100%;">
					<div class="bg-light  text-dark m-auto shadow rounded" style="width: 95%;">
					<table id="productsTable">
						<thead>
                 			<tr>
                 				<th>User Id</th>
                    			<th>Name</th>
                    			<th>Email</th>
                    			<th>DOB</th>
                    			<th>Gender</th>
                    			<th>Phone</th>
                    			<th>NRC</th>
                    			<th>Image</th>
                    			<th></th>
                			</tr>
            			</thead>
            			<tbody>
                			<c:forEach var="u" items="${users}">
                    			<tr>
                    				<td>${u.user_code}</td>
                        			<td>${u.name}</td>
                        			<td>${u.email}</td>
                        			<td>${u.dob}</td>
                        			<td>${u.gender}</td>
                        			<td>${u.phone}</td>
                        			<td>${u.nrc}</td>
                        			<td><img alt="ProfileImage" src="data:image/jpeg;base64,${u.image}" style="width:100px;height:60px;" /></td>
                        			<td>
                            			<div class="col">
                                			<a href="edit/${u.id}" class="btn bi bi-heart" style="color:white"><i class="fa-solid fa-pen" style="color: blue;"></i></a>
                                			<a href="#" onclick="showModal('${u.id}');" class="btn " style="color:white"><i class="fa-solid fa-trash" style="color: red;"></i></a>
                                			
                                			<c:if test="${u.is_disabled == true}">
                        						<a href="disable/${u.id}" style="color:white" class="btn btn-danger">Disable</a>
                    						</c:if>
                    						
                    						<c:if test="${u.is_disabled == false}">
                        						<a href="enable/${u.id}" style="color:white" class="btn btn-success">Enable</a>
                    						</c:if>
                                		</div>
                                		
                                		<div class="modal" tabindex="-1" role="dialog" id="deleteModal">
    										<div class="modal-dialog" role="document">
        										<div class="modal-content">
            										<div class="modal-header">
                										<h5 class="modal-title">Delete Confirmation</h5>
                										<button type="button" class="close" data-dismiss="modal" aria-label="Close"></button>
            										</div>
            										<div class="modal-body">
                										<p>Are you sure you want to delete?</p>
            										</div>
            										<div class="modal-footer">
                										<button type="button" class="btn btn-secondary" onclick="Delete()">Cancel</button>
                										<a id="deleteLink" href="#" class="btn btn-danger" onclick="deleteItem()">Delete</a>
            										</div>
        										</div>
    										</div>
										</div>
                        			</td>
                    			</tr>
                			</c:forEach>
            			</tbody>
					</table>
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
function showModal(uId) {
    $('#deleteLink').attr('href', 'delete/' + uId);
    $('#deleteModal').modal('show');
}

function deleteItem() {
    $('#deleteModal').modal('hide');
}

function Delete() {
    $('#deleteLink').attr('href', '#');
    $('#deleteModal').modal('hide');
}

function showLogoutConfirmation() {
	$('#logoutModal').modal('show');
}

function logout() {
	window.location.href = "/StudentRegistration/logout/";
}

function cancelDelete() {
	$('#logoutModal').modal('hide');
}

$(document).ready(function () {
    $('#productsTable').DataTable({
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'excel',
                exportOptions: {
                    columns: [0, 1, 2, 3, 4, 5, 6]
                },
                customize: function (xlsx) {
                    var sheet = xlsx.xl.worksheets['sheet1.xml'];
                    $('row c[r^="F"]', sheet).attr('s', '2');
                }
            },
            {
                extend: 'pdf',
                exportOptions: {
                    columns: [0, 1, 2, 3, 4, 5, 6]
                },
                customize: function (doc) {
                    doc.styles.tableHeader.fillColor = '#007BFF';
                }
            },
            'print'
        ]
    });
});
</script>
</body>
</html>





