var userid = 0;

$(document).ready(
		function() {
			
			
			/* 검색결과 오른쪽 마우스 이벤트 등록 */
			if ($(".test").addEventListener) {
			/*
			 * $(".test").addEventListener('contextmenu',
			 * function(e) { e.preventDefault(); alert("You've tried
			 * to open context menu"); var userid =
			 * $(".test").attr("data-userid");
			 * document.getElementById("rmenu"+userid).className =
			 * "show";
			 * document.getElementById("rmenu"+userid).style.top =
			 * mouseY(event) + 'px';
			 * document.getElementById("rmenu"+userid).style.left =
			 * mouseX(event) + 'px'; }, false);
			 */
				} else {
					// document.getElementById("test").attachEvent('oncontextmenu',
					// function() {
					$(".test").bind('contextmenu',function(e) {
						var _target = e.target.parentNode.id;
						_target = _target.substr(5);
						var followId = "#followText-"+_target;
						/*console.log(_target);*/
						var sendData = "userid2=" + _target;
						$.post('followChk.jsp', sendData, function(data) {
							console.log("data true: "+ data.indexOf("true"));
							console.log("data false: "+ data.indexOf("false"));
							if (data.indexOf("true") > 0) {
								console.log("unfollow");
								$(followId).text("Unfollow");
								} else if (data.indexOf("false") > 0) {
									console.log("follow");
									$(followId).text("Follow");
									}
							});
						e.preventDefault();
						var rmenu = document.getElementsByName("rmenu");
						var i;
						for (i = 0; i < rmenu.length; i++) {
							// alert(rmenu[i].id);
							rmenu[i].className = "hide";
							}
						/*console.log("rmenu-"+_target);*/
						document.getElementById("rmenu-"+ _target).className = "show";
						document.getElementById("rmenu-"+ _target).style.top = mouseY(event)+ 'px';
						document.getElementById("rmenu-"+ _target).style.left = mouseX(event)+ 'px';
						});
					}

					/*
					 * function sendId() {
					 * 
					 * 
					 * function displayResult(data) { var listView =
					 * document.getElementById('checkMsg'); if (data == 0) {
					 * console.log("사용가능"); listView.innerHTML = "사용할 수 있는
					 * ID에요"; listView.style.color = "#4a76b2";
					 * $('#signup_fin').prop('disabled', false);
					 * $('#signup_fin').css('background-color', '#97a6bf');
					 * $('#signup_fin').mouseover(function() {
					 * $(this).css('background-color', '#3f5068'); });
					 * $('#signup_fin').mouseout(function() {
					 * $(this).css('background-color', '#97a6bf'); });
					 * btnDisabled(); } else { console.log("사용불가");
					 * listView.innerHTML = "이미 다른분이 사용중인 ID에요";
					 * listView.style.color = "#ff4242";
					 * $('#signup_fin').prop('disabled', true);
					 * $('#signup_fin').css('background-color', '#ff7070'); } }
					 */
			});

// this is from another SO post...
$(document).bind("click", function(event) {
	/* document.getElementById("rmenu").className = "hide"; */
	var rmenu = document.getElementsByName("rmenu");
	var i;
	for (i = 0; i < rmenu.length; i++) {
		// alert(rmenu[i].id);
		rmenu[i].className = "hide";
	}
});

function mouseX(evt) {
	if (evt.pageX) {
		return evt.pageX;
	} else if (evt.clientX) {
		return evt.clientX
				+ (document.documentElement.scrollLeft ? document.documentElement.scrollLeft
						: document.body.scrollLeft);
	} else {
		return null;
	}
}

function mouseY(evt) {
	if (evt.pageY) {
		return evt.pageY;
	} else if (evt.clientY) {
		return evt.clientY
				+ (document.documentElement.scrollTop ? document.documentElement.scrollTop
						: document.body.scrollTop);
	} else {
		return null;
	}
}

// 검색 자동완성
var check = false;
var lastKeyword = '';
var loopSearchKeyword = false;
function runSearch() {
	if (check == false) {
		setTimeout("sendSearch();", 200);
		loopSearchKeyword = true;
	}
	check = true;
}
function sendSearch() {
	if (loopSearchKeyword == false) {
		return;
	}
	var keyword = $('.searchTerm').val();
	if (keyword == '') {
		lastKeyword = '';
	} else if (keyword != lastKeyword) {
		lastKeyword = keyword;
		if (keyword != '') {
			var params = "searchTerm=" + encodeURIComponent(keyword);
			console.log(params);

			$.ajax({
				url : "search.jsp",
				type : "POST",
				data : params,
				success : function(data) {
					var start = data.indexOf('<body>');
					var end = data.indexOf('</body>');
					var searchReturn = data.slice(start + 6, end);
					console.log(data);
					$("#searchResult").html(data);

				},
				error : function() {
					console.log("자동완성기능 에러");
				}
			});
		} else {
		}
	}
	setTimeout("sendSearch();", 200);
}


