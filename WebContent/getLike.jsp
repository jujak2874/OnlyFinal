<%@page import="dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	String userid = (String) session.getAttribute("sessionId");
	String pidParam = request.getParameter("pid");
	System.out.println(pidParam);
	int pid = Integer.parseInt(pidParam.trim());
	
	LikeDao ldo = LikeDao.getInstance();
	int result = ldo.getLike(pid);%><%=result%>