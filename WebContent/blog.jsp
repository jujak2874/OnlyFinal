<%@page import="dao.AlertDao"%>
<%@page import="java.util.List"%>
<%@page import="dao.PostDao"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="dto.Post"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="sessionChk.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.2.1.js"></script>
<script type="text/javascript" src="js/timeline.js"></script>
<script type="text/javascript" src="js/chat.js"></script>
<script type="text/javascript">
	/* 검색창 focus 상태에서 검색이력 view */
	/* 검색창 focus 상태에서 검색이력 view 끝 */
</script>
<link rel="stylesheet" type="text/css" href="css/timelineFull.css">
<link rel="stylesheet" type="text/css" href="css/timelineMobile.css">
<link rel="stylesheet" type="text/css" href="css/search.css">
<link rel="stylesheet" type="text/css" href="css/chat.css">
</head>
<%
	String userid = (String) session.getAttribute("sessionId");
	String blogowner = (String) request.getAttribute("blogowner");
	System.out.println("user: " + userid + ", " + "blogowner: " + blogowner);
%>
<body>
	<div id="wrapper">
		<div id="layerPop">
			<h3>공유하기</h3>
			<hr>
			테스트
			<hr>
			테스트 <a href="#" onclick="closeLayer('layerPop')" class="close">close</a>
		</div>
	</div>
	<!-- 상단고정바 시작 -->
	<div id="header"><jsp:include page="header.jsp"></jsp:include></div>
	
	<div class="header_hidden"></div>
	<!-- 내용 들어갈 부분 -->
	<div class="timeline_main">
	<!-- 포스트작성 / 뷰 부분 -->
	<% if(userid.equals(blogowner)){ %>
	<jsp:include page="blogAside.jsp"></jsp:include>
	<%} %>
	<div class="blog_post">
			<div class="page_margin"></div>
			<!-- 테스트 -->
			<!--  -->
			<div id="main_container">
				<ol class="post_view_box">
				<% if(userid.equals(blogowner)){
					System.out.println("내블로그");
					%>
					<li class="type_choice_box" id="infinite_container">
						<form action="postWrite.do" method="post"
							enctype="multipart/form-data" onsubmit='return sendChat(JSON.stringify({type:"post",from:"<%=id%>"}));'>
							<input type="hidden"
								value="<%=(String) session.getAttribute("sessionId")%>"
								name="member_id">
							<%
								System.out.println("path=" + application.getRealPath("/fileSave"));
							%>
							<input type="hidden"
								value="<%=application.getRealPath("/fileSave")%>" name="path">
							<textarea rows="1" cols="1" class="type_choice_textarea"
								name="text" placeholder="오늘은 무슨일이 있었나요?"></textarea>
							<div class="write_type_choice">
								<a class="choice_type"> <img alt=""
									src="img_timeline/picture.svg"
									class="img_hide img_hidden type_photo" width="30px" border='0'
									onclick='document.all.imageUpload.click();'> <input
									type="file" name="imageUpload" style='display: none;'
									accept="image/*">
								</a> <a class="choice_type"> <img
									src='img_timeline/video-camera.svg'
									class="img_hide img_hidden img_video" width="30px" border='0'
									onclick='document.all.videoUpload.click();'> <input
									type="file" name="videoUpload" style='display: none;'
									accept="video/*">
								</a> <label class="img_hide img_hidden"><br>해시태그</label><input
									class="img_hide img_hidden" type="text" name="hashtag"><br>
								<label class="img_hide img_hidden">회원태그</label><input
									class="img_hide img_hidden" type="text" name="membertag"><br>
							</div>
							<button type="submit" class="post_submit_btn">작성</button>
						</form>
					</li>
					<% }%>
					<!-- 타입 선택 후 끝 -->
					<%
						PostDao pdo = PostDao.getInstance();
							List<Post> postList = pdo.getBlogPostList(userid);
							if (postList == null) {
					%>
					<li class="infinite_scroll">
						<h3>등록된 글이 없습니다</h3>
					</li>
					<%
						} else {
								System.out.println(postList.size() + "개의 포스트가 있음");
								for (Post p : postList) {
									AlertDao ado = AlertDao.getInstance();
									ado.markReadPost(userid, p.getPid());
					%><li class="infinite_scroll">
						<%
							if (p.getType() == 0) { // 텍스트 타입
						%>
						<h3><%=p.getUserid()%></h3>
						<hr>
						<h3><%=p.getText()%></h3> <%
 	} else if (p.getType() == 1) { // 사진타입
 %>
						<h3><%=p.getUserid()%></h3>
						<hr>
						<h3><%=p.getText()%></h3> <img src="upload/<%=p.getUrl()%>"
						style="height: 200px; width: 50%; display: inline;"> <%
 	} else if (p.getType() == 2) {
 %> <video width="320" height="240" controls>
							<source src="upload/<%=p.getUrl()%>" type="video/mp4">
							<source src="upload/<%=p.getUrl()%>" type="video/ogg">
							<source src="upload/<%=p.getUrl()%>" type="video/mp4">
						</video> <%
 	}
 %><div class="reactBtn">
							<div class='heart'></div>
							<div class="share_out" onclick="openLayer('layerPop',200,18)"></div>
						</div>
						<div class="commentForm">
							<textarea rows="1" cols="1" name="text" placeholder="댓글쓰기"
								class="comment_textarea"></textarea>
						</div>
					</li>
					<%
						}
							}
					%>
				</ol>
			</div>
			<!-- 포스트 뷰 끝 -->
		</div>
	<jsp:include page="timelineAside.jsp"></jsp:include>
						<!-- <input id="FKKK" type="submit" value="나가기" style="width: 112px; float: left; height: 26px;"> -->
	</div>
	<!-- 내용 들어갈 부분 끝 -->
</body>
</html>