$(function() {
	$(".chatStart").click(function(e) {
				console.log("chatroomid: " + $(".chatStart").attr("data-getT"));
				var chatroomid = e.target.id;
				var sendData = "chatRoom=" + chatroomid;
				$("#chatRoomDisplay").attr("data-currentroom", chatroomid);
				$.post("getChat.jsp", sendData, function(data) {
					var start = data.indexOf('<span>');
					var end = data.indexOf('</span>');
					var result = data.slice(start + 6, end);
					//console.log("chat" + result);
					
					var sendData2 = "member_id=" + e.target.getAttribute("data-getT");
					$.post("getMember.jsp", sendData2, function(data2){
						var start2 = data2.indexOf('<body>');
						var end2 = data2.indexOf('</body>');
						var result2 = data2.slice(start2+6, end2);
						var memberid = result2.substr(0, result2.indexOf(":"));
						var profileImgUrl = "."+result2.substr(result2.indexOf(":")+1);
						$(".chatProfileImg").attr("src", profileImgUrl);
						$(".chatUserName").text(memberid);
												
					});
					
					$.post("updateNotification.jsp", {
						type : "chat"
					}, function(data) {
						updateMessageNotification(data.trim());
					});
					
					$(".chat").attr("id", "chat-" + chatroomid);
					$(".send").attr("id", "send-" + chatroomid);
					$(".send").attr("data-chatRoom", chatroomid)
					$(".send").attr("data-getT",
							e.target.getAttribute("data-getT"));
					$(".send").attr("data-sendT",
							e.target.getAttribute("data-sendT"));
					$(".FKKK").attr("id", "FKKK-" + chatroomid);
					$("#placeI").show();
					$(".chatProfile").show();
					$("#chatRoomInput").show();
					$("#chatRoomDisplay").html(result);
					$("#placeI").scrollTop(
							$("#placeI")[0].scrollHeight);
					$("#chatRoomDisplay").scrollTop(
							$("#chatRoomDisplay")[0].scrollHeight);

				});
			});
/*
 * $(".chatStart").click(function(e) { alert("[roomid]: " + e.target.id); var
 * chatroomid = e.target.id; var sendData = "chatRoom=" + chatroomid;
 * $.post("getChat.jsp", sendData, function(data) { var start = data.indexOf('<span>');
 * var end = data.indexOf('</span>'); var result = data.slice(start + 6, end);
 * console.log("chat" + result); $(".chat").attr("id", "chat-" + chatroomid);
 * $(".send").attr("id", "send-" + chatroomid); $(".send").attr("data-chatRoom",
 * chatroomid) $(".send").attr("data-getT", e.target.getAttribute("data-getT"));
 * $(".send").attr("data-sendT", e.target.getAttribute("data-sendT"));
 * $(".FKKK").attr("id", "FKKK-" + chatroomid); $("#placeI").show();
 * $("#chatRoomDisplay").html(result); $("#chatRoomDisplay").scrollTop(
 * $("#chatRoomDisplay")[0].scrollHeight);
 * 
 * }); });
 */

	$('#follow').click(function() {
		var userid = $(this).data("followid");
		var followid = $(this).attr("data-followid");
		var sendData = "userid2=" + userid;
		$.post('follow.jsp', sendData, function(data) {
			var start = data.indexOf('<body>');
			var end = data.indexOf('</body>');
			var followReturn = data.slice(start + 6, end).trim();
			alert(followReturn + " " + userid);
			if(window.location.href.indexOf('friendList.do')>=0){
				location.reload();
			}
		});
		
	});
	$(".send").off('click').on('click', function(e) {
				console.log("send 눌림");
				if ($(".chat").val() == "") {
					alert("메시지를 입력하세요");
					$(".chat").focus();
					return;
				}
				var sendData = {
					chat : $(".chat").val(),
					sendT : $(".send").attr("data-sendT"),
					getT : $(".send").attr("data-getT")
				};

				$.post("saveChat.jsp", sendData, function(data) {
					sendChat(JSON.stringify({
						type : "chat",
						message : $(".send").attr("data-chatroom"),
						from : $(".send").attr("data-sendT"),
						to : $(".send").attr("data-getT")
						}));
					var sendData = "chatRoom="
							+ $(".send").attr("data-chatroom");
					$.post("getChat.jsp", sendData, function(data) {
						var start = data.indexOf('<span>');
						var end = data.indexOf('</span>');
						var result = data.slice(start + 6, end);
						//console.log("chat" + result);
						// $(".send").attr("data-getT",
						// e.target.getAttribute("data-getT"));
						// $(".send").attr("data-sendT",
						// e.target.getAttribute("data-sendT"));
						// $("#placeI").show();
						$("#chatRoomDisplay").html(result);
						$("#placeI").scrollTop($("#placeI")[0].scrollHeight);
						$("#chatRoomDisplay").scrollTop($("#chatRoomDisplay")[0].scrollHeight);
						$(".chat").val("");
					});
				});
			});

	$(".FKKK").click(function(e) {
		$("#chatRoomDisplay").attr("data-currentroom", "");
		$("#placeI").css('display','none');
		$(".chatProfile").css('display','none');
		$("#chatRoomInput").css('display','none');
	});
});

function enterKeyPressed() {
	console.log("enterkey눌림");
	if ($(".chat").val() == "") {
		alert("메시지를 입력하세요");
		$(".chat").focus();
		return;
	}
	var sendData = {
		chat : $(".chat").val(),
		sendT : $(".send").attr("data-sendT"),
		getT : $(".send").attr("data-getT")
	};

	$.post("saveChat.jsp", sendData, function(data) {
		sendChat(JSON.stringify({
			type : "chat",
			message : $(".send").attr("data-chatroom"),
			from : $(".send").attr("data-sendT"),
			to : $(".send").attr("data-getT")
			}));
		var sendData = "chatRoom="
				+ $(".send").attr("data-chatroom");
		$.post("getChat.jsp", sendData, function(data) {
			var start = data.indexOf('<span>');
			var end = data.indexOf('</span>');
			var result = data.slice(start + 6, end);
			//console.log("chat" + result);
			// $(".send").attr("data-getT", e.target.getAttribute("data-getT"));
			// $(".send").attr("data-sendT",
			// e.target.getAttribute("data-sendT"));
			// $("#placeI").show();
			$("#chatRoomDisplay").html(result);
			$("#placeI").scrollTop($("#placeI")[0].scrollHeight);
			$("#chatRoomDisplay").scrollTop($("#chatRoomDisplay")[0].scrollHeight);
			$(".chat").val("");
		});
	});
}

function chat_reload(chatroom){
	console.log("chat_reload()불림");
	if($("#chatRoomDisplay").attr("data-currentroom")==chatroom){
		  // 해당 채팅창이 열려있으면
		  var sendData = "chatRoom=" + chatroom;
		  $.post("getChat.jsp", sendData, function(data) {
				var start = data.indexOf('<span>');
				var end = data.indexOf('</span>');
				var result = data.slice(start + 6, end);
				//$("#placeI").show();
				$("#chatRoomDisplay").html(result);
				$("#placeI").scrollTop($("#placeI")[0].scrollHeight);
				$("#chatRoomDisplay").scrollTop($("#chatRoomDisplay")[0].scrollHeight);
			});
	}
}
