<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/login.css">
	<title>Login</title>
</head>
<body>
<div id="contentContainer">
	<div id="loginContainer">
		<p id="redirectMessage">${redirectMessage}</p>
		<form action="login" method="post">
			<fieldset id="formContainer">
				Whats your name?<br>
				<input type="text" name="username"/><br>
				<input type="radio" name="difficulty" value="easymode">Easy</input>
				<input type="radio" name="difficulty" value="hardmode">Hard</input><br>
				<input type="submit" value="Start"/>
			</fieldset>
		</form>
	</div>
</div>
</body>
</html>

