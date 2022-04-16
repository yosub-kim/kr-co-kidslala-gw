j$(document).ready(function(){
	j$(".depth2").hide();
	

	j$('.gnb li').on('mouseenter focusin', function(){
		j$(this).children(".depth2").show();
		
		
	});

	j$('.gnb li').on('mouseleave focusout',function(){
		j$(this).children(".depth2").hide();
	
		
	});



	j$(".leftMenuList a").click(function(){
		j$(".leftMenuList a").removeClass("on");
		j$(this).addClass("on");
	
	});

})
