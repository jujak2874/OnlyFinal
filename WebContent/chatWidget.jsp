<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	console.log("세션불러오기실패");
	location.href = "signUpForm.jsp";

	<script type="text/javascript">
	$(function() {
		$("#send").click(function() {
			if ($("#chat").val() == "") {
				alert("메시지를 입력하세요");
				return;
			}
			console.log($("#chat").val());
			var chatroom = $("#send").attr("data-room");
			var getT = $("#send").attr("data-gett");
			var sendT = $("#send").attr("data-sendt");
			console.log(getT + ", " + sendT);

			var sendData = {
				chat : $("#chat").val(),
				sendT : sendT,
				getT : getT
			};
			$.post("chatsave.jsp", sendData, function(data) {
				document.getElementById("chat").value = "";
				/* setTimeout(function() {
					$("#placeI").scrollTop($("#placeI")[0].scrollHeight)
				}, 500);
				webStart(); */

				/* var sendData = "chatRoom=" + chatroom;
				$.post("getChat.jsp", sendData, function(data) {
				}); */
			});
		});

		$("#FKKK").bind("click", function() {
			$("#placeI").addClass("chatListToggle");

		});
	});
</script>
<body>
	<div id="placeI" class="chatListToggle">
		<div class="aside_chat">
			<div id="chatRoomDisplay" max-height="380px"></div>
			<div id="chatRoomInput">
				<input id="chat" type="text" name="chat"
					style="float: left; width: 225px; height: 20px;"> <input
					id="send" type="submit" value="보내기"
					style="float: left; height: 26px; width: 113px;"> <input
					id="FKKK" type="submit" value="나가기"
					style="width: 112px; float: left; height: 26px;">
			</div>
		</div>
	</div>
</body>
</html>