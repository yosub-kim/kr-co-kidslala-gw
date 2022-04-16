
function jobClass_Select(obj){
	var svalue = obj.options[obj.selectedIndex].value;
	var objCompany, objCompayId, objComSearch, objRate;
	var objTxtDept, objSltDept, objTxtPosition, objSltPosition, objTxtExpertField, objSltRank;
	objCompany   = document.getElementById("company");
	objCompanyId = document.getElementById("companyId");
	objComSearch = document.getElementById("btnCompany"); 
	objRate = document.getElementById("rate");
	
	objTxtDept = document.getElementById("deptName");
	objSltDept = document.getElementById("selectDept");
	objSltPosition = document.getElementById("selectPosition");
	objTxtPosition = document.getElementById("companyPositionName");
	objSltMenu = document.getElementById("selectMenu");
	objSltEnable = document.getElementById("enable");
	objHidMenuCode = document.getElementById("menuCode");
	objTxtExpertField = document.getElementById("expertField");
	objSltRank = document.getElementById("rank");
	
	if(svalue == "A" || svalue == "J" || svalue == "B"){	// 상근, BA, 전문가 그룹
		//소속기관TextBox
		objCompany.value = "KMAC";
		objCompany.style.width="100%";
		objCompany.readOnly = true;
		objComSearch.style.display = "none";
		//objRate.Value = "";
		
		objSltDept.style.display = "";
		objTxtDept.style.display = "none";

		//DropDownList_DataLoad2(document.getElementById("selectPosition"), "getBuRoleCode", "");
		objSltPosition.style.display = "";
		objTxtPosition.style.display = "none";
		
		objSltEnable.value = "1";
		objTxtExpertField.style.display = "none";
		objSltRank.style.display = "none";
	}else if(svalue == "H" || svalue == "N"){	// RA
		objCompany.value = "KMAC";
		objCompany.style.width="100%";
		objCompany.readOnly = true;
		objComSearch.style.display = "none";
		//objRate.Value = "";

		objSltDept.style.display = "";
		objTxtDept.style.display = "none";

		//DropDownList_DataLoad2(document.getElementById("selectPosition"), "getPuRoleCode", "");
		objSltPosition.style.display = "";
		objTxtPosition.style.display = "none";
		
		//objSltMenu.value = "ROLE12EB7551DAA";
		//objHidMenuCode.value = "ROLE12EB7551DAA";
		objSltEnable.value = "1";
		objTxtExpertField.style.display = "none";
		objSltRank.style.display = "none";
	}else if(svalue == "C"){
		objCompany.value = "KMAC(엑스퍼트)";
		objCompany.readOnly = false;
		objCompany.style.width="83";
		objComSearch.style.display = "";
		//objRate.Value = "";

		objSltDept.style.display = "none";
		objTxtDept.style.display = "";
		objTxtDept.value = "엑스퍼트";
		
		objSltPosition.style.display = "none";
		objTxtPosition.style.display = "";		
		objTxtPosition.value = "엑스퍼트";
		//DropDownList_DataLoad2(document.getElementById("selectPosition"), "getPuRoleCode", "");

		document.getElementById("companyPosition").value = "40EE";
		document.getElementById("deptCode").value = "2500";
		
		objSltMenu.value = "ROLE2006080118352520784";
		objHidMenuCode.value = "ROLE2006080118352520784";
		objSltEnable.value = "1";
		objTxtExpertField.style.display = "";
		objSltRank.style.display = "";
	}else if(svalue == "F"){ // (대기)엑스퍼트 admin 화면 변경 부분
		objCompany.value = "(대기)엑스퍼트";
		objCompany.readOnly = false;
		//objRate.Value = "";
				
		objSltDept.style.display = "none";
		
		objTxtDept.value = "";
		objTxtDept.style.display = "";
		
		document.getElementById("deptCode").value = "";
		//DropDownList_DataLoad2(document.getElementById("selectPosition"), "getRoleCode", "");

		objSltPosition.style.display = "none";
		objTxtPosition.style.display = "";
		objTxtPosition.value = "";
		document.getElementById("companyPosition").value = "";
		objSltMenu.value="ROLE2006072522015535636";
		objHidMenuCode.value="ROLE2006072522015535636";
		objSltEnable.value = "0";
		objTxtExpertField.style.display = "none";
		objSltRank.style.display = "none";
	}else if(svalue == "L"){// (대기)산업계강사 admin 화면 변경 부분_190910_김요섭
		objCompany.value = "(대기)산업계강사";
		objCompany.readOnly = false;
		//objRate.Value = "";
				
		objSltDept.style.display = "none";
		
		objTxtDept.value = "";
		objTxtDept.style.display = "";
		
		document.getElementById("deptCode").value = "";
		//DropDownList_DataLoad2(document.getElementById("selectPosition"), "getRoleCode", "");

		objSltPosition.style.display = "none";
		objTxtPosition.style.display = "";
		objTxtPosition.value = "";
		document.getElementById("companyPosition").value = "";
		objSltMenu.value="ROLE2006072522015535636";
		objHidMenuCode.value="ROLE2006072522015535636";
		objSltEnable.value = "0";
		objTxtExpertField.style.display = "none";
		objSltRank.style.display = "none";
	}else if(svalue == "M"){// (대기)대학교수 admin 화면 변경 부분_190910_김요섭
		objCompany.value = "(대기)대학교수";
		objCompany.readOnly = false;
		//objRate.Value = "";
				
		objSltDept.style.display = "none";
		
		objTxtDept.value = "";
		objTxtDept.style.display = "";
		
		document.getElementById("deptCode").value = "";
		//DropDownList_DataLoad2(document.getElementById("selectPosition"), "getRoleCode", "");

		objSltPosition.style.display = "none";
		objTxtPosition.style.display = "";
		objTxtPosition.value = "";
		document.getElementById("companyPosition").value = "";
		objSltMenu.value="ROLE2006072522015535636";
		objHidMenuCode.value="ROLE2006072522015535636";
		objSltEnable.value = "0";
		objTxtExpertField.style.display = "none";
		objSltRank.style.display = "none";
	}
		else{
		if(svalue=="I") {	// KMAC Alumni
			objCompany.value = "(前)KMAC";
			objComSearch.style.display = "none";
			objCompany.style.width="100%";
		} else {
			objCompany.value = "";
			objComSearch.style.display = "";
			objCompany.style.width="83";
		}
		objCompany.readOnly = false;
		//objRate.Value = "";
				
		objSltDept.style.display = "none";
		
		objTxtDept.value = "";
		objTxtDept.style.display = "";
		
		document.getElementById("deptCode").value = "";
		//DropDownList_DataLoad2(document.getElementById("selectPosition"), "getRoleCode", "");

		objSltPosition.style.display = "none";
		objTxtPosition.style.display = "";
		objTxtPosition.value = "";
		document.getElementById("companyPosition").value = "";
		objSltMenu.value="ROLE2006072522015535636";
		objHidMenuCode.value="ROLE2006072522015535636";
		if(svalue=="G"){
			objSltEnable.value = "1";
		}else{
			objSltEnable.value = "0";
		}
		objTxtExpertField.style.display = "none";
		objSltRank.style.display = "none";
	}
	/*
	switch (svalue){
	case "A" :
		DropDownList_DataLoad2(objSltDept, "getBuGroupCode", "");
		DropDownList_DataLoad2(objSltPosition, "getBuRoleCode", "");
		break;
	case "H" :
		DropDownList_DataLoad2(objSltDept, "getBuGroupCode", "");
		DropDownList_DataLoad2(objSltPosition, "getBuRoleCode", "");
		break;
	case "J" :
		DropDownList_DataLoad2(objSltDept, "getBuGroupCode", "");
		DropDownList_DataLoad2(objSltPosition, "getBuRoleCode", "");
		break;
	default :
		//alert("A 도 B도 아닝기라..");			
	}
	*/
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
	            for(var i=0; i < rsObj.length; i++){
		            targetObj.options[i].text = rsObj[i].name; //).replaceAll(" ","&nbsp;&nbsp;");
	                targetObj.options[i].value = rsObj[i].id;
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

function checkEmail(){
	var email = document.getElementById("email").value;
	if(!CheckMail2(email)) {
		//document.getElementById("email").value="";
		//document.getElementById("email").focus();
	}
}

function checkSsn(){
	var vNational = document.getElementById("nationality").value;
	var regno = document.getElementById("uid").value;
	
	if(regno=="") return;
	
	if(vNational == "KOR"){
		if(!RegNo_Check(regno)){
			alert("올바르지 않은 주민등록번호 입니다.");
			document.getElementById("uid").select();
		}
	}
	// 주민등록번호 존재 여부 체크
	AjaxRequest.post (
		{	'url': "/action/ExpertPoolCheckAction.do?mode=checkSsn",
			'parameters' : { "ssn": regno},
			'onSuccess':function(obj){
				var res = eval('(' + obj.responseText + ')');
				if(res.returnValue != 'true'){
					alert("이미 등록된 주민등록번호 입니다. ");
					document.getElementById("uid").select();
				}
			},
			'onLoading':function(obj){},
			'onError':function(obj){
				alert("확인할 수 없습니다.");
			}
		}
	); 		
}

function checkUserId(){
	// update 모드일 때는 아이디 중복 여부 체크하지 않음 
	if(document.getElementById("saveMode").value == "UPDATE") {
		return;
	}
	
	var userId = document.getElementById("identify").value;
	if(userId=="") return;
	// 사용자 아이디 존재 여부 체크
	AjaxRequest.post (
		{	'url': "/action/ExpertPoolCheckAction.do?mode=checkUserId",
			'parameters' : { "userId": userId},
			'onSuccess':function(obj){
				var res = eval('(' + obj.responseText + ')');
				if(res.returnValue != 'true'){
					alert("이미 등록된 아이디 입니다. ");
					//document.getElementById("identify").value = "";
					//document.getElementById("identify").focus();
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
	if (obj && obj.value != "") {
		document.getElementById("deptCode").value = obj.value;
		document.getElementById("deptName").value = obj.options[obj.selectedIndex].text;
	}
}

function companyPosition_Set(obj){
	var svalue = document.getElementById("jobClass").value;
	var rate = obj.value;
	
	if (obj && obj.value != "") {
		document.getElementById("companyPosition").value = rate;
		document.getElementById("companyPositionName").value = obj.options[obj.selectedIndex].text;
	}
	
	switch (svalue){
	case "A" :
		switch (rate){
		case "02GM" :
			document.getElementById("rate").value=0.42;break;
		case "15EJR" :
			document.getElementById("rate").value=0.38;break;
		case "17CJR" :
			document.getElementById("rate").value=0.32;break;
		case "19JR" :
			document.getElementById("rate").value=0.25;break;
		case "20SR" :
			document.getElementById("rate").value=0.18;break;
		case "23NRB" :
			document.getElementById("rate").value=0.12;break;
		case "23NRC" :
			document.getElementById("rate").value=0.148;break;
		case "23NRD" :
			document.getElementById("rate").value=0.131;break;
		case "23NRE" :
			document.getElementById("rate").value=0.115;break;
		case "23NRF" :
			document.getElementById("rate").value=0.107;break;
		case "23NRG" :
			document.getElementById("rate").value=0.098;break;
		default :
			document.getElementById("rate").value=0;
		}
		break;
	case "J" :
		switch (rate){
		case "15EJR" :
			document.getElementById("rate").value=0.42;break;
		case "17CJR" :
			document.getElementById("rate").value=0.34;break;
		case "19JR" :
			document.getElementById("rate").value=0.26;break;
		case "20SR" :
			document.getElementById("rate").value=0.19;break;
		case "23NRB" :
			document.getElementById("rate").value=0.14;break;
		default :
			document.getElementById("rate").value=0;
		}
		break;
	case "C" :
		switch (rate){
		case "40EE" :
			document.getElementById("rate").value=0.5;break;
		case "41EF" :
			document.getElementById("rate").value=0.4;break;
		case "42EG" :
			document.getElementById("rate").value=0.35;break;
		case "43EH" :
			document.getElementById("rate").value=0.3;break;
		case "44EI" :
			document.getElementById("rate").value=0.23;break;
		default :
			document.getElementById("rate").value=0;
		}
		break;
	case "H" :
		switch (rate){
		case "61DT" :
			document.getElementById("rate").value=0.165;break;
		case "62ET" :
			document.getElementById("rate").value=0.125;break;
		case "63FT" :
			document.getElementById("rate").value=0.085;break;
		case "64GT" :
			document.getElementById("rate").value=0.045;break;
		default :
			document.getElementById("rate").value=0;
		}
		break;
	default :
		document.getElementById("rate").value=1;			
	}
}

function menu_Set(obj) {
	if (obj && obj.value != ""){
		document.getElementById("menuCode").value = obj.value;
		document.getElementById("menuName").value = obj.options[obj.selectedIndex].text;
	}
}
