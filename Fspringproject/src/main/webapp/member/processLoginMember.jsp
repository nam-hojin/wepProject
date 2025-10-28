<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<%
	request.setCharacterEncoding("UTF-8");
	
	String id = request.getParameter("id");
	String password = request.getParameter("password");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인처리</title>
</head>
<body>
	<sql:setDataSource var="dataSource"
		url="jdbc:mysql://localhost:3307/embed"
		driver="com.mysql.cj.jdbc.Driver" user="root" password="1111" />

	<sql:query var="resultSet" dataSource="${dataSource}">
		SELECT * FROM member WHERE id = ? AND password = ?
		<sql:param value="<%=id %>" />
		<sql:param value="<%=password %>" />
	</sql:query>

	<c:forEach var="row" items="${resultSet.rows}">
		<%
			session.setAttribute("sessionId", id);
		%>
		<c:redirect url="resultMember.jsp?msg=2" />
	</c:forEach>

	<c:redirect url="loginMember.jsp?error=1" />
</body>
</html>








