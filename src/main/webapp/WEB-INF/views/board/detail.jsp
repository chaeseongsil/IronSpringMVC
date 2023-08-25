<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 상세조회</title>
		<style type="text/css">
			table {
				border : 1px solid black;
				border-collapse : collapse;
				width : 800px;
			}
			
			th, td {
				border : 1px solid black;
				padding : 5px 5px;
			}
		</style>
	</head>
	<body>
		<h1>공지 내용</h1>
		<ul>
			<li>
				<label>제목</label>
				<input type="text" name="boardTitle" value="${board.boardTitle }" readonly>
			</li>
			<li>
				<label>작성자</label>
				<input type="text" name="boardWriter" value="${board.boardWriter }" readonly>
			</li>
			<li>
				<label>내용</label>
			    <p>${board.boardContent }</p>
			</li>
			<li>
				<label>첨부파일</label>
				<!-- String으로 받을 수 없고 변환 작업이 필요함 -->
				<!-- 이미지만 올리게 한 경우엔 img태그 사용 -->
				<img alt="첨부파일" src="../resources/buploadFiles/${board.boardFileRename }" style="width:300px;">
				<a href="../resources/buploadFiles/${board.boardFileRename }" download>${board.boardFilename }</a>
			</li>
		</ul>
		<div>
			<button type="button" onclick="showModifyPage();">수정하기</button>
			<button>삭제하기</button>
			<button type="button" onclick="showboardList();">뒤로가기</button>
		</div>
		<!-- 댓글 등록 -->
		<form action="/reply/add.kh" method="post">
			<input type="hidden" name="refBoardNo" value="${board.boardNo }">
			<table>
				<tr>
					<td colspan="2">
						<textarea rows="2" cols="70" name="replyContent"></textarea>
					</td>
					<td>
						<input type="submit" value="등록">
					</td>
				</tr>
			</table>
		</form>
		<!-- 댓글 목록 -->
		<table>
			<c:forEach items="${rList }" var="reply">
				<tr>
					<td>${reply.replyWriter }</td>
					<td>${reply.replyContent }</td>
					<td>${reply.rCreateDate }</td>
					<td>
						<c:if test="${reply.replyWriter eq memberId }">
							<a href="javascript:void(0)" onclick="showModifyForm(this);">수정하기</a>
							<a href="#">삭제하기</a>
						</c:if>
					</td>
				</tr>
				<tr style="display:none" id="replyModifyForm">
					<%-- <form action="/reply/update.kh" method="post">
						<input type="hidden" name="replyNo" value="${reply.replyNo }">
						<input type="hidden" name="refBoardNo" value="${reply.refBoardNo }"> 
						<td colspan="3">
							<input type="text" size="50" name="replyContent" value="${reply.replyContent }">
						</td>
						<td><input type="submit" value="완료"></td>
						--%>
					<!-- </form> -->
						<td colspan="3">
							<input type="text" size="50" id="replyContent" name="replyContent" value="${reply.replyContent }">
						</td>
						<td><input type="button" onclick="replyModify(this, '${reply.replyNo}', '${reply.refBoardNo }');" value="완료"></td>
				</tr>
			</c:forEach>
		</table>
		<script>
			function showModifyPage(){
				const boardNo = '${board.boardNo}';
				location.href="/board/modify.kh?boardNo="+boardNo;
			}
			function showboardList(){
				location.href="/board/list.kh";
			}
			function replyModify(obj, replyNo, refBoardNo){
				// DOM 프로그래밍 이용하는 방법
				const form = document.createElement("form");
				form.action = "/reply/update.kh";
				form.method = "post";
				const inputTag1 = document.createElement("input");
				inputTag1.type ="text";
				inputTag1.value = "";
				console.log(inputTag1.value);
				inputTag1.name = "replyContent";
				const inputTag2 = document.createElement("input");
				inputTag2.type ="hidden";
				inputTag2.value = replyNo;
				inputTag2.name = "replyNo";
				const inputTag3 = document.createElement("input");
				inputTag3.type ="hidden";
				inputTag3.value = refBoardNo;
				inputTag3.name = "refBoardNo";
				form.appendChild(inputTag1);
				form.appendChild(inputTag2);
				form.appendChild(inputTag3);
				document.body.appendChild(form);
				form.submit();
			}
			function showModifyForm(obj){
				
				// 1. HTML 태그, display:none 사용하는 방법
				obj.parentElement.parentElement.nextElementSibling.style.display = "block";
			}
			//function showModifyForm(obj, replyContent){
				// 2. DOM프로그래밍을 이용하는 방법
				/* const trTag = document.createElement("tr");
				const tdTag1 = document.createElement("td");
				tdTag1.colSpan = 3;
				const inputTag1 = document.createElement("input");
				inputTag1.type = "text";
				inputTag1.size = 50;
				inputTag1.value = replyContent;
				tdTag1.appendChild(inputTag1);
				const tdTag2 = document.createElement("td");
				const inputTag2 = document.createElement("input");
				inputTag2.type="button";
				inputTag2.value="완료";
				tdTag2.appendChild(inputTag2);
				trTag.appendChild(tdTag1);
				trTag.appendChild(tdTag2);
				console.log(trTag);
				// 클릭한 a를 포함하고 있는 tr 다음에 수정폼이 있는 tr 추가하기
				const prevTrTag = obj.parentElement.parentElement;
				prevTrTag.parentNode.insertBefore(trTag, prevTrTag.nextSibling); */
			//}
			
			
		</script>
	</body>
</html>