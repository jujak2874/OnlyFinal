<%@page import="dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String type= request.getParameter("type");
	System.out.println(type);
	int unchecked = 0;
	if(type.equals("chat")){
		ChatDao cdo = ChatDao.getInstance();
		unchecked = cdo.checkUnreadMessage((String) session.getAttribute("sessionId"));
		System.out.println("unread message: " + unchecked);
	} else if(type.equals("post")){
		AlertDao ado = AlertDao.getInstance();
		unchecked = ado.uncheckedAlert((String) session.getAttribute("sessionId"));
		System.out.println("unchecked alert: "+ unchecked);
	}
%><%=unchecked %>
