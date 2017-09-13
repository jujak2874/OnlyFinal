<%@page import="dao.ChatDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		System.out.println("chatsave called..");
		ChatDao dao = ChatDao.getInstance();
		System.out.println(request.getParameter("chat") + ", " + request.getParameter("getT"));
		dao.ChatService(request.getParameter("chat"),request.getParameter("getT"), request.getParameter("sendT"));
	%>
</body>
</html>