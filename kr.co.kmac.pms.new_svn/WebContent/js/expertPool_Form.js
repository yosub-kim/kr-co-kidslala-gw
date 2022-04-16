
function jobClass_Select(obj){
	var svalue = obj.options[obj.selectedIndex].value;
	var objCompany, objCompayId, objComSearch;
	var objTxtDept, objSltDept, objTxtPosition, objSltPosition;
	objCompany   = document.getElementById("company");
	objCompanyId = document.getElementById("companyId");
	
	objComSearch = document.getElementById("btnCompany");
	
	objTxtDept = document.getElementById("deptName");
	objSltDept = document.getElementById("selectDept");
	objSltPosition = document.getElementById("selectPosition");			// 직위 select box
	objTxtPosition = document.getElementById("companyPositionName");	// 직위명
	
	if(svalue == "H"){
		//소속기관TextBox
		objCompany.value = "KMAC";
		objCompany.readOnly = true;
		objCompany.style.width = "100%";
		objComSearch.style.display = "none";            //  disabled = true;

		objSltDept.style.display = "";
		objTxtDept.style.display = "none";

		objSltPosition.style.display = "";
		objSltPosition.value="40AT";
		objTxtPosition.style.display = "none";

	}else if(svalue == "C"){
		objCompany.value = "KMAC(엑스퍼트)";
		objCompany.readOnly = false;
		objCompany.style.width = "60%";
		objComSearch.style.display = "";    

		objSltDept.style.display = "";
		objTxtDept.style.display = "none";

		objSltPosition.style.display = "";
		objTxtPosition.style.display = "none";
		//objSltPosition.style.display = "none";
		//objTxtPosition.style.display = "";
		//objTxtPosition.value = "엑스퍼트";
		//document.getElementById("companyPosition").value="엑스퍼트";
	}else{
		objCompany.value = "";
		objCompany.readOnly = false;
		objCompany.style.width = "60%";
		objComSearch.style.display = "";    
				
		objSltDept.style.display = "none";
		objTxtDept.style.display = "";
		objTxtDept.value = "";

		objSltPosition.style.display = "none";
		objTxtPosition.style.display = "";
		objTxtPosition.value = "";
		document.getElementById("companyPosition").value="";
	}
	

}
function DropDownList_DataLoad2(targetObj, mode, selectedCode){
	/************************************************
	 * 함수명 : DropDownList_DataLoad
	 * 인  수 : targetObj(해당 드롭다운리스 컨트롤), tabName(코드 구분자)
	 * 역  할 : 코드를 읽어와서 드로다운리스트 컨트롤에 데이타 셋팅
	 ************************************************/
	
	var ActionURL = "/action/ExpertPoolCodeRetrieveAction.do";
	ActionURL += "?mode=" + mode;
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
	            var selIndex = 0;
	            for(var i=0; i < rsObj.length; i++){
		            targetObj.options[i].text = rsObj[i].name; //).replaceAll(" ","&nbsp;&nbsp;");
	                targetObj.options[i].value = rsObj[i].id;
	                if(rsObj[i].id == selectedCode){
	                	selIndex = i;
	                }
	            } 
	            targetObj.selectedIndex = selIndex;
			}// END  -- onSuccess
			, 'onLoading':function(obj){}
			, 'onError':function(obj){
				alert(obj.responseText);
						
				alert("데이터를 가져오지 못 했습니다.");
			}//END -- onError
		});// END -- status
	status = null;	
	
}

function callBackByZipWin1(zipcode, address){
	frm.zipCodeview.value = zipcode;
	frm.zipCode.value = zipcode.replaceAll("-","");
	frm.address1.value = address;
	frm.address2.focus();
}

function callBackByZipWin2(zipcode, address){
	frm.companyZipcodeview.value = zipcode;
	frm.companyZipcode.value = zipcode.replaceAll("-","");
	frm.companyAddress1.value = address;

	frm.companyAddress2.focus();
}

function check_Mail(email){
	if(email.value == "") return;
	if(!CheckMail(email.value)){
		alert("올바르지 않은 e메일 주소입니다.");
	}	
}

function Jumin_Check(){
	var vNational = document.getElementById("nationality").value;
	var regno = document.getElementById("uid").value;
	
	// 주민등록번호 정상 체크
	if(vNational == "KOR" && regno != ''){
		if(!RegNo_Check(regno)){
			alert("올바르지 않은 주민등록번호 입니다.");
			document.getElementById("uid").value="";
			document.getElementById("uid").focus();
		}
	}
	
	// 주민등록번호 존재 여부 체크
	AjaxRequest.post (
		{	'url': "/action/ExpertPoolCheckAction.do?mode=checkSsn",
			'parameters' : { "ssn": regno },
			'onSuccess':function(obj){
				var res = eval('(' + obj.responseText + ')');
				if(res.returnValue != 'true'){
					alert("이미 등록된 주민등록번호 입니다. 인력POOL을 확인해 보시기 바랍니다.");
					document.getElementById("uid").value="";
				}
			},
			'onLoading':function(obj){},
			'onError':function(obj){
				alert("확인할 수 없습니다.");
			}
		}
	); 			
}

function search_Company(){
	relationCompanyWin_Open("callBackByCompanyWin");
}

function callBackByCompanyWin(companyList){
	//alert(companyList.length);
	frm.companyId.value = companyList[0].OrgCODE;
	frm.company.value = companyList[0].name;
}

function companyDept_Set(obj){
	document.getElementById("dept").value = obj.value;
	document.getElementById("deptName").value = obj.options[obj.selectedIndex].text;
}

function companyPosition_Set(obj){
	document.getElementById("companyPosition").value = obj.value;
	document.getElementById("companyPositionName").value = obj.options[obj.selectedIndex].text;
}
