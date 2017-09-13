<%@page import="dao.MemberDao"%>
<%@page import="dto.ChatMessage"%>
<%@page import="dao.ChatDao"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
	<span>
	<%
			System.out.println("getChat.jsp called " + request.getParameter("chatRoom"));
			List<ChatMessage> chatMessages = new ArrayList<>();
			ChatDao dao = ChatDao.getInstance();
			String sendT = (String) session.getAttribute("sessionId");
			String recvT = "";
			chatMessages = dao.chatload(request.getParameter("chatRoom"));
			String[] participants= ((String) request.getParameter("chatRoom")).split(":");
			if(participants[0].equals(sendT)){
				recvT = participants[1];
			} else{
				recvT = participants[0];
			}
			System.out.println("recvT: " + recvT);
			MemberDao md = MemberDao.getInstance();
			String revcProfileImage = "."+md.getMember(recvT).getProfile_image();
			if (chatMessages.size() == 0) {
				System.out.println("보여줄 채팅내용 없음");
			} else{
				for (ChatMessage cm : chatMessages) {
					if(cm.getUserid().equals((String) session.getAttribute("sessionId"))){
						%>
						<div class="sentMsg">
							<div class="chatMessage"><%=cm.getMessage() %></div>
						</div>
	<%
					}else{
						if(cm.getStatus()==0){
							dao.readMessage(cm.getMid());
						}
						%><div class="receivedMsg">
							<div class="chatMessage"><%=cm.getMessage() %></div>
						</div>
	<%						
					}
				}
				%>
				<%
			}
		%>
		</span>
</body>
</html>