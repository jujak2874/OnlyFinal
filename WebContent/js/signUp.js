// 회원가입 로그인페이지 버튼 누를시 페이지 변경하게 하는 스크립트
$(function() {

	$('#signup-btn').on("click", function() {
		$("#signup").removeClass("s-atbottom");
		$("#signup").addClass("s-attop");
		$("#login").removeClass("l-attop");
		$("#login").addClass("l-atbottom");
		$("#member_id").focus();
	});

	$('#login-btn').click(function() {
		$("#signup").removeClass("s-attop");
		$("#signup").addClass("s-atbottom");
		$("#login").removeClass("l-atbottom");
		$("#login").addClass("l-attop");
		$("#login_inputId").focus();
	});
});
// 회원가입 로그인페이지 버튼 누를시 페이지 변경하게 하는 스크립트 끝

// 아이디 중복체크하는 스크립트
/*
 * function chk() { inputLogin = eval("document.loginForm"); if
 * (!inputLogin.member_id.value) { alert("아이디를 입력하세요");
 * inputLogin.member_id.focus(); return false; } if (!inputLogin.password.value) {
 * alert("비밀번호를 입력하세요"); inputLogin.password.focus(); return false; } }
 */

// 아이디 실시간 중복체크하는 스크립트
var check = false;
var lastKeyword = '';
var loopSendKeyword = false;
var allChk = 0;
function checkId() {
	if (check == false) {
		setTimeout("sendId();", 500);
		loopSendKeyword = true;
	}
	check = true;
}
function sendId() {
	if (loopSendKeyword == false) {
		return;
	}
	var keyword = $('#member_id').val();
	if (keyword == '') {
		lastKeyword = '';
		$('#checkMsg').css('color', 'red');
		$('#checkMsg').text("아이디를 입력하세요");
		console.log('아이디작성안하면작동');
	} else if (keyword != lastKeyword) {
		lastKeyword = keyword;
		if (keyword != '') {
			var params = "id=" + encodeURIComponent(keyword);
			console.log(params);
			$.post("id_check.jsp", params, function(data) {
				var start = data.indexOf('<li>');
				var end = data.indexOf('</li>');
				var kk = data.slice(start + 4, end);
				displayResult(kk);
				console.log(kk);
			});
			console.log("아이디작성하면작동");
		} else {
		}
	}
	setTimeout("sendId();", 500);
}

function displayResult(data) {
	var listView = document.getElementById('checkMsg');
	if (data == 0) {
		console.log("사용가능");
		listView.innerHTML = "사용할 수 있는 ID에요";
		listView.style.color = "#4a76b2";
		$('#signup_fin').prop('disabled', false);
		$('#signup_fin').css('background-color', '#97a6bf');
		$('#signup_fin').mouseover(function() {
			$(this).css('background-color', '#3f5068');
		});
		$('#signup_fin').mouseout(function() {
			$(this).css('background-color', '#97a6bf');
		});
		/* btnDisabled(); */
	} else {
		console.log("사용불가");
		listView.innerHTML = "이미 다른분이 사용중인 ID에요";
		listView.style.color = "#ff4242";
		$('#signup_fin').prop('disabled', true);
		$('#signup_fin').css('background-color', '#ff7070');
	}
}
// 아이디 실시간 중복체크하는 스크립트 끝

/*
 * function btnDisabled() { var inputNick = $('#nickname').val(); var inputMail =
 * $('#email').val();
 * 
 * if (inputNick != '') { $('#signup_fin').prop('disabled', false);
 * $('#signup_fin').css('background-color', '#97a6bf');
 * $('#signup_fin').mouseover(function() { $(this).css('background-color',
 * '#3f5068'); }); $('#signup_fin').mouseout(function() {
 * $(this).css('background-color', '#97a6bf'); }); } else {
 * $('#signup_fin').prop('disabled', true);
 * $('#signup_fin').css('background-color', '#ff7070'); } if (inputMail != '') {
 * $('#signup_fin').prop('disabled', false);
 * $('#signup_fin').css('background-color', '#97a6bf');
 * $('#signup_fin').mouseover(function() { $(this).css('background-color',
 * '#3f5068'); }); $('#signup_fin').mouseout(function() {
 * $(this).css('background-color', '#97a6bf'); }); } else{
 * $('#signup_fin').prop('disabled', true);
 * $('#signup_fin').css('background-color', '#ff7070'); } }
 */

// 랜덤배경
$(function() {
	var images = [ 'mainbg1.jpg', 'mainbg2.jpg', 'mainbg3.jpg', 'mainbg4.jpg',
			'mainbg6.jpg', 'mainbg7.jpg', 'mainbg8.jpg', 'mainbg9.jpg' ];
	$('body').css(
			{
				'background-image' : 'url(./img_all/'
						+ images[Math.floor(Math.random() * images.length)]
						+ ')'
			});
});
