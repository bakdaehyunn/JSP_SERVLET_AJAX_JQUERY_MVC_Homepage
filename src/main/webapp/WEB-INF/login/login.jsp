<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="login.go" method="POST">
		<input type="text" name="memberId"
		placeholder="아이디" required autofocus><br>
		<input type="password" name="password"
		placeholder="비밀번호" required ><br>
		<input type="submit" value="로그인">
		
	</form>
</body>
</html>