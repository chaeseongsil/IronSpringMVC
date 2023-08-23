<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Welcome to Spring Web</title>
	</head>
	<body>
		<jsp:forward page="/home.kh"></jsp:forward>
		<!-- forward는 response.sendRedirect처럼 페이지 이동하게 해주는 역할 -->
		<!-- web.xml을 .kh로 바꾸면 / 경로는 동작하지 않는데 index.jsp는 무조건 뜨니까 home.kh로 이동하게 만들어줌 -->
	</body>
</html>