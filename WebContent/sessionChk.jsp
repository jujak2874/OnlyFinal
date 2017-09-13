<%
String id = (String) session.getAttribute("sessionId");
if(id==null || id.equals("")){
	response.sendRedirect("signUpForm.jsp");
	return;
}
%>