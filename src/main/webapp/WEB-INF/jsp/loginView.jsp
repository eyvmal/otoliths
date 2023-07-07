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
	<div id="infoContainer">
		<p>Easymode will show two pictures where one is annotated by a human and the other is annotated by AI.
			Your job is to find out which one is the human annotated one.</p><br>
		<p>Hardmode will show only one picture. And you will have to guess if it's annotated by a human or an AI.</p>
	</div>
</div>
</body>
</html>

