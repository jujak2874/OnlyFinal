<%@page import="java.util.Enumeration"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
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
		String folder = "";
		String fileName = "";
		int maxSize = 1024 * 1024 * 5;
		String encode = "utf-8";
		String saveFile = "imgUpload";
		ServletContext servletCon = getServletContext();
		folder = servletCon.getRealPath(saveFile);

		try {
			MultipartRequest multi = new MultipartRequest(request, folder, maxSize, encode,
					new DefaultFileRenamePolicy());
			Enumeration<?> files = multi.getFileNames();
			String file = (String) files.nextElement();
			fileName = multi.getFilesystemName(file);
		} catch (Exception e) {

		}

		String fullpath = folder + "" + fileName;
	%>
</body>
</html>