var UserAgent = navigator.userAgent;
//alert(UserAgent);
var isMobile = "";
var isIE = "";
if (UserAgent.match(/iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null 
		|| UserAgent.match(/LG|SAMSUNG|Samsung/) != null){
	isMobile = true;
} else {
	isMobile = false;
}


if( navigator.appName.indexOf("Microsoft") > -1 
		|| navigator.appVersion.indexOf("MSIE 6") > -1 
		|| navigator.appVersion.indexOf("MSIE 7") > -1){ 
	isIE = true;
}else{ 
	isIE = false;
}




//첨부파일 다운로드
function fileDownload(uuid, log){
	if(uuid != ""){
		if(isMobile){
			if($$("#_targetDownload").length == 0){
				document.body.insertAdjacentHTML('beforeEnd', 
						 "<form id='_targetDownload' name='_targetDownload' style='display:none'>"
						+"	<input name='fileId' id='_targetDownloadId' value='"+uuid+"'/>" 
						+"	<input name='isMobile' value='Y'/>" 
						+"</form>");
			}else{
				$('_targetDownloadId').value = uuid;
			}
			if(log == 'Y'){
				document._targetDownload.action= "/servlet/RepositoryDownLoadServlet";
			}else{
				document._targetDownload.action= "/servlet/PhotoDownLoadServlet";
			}
	    	document._targetDownload.target= "_blank";
	    	document._targetDownload.submit();
		}else{
			if($$("#_targetDownload").length == 0){
				document.body.insertAdjacentHTML('beforeEnd', "<iframe id=\"_targetDownload\" src=\"\" style=\"display:none\"></iframe>");
			}
			if(log == 'Y'){
				$("_targetDownload").src = "/servlet/RepositoryDownLoadServlet?fileId=" + uuid;
			}else{
				$("_targetDownload").src = "/servlet/PhotoDownLoadServlet?fileId=" + uuid;
			}
		}
	}
}

if(typeof HTMLElement!="undefined" && !HTMLElement.prototype.insertAdjacentElement){
	HTMLElement.prototype.insertAdjacentElement = function(where,parsedNode){
		switch (where){
			case 'beforeBegin':
				this.parentNode.insertBefore(parsedNode,this)
				break;
			case 'afterBegin':
				this.insertBefore(parsedNode,this.firstChild);
				break;
			case 'beforeEnd':
				this.appendChild(parsedNode);
				break;
			case 'afterEnd':
				if (this.nextSibling) 
					this.parentNode.insertBefore(parsedNode,this.nextSibling);
				else 
					this.parentNode.appendChild(parsedNode);
				break;
		}
	};

	HTMLElement.prototype.insertAdjacentHTML = function(where,htmlStr){
		var r = this.ownerDocument.createRange();
		r.setStartBefore(this);
		var parsedHTML = r.createContextualFragment(htmlStr);
		this.insertAdjacentElement(where,parsedHTML);
	};

	HTMLElement.prototype.insertAdjacentText = function(where,txtStr){
		var parsedText = document.createTextNode(txtStr);
		this.insertAdjacentElement(where,parsedText);
	};
}


// JScript source code
//
function CreateXMLDocument(xmlText){
	var xmlDoc;
	if(window.ActiveXObject){ //code for IE
		xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async = "false";
		xmlDoc.loadXML(xmlText);
	}else{
		var parser = new DOMParser();
		xmlDoc = parser.parseFromString(xmlText,"text/xml");
	}
    return xmlDoc;
}

// String 타입에 replaceAll 이라는 메소드 추가
String.prototype.replaceAll = function(_findValue, _replaceValue) {
	 return this.replace(new RegExp(_findValue,"g"), _replaceValue);
};

// DropDownList에 데이타를 동적으로 바인딩하는 함수
function DropDownList_DataLoad(targetObj, tabName, selectedCode){
	/************************************************
	 * 함수명 : DropDownList_DataLoad
	 * 인  수 : targetObj(해당 드롭다운리스 컨트롤), tabName(코드 구분자)
	 * 역  할 : 코드를 읽어와서 드로다운리스트 컨트롤에 데이타 셋팅
	 ************************************************/
	
	var ActionURL = "/action/CommonCodeRetrieveAction.do"; // /action/CommonCodeRetrieveAction.do?mode=getCodeEntityList&tableName=
	ActionURL += "?mode=getCodeEntityList";
	ActionURL += "&tableName=" + tabName;
	// alert("페이지 로드");
	var status = AjaxRequest.get(
		{
			'url':ActionURL
			, 'anotherParameter':'false'
			, 'onSuccess':function(obj){  // 
				// alert(obj.responseText);
	           	var res = eval('(' + obj.responseText + ')');
	           	var rsObj = res.returnValue;
	           	
	            targetObj.options.length = rsObj.length;
	            for(var i=0; i < rsObj.length; i++){
		            targetObj.options[i].text = rsObj[i].data1;
	                targetObj.options[i].value = rsObj[i].key1;
	                if(rsObj[i].key1 == "selectedCode"){
						targetObj.selectedIndex = i;
	                }
	            }           
			}// END  -- onSuccess
			, 'onLoading':function(obj){}
			, 'onError':function(obj){
				alert(obj.responseText);
						
				alert("데이터를 가져오지 못 했습니다.");
			}//END -- onError
		});// END -- status
	status = null;	
	
}
// 키 입력을 숫자만 받게끔 제어 하는 함수
function Number_Only(obj, max, min){
	var regExp = /^[\d]*$/g;
	if(!regExp.test(obj.value)){
		obj.value = "";
		alert("숫자만 입력이 가능합니다.");
		return;
	}else{
		if(max != -1){
			if(obj.value > max){
				obj.value = "";
				alert(max+" 이하 값을 입력하세요.");
				return;
			}
		}
		if(min != -1){
			if(obj.value < min){
				obj.value = "";
				alert(min+" 이상 값을 입력하세요");
				return;
			}
		}
	}
}

function getNumber(obj){
	var rgx1 = /\D/g;  // /[^0-9]/g 와 같은 표현
	var rgx2 = /(\d+)(\d{3})/; 
	var num01;
	var num02;
	num01 = obj.value;
	num02 = num01.replace(rgx1,"");
	num01 = setComma(num02);
	obj.value =  num01;
	//Money_Only(obj, 13000000, -1);
}

function setComma(inNum){
	var rgx1 = /\D/g;  // /[^0-9]/g 와 같은 표현
	var rgx2 = /(\d+)(\d{3})/; 	 
	var outNum;
	outNum = inNum; 
	while (rgx2.test(outNum)) {
		outNum = outNum.replace(rgx2, '$1' + ',' + '$2');
	}
	return outNum;
}

function Money_Only(obj, max, min){
	var regExp = /^[\d\.]*$/g;
	if(!regExp.test(obj.value)){
		obj.value = "";
		alert("숫자만 입력이 가능합니다.");
		return;
	}else{
		if(max != -1){
			if(obj.value > max){
				obj.value = "";
				alert(max+" 이하 값을 입력하세요.");
				return;
			}
		}
		if(min != -1){
			if(obj.value < min){
				obj.value = "";
				alert(min+" 이상 값을 입력하세요");
				return;
			}
		}
	}
}
function Check_MoneyAmt(obj){
	//alert("jobClass: " + obj.jobClass + ", bizCode: " + obj.businessTypeCode + ", typeCode: " + obj.projectTypeCode);
	//alert("value: " + obj.value + ", maxEdu: " + obj.maxEdu + ", minEdu: " + obj.minEdu + ", maxAmt: " + obj.maxAmt + ", minAmt: " + obj.minAmt + ", maxMMAmt: " + obj.maxMMAmt + ", minMMAmt: " + obj.minMMAmt);
	/*if(obj.jobClass == "C" || obj.jobClass == "J"){
		if(obj.businessTypeCode == "BTI" || obj.businessTypeCode == "BTE"){
			if(parseInt(obj.value) == 0 || parseInt(obj.value) > parseInt(obj.maxEdu) || parseInt(obj.value) < parseInt(obj.minEdu)){
				alert(stringToMoney(obj.maxEdu) + " ~ " + stringToMoney(obj.minEdu) + " 사이의 금액을 입력하세요.");obj.value="";return;
			} 
		}else{
			var maxAmt = obj.maxAmt;
			var minAmt = obj.minAmt;
			if(obj.projectTypeCode == "MM") {
				maxAmt = obj.maxMMAmt;
				minAmt = obj.minMMAmt;
			}
			if(parseInt(obj.value) == 0 || parseInt(obj.value) > parseInt(maxAmt) || parseInt(obj.value) < parseInt(minAmt)){
				alert(stringToMoney(maxAmt) + " ~ " + stringToMoney(minAmt) + " 사이의 금액을 입력하세요.");obj.value="";return;				
			} 
		}
	}*/
}
function stringToMoney(str) {
	if (str == "" || str == "0")
		return 0;

	var returnVal = 0;
	var temp1 = str.replace(/,/g,"");	// 입력 데이터를 숫자로 변환
	var temp = temp1.split('.');

	var num1 = "";
	var comma = 1;

	for(var i=temp[0].length-1; i >= 0; i--) {
		num1 += temp[0].charAt(i);
		if(comma % 3 == 0 && comma != 0) {
			num1 += ",";
		}
		comma++;
	}

	var num2 = "";
	for(var i=num1.length-1; i >= 0; i--) {
		num2 += num1.charAt(i);
	}

	if(temp.length > 1) {
		var num3 = "";
		for(var i=1; i <= temp[1].length; i++) {
			num3 += temp[1].charAt(i-1);

			if((i%3 == 0) && (i != 0)) {
				num3 += ",";
			}
		}

		var num4 = num2 + "." + num3;
		returnVal = num2.replace(/(^,)|(,$)/g,"");
	} else
		returnVal = num2.replace(/(^,)|(,$)/g,"");

	if (returnVal == "" || returnVal == ".")
		return 0;
	else
		return returnVal;
}
function PhoneNumber_Only(){
	// 소수점, 대시 허용함
	if((event.keyCode<44)||(event.keyCode>57)){
		event.returnValue=false;
	}
}

function ltrim(str)
{
	var LeftTrimValue = "";
	var i=0;
	if( str == "" ) 
		return "";
	strLen = str.length; 
	while(i<strLen){
		if(str.charAt(i) != " ") break;
		i++;
	}
	for(s=i;s<strLen;s++){
		LeftTrimValue = LeftTrimValue + str.charAt(s);
	}
	return LeftTrimValue;
}

function dateDiff(_date1, _date2) {
    var diffDate_1 = _date1 instanceof Date ? _date1 : new Date(_date1);
    var diffDate_2 = _date2 instanceof Date ? _date2 : new Date(_date2);
 
    diffDate_1 = new Date(diffDate_1.getFullYear(), diffDate_1.getMonth()+1, diffDate_1.getDate());
    diffDate_2 = new Date(diffDate_2.getFullYear(), diffDate_2.getMonth()+1, diffDate_2.getDate());
 
    var diff = Math.abs(diffDate_2.getTime() - diffDate_1.getTime());
    diff = Math.ceil(diff / (1000 * 3600 * 24));
 
    return diff;
}

function ListData_Bind(xNode,templete,pNode){
	//pNode.childNodes[0].parentNode.removeChild(pNode.childNodes[0]);
	var divObj = document.createElement("div");
	divObj.id = "xxxTempDivForTemplete";
	document.body.appendChild(divObj);
	for(var i = pNode.childNodes.length-1; i >= 0; i--){
		pNode.removeChild(pNode.childNodes[i]);
	}
	if(xNode.childNodes.length > 0){
		for(var i = 0; i < xNode.childNodes.length; i++){
			var temp = templete;
			// var trObj = document.createElement("<tr>");
			for(var j = 0; j < xNode.childNodes[i].childNodes.length; j++){
				var nodeName  = xNode.childNodes[i].childNodes[j].nodeName;
				var nodeValue = xNode.childNodes[i].childNodes[j].text;
				temp = temp.replaceAll("#" + nodeName.toUpperCase() + "#" , nodeValue);
				// trObj.innerHTML = trObj.innerHTML + temp
			}
			var rowData = HTMLtoDOM(temp);
			rowData.style.display = "";
			
			pNode.appendChild(rowData);
		}
	}
	document.body.removeChild(document.getElementById("xxxTempDivForTemplete"));
}
// Html text를 DOM 객체로 변환하는 함수
function HTMLtoDOM(html){
	document.getElementById("xxxTempDivForTemplete").innerHTML = "<table>" + html + "</table>";
	return document.getElementById("xxxTempDivForTemplete").childNodes[0].childNodes[0].childNodes[0];
}


// 우편번호 찾기 
function zipCodeWin_Open(callBackFunc){
	/*
	var zipWin;
	var sURL = "/action/ExpertPoolManagerAction.do?mode=zipCodeSearch";
	sURL += "&callbackFunc=" + callBackFunc;
	var sFeather = "top=100,left=100,width=400,height=300,scrollbars=no";
	zipWin = window.open(sURL, "zipWin", sFeather);
	zipWin.focus();
	*/
	
	new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 도로명 조합형 주소 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }
            // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
            if(fullRoadAddr !== ''){
                fullRoadAddr += extraRoadAddr;
            }
			if(callBackFunc=='callBackByZipWin1'){
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipCode').value = data.zonecode; //5자리 새우편번호 사용jobzip
            document.getElementById('zipCodeview').value = data.zonecode; //5자리 새우편번호 사용jobzip
            document.getElementById('address1').value = fullRoadAddr;
          //  document.getElementById('address1').value = data.jibunAddress;
           }else{
           	document.getElementById('companyZipcode').value = data.zonecode; //5자리 새우편번호 사용jobzip
           	document.getElementById('companyZipcodeview').value = data.zonecode; //5자리 새우편번호 사용jobzip
            document.getElementById('companyAddress1').value = fullRoadAddr;
          //  document.getElementById('jobaddress1').value = data.jibunAddress;
           	
          }
            // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
            if(data.autoRoadAddress) {
                //예상되는 도로명 주소에 조합형 주소를 추가한다.
                var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                document.getElementById('guide').innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';

            } else if(data.autoJibunAddress) {
                var expJibunAddr = data.autoJibunAddress;
                document.getElementById('guide').innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';

            } else {
                document.getElementById('guide').innerHTML = '';
            }
        }
    }).open();
}

// 협력기관 검색 
function relationCompanyWin_Open(callBackFunc){
	var companyWin;
	var sURL = "/action/ExpertPoolManagerAction.do?mode=relationWithKmacSearch";
	sURL += "&callbackFunc=" + callBackFunc;
	var sFeather = "top=100,left=100,width=400,height=330,scrollbars=no";
	companyWin = window.open(sURL, "companyWin", sFeather);
	companyWin.focus();
}
 
// DB List
function dmlist_Open(callBackFunc){
	var companyWin;
	var sURL = "/action/ProjectDmSearchAction.do?mode=getProjectDmSearchList";
	sURL += "&callbackFunc=" + callBackFunc; 
	var sFeather = "top=0,left=0,width=1410,height=850,scrollbars=no";
	companyWin = window.open(sURL, "companyWin", sFeather);
	companyWin.focus();
}

// 전문가 찾기
function orgFinder_Open(callBackFunc){
	var companyWin;
	var sURL = "/action/ExpertPoolManagerAction.do?mode=orgFinder";
	sURL += "&callbackFunc=" + callBackFunc; 
	var sFeather = "top=120,left=120,width=920,height=630,scrollbars=no,toolbar=no,status=no,directories=no,menubar=no";
	companyWin = window.open(sURL, "companyWin", sFeather);
	companyWin.focus();
}

// 투입인력 검색 (검색이 한개면 바로 입력 아니면 팝업)
function orgFinder_Open2(callBackFunc, searchValue){
	var status = AjaxRequest.post (
			{	'url':"/action/ExpertPoolManagerAction.do?mode=expertSelectAsName",
				'parameters' : { "name": searchValue },
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					if(res.expertPoolList.length == 1 ){
						setProjectMember(res.expertPoolList);
					} else {
						var companyWin;
						var sURL = "/action/ExpertPoolManagerAction.do?mode=orgFinder";
						sURL += "&callbackFunc=" + callBackFunc; 
						sURL += "&searchValue=" + searchValue; 
						var sFeather = "top=120,left=120,width=920,height=630,scrollbars=no,toolbar=no,status=no,directories=no,menubar=no";
						companyWin = window.open(sURL, "companyWin", sFeather);
						companyWin.focus();						
					}
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.7");
				}
			}
	);	
}

function orgFinder_OpenForProjectMember(projectCode, callBackFunc){
	var companyWin;
	var sURL = "/action/ExpertPoolManagerAction.do?mode=orgFinderForProjectMember";
	sURL += "&callbackFunc=" + callBackFunc; 
	sURL += "&projectCode=" + projectCode; 
	sURL += "&init=init"; 
	var sFeather = "top=0,left=0,width=450,height=460,scrollbars=no,toolbar=no,status=no,directories=no,menubar=no";
	companyWin = window.open(sURL, "companyWin1", sFeather);
	companyWin.focus();
}
// 주민등록번호 체크
function RegNo_Check(num){
	   if(num.length != 13){
	      return false;	
	   }
	   var month='(0[1-9]|1[0-2])'; 
	   var date='(0[1-9]|[1-2][0-9]|3[0-1])';
	   var regMonth = new RegExp(month);
	   var regDate = new RegExp(date);
	   if(!regMonth.test(num.substring(2,4))){
	      return false;
	   }
	   if(!regDate.test(num.substring(4,6))){
	      return false;
	   }
	   var tab=[2,3,4,5,6,7,8,9,2,3,4,5];
	   var sum=0,i=0;
	   for(i=0;i<12;i++) sum+=parseInt(num.charAt(i))*tab[i];
	   if(((11-(sum%11))%10!=num.charAt(12))){
	      return false;
	   }
	   return true;
}

// image 파일 확장자 체크
function imageFile_Check(fname){
	var check1 = "\\.(bmp|gif|jpg|jpeg|png)$";
	return (new RegExp(check1, "i")).test(fname);
}

// 이메일 체크 
function CheckMail(strMail) { 
   /** 체크사항 
     - @가 2개이상일 경우 
     - .이 붙어서 나오는 경우 
     -  @.나  .@이 존재하는 경우 
     - 맨처음이.인 경우 
     - @이전에 하나이상의 문자가 있어야 함 
     - @가 하나있어야 함 
     - Domain명에 .이 하나 이상 있어야 함 
     - Domain명의 마지막 문자는 영문자 2~4개이어야 함 **/ 

    var check1 = /(@.*@)|(\.\.)|(@\.)|(\.@)|(^\.)/;  

    var check2 = /^[a-zA-Z0-9\-\.\_]+\@[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,4})$/; 
     
    if ( !check1.test(strMail) && check2.test(strMail) ) { 
        return true; 
    } else { 
        return false; 
    } 
}

function CheckMail2(email) {
	if (email == "") return;
	var invalidChars = "\"|&;<>!*\'\\"   ;
	for (var i = 0; i < invalidChars.length; i++) {
		if (email.indexOf(invalidChars.charAt ) != -1) {
			alert("올바르지 않은 e메일 주소입니다.");
			return;
		}
	}
	if (email.indexOf("@")==-1){
		alert("올바르지 않은 e메일 주소입니다. '@'가 없습니다.");
		return;
	}
	if (email.indexOf(" ") != -1){
		alert("올바르지 않은 e메일 주소입니다.");
		return;
	}
	if (window.RegExp) {
		var reg1str = "(@.*@)|(\\.\\.)|(@\\.)|(\\.@)|(^\\.)";
		var reg2str = "^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$";
		var reg1 = new RegExp (reg1str);
		var reg2 = new RegExp (reg2str);
	 
		if (reg1.test(email) || !reg2.test(email)) {
			alert("올바르지 않은 e메일 주소입니다.");
			return;
		}
	}
	return true;
}

var preClassName = "";
function row_over(obj){
	preClassName = obj.className;
	obj.className = "listTableMouseOver";
}

function row_over2(obj){
	obj.className = "listTableMouseOver2";
}
function topArticleRow_over(obj) {
	preClassName = obj.className;
	obj.clasName = "listTableMouseOverTopArticle";
}
function row_out(obj){
	
	obj.className = (preClassName != "")?preClassName:"";
}


function setCookie1( name, value, expiredays ){
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + '=' + escape( value ) + '; path=/; expires=' + todayDate.toGMTString() + ';'
}

function setCookie2( name, value ){
	document.cookie = name + '=' + escape( value ) + '; path=/; '
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
	return "";
}
