// textarea focus일 때 작성폼 열기
$(function() {
	$('.type_choice_textarea').on('focus', function() {
		$(this).css("height", "150px");
		$('.img_hide').removeClass('img_hidden');
	});
	$('.type_choice_textarea').on('blur', function() {
		if ($('textarea[name="text"]').val() == '') {
			$(this).css("height", "30px");
			$('.img_hide').addClass('img_hidden');
		}
	});
});
// textarea focus일 때 작성폼 열기 끝
// infinite scroll 구현
/*var page = 4;
$(window)
		.scroll(
				function() {
					if ($(window).scrollTop()+$(window).height() == $(document).height()) {
						for(var z=0; z<6; z++){
						$('.post_view_box')
								.append(
										'<li class="infinite_scroll">'
										+'<h3>'
										+ page++
										+'번포스트</h3>'
										+'<hr> 테스트<br>테스트<br>테스트<br>테스트<br>테스트<br>테스트<br>테스트<br>'
										+'<hr>'
										+'<div class="reactBtn">'
										+"<div class='heart'></div>"
										+'<div class="share_out" onclick="openLayer("layerPop",200,18)"></div>'
										+'</div>'
										+'<div class="commentForm">'
										+'<textarea rows="1" cols="1" name="text" placeholder="댓글쓰기" class="comment_textarea"></textarea>'
										+'</div>'
										+'</li>'
										)}
					}
				});*/
// infinite scroll 끝
// Like 버튼 활성화
/*$(function(){
	$("ol").on('click','.heart', function(){
		$(this).toggleClass('is_animating');
	});
	$("ol").on('animationend','.heart', function(){
		$(this).toggleClass('is_animating');
		$(this).toggleClass('bg-position');
	});
});*/
// Like 버튼 활성화 끝
// DimLayer 
function dEI(elementID){
	return document.getElementById(elementID);
}
// 레이어 팝업 열기
function openLayer(IdName, tpos, lpos){
	var pop = dEI(IdName);
	pop.style.top = tpos + "px";
	pop.style.left = lpos + "%";
	pop.style.display = "block";

	var wrap = dEI("wrapper");
	var reservation = document.createElement("div");
	reservation.setAttribute("id", "deemed");
	wrap.appendChild(reservation);
}
// 레이어 팝업 닫기
function closeLayer( IdName ){
	var pop = dEI(IdName);
	pop.style.display = "none";
	var clearEl=parent.dEI("deemed");
	var momEl = parent.dEI("wrapper");
	momEl.removeChild(clearEl);
}
// DimLayer 끝
// 글 내용 없을 때 작성버튼 비활성화
$(function(){
	if($('.type_choice_textarea').val()==''){
		$('post_submit_btn').prop('disabled',true);
		$('post_submit_btn').css('opacity',0.5); 
	}else{
		$('post_submit_btn').prop('disabled',false);
		$('post_submit_btn').css('opacity',1); 
	}
});
// 글 내용 없을 때 작성버튼 비활성화 끝