<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>마이페이지</title>
		<link rel="stylesheet" href="../resources/css/main.css">
	</head>
	<body>
		<h1>마이페이지</h1>
		<fieldset>
			<legend>회원 정보</legend>
			<ul>
				<li>
					<label>아이디</label>
					<span>${member.memberId }</span>
				</li>
				<li>
					<label>이름</label>
					<span>${member.memberName }</span>
				</li>
				<li>
					<label>나이</label>
					<span>${member.memberAge }</span>
				</li>
				<li>
					<label>성별</label>
					<c:if test="${member.memberGender eq 'M' }">
						<span>남자</span>
					</c:if>
					<c:if test="${member.memberGender eq 'F' }">
						<span>여자</span>
					</c:if>
				</li>
				<li>
					<label>이메일</label>
					<span>${member.memberEmail }</span>
				</li>
				<li>
					<label>전화번호</label>
					<span>${member.memberPhone }</span>
				</li>
				<li>
					<label>주소</label>
					<span>${member.memberAddr }</span>
				</li>
				<li>
					<label>가입일</label>
					<span>${member.memberDate }</span>
				</li>
				<li>
					<label>취미</label>
					<span>${member.memberHobby }</span>
				</li>
			</ul>
		</fieldset>
		<a href="/index.jsp">이전페이지</a>
		<a href="/member/modify.kh?memberId=${member.memberId }">수정하기</a>
		<a href="/member/delete.kh?memberId=${member.memberId }">탈퇴하기</a>
	</body>
</html>