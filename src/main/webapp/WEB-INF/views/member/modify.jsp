<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원 정보 수정</title>
	</head>
	<body>
		<h1>회원 정보 수정</h1>
		<form action="/member/modify.kh" method="post">
			<fieldset>
				<legend>수정할 정보 입력</legend>
				<ul>
					<li>
						<label>아이디</label>						
						<input type="text" name="memberId" value="${member.memberId }" readonly>
					</li>
					<li>
						<label>비밀번호 *</label>						
						<input type="password" name="memberPw">
					</li>
					<li>
						<label>이름</label>						
						<input type="text" name="memberName" value="${member.memberName }" readonly>
					</li>
					<li>
						<label>나이</label>						
						<input type="text" name="memberAge" value="${member.memberAge }" readonly>
					</li>
					<li>
						<label>성별</label>						
						남 <input type="radio" name="memberGender" value="M" <c:if test="${member.memberGender eq 'M' }">checked</c:if> disabled>
						여 <input type="radio" name="memberGender" value="F"<c:if test="${member.memberGender eq 'F' }">checked</c:if> disabled>
					</li>
					<li>
						<label>이메일</label>						
						<input type="text" name="memberEmail" value="${member.memberEmail }">
					</li>
					<li>
						<label>전화번호</label>						
						<input type="text" name="memberPhone" value="${member.memberPhone }">
					</li>
					<li>
						<label>주소</label>						
						<input type="text" name="memberAddr" id="memberAddr" value="${member.memberAddr }"> 
						<input type="button" onclick="sample4_execDaumPostcode();" value="우편번호 찾기">
					</li>
					<li>
						<label>취미</label>						
						<input type="text" name="memberHobby" value="${member.memberHobby }">
					</li>
				</ul>
			</fieldset>
			<div>
				<input type="submit" value="수정하기">
				<input type="reset" value="초기화">
			</div>
		</form>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript">
		function sample4_execDaumPostcode(){
			new daum.Postcode({
				oncomplete : function(data){
					// 주소 검색이 끝났을 때 동작하는 코드
					document.querySelector("#memberAddr").value = "[" + data.zonecode + "] " + data.jibunAddress + ", " + data.buildingName;
				}
			}).open();
		}
	</script>
	</body>
</html>