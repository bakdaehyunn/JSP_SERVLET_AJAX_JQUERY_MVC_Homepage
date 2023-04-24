<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정 페이지</title>
</head>
<body>
	<h2>글 수정 페이지</h2>
	<form action="update.do" method="POST">
		<div>
			<p>글 번호 :${vo.boardId }</p>
			<input type="hidden" name="boardId" value="${vo.boardId }">
		</div>
		<div>
			<p>제목 :</p>
			<input type="text" name="boardTitle" value="${vo.boardTitle }"
				required>
		</div>
		<div>
			<p>작성자 : ${vo.memberId }</p>

		</div>
		<div>
			<p>작성일: ${vo.boardDateCreated }</p>
		</div>

		<div>
			<p>내용 :</p>
			<textarea rows="20" cols="120" name="boardContent"
				placeholder="내용 입력" required>${vo.boardContent }</textarea>
		</div>
		<div>
			<input type="submit" value="수정">
		</div>
	</form>
</body>
</html>