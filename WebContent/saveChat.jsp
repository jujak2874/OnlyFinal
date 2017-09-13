<%@page import="dao.ChatDao"%>
<%@page import="dto.ChatMessage"%>
<%@page import="dao.ChatDao"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
	<%
	String chatMessage = request.getParameter("chat");
	String sendT = request.getParameter("sendT");
	String getT = request.getParameter("getT");
 	System.out.println("saveChat.jsp called " + chatMessage+","+sendT+","+getT);
 	ChatDao dao = ChatDao.getInstance();
 	dao.ChatService(chatMessage, getT, sendT);
 	%>	
</body>
</html>