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
<title>회원탈퇴</title>
</head>
<body>
	<sql:setDataSource var="dataSource"
		url="jdbc:mysql://localhost:3307/embed"
		driver="com.mysql.cj.jdbc.Driver" user="root" password="1111" />

	<sql:update dataSource="${dataSource}" var="resultSet">
		DELETE FROM member WHERE id=?
		<sql:param value="<%=sessionId%>" />
	</sql:update>

	<c:if test="${resultSet >= 1}">
		<c:import var="url" url="logoutMember.jsp" />
		<c:redirect url="resultMember.jsp?msg=9" />
	</c:if>
</body>
</html>





