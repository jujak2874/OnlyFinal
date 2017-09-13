$(function() {
	$(".heart").off('click').on('click', function(e) {
				var pid = e.target.id.split("-");
				console.log("like 눌림" + pid[1]);
				var sendData = "pid=" + pid[1];
				$.post("likes.jsp", sendData, function(data) {
					if(data.indexOf("true")>0){
						console.log("toggle Like Class");
						$(e.currentTarget).toggleClass("on");
					}
				});
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
		var sendData = "chatRoom=" + $(".send").attr("data-chatroom");
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
			$("#chatRoomDisplay").scrollTop($("#chatRoomDisplay")[0].scrollHeight);
			$(".chat").val("");
		});
	});
}

