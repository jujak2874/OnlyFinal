<%@page import="dto.ChatMessage"%>
<%@page import="dao.ChatDao"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="jquery-ui.css" />
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
	<script type="text/javascript" src="js/chat.js"></script>
	<span> <%
 	List<ChatMessage> chatRoomList = new ArrayList<>();
	String memberId = (String) session.getAttribute("sessionId");
 	ChatDao dao = ChatDao.getInstance();
 	chatRoomList = dao.chatRLoad(memberId);
 	if (chatRoomList.size() == 0) {
 %>No Chat History<%
 	} else {
 %><ul>
			<%
				for (ChatMessage cm : chatRoomList) {
					String chatroom = cm.getCid();
					String[] parseChatRoom = chatroom.split(":");
					String getT ="";
					for(String test: parseChatRoom){
						System.out.println("user: "+ test);
					}
					if(parseChatRoom[0].equals(memberId)){
						getT = parseChatRoom[1];
					} else{
						getT = parseChatRoom[0];
					}
			%><li><a class="chatStart <%=cm.getCid() %>" id="<%=cm.getCid() %>"
				data-sendT="<%=session.getAttribute("sessionId") %>" data-getT="<%=getT%>"><%=cm.getCid()%>||
					<%=cm.getMessage()%></a></li>

			<%
				}
			%>
		</ul> <%
 	}
 %>
	</span>
</body>
</html>