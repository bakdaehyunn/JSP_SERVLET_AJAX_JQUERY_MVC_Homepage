<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
table, th, td {
	border-style: solid;
	border-width: 1px;
	text-align: center;
}

ul {
	list-style-type: none;
}

li {
	display: inline-block;
}
</style>
<meta charset="UTF-8">
<title>게시판 메인 페이지</title>
</head>
<body>
	<h1>게시판 메인</h1>
	<c:if test="${empty sessionScope.memberId }">
		<a href="login.go">로그인</a>
	</c:if>
	<c:if test="${not empty sessionScope.memberId }">
		<p>${sessionScope.memberId } 님, 환영합니다.!</p>
		<a href="logout.go">로그아웃</a>
	</c:if>
	
	<a href="register.do"><input type="button" value="글 작성"></a>

	<hr>
	<table>
		<thead>
			<tr>
				<th style="width: 60px">번호</th>
				<th style="width: 700px">제목</th>
				<th style="width: 120px">작성자</th>
				<th style="width: 100px">작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vo" items="${list }">
				<tr>
					<td>${vo.boardId }</td>
					<td><a href='detail.do?boardId=${vo.boardId }'>${vo.boardTitle }</a></td>
					<td>${vo.memberId }</td>
					<td>${vo.boardDateCreated }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<ul>
		<%--<c:forEach begin="${pageMaker. startPageNo }" end="${pageMaker.endPageNo }" var="num">
			 --%>
		<c:if test="${pageMaker.hasPrev }">
			<a href="list.do?page=${pageMaker.startPageNo - 1} ">이전</a>
		</c:if>
		<c:forEach begin="${pageMaker.startPageNo }" end="${pageMaker.endPageNo }" var="num">
			<li><a href="list.do?page=${num }">${num }</a></li>
		</c:forEach>
		<c:if test="${pageMaker.hasNext }">
			<a href="list.do?page=${pageMaker.endPageNo + 1} ">다음</a>
		</c:if>

	</ul>

</body>
</html>











