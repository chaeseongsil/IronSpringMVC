<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		<form action="/board/addReply.kh" method="post">
			<table>
				<tr>
					<td colspan="2">
						<textarea rows="2" cols="70"></textarea>
					</td>
					<td>
						<input type="submit" value="등록">
					</td>
				</tr>
			</table>
		</form>
		<!-- 댓글 목록 -->
		<table>
			<tr>
				<td>일용자</td>
				<td>환영합니다~</td>
				<td>2023-08-24</td>
				<td>
					<a href="#">수정하기</a>
					<a href="#">삭제하기</a>
				</td>
			</tr>
		</table>
		<script>
			function showModifyPage(){
				const boardNo = '${board.boardNo}';
				location.href="/board/modify.kh?boardNo="+boardNo;
			}
			function showboardList(){
				location.href="/board/list.kh";
			}
		</script>
	</body>
</html>