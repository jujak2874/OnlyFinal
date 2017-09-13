<%@page import="service.Follow"%>
<%@page import="java.util.List"%>
<%@page import="dto.Member"%>
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
<script type="text/javascript" src="js/search.js"></script>
<script type="text/javascript" src="js/chat.js"></script>

<!-- <script type="text/javascript">
	$(function() {
		$('.follow').click(function() {
			//var userid =  $(this).data("followid");
			//var followid =  $(this).attr("data-followid");
			alert(userid);
			var sendData = "userid2=" + userid;
			console.log(userid);
			$.post('follow.jsp', sendData, function(data) {
				//$('#disp').html(data);
			});
		});

		$(".chatStart").click(
				function(e) {
					alert("chatroomid: " + $(".chatStart").attr("data-getT"));
					var chatroomid = e.target.id;
					var sendData = "chatRoom=" + chatroomid;
					$.post("getChat.jsp", sendData, function(data) {
						var start = data.indexOf('<span>');
						var end = data.indexOf('</span>');
						var result = data.slice(start + 6, end);
						console.log("chat" + result);
						$(".chat").attr("id", "chat-" + chatroomid);
						$(".send").attr("id", "send-" + chatroomid);
						$(".send").attr("data-chatRoom", chatroomid)
						$(".send").attr("data-getT",
								e.target.getAttribute("data-getT"));
						$(".send").attr("data-sendT",
								e.target.getAttribute("data-sendT"));
						$(".FKKK").attr("id", "FKKK-" + chatroomid);
						$("#placeI").show();
						$("#chatRoomDisplay").html(result);
						$("#chatRoomDisplay").scrollTop(
								$("#chatRoomDisplay")[0].scrollHeight);

					});
				});
	});
</script> -->
<link rel="stylesheet" type="text/css" href="css/timelineFull.css">
<link rel="stylesheet" type="text/css" href="css/timelineMobile.css">
<link rel="stylesheet" type="text/css" href="css/search.css">
</head>
<body>

	<!-- 상단고정바 시작 -->
	<div id="header"><jsp:include page="header.jsp"></jsp:include></div>
	<!-- 상단고정바 끝 -->
	<div class="header_hidden"></div>
	<!-- 내용 들어갈 부분 -->

	<div class="search_main">
		<!-- 포스트작성 / 뷰 부분 -->
		<div class="search_post">
			<div class="page_margin"></div>
			<!-- 테스트 -->
			<!--  -->
			<ol class="search_view_box">
				<!-- 타입 선택 후 끝 -->
				<li><span style="color: #ffffff; font-weight: bold;">Search
						Result</span>
					<hr></li>
				<%
					if (request.getAttribute("searchResult") == null) {
				%>
				<li class="infinite_scroll"><h3>검색 결과가 없습니다</h3></li>
				<%
					} else {
						List<Member> list = (ArrayList) request.getAttribute("searchResult");
						for (Member member : list) {
							String chatroom = "";
							String sendT = (String) session.getAttribute("sessionId");
							String getT = member.getUserid();
							if (getT.compareTo(sendT) > 0) {
								chatroom = getT + ":" + sendT;
							} else {
								chatroom = sendT + ":" + getT;
							}
							System.out.println(chatroom);
				%>
				<li class="search_result">
					<div class="search_profile">
						<table width=100%>
							<tr>
								<td valign="middle" width="10%"><img alt=''
									src='<%=application.getContextPath() + (member.getProfile_image())%>'>
								</td>
								<td valign="middle" align="left"><a href="#" class="test"
									id="test-<%=member.getUserid()%>"><h3 onclick="location.href='<%=member.getUserid()%>.blog'"><%=member.getUsername()%></h3>

										<div class="hide" id="rmenu-<%=member.getUserid()%>"
											name="rmenu">
											<ul>
												<li><a href="<%=member.getUserid() %>.blog">Visit</a></li>
												<%if(sendT.equals("") || sendT==null || sendT.equals(getT)){
													System.out.println("본인 생략");
												} else{%>
												<li><a href="#" id="follow"
													data-followid="<%=member.getUserid()%>"><span
														id="followText-<%=member.getUserid()%>">Follow</span></a></li>
												<li><a href="#" class="chatStart <%=chatroom%>"
													id="<%=chatroom%>" data-sendT="<%=sendT%>"
													data-getT="<%=getT%>">Send Message</a></li>
												<%} %>

											</ul>
										</div> </a></td>
							</tr>
							<tr>
								<td colspan="2">
									<hr><%=member.getEmail()%><br> <%=member.getProfile_image()%><br>
									<%=member.getBirth()%><br>
									<hr>
								</td>
							</tr>
						</table>
					</div>
				</li>
				<%
					}
					}
				%>
			</ol>
			<!-- 포스트 뷰 끝 -->
		</div>
		<!-- 포스트작성 / 뷰 끝 -->
		<!-- aside 부분 / *팔로우 추천, 광고등 -->
		<jsp:include page="timelineAside.jsp"></jsp:include>
	</div>
	<!-- 내용 들어갈 부분 끝 -->
</body>
</html>