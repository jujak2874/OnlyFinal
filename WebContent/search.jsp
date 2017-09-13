<%@page import="dto.Member"%>
<%@page import="java.util.List"%>
<%@page import="dao.MemberDao"%>
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
		request.setCharacterEncoding("utf-8");
		String searchTerm = request.getParameter("searchTerm");
		System.out.println("Search 실행.. " + searchTerm);
		MemberDao dao = MemberDao.getInstance();
		
		List<Member> result = dao.searchMember(searchTerm);
		request.setAttribute("searchResult", result);
		if(result.size()<=0){
			%><option value="null">검색 결과가 없습니다</option><%
		} else {
			for (Member member : result) {
			%><option value="<%=member.getUserid()%>"><%=member.getUsername()%></option>">
		<%
			}
		}
	%>
</body>
</html>