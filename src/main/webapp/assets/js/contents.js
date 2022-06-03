/* 레이아웃 height */
/*
$(document).ready(function(){
	setTimeout(function() {	
		var $win_Hei = $(window).height();
		var $tree_view = $(".tree_view");		
		var $tab_con = $(".contents .tab_wrap > .w2tabcontrol_container");

		$tree_view.css({"height":$win_Hei - 143 - 60});	
		$tab_con.css({"height":$win_Hei - 143 - 61});
		
	}, 100);
	
	
	$(window).resize(function() {
		var $win_Hei = $(window).height();
		var $tree_view = $(".tree_view");		
		var $tab_con = $(".contents .tab_wrap > .w2tabcontrol_container");

		$tree_view.css({"height":$win_Hei - 143 - 60});	
		$tab_con.css({"height":$win_Hei - 143 - 61});
	});	
});*/
/* 레이아웃 height -2020.05.30 */
$(document).ready(function(){
	
	setTimeout(function() {	
		var $win_Hei = window.innerHeight;	
		var $tree_view = $(".tree_view");
		var $tab_con = $(".contents .tab_wrap > .w2tabcontrol_container");		
		
		$tree_view.css({"height":$win_Hei - 143 - 60});
		$tab_con.css({"height":$win_Hei - 143 - 31});		
	}, 500);
	
	
	$(window).resize(function() {
		var $win_Hei = window.innerHeight;
		var $tree_view = $(".tree_view");	
		var $tab_con = $(".contents .tab_wrap > .w2tabcontrol_container");	
		
		$tree_view.css({"height":$win_Hei - 143 - 60});	
		$tab_con.css({"height":$win_Hei - 143 - 31});
	});
	
	
/*	setTimeout(function() {	
		var $win_Hei = $(window).height();	
		var $tree_view = $(".tree_view");
		var $tab_con = $(".contents .tab_wrap > .w2tabcontrol_container");		
		
		$tree_view.css({"height":$win_Hei - 143 - 60});
		$tab_con.css({"height":$win_Hei - 143 - 61});		
	}, 500);
	
	
	$(window).resize(function() {
		var $win_Hei = $(window).height();
		var $tree_view = $(".tree_view");	
		var $tab_con = $(".contents .tab_wrap > .w2tabcontrol_container");	
		
		$tree_view.css({"height":$win_Hei - 143 - 60});	
		$tab_con.css({"height":$win_Hei - 143 - 61});
	});*/
	
	
});


/* GNB  */
function renderGNB() {
	$menu = $(".gnb .th1");		
	$active_th2 = $(".gnb .th1.current .th2");
	$subList = $(".th2");	

	$menu.bind("mouseenter focusin", function() {
		if(($(this).hasClass("current"))) {
			$active_th2.css({"height":"auto"});
		}else {
			$(this).addClass("on").siblings().removeClass("on");		
			$(this).find($subList).css({"height":"auto"});
			$active_th2.css({"height":"0"});
		}		
	});
	$menu.bind("mouseleave focusout", function() {
		$(this).removeClass("on");		
		$(this).find($subList).css({"height":"0"});
		$active_th2.css({"height":"auto"});
	});	
}

/* aside 열기 닫기 */
/*
$(function(){
	var $aside = $("#aside");
	var $contents = $(".contents");
	var $btn_clo = $(".btn_clo");	
	var asidetStats = true;

	$btn_clo.click(function (e) {
		e.preventDefault(e);
		if (asidetStats) {			
			$(this).addClass("on");
			$aside.css({"display":"none"});		
			$contents.addClass("on");
			asidetStats = false;
		}else {			
			$(this).removeClass("on");
			$aside.css({"display":"block"});
			$contents.removeClass("on")
			asidetStats = true;			
		}	
	});
});
*/
/* aside 열기 닫기 - 2020.05.30 */
/*$(function(){
	var $aside = $("#aside");
	var $contents = $(".contents");
	var $btn_clo = $(".btn_clo");	
	var asidetStats = true;

	$btn_clo.click(function (e) {
		e.preventDefault();
		if (asidetStats) {
			$aside.css({"display":"none"});		
			$contents.addClass("on");
			asidetStats = false;
		}else {	
			$aside.css({"display":"block"});
			$contents.removeClass("on")
			asidetStats = true;			
		}	
	});
});*/
/* aside 열기 닫기 - 2020.06.02 */
function asideArea(){
	var $aside = $("#aside");
	var $contents = $(".contents");
	var $btn_clo = $(".btn_clo");	
	var asidetStats = true;
	
	$contents.removeClass("on")

	$btn_clo.click(function (e) {
		e.preventDefault();
		if (asidetStats) {
			$aside.css({"display":"none"});		
			$contents.addClass("on");
			asidetStats = false;
		}else {	
			$aside.css({"display":"block"});
			$contents.removeClass("on")
			asidetStats = true;			
		}	
	});
}



/* lnb 
$(function(){
	$th1 = $(".lnb .th1>li>a"),
	$th2Group = $(".lnb .th2"),
	$th2 = $(".lnb .th2>li>a"),
	$th3 = $(".lnb .th3>li>a"),
	$th3Group = $(".lnb .th3");		
	
	$th1.click(function(e) {
		 e.preventDefault();
		 $(this).next($th2Group).slideToggle();
		 $(this).toggleClass("active");		
		 $(this).parent("li").siblings("li").children("a").removeClass("active");
		 $(this).parent("li").siblings("li").find($th2Group).slideUp();		
		
		 if(($(".lnb .th1>li>a").hasClass("active") === false ) && ($(".lnb .th1>li").hasClass("current") === false )) {		
			 $th3Group.slideUp();	
			 $th2.removeClass("active");
		}	
		
	});		

	$th2.click(function(e) {
		 e.preventDefault();	
		
		if ($(this).parent("li.current").length > 0){
			$(this).next(".th3").slideDown();
			$(this).addClass("active");
		}else{				
			$(this).next($th3Group).slideToggle();
			$(this).toggleClass("active");
			$(this).parent("li").siblings("li").children("a").removeClass("active");
			$(this).parent("li").siblings("li.current").children("a").addClass("active");			
			$(this).parent("li").siblings("li").not(".current").find($th3Group).slideUp();			
		};		
		
	});	
	 
	 if ($(".lnb .th3").length > 0 ) {		
		$(".lnb .th3").parent("li").addClass("under");		
	 };	 
	 
});

*/

/* Toggle */

$(function(){
	$toggle_btn = $(".toggle_btn");
	$toggle_con = $(".toggle_con");	
	var toggleStats = true;
	
	$toggle_btn.click(function(e){
		e.preventDefault();
		if (toggleStats) {
			$toggle_con.slideDown();
			$(this).addClass("open");
			toggleStats = false;
		}else {
			$toggle_con.slideUp();
			$(this).removeClass("open");
			toggleStats = true;			
		}	
	});
	 
});

$(window).load(function() {
	asideArea();
});	

//추가 : 이원학(2020.06.18)-마우스 우클릭 방지(헤더,푸터)
//$(document).mousedown(function(e) {
//	if( e.button == 2 ) {
//			alert('오른쪽 마우스는 사용할 수 없습니다.');
//			return false;
//			
//	} else {
//			return true;
//	}
//
//});




