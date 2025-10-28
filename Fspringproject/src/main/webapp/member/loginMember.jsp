<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container py-4">
		<jsp:include page="/menu.jsp" />

		<div class="p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="display fw-bold">회원 로그인</h1>
				<p class="col-md-8 fs-4">Membership Login</p>
			</div>
		</div>

		<div class="container" align="center">
			<div class="col-md-4 col-md-offset-4">
				<h3 class="form-signin-heading">Please sign in</h3>
				<%
					String error = request.getParameter("error");
					
					if(error != null){
						out.println("<div class='alert alert-danger'>");
						out.println("아이디와 비밀번호를 확인해 주세요.");
						out.println("</div>");
					}
				%>

				<form action="processLoginMember.jsp" method="post"
					class="form-signin">
					<div class="form-floating mb-3 row">
						<input type="text" class="form-control" name="id"
							id="floatingInput" placeholder="ID" required="required"
							autofocus="autofocus"> <label for="floatingInput">ID</label>
					</div>

					<div class="form-floating mb-3 row">
						<input type="password" class="form-control" name="password"
							placeholder="Password"> <label for="floatingPassword">Password</label>
					</div>

					<button class="btn btn btn-lg btn-success btn-block" type="submit">로그인</button>
				</form>
			</div>
		</div>

		<jsp:include page="/footer.jsp" />
	</div>
</body>
</html>








