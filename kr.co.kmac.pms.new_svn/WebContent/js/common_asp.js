//공백체크함수
		//************************************************************************************************************************************************
		function IsEmpty(data) {
			for (var i=0;i<data.length;i++) {
			if(data.substring(i,i+1) != " ")
					
				return false;
			}
			return true;
		}
		//************************************************************************************************************************************************

	
		//텍스트박스 체크
		//************************************************************************************************************************************************
		function TextCheck(p,o,m,r){//p:폼이름 o:객체,m:메세지,r:리턴값											
			var f=eval("document."+p)//폼정의								
		//	alert(eval("f."+o+".value"));
			if(IsEmpty(eval("f."+o+".value"))){
				alert(m);
				eval("f."+o+".focus()");
				if(r==1) return true;					
			}
		}
		//************************************************************************************************************************************************
				
	
		//텍스트에어리어 체크
		//************************************************************************************************************************************************
		function TextareaCheck(p,o,m,r){//p:폼이름 o:객체,m:메세지,r:리턴값								
			var f=eval("document."+p)//폼정의								
			var v=eval("f."+o+".value");
			v=v.replace(/\r\n/g,"")			
			
			if(IsEmpty(v)){
				alert(m);
				eval("f."+o+".focus()");				
				if(r==1) return true;					
			}
		}
		//************************************************************************************************************************************************
		
					

		//같은지 체크	// 비밀번호 확인시
		//************************************************************************************************************************************************
		function SameCheck(p,o1,o2,m,r){
			var f=eval("document."+p); 	
			var val1=eval("f."+o1+".value");
			var val2=eval("f."+o2+".value");		
			
			if(val1!=val2){
				alert(m);
				eval("f."+o2+".focus()");
				if(r==1) return true;
			}
		}
		//************************************************************************************************************************************************
		
		
		//글자수체크함수
		//************************************************************************************************************************************************
		function WordLengthChk(p,o,m,n,r) {//f:폼이름 o:객체,m:메세지,n:기준값,r:리턴값
			var f=eval("document."+p);
			var val=eval("f."+o+".value");
			var c=0;
			val=val.replace(/\r\n/g,"");				
			for (var i=0; i<val.length; i++) {
				if(val.substring(i,i+1) != " "){
					c++;					
				}
			}				
			if(parseInt(c)<parseInt(n)){
				alert(m);//메세지
				eval("f."+o+".focus()");//포커스
				return true;//리턴결과
			}
		}
		//************************************************************************************************************************************************

		
		//confirm 메세지
		//************************************************************************************************************************************************
		function Confirm_msg(m){
			if(confirm(m)){
				return true;
			}					
		}
		//************************************************************************************************************************************************

		//체크박스|| 체크(배열일때)
		//************************************************************************************************************************************************
		function CheckboxCheck(p,o,m,r){//f:폼이름 o:객체,m:메세지,r:리턴값	
			var f=eval("document."+p); 				
			var v=0				
			for(var i=0;i<eval("f."+o+".length");i++){
				if(eval("f."+o+"["+i+"].checked")){					
					v="1";
					break;
				}
			}									
			if(v=="0"){
				alert(m);
				eval("f."+o+"[0].focus()");
				return true;
			}
		}
		//************************************************************************************************************************************************
	
		//셀렉트박스 체크
		//************************************************************************************************************************************************
		function SelectCheck(p,o,m,r){//p:폼이름 o:객체,m:메세지,r:리턴값				
			var f=eval("document."+p);						
			if(eval("f."+o+".value")==""){					
				alert(m);
				eval("f."+o+".focus()");
				return true;
			}				
		}
		//************************************************************************************************************************************************



	// 셀렉트박스 기타처리		
function div_view(obj, Value,Cnt){
		
	if(Value==Cnt){
			eval('document.all.'+obj+'_div.style').display='';
			}
	else
		{
			eval('document.all.'+obj+'_div.style').display='none';
			eval('document.frmVote.'+ obj +'_txt').value="";
		}
	}
	
	
function GetChecked(idx,cnt){
		var rtn = '';
		for(i=0;i<cnt;i++)
			{
				if(eval('document.frmVote.'+ idx +'['+i+']').checked){
							rtn	= rtn + 'c';
						}
			}		
		return rtn;
	}
	
	
function TextReturn(idx){
		var rtn = '';
		 
				if(eval('document.frmVote.'+ idx ).value!=""){
							rtn	= rtn + eval('document.frmVote.'+ idx ).value ;
						}
		 
		return rtn;
	}	
	
// 샘플	
	function chk_cnt(Val,Obj,msg,Cnt){
		var f = document.frmVote ;
		var objCnt = eval('f.'+Obj+'.length');
	
		var count=0;
				for(i=0;i<objCnt;i++){
					if(eval('f.'+Obj+'['+i+'].checked')){
							count++;
							}			
					}
				if(count>Cnt)
					{
						alert(msg);
						eval('f.'+Obj)[Val-1].checked=false;
					}
}


	function focusMove(p, len, obj1, obj2){
		var f=eval("document."+p)//폼정의								
	 	var mini_txt = eval("f." + obj1);
		if ( mini_txt.value.length >= len ){
			var mini_txt2 = eval("f." + obj2);
			mini_txt2.focus();
		}
	}
	
	
	function goNav(val) {
	form = document.frmRegist
	intPage = parseInt(form.intPage.value)
	newPage = parseInt(eval("intPage" + val + "1"))
//	form.action="<%= thisPage %>";
	form.intPage.value = newPage
	form.submit()
}
function goNum(val) {
	form = document.frmRegist
//	form.action="<%= thisPage %>"
	form.intPage.value = val
	form.submit()
} 	


function row_over(obj){
	
	obj.className = "listTableMouseOver";
}

function row_out(obj){
	obj.className = "";
}

	//텍스트박스 체크
		//************************************************************************************************************************************************
		function TextCheckArray(p,o,m,r){//p:폼이름 o:객체,m:메세지,r:리턴값											
			var f=eval(p)//폼정의								
			if(IsEmpty(eval(o+".value"))){
				alert(m);
				eval(o+".focus()");
				if(r==1) return true;					
			}
		}
		//************************************************************************************************************************************************

function fn_datepicker(id) {
	$('#' + id).focus();
}				 


//  div 보이기,말기
function view1(obj,mode){
	 for(i=0;i<eval('document.all.'+obj+'.length');i++){
   eval('document.all.'+obj+'[i].style').display=mode;
   }
	}

function view2(obj,mode){
	//	alert(mode);
   eval('document.all.'+obj+'.style').display=mode;
	}

function view3(obj,mode,obj1,val){
	if(eval('document.all.'+obj1+'[val-1].checked')==false){
	 for(i=0;i<eval('document.all.'+obj+'.length');i++){
   eval('document.all.'+obj+'[i].style').display=mode;
   }
	}
	else
		{
			 for(i=0;i<eval('document.all.'+obj+'.length');i++){
		   eval('document.all.'+obj+'[i].style').display='none';
		   }
		}
}

function setCookie( name, value, expiredays ) {
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}

function getCookie( name ){
	var nameOfCookie = name + "=";
	var x = 0;
	while ( x <= document.cookie.length ){
		var y = (x+nameOfCookie.length);
		if ( document.cookie.substring( x, y ) == nameOfCookie ) {
			if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
			endOfCookie = document.cookie.length;
			return unescape( document.cookie.substring( y, endOfCookie ) );
			}
		x = document.cookie.indexOf( " ", x ) + 1;
		if ( x == 0 )
		break;
		}
		return ;
		}
		
function notice_setCookie( name, value, expiredays )
{
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}

function notice_closeWin(eventName, uid){	 
/*	if ($("input:checkbox[id='Notice']").is(":checked") == true) {		 
		notice_setCookie( eventName, "done" , 1); // 1=하룻동안 공지창 열지 않음
		self.close(); 
	}
	
	$.ajax({ 
            type:"POST",
						url:"chk_popup.asp",
						data : { "eventname" : eventName,"uid" : uid },
						async: false,          
						dataType:"json",		 									 
            success: function (json.sendResult) {  
							 self.close(); 
					  },
            error: function (request, status, error) {
                   //alert("[오류] 서버 장애로 인하여 정보를 가져오지 못했습니다.");
                  // alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
	});	 
	
*/	
	$.ajax({
			type:"POST",
			url:"chk_popup.asp",
			data : { "eventname" : eventName, "uid": uid} ,
			async: false,          
			dataType:"json",
			error:function(){
				alert('【 오 류 】: 데이터 수신중 오류가 발생 하였습니다.');
			},
			success:function(json){					
				 self.close(); 
			}
		});
	
}

	//텍스트박스 체크
		//************************************************************************************************************************************************
		function TextCheck_M(p,o,m,r){//p:폼이름 o:객체,m:메세지,r:리턴값											
			var f=eval("document."+p)//폼정의								
		//	alert(eval("f."+o+".value"));
			if(IsEmpty(eval("f."+o+".value"))){
				modalAlert(m);
				eval("f."+o+".focus()");
				if(r==1) return true;					
			}
		}
		//************************************************************************************************************************************************
				
		
		//체크박스|| 체크(배열일때)
	      //************************************************************************************************************************************************
	      function CheckboxCheck_M(p,o,m,r){//f:폼이름 o:객체,m:메세지,r:리턴값   
	         var f=eval("document."+p);             
	         var v=0            
	         for(var i=0;i<eval("f."+o+".length");i++){
	            if(eval("f."+o+"["+i+"].checked")){               
	               v="1";
	               break;
	            }
	         }                           
	         if(v=="0"){
	            modalAlert(m);
	            eval("f."+o+"[0].focus()");
	            return true;
	         }
	      }
	      
	      function modalAlert(msg){ 
	          $.ajax({ 
	              type:"POST",
	                   url : "/kmac/common/func/modal_alert.asp",
	                   data : { "msg" : escape(msg)} ,
	                   async: false,          
	                   dataType:"text",                               
	                   contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
	              success: function (content) {  
	                      $("#comment").html(content);      
	                  },
	              error: function (request, status, error) {
	                     alert("[오류] 서버 장애로 인하여 정보를 가져오지 못했습니다.");
	                     alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	              }
	                });     
	       }
	      
	      function modalComfirm(msg, Mode){ 
	          $.ajax({ 
	              type:"POST",
	                   url : "/kmac/common/func/modal_confirm.asp",
	                   data : { "msg" : escape(msg),"Mode" : Mode} ,
	                   async: false,          
	                   dataType:"text",                               
	                   contentType: 'application/x-www-form-urlencoded; charset=euc-kr',
	                    success: function (content) {  
	                      $("#comment").html(content);      
	                  },
	                    error: function (request, status, error) {
	                     alert("[오류] 서버 장애로 인하여 정보를 가져오지 못했습니다.");
	                     alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	                    }
	                });     
	       }