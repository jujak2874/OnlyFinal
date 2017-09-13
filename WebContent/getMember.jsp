<%@page import="dto.*"%>
<%@page import="dao.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
	<%
		System.out.println("getMember.jsp called " + request.getParameter("member_id"));
		MemberDao dao = MemberDao.getInstance();
		String getT = request.getParameter("member_id");
		Member member = dao.getMember(getT);
		if (member!=null) {
			%><%=member.getUsername() %>:<%=member.getProfile_image()%><%
		}
	%>
</body>
</html>