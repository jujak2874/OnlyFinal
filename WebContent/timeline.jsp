<!--                                                                                                     
                                                                                                    
                                                                      
                                                                      
                                                                      
                                                                      
                                                                      
         KeeeeD                             eee                       
       eeeeeeeeeee                         Xee                        
     eeeK       eee                        eee                        
    eee          eeD                       ee                         
   ee#           eeE    ee     yee        9e9        ee      Xee      
  eee            ee     ee   #eeee        ee        ee5      ee       
  ee            eee     ee  eeW ee       eee       Ee#      ,ee       
 eee           eee     5e  ee   ee     9eeee       ee       ee        
 eee          eee      ee,ee    ee   Eee  ee     zeee    GeeeX        
  eey      zeee        eeeK     eeeeeeW   ee   Xee eeeeeee ee         
   eeeeeeeeee          ee                 eeeeeeD   Xeeu   e#         
    yeeee9                                 5zD            ee          
                                                         ee,          
                                                         ee           
                                                        eeE           
                                                                      
                                                                      
                                                                      
         
                                                                                                    
                                                                                                    

 -->
<%@page import="dto.Hashtag"%>
<%@page import="dto.Member"%>
<%@page import="dao.LikeDao"%>
<%@page import="dto.Comment"%>
<%@page import="dao.CommentDao"%>
<%@page import="dao.AlertDao"%>
<%@page import="java.util.List"%>
<%@page import="dao.PostDao"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="dto.Post"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="sessionChk.jsp"%>
<%!boolean isset(String str) {
		if (str == null) {
			return false;
		}
		if (str.equals("")) {
			return false;
		}
		return true;
	}%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.2.1.js"></script>
<script type="text/javascript" src="js/timeline.js"></script>
<script type="text/javascript" src="js/chat.js"></script>
<script type="text/javascript" src="js/likes.js"></script>
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
	if (!isset((String) session.getAttribute("sessionId"))) {
%>
<%
	} else {
		System.out.println("세션받아옴");
		String userid = (String) session.getAttribute("sessionId");
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
		<div class="timeline_post">
			<div class="page_margin"></div>
			<!-- 테스트 -->
			<!--  -->
			<div id="main_container">
				<ol class="post_view_box">
					<li class="type_choice_box" id="infinite_container">
						<form action="postWrite.do" method="post"
							enctype="multipart/form-data"
							onsubmit='return sendChat(JSON.stringify({type:"post",from:"<%=id%>"}));'>
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
									accept="image/png, image/gif, image/jpg">
								</a> <a class="choice_type"> <img
									src='img_timeline/video-camera.svg'
									class="img_hide img_hidden img_video" width="30px" border='0'
									onclick='document.all.videoUpload.click();'> <input
									type="file" name="videoUpload" style='display: none;'
									accept="video/mp4">
								</a> <label class="img_hide img_hidden"><br>해시태그</label><input
									class="img_hide img_hidden" type="text" name="hashtag"><br>
								<label class="img_hide img_hidden">회원태그</label><input
									class="img_hide img_hidden" type="text" name="membertag"><br>
							</div>
							<button type="submit" class="post_submit_btn">작성</button>
						</form>
					</li>
					<!-- 타입 선택 후 끝 -->
					<%
						PostDao pdo = PostDao.getInstance();
							List<Post> postList = pdo.getTimelinePostList(userid);
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
						class="postImg"> <%
 	} else if (p.getType() == 2) {
 %>
						<h3><%=p.getUserid()%></h3>
						<hr> <video class="postVideo" controls>
							<source src="upload/<%=p.getUrl()%>" type="video/mp4">
							<source src="upload/<%=p.getUrl()%>" type="video/ogg">
							<source src="upload/<%=p.getUrl()%>" type="video/mp4">
						</video> <%
 	}
 %><div class="reactBtn">
 							<%
 								LikeDao ldo = LikeDao.getInstance();
 								String likeStatus = ldo.checkLikeStatus(userid, p.getPid());
 								int likeCount = ldo.getLike(p.getPid());
 								if(likeStatus.equals("y")){
 									%>
 									<div class='heart on' id="heart-<%=p.getPid()%>"><span><%=likeCount %></span></div>			
 									<%
 								} else{
 									%>
			 						<div class='heart' id="heart-<%=p.getPid()%>"><span><%=likeCount %></span></div>
 									<%
 									
 								}
 							%>
							<div class="share_out" onclick="openLayer('layerPop',200,18)"></div>
						</div>
						<% 
							List<Hashtag> hList=pdo.getHashtagList(p.getPid());
							if(hList.size()!=0){
						%>
								<div>
						<%		for(Hashtag ht: hList){										
						%>
							<span style="font-weight:bold; color:lightblue;">#<%=ht.getTag_id()%> </span>
						
						<%
								}
						%>
							</div>
						<%
							}
						%>
						<%
							List<Member> mList=pdo.getMembertagList(p.getPid());
							if(mList.size()!=0){
						%>
							<div class="displayMemberTag">
						<%
								for(Member m: mList){
						%>
							<span style="font-weight:bold; color:lightpink;"><%=m.getUserid() %> </span>
						<%
								}
						%></div><%
							}
						%>
						<form action="commentWrite.do">
							<div class="commentForm">
								<input type="hidden"
									value="<%=(String) session.getAttribute("sessionId")%>"
									name="userid">
								<%
									CommentDao cdo = CommentDao.getInstance();
												List<Comment> cList = cdo.commentView(p.getPid());
								%>
								<textarea rows="1" cols="1" name="commentText"
									placeholder="댓글쓰기" class="comment_textarea"></textarea>
								<button class="commentBtn">입력</button>
								<input type="hidden" value="<%=p.getPid()%>" name="commentPid">
							</div>
						</form>
						<div class="commentView">
							<%
								for (Comment c : cList) {
												System.out.println(c.getText());
							%>
							<hr>
							<div class="commentSpace">
								<span class="commentViewMid"> 
									<%=c.getUserId()%>
								</span> <span class="commentViewCre"> 
									<%=c.getCreated()%>
								</span> <br> <span class="commentViewText"> 
									<%=c.getText()%>
								</span>
							</div>
							<br>
							<%
								}
							%>
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
	<%
		}
	%>
</body>
</html>