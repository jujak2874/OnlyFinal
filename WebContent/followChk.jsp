<%@page import="dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	String userid1 = (String) session.getAttribute("sessionId");
	String userid2 = request.getParameter("userid2");
	System.out.println(userid1+"이 " + userid2+"를 팔로윙 합니다");
	FollowDao fd = FollowDao.getInstance();
	
	int result = fd.getFollowStatus(userid1, userid2);
	if (result > 0) {  %>true<%  } else { %>false<%  } %>