<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<title>Innobyte Library Login</title>
	<link rel="stylesheet"
		href="loginStyle.css">
		
<style>
body {
  background-image: url('https://www.innobytess.com/assets/images/background.png');
}
</style>
</head>


<body>

	<div class="main">
		<h1>Innobyte Service</h1>
		<h3>Enter your login credentials</h3>
		<form action="LoginValid" method="get">
		<label for="Enter your Name">
				Login Name:
			</label>
			<input type="Text"
				id="Name"
				name="Name"
				placeholder="Enter your Name" required>
			

			<label for="password">
				Password:
			</label>
			<input type="password"
				id="password"
				name="password"
				placeholder="Enter your Password" required>

			<div class="wrap">
			
				<button type="submit">
					Submit
				</button>
			</div>
		</form>
		<script type="text/javascript">
			console.log("Value to pass Name: " + document.getElementById("Name").value);
			console.log("Value to pass pass: " + document.getElementById("password").value);

			</script>
		<p>Not registered? 
			<a href="AccountCreate.html"
			style="text-decoration: none;">
				Create an account
			</a>
		</p>
	</div>
</body>

</html>
