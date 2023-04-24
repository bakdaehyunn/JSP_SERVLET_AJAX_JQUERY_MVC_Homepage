<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 작성 페이지</title>
</head>
<body>
	<h2>글 작성 페이지</h2>
	<form action="register.do" method="POST">
		<div>
			<p>제목 : </p>
			<input type="text" name="boardTitle" placeholder="제목 입력" required>
		</div>
		<div>
			<p>작성자 : </p>
			<input type="text" name="memberId" value="${sessionScope.memberId }"readonly>
		</div>
		<div>
			<p>내용 : </p>
			<textarea rows="20" cols="120" name="boardContent" placeholder="내용 입력" required></textarea>
		</div>
		<div>
			<input type="submit" value="등록">
		</div>
	</form>
</body>
</html>











