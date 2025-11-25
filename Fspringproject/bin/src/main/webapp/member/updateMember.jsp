<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<%
	String sessionId = (String)session.getAttribute("sessionId");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원수정</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<script type="text/javascript">
		function init() {
			setComboMailValue("${mail2}");
			setComboBirthValue("${month}");
		}
		
		function setComboMailValue(val) {
			let selectMail = document.getElementById("mail2");
			for (let i = 0, let j = selectMail.length;  i < j; i++) {
				if (selectMail.options[i].value == val) {
					selectMail.options[i].selected = true;
					break;
				}
			}
		}
		
		function setComboBirthValue(val) {
			let selectBirth = document.getElementById("birthmm");
			for (let i = 0, let j = selectBirth.length; i < j; i++) {
				if (selectBirth.options[i].value == val) {
					selectBirth.options[i].selected = true;
					break;
				}
			}
		}
		
		function checkForm() {
			if (!document.newMember.id.value) {
				alert("아이디를 입력하세요.");
				return false;
			}
			
			if (!document.newMember.password.value) {
				alert("비밀번호를 입력하세요.");
				return false;
			}
			
			if (document.newMember.password.value != document.newMember.password_confirm.value) {
				alert("비밀번호를 동일하게 입력하세요.");
				return false;
			}
		}
	</script>
</head>
<body onload="init()">

	<sql:setDataSource var="dataSource"
		url="jdbc:mysql://localhost:3307/embed"
		driver="com.mysql.cj.jdbc.Driver" user="root" password="1111" />

	<sql:query var="resultSet" dataSource="${dataSource}">
		SELECT * FROM member WHERE id = ?
		<sql:param value="<%=sessionId%>" />
	</sql:query>

	<div class="container py-4">
		<jsp:include page="/menu.jsp" />

		<div class="p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="display-5 fw-bold">회원 수정</h1>
				<p class="col-md-8 fs-4">Membership Updating</p>
			</div>
		</div>

		<c:forEach var="row" items="${resultSet.rows}">

			<c:set var="mail" value="${row.mail}" />
			<c:set var="mail1" value="${mail.split('@')[0]}" />
			<c:set var="mail2" value="${mail.split('@')[1]}" />

			<c:set var="birth" value="${row.birth}" />
			<c:set var="year" value="${birth.split('/')[0]}" />
			<c:set var="month" value="${birth.split('/')[1]}" />
			<c:set var="day" value="${birth.split('/')[2]}" />

			<div class="container">
				<form action="processUpdateMember.jsp" method="post"
					onsubmit="return checkForm()" name="newMember">
					<div class="mb-3 row">
						<label class="col-sm-2">아이디</label>
						<div class="col-sm-3">
							<input name="id" type="text" class="form-control"
								placeholder="id" value="<c:out value='${row.id}'/>">
						</div>
					</div>

					<div class="mb-3 row">
						<label class="col-sm-2">비밀번호</label>
						<div class="col-sm-3">
							<input name="password" type="password" class="form-control"
								placeholder="password" value="<c:out value='${row.password}'/>">
						</div>
					</div>

					<div class="mb-3 row">
						<label class="col-sm-2">비밀번호확인</label>
						<div class="col-sm-3">
							<input name="password_confirm" type="password"
								class="form-control" placeholder="password_confirm">
						</div>
					</div>

					<div class="mb-3 row">
						<label class="col-sm-2">성명</label>
						<div class="col-sm-3">
							<input name="name" type="text" class="form-control"
								placeholder="name" value="<c:out value='${row.name}'/>">
						</div>
					</div>

					<div class="mb-3 row">
						<label class="col-sm-2">성별</label>
						<div class="col-sm-10">
							<c:set var="gender" value="${row.gender}" />
							<input name="gender" type="radio" value="남"
								<c:if test="${gender.equals('남')}"> <c:out value="checked"/> </c:if>>남
							<input name="gender" type="radio" value="여"
								<c:if test="${gender.equals('여')}"> <c:out value="checked"/> </c:if>>여
						</div>
					</div>

					<div class="mb-3 row">
						<label class="col-sm-2">생일</label>
						<div class="col-sm-10">
							<div class="row">
								<div class="col-sm-2">
									<input name="birthyy" type="text" maxlength="4"
										class="form-control" placeholder="년(4)" size="6"
										value="${year}">
								</div>

								<div class="col-sm-2">
									<select name="birthmm" class="form-select">
										<option value="">월</option>
										<option value="01">1</option>
										<option value="02">2</option>
										<option value="03">3</option>
										<option value="04">4</option>
										<option value="05">5</option>
										<option value="06">6</option>
										<option value="07">7</option>
										<option value="08">8</option>
										<option value="09">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
									</select>
								</div>

								<div class="col-sm-2">
									<input name="birthdd" type="text" maxlength="2"
										class="form-control" placeholder="일" size="4" value="${day}">
								</div>
							</div>

						</div>
					</div>

					<div class="mb-3 row">
						<label class="col-sm-2">이메일</label>
						<div class="col-sm-10">
							<div class="row">
								<div class="col-sm-4">
									<input name="mail1" type="text" maxlength="50"
										class="form-control" value="${mail1}">
								</div>
								@

								<div class="col-sm-3">
									<select name="mail2" class="form-select">
										<option>naver.com</option>
										<option>daum.net</option>
										<option>gmail.com</option>
										<option>nate.com</option>
									</select>
								</div>
							</div>
						</div>
					</div>

					<div class="mb-3 row">
						<label class="col-sm-2">전화번호</label>
						<div class="col-sm-3">
							<input name="phone" type="text" class="form-control"
								placeholder="phone" value="<c:out value='${row.phone}'/>">
						</div>
					</div>

					<div class="mb-3 row">
						<label class="col-sm-2">주소</label>
						<div class="col-sm-5">
							<input name="address" type="text" class="form-control"
								placeholder="address" value="<c:out value='${row.address}'/>">
						</div>
					</div>

					<div class="mb-3 row">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" class="btn btn-primary" value="회원수정">
							<a href="deleteMember.jsp" class="btn btn-primary">회원탈퇴</a>
						</div>
					</div>
				</form>
			</div>
		</c:forEach>

		<jsp:include page="/footer.jsp" />
	</div>

</body>
</html>