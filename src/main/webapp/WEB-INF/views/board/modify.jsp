<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시글 수정</title>
		<link rel="stylesheet" href="../resources/css/main.css">
	</head>
	<body>
		<h1>게시글 수정</h1>
		<form action="/board/modify.kh" method="post" enctype="multipart/form-data">
			<input type="hidden" name="boardNo" value="${board.boardNo }">
			<input type="hidden" name="boardFilename" value="${board.boardFilename }">
			<input type="hidden" name="boardFileRename" value="${board.boardFileRename }">
			<input type="hidden" name="boardFilepath" value="${board.boardFilepath }">
			<input type="hidden" name="boardFileLength" value="${board.boardFileLength }">
			<ul>
				<li>
					<label>제목</label>
					<input type="text" name="boardTitle" value="${board.boardTitle }">
				</li>
				<li>
					<label>작성자</label>
					<input type="text" name="boardWriter" value="${board.boardWriter }" readonly>
				</li>
				<li>
					<label>내용</label>
					<textarea rows="4" cols="50" name="boardContent">${board.boardContent }</textarea>
				</li>
				<li>
					<label>첨부파일</label>
					<a href="../resources/buploadFiles/${board.boardFilename }" download>${board.boardFilename }</a>
					<input type="file" name="uploadFile">
					<!-- String으로 받을 수 없고 변환 작업이 필요함 -->
				</li>
			</ul>
			<div>
				<input type="submit" value="수정">
				<input type="button" value="뒤로가기" onclick="goBack();">
			</div>
		</form>
	<script>
		function goBack(){
			history.back();
		}
	</script>
	</body>
</html>