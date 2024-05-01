<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<!-- Bootstrap CSS link -->
<link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
<link
    href="https://fonts.googleapis.com/css2?family=Jost:wght@500&display=swap"
    rel="stylesheet">
<!-- Custom CSS for styling -->
<style type="text/css">
@import
    url('https://fonts.googleapis.com/css2?family=Nerko+One&display=swap');

* {
    margin: 0;
    box-sizing: border-box;
    /* font-family: 'Nerko One', cursive; */
}

:root {
    --colour-one: #ffd1b7;
    --colour-two: #fee4d4;
    --colour-three: #fdb493;
    --colour-four: #fb966f;
    --colour-five: #f45f34;
    --colour-six: #302825;
    --colour-seven: #a69f9c;
    --colour-eight: #707e8d;
}

body {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: var(--colour-one);
}

.login_form {
    position: relative;
    border-bottom: 3px solid var(--colour-four);
    border-right: 3px solid var(--colour-four);
    background: var(--colour-one);
    height: 400px;
    width: 700px;
    box-shadow: 10px 10px 6px 2px rgba(251, 150, 111, 0.6);
    overflow: hidden;
}

.details {
    position: relative;
    background: var(--colour-two);
    height: 400px;
    width: 350px;
}

.welcome {
    position: absolute;
    color: var(--colour-six);
    left: 40%;
    top: 30%;
}

.form-group:nth-child(1) {
    position: absolute;
    top: 25%;
    left: 20%;
    margin: 12px;
}

.form-group:nth-child(2) {
    position: absolute;
    top: 40%;
    left: 20%;
    font-size: 15px;
    margin: 12px;
}

.form-group:nth-child(3) {
    position: absolute;
    top: 55%;
    left: 20%;
    font-size: 15px;
    margin: 12px;
}

.formcontrol {
    border: 1px solid var(--colour-three);
    padding: 5px 20px;
    border-radius: 5px;
    background: none;
    box-shadow: inset 10px 10px 6px -5px rgba(255, 209, 183, 1);
    text-align: center;
}

label {
    color: var(--colour-eight);
    display: block;
}

.button {
    background: var(--colour-six);
    position: absolute;
    width: 80px;
    top: 70%;
    right:10%;
    padding: 5px;
    font-color: var(--colour-one);
    border-radius: 10px;
    border: none;
}

.forgot {
    position: absolute;
    top: 80%;
    right: 10%;
    padding: 5px;
    font-size: 14px;
    color: var(--colour-five); /* Change link color */
    text-decoration: none; /* Remove underline from link */
    transition: color 0.3s ease; /* Smooth color transition on hover */

    /* Optional: Add a hover effect */
}
.forgot:hover {
    color: var(--colour-four); /* Change color on hover */
}

.button {
    color: #fff;
    font-size: 15px;
}

.fox {
    position: absolute;
    top: 0;
    left: 50%;
    width: 50%;
    height: 100%;
}

.back {
    position: absolute;
    top: 44%;
    left: 56%;
    font-size: 20px;
}

.log {
    position: absolute;
    top: 50%;
    left: 56%;
    font-size: 14px;
}

.acc {
    position: absolute;
    top: 62%;
    left: 56%;
    font-size: 15px;
}

.customer {
    position: absolute;
    top: 72%;
    left: 56%;
    font-color: var(--colour-five);
    background: var(--colour-four);
    border-radius: 5px;
    border: none;
}

.res {
    position: absolute;
    top: 72%;
    left: 76%;
    font-color: var(--colour-five);
    background: var(--colour-four);
    border-radius: 5px;
    border: none;
}

h3 {
    font-size: 14px;
    color: var(--colour-five);
    padding: 5px;
}

/* Remove underline from links */
a {
    text-decoration: none;
    color: var(--colour-five);
}
</style>
</head>
<body>

    <div class="login_form">
        <div class="details">
            <div class="login">
                <h2 class="text-center">Student Management System</h2>

                <form:form class="container" action="login" method="post"
                    modelAttribute="user">
                    
                    <div class="form-group">
                        <label for="emailInput">Email</label>
                        <form:input type="email" class="formcontrol" id="emailInput"
                            path="email"  placeholder="Please enter email" />
                    </div>

                    <div class="form-group">
                        <label for="passwordInput">Password</label>
                        <p style="color: red">${error}</p>
                        <form:input type="password" class="formcontrol" id="passwordInput"
                            path="password"  placeholder="Please enter password" />
                    </div>
                    
                    <div class="d-flex justify-content-end mt-3">
                        <button type="submit" class="button">Login</button>
                    </div>
                    
                    <p class="forgot">
                    	<a href="/StudentRegistration/emailcheck">Forgot password</a>
                    </p>
                    
                </form:form>
                
            </div>
            
        </div>

        <div class="detail">
            <img class="fox"
                src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIGTHDhAJABh_zIuXEf7pKHqsUCRovits49Bqc-ADAKw&s" />
        </div>

    </div>
    

</body>
</html>
