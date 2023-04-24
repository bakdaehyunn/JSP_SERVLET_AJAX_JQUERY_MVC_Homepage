<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<meta charset="UTF-8">
<title>글보기</title>
</head>
<body>
	<h2>글 보기</h2>
	<div>
		<p>글 번호 : ${vo.boardId }</p>
		<p>
			제목 : <br>${vo.boardTitle }
		</p>
		<p>작성자 : ${vo.memberId }</p>
		<p>작성일: ${vo.boardDateCreated }</p>
	</div>
	<textarea rows="30" cols="100" id="boardContent" readonly>${vo.boardContent }
	</textarea>
	<br>
	<a href="index.jsp"><input type="button" value="글 목록"></a>
	<a href="update.do?boardId=${vo.boardId }"><input type="button"
		value="글 수정"></a>
	<br>

	<form action="delete.do" method="POST">
		<input type="hidden" name="boardId" value="${vo.boardId }"> <input
			type="submit" value="글 삭제">
	</form>

	<c:if test="${empty sessionScope.memberId }">
		* 댓글은 로그인이 필요한 서비스입니다.
		<a href="login.go">로그인하기</a>
	</c:if>
	
	<input type="hidden" id="boardId" value="${vo.boardId }"> 
	<c:if test="${not empty sessionScope.memberId }">
		${sessionScope.memberId }님, 이제  댓글을 작성할 수 있어요.
		<div style="text-align: left;">
			<input type="text" id="memberId" value="${sessionScope.memberId }" readonly> 
			<input type="text"	id="replyContent">
			<button id="btn_add">작성</button>
		</div>
	</c:if>
	
	<hr>
	<div style="text-align: left;">
		<div id="replies"></div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
					getAllReplies();
					$('#btn_add').click(function() {
						var boardId = $('#boardId').val(); // 게시판 번호 데이터
						var memberId = $('#memberId').val(); // 작성자 데이터
						var replyContent = $('#replyContent').val(); // 댓글 내용
						var obj = {
							'boardId' : boardId,
							'memberId' : memberId,
							'replyContent' : replyContent
						}
						console.log(obj);
						// $.ajax로 송수신
						$.ajax({
							type : 'POST',
							url : 'replies/add',
							data : {
								'obj' : JSON.stringify(obj)
							}, // JSON으로 변환
							success : function(result) {
								console.log(result);
								if (result == 'success') {
									alert('댓글 입력 성공');
									getAllReplies();
								}
							}
	
						});
					}); // end btn_add.click()
							function getAllReplies() {
								var boardId = $('#boardId').val();
								var url = 'replies/all?boardId=' + boardId;
								$.getJSON(url,function(data) {
									// data : 서버에서 전송받은 list 데이터가 저장
									// getJSON()에서 json 데이터는
									// javascript object로 자동 parsing됨.
									console.log(data);
									
									var list = '';
									// $(컬렉션).each() :  켈렉션 데이터를 반복문으로 꺼내는 함수
									$(data).each(function() {// this : 컬렉션의 각 인덱스 데이터를 의미
										var show =''
										var sessionMemberId = "<%=session.getAttribute("memberId") %>"; 
										<%--var memberid =$('#memberId').val();--%>
										if(this.memberId != sessionMemberId){
											show='disabled';
										}
										console.log(this);
										var replyDateCreated = new Date(this.replyDateCreated);
										list += '<div class="reply_item">'
													+ '<pre>'

													+ '<input type="hidden" id="replyId" value="'+this.replyId+'"/>'
													+ '<p>아이디 : '
													+ this.memberId
													+ '</p>'
													+ '<input type="hidden" id="memberId" value="'+this.memberId+'" readonly/>'

													+ '<textarea rows="4" cols="30" id="replyContent" >'
													+ this.replyContent
													+ '</textarea>'

													+ '<br>'
													+ replyDateCreated
													+ '&nbsp;&nbsp'
													+ '&nbsp;&nbsp'
													+ '<button class="btn_update" '+show+'>수정</button>'
													+ '<button class="btn_delete" '+show+'>삭제</button>'
													+ '</pre>'
													+ '</div>';
										console.log(list);
								});
								$('#replies').html(list);
								} // end function()
						); // end getJSON()
					} // getALLReplies()
							
					// 수정 버튼을 클릭하면 선택된 댓글 수정
					$('#replies').on('click','.reply_item .btn_update',function() {
						console.log(this);
						// 선택된 댓글의 replyId, replyContent 값을 저장
						// prevAll() : 선택된 노드 이전에 있는 모든 형제 노드를 접근
						var replyId = $(this).prevAll('#replyId').val();
						var replyContent = $(this).prevAll('#replyContent').val();
						console.log("선택된 댓글 번호 : " + replyId
								+ ", 댓글 내용 : " + replyContent);
						$.ajax({
							type : 'POST',
							url : 'replies/update',
							data : {
								'replyId' : replyId,
								'replyContent' : replyContent
							},
							success : function(result) {
								console.log(result);
								if (result == 'success') {
									alert('댓글 수정 성공!');
									getAllReplies();
								}
							}
						});
					}); // end replies.on()

				// 삭제 버튼을 클릭하면 선택된 댓글 삭제
				$('#replies').on('click','.reply_item .btn_delete',function() {
							var replyId = $(this).prevAll('#replyId').val();
							// ajax 요청
							$.ajax({
								type : 'POST',
								url : 'replies/delete',
								data : {
									'replyId' : replyId
								},
								success : function(result) {
									console.log(result);
									if (result == 'success') {
										alert('댓글 삭제 성공!');
										getAllReplies();
									}
								}
							})
						});
				}); // end documnet()
	</script>
</body>
</html>