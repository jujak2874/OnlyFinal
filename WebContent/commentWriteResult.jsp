<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String id = (String) session.getAttribute("sessionId");
%>
	<c:if test="${commentResult > 0 }">
		<script type="text/javascript">
			location.href = "timeline.jsp";
		</script>
	</c:if>
	<c:if test="${commentResult <= 0 }">
		<script type="text/javascript">
			location.href = "timeline.jsp";
		</script>
	</c:if>
</body>
</html>