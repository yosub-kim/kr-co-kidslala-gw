<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript" src='<c:url value="/js/expertPool_Form2.js"/>'></script>
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
var searchFlag = 'ssn';
var modeType;
function Page_OnLoad(){
	FILEUPLOADER_INIT();
	DropDownList_DataLoad2(document.getElementById("selectDept"), "getGroupCode", "");
	DropDownList_DataLoad2(document.getElementById("selectPosition"), "getRoleCode", "");
	DropDownList_DataLoad2(document.getElementById("selectMenu"), "getMenuRoleCode", "");
}

function FILEUPLOADER_INIT() {
	var button =$("MyPic");
	new AjaxUpload(button,{
		action: '/action/RepositoryAction.do?mode=fileUpload', 
		name: 'userfile',
		onSubmit : function(file, ext){
			//button.src="/images/ajax-loader.gif";
           	//button.style.width = "16";
           	//button.style.height = "16";
			this.disable();
		},
		onComplete: function(file, response){
			this.enable();
           	var res = eval('(' + response + ')');
           	
           	//fileName.value = res.file.orginalFileName;
           	frm.photoId.value = res.file.fileId;
           	button.src="/servlet/PhotoDownLoadServlet?fileId=" + res.file.fileId;
		}
	});
}

function clear_Form(oType) {
	if(oType == "USER") {
		document.getElementById("divOrg").style.display = "none";
		document.getElementById("divExpert").style.display = "";
		//document.getElementById
		frm.reset();
		frm.saveMode.value = "INSERT";
		frm.uid.disabled = false;	// 주민등록번호 입력란 활성화
		document.getElementById("MyPic").src = "/images/im_noimage.jpg";
	} else if(oType == "GROUP") {
		document.getElementById("divOrg").style.display = "";
		document.getElementById("divExpert").style.display = "none";

		orgFrm.reset();
		orgFrm.saveMode.value = "INSERT";
		if(groupSelectedNode != null)
			groupDataSet(groupSelectedNode);
	}
	modeType = oType;
}
function btnSave_click(){
	if(modeType == "GROUP") {
		save_Group();
	} else {
		save_ExpertPool();
	}
}
var groupSelectedNode;
function groupNodeSelect(oNode){
	groupSelectedNode = oNode;
	groupDataSet(oNode);
}

function groupDataSet(oNode) {
	orgFrm.parentId.value = groupSelectedNode.id;
	orgFrm.parentName.value = groupSelectedNode.name;
}
function LoadData(oNode) {
	searchFlag = "ssn";
	clear_Form(oNode.objType);
	if(oNode.objType == "USER") {		
		LoadUserInfo(oNode.id);
	} else if(oNode.objType == "GROUP") {
		LoadGroupInfo(oNode.id);
	}
	modeType = oNode.objType;
}
function LoadGroupInfo(id){
	var sfrm = document.orgFrm;
	var ActionURL = "/action/OrgMngAction.do?mode=loadGroupInfo";
	ActionURL += "&id=" + id;
	// alert("페이지 로드");
	var status = AjaxRequest.get(
		{
			'url':ActionURL
			, 'anotherParameter':'false'
			, 'onSuccess':function(obj){  // 
				// alert(obj.responseText);
	           	var res = eval('(' + obj.responseText + ')');
	           	var rsObj = res.returnValue;
	           	sfrm.saveMode.value = "UPDATE";
	           	sfrm.id.value = res.id;
	           	sfrm.name.value = res.name;
	           	sfrm.description.value = res.description;
	           	sfrm.parentId.value = res.parentId;
	           	sfrm.parentName.value = res.parentName;
	           	sfrm.depth.value = res.depth;
	           	sfrm.path.value = res.path;
	           	sfrm.seq.value  = res.seq;
	           	if(res.enable == true)
		           	sfrm.enable.value = "1";
	           	else
		           	sfrm.enable.value = "0";
	           	//setDataToForm(expertPool);
          
			}// END  -- onSuccess
			, 'onLoading':function(obj){}
			, 'onError':function(obj){
						
				alert("데이터를 가져오지 못 했습니다.");
			}//END -- onError
		});// END -- status
	status = null;	
}


function researchUser(ssn){
	$('searchResult').style.display = "none";
	$('searchType').value = "ssn";
	LoadUserInfo(ssn); 
	searchFlag = "name";
}

function LoadUserInfo(searchValue) {
	if(searchValue == ""){
		alert("검색어를 입력하세요");return;
	}
	var ActionURL = "";
	if($('searchType').value == "ssn" || searchFlag == "ssn"){
		ActionURL = "/action/ExpertPoolManagerAction.do?mode=expertSelect&ssn=" + searchValue;
	}else if($('searchType').value == "uid"){
		ActionURL = "/action/ExpertPoolManagerAction.do?mode=expertSelectAsUid";
	}else if($('searchType').value == "name"){
		ActionURL = "/action/ExpertPoolManagerAction.do?mode=expertSelectAsName";
	}
	
	// alert("페이지 로드");
	var status = AjaxRequest.post( 
		{
			'url':ActionURL,
			'parameters' : { "name": searchValue},
			'onSuccess':function(obj){  // 
				// alert(obj.responseText);
	           	var res = eval('(' + obj.responseText + ')');
	           	var rsObj = res.returnValue;
				if($('searchType').value == "ssn" || searchFlag == "ssn"){
		           	var expertPool = res.expertPool;
		           	setDataToForm(expertPool);
		           	if(searchFlag == "name"){
		           		$('searchType').value = "name";
		           		//searchFlag = "ssn";
		           	}
				}else{
		           	var expertPoolList = res.expertPoolList;
		           	if(expertPoolList.length > 1){
			           	var resStr = "";
			           	for(var i=0; i < expertPoolList.length; i++){
				           	resStr += "<span onclick='javascript:researchUser(\""+expertPoolList[i].ssn+"\")', style='cursor:pointer; margin-bottom: 5px; width: 100%;'>"
				           		+expertPoolList[i].name+" | "+expertPoolList[i].uid.substring(0,2)+"년 | "+expertPoolList[i].company+"</span>"
			           	}					
			           	$('searchResult').innerHTML = resStr;	   
			           	$('searchResult').style.display = "";
		           	}else if(expertPoolList.length == 1){
		           		setDataToForm(expertPoolList[0]);
		           	}else {
		           		clear_Form('USER');
		           		alert("검색 결과가 없습니다.");
		           	}
				}
          
			}// END  -- onSuccess
			, 'onLoading':function(obj){}
			, 'onError':function(obj){
						
				alert("데이터를 가져오지 못했습니다.");
			}//END -- onError
		});// END -- status
	status = null;	
}
function setDataToForm(expertPool) {
	var sFrm = document.frm;
	sFrm.saveMode.value = "UPDATE";

	if (expertPool.photo && expertPool.photo != "") {
		document.getElementById("MyPic").src = '/servlet/PhotoDownLoadServlet?fileId=' + expertPool.photo;
	} else {
		document.getElementById("MyPic").src = "/images/im_noimage.jpg";
	}
	
   	sFrm.name.value = expertPool.name;					// 성명(한글)
   	sFrm.chnName.value = expertPool.chnName;			// 성명(한자)
   	sFrm.engName.value = expertPool.engName;			// 성명(영문)
   	sFrm.gender.value = expertPool.gender;				// 성별
   	sFrm.uid.value = expertPool.uid;					// 주민등록번호
   	sFrm.uid.disabled = true;							// 주민등록번호 수정 불가
   	sFrm.ssn.value = expertPool.ssn;					// 사용자 식별 아이디
   	sFrm.ssn.disabled = true;							// 사용자 식별 아이디 수정 불가
   	sFrm.identify.value = expertPool.userId;			// 사용자 아이디
   	sFrm.nationality.value = expertPool.nationality;	// 국적
   	sFrm.telNo.value = expertPool.telNo;				// 전화번호
   	sFrm.mobileNo.value = expertPool.mobileNo;			// 핸드폰 번호
   	
   	sFrm.jobClass.value = expertPool.jobClass;			// 직업구분
   	sFrm.company.value = expertPool.company;			// 소속기관
   	sFrm.companyId.value = expertPool.companyId;        // 소속기관코드
   	
   	sFrm.selectDept.value = expertPool.dept;			// 부서코드
   	sFrm.deptCode.value = expertPool.dept;				// 부서코드
   	sFrm.deptName.value = ltrim(expertPool.deptName);	// 부서명

	sFrm.selectPosition.value  = expertPool.companyPosition;
   	sFrm.companyPosition.value = expertPool.companyPosition;
   	sFrm.companyPositionName.value = expertPool.companyPositionName;
   	
   	sFrm.companyTelNo.value = expertPool.companyTelNo;
   	sFrm.companyFaxNo.value = expertPool.companyFaxNo;
   	
   	sFrm.zipCode.value = expertPool.zipCode;
   	sFrm.address1.value = expertPool.address1;
   	sFrm.address2.value = expertPool.address2;
   	if(expertPool.zipCode.length == 6)
   	   	sFrm.zipCodeview.value = expertPool.zipCode.substring(0,3) + "-" + expertPool.zipCode.substring(3,6);
   	
   	sFrm.companyZipcode.value = expertPool.companyZipcode;
   	sFrm.companyAddress1.value = expertPool.companyAddress1;
   	sFrm.companyAddress2.value = expertPool.companyAddress2;
   	if(expertPool.companyZipcode.length == 6)
   	   	sFrm.companyZipcodeview.value = expertPool.companyZipcode.substring(0,3) + "-" + expertPool.companyZipcode.substring(3,6);

   	sFrm.email.value = expertPool.email;
	sFrm.prepassword.value = expertPool.password;
   	sFrm.rate.value = expertPool.rate;
   	sFrm.rank.value = expertPool.rank;
   	sFrm.enable.value = expertPool.enable;
   	sFrm.emplNo.value = expertPool.emplNo;
   	sFrm.remark.value = expertPool.remark;

   	sFrm.acctBeginDate.value = expertPool.acctBeginDate;
   	sFrm.acctExpireDate.value = expertPool.acctExpireDate;
   	sFrm.blockDownload.value = expertPool.blockDownload;
   	sFrm.blockLogin.value = expertPool.accountNonLocked;

   	sFrm.selectMenu.value = expertPool.role;
   	sFrm.menuCode.value = expertPool.role;
   	$('pwdReset').show();

   	// 소속기관 컨트롤 속성 지정
   	var jobClass = expertPool.jobClass;
   	var objCompany = document.getElementById("company");
   	var objComSearch = document.getElementById("btnCompany");
   	if(jobClass == "A"||jobClass == "H"||jobClass == "J"){
		objCompany.readOnly = true;
   		objCompany.style.width="100%";
		objComSearch.style.display = "none";
   	} else if (jobClass == "I") {
   		objCompany.readOnly = false;
   		objCompany.style.width="100%";
		objComSearch.style.display = "none";
   	} else {
   		objCompany.readOnly = false;
		objCompany.style.width="80";
		objComSearch.style.display = "";
   	}
}

function save_Group() {
	var sFrm = document.forms["orgFrm"];

	var ActionURL = "/action/OrgMngAction.do";	
	ActionURL += "?mode=saveGroupInfo";

	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					//document.location = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolList&pg=48&pageSize=13&";
					document.location.reload();					
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;
}

function save_ExpertPool(){
	if(frm.name.value == ""){
		alert("성명을 입력하시기 바랍니다.");
		frm.name.focus();
		return false;
	}
	if(frm.uid.value == ""){
		alert("주민등록번호를 입력하시기 바랍니다.");
		frm.uid.focus();
		return false;
	}
	if( (frm.jobClass.value == "A" || frm.jobClass.value == "J" || frm.jobClass.value == "H") 
			&& frm.identify.value == "" && frm.enable.value == "1" ) {
			alert("UserId를 입력하시기 바랍니다.");
			frm.identify.focus();
			return false;
	}
	//if ( (frm.jobClass.value != "A" && frm.jobClass.value != "J" && frm.jobClass.value != "C"  && frm.jobClass.value != "H") 
	//		&& frm.enable.value == "1" ) {
	//	alert("상근/전문가그룹/엑스퍼트/RA 외에는 재직여부를 선택할 수 없습니다.");
	//	return false;
	//}
	
	if(frm.email.value.substr(frm.email.value.length-11,frm.email.value.length) == "@kmac.co.kr") {
		AjaxRequest.submit(
				document.forms["frm"],
				{	'url':"/action/OrgMngAction.do?mode=saveUserInfo",
				 	'onSuccess':function(obj){
						webmailForm.user_account.value = frm.email.value;
	                	webmailForm.pass.value = frm.saveMode.value == "INSERT" ? frm.password.value : frm.prepassword.value;
	                	webmailForm.name.value = frm.name.value;
	                	webmailForm.email.value = frm.email.value;
	                	webmailForm.dept_code.value = frm.deptCode.value;
	                	webmailForm.role_code.value = frm.companyPosition.value;
	                	webmailForm.is_encrypt.value = 0;
						webmailForm.action="https://webmail.kmac.co.kr/a_biz/api/make_user.nvd";
						webmailForm.target = "_webmail";
						popup = window.open('', '_webmail', 'toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,left=0,top=0,width=250,height=80');
	//					popup.focus();
	                	webmailForm.submit();
	                	alert("저장하였습니다.");
	                	document.location = "/action/OrgMngAction.do?mode=orgMngPage";
					},
				 	'onLoading':function(obj){},
				 	'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;
	} else {
		AjaxRequest.submit(
				document.forms["frm"],
				{	'url':"/action/OrgMngAction.do?mode=saveUserInfo",
				 	'onSuccess':function(obj){
				 		alert("저장하였습니다.");
				 		document.location = "/action/OrgMngAction.do?mode=orgMngPage";
				 	},
				 	'onLoading':function(obj){},
				 	'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				 }
		); status = null;
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
						
				alert("데이터를 가져오지 못했습니다.");
			}//END -- onError
		});// END -- status
	status = null;	
	
}
function passwordResetInfoShow() {
	$('passwordResetInfo').style.top = 320;
	$('passwordResetInfo').style.left = 335;
	$('passwordResetInfo').show();
}
function passwordResetInfoHide(){
	$('passwordResetInfo').hide();
}
		
function passwordReset() {
	if($F('newPwd') ==""){alert("초기화 할 비밀번호를 입력하세요.");return;}
	//alert("ssn: " + $F('ssn'));
	var sFrm = document.forms["frm"];
	var status = AjaxRequest.submit(
			sFrm, 
			{	'url': "/action/ExpertPoolManagerAction.do?mode=setPasswordReset",
				'parameters' : { "ssn": $F('ssn'), "newPwd": $F('newPwd')}, 
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					webmailForm.user_account.value = $F('email');
                	webmailForm.new_pass.value = res.encPwd;
                	webmailForm.is_encrypt.value = 1;
                	webmailForm.target = "_webmail";
					popup = window.open('', '_webmail', 'toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,left=0,top=0,width=250,height=80');
					webmailForm.action="https://webmail.kmac.co.kr/a_biz/api/change_pass.nvd";
					webmailForm.target = "hiddenifr"
                	webmailForm.submit();
					alert('비밀번호를 초기화하였습니다.');
					$('passwordResetInfo').hide();
					$('newPwd').value = "";
					LoadUserInfo($('searchSsn').value);
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("비밀번호 초기화 실패");
				}
			}
	); status = null;
}

function viewExpertPoolWorkPeriodList_HR(){
	var url = "/action/ExpertPoolManagerAction.do?mode=getExpertPoolWorkPeriodList_HR";
	var sFeatures = "top=50,left=50,width=500,height=600,resizable=no,scrollbars=yes";
	var detailWin = window.open(url, "ExpertPoolWorkPeriodList_HR", sFeatures);
	detailWin.focus();
}
function saveListToExcel() {
	var surl = '/action/ExpertPoolManagerAction.do?mode=saveExpertPoolListToExcel';
	//surl += "&jobClass=" + document.frm.jobClass.value;
	//surl += "&name=" + document.frm.name.value;
	document.location = surl;
}
</script>
</head>
<body onload="Page_OnLoad();">
<span id="guide" style="color:#999"></span>
<form name="webmailForm" method="post">
	<input name="id" value="" type="hidden" />
	<input name="user_account" value="" type="hidden" />
	<input name="new_pass" value="" type="hidden" />
	<input name="pass" value="" type="hidden" />
	<input name="cmd" value="" type="hidden" />
	<input name="is_encrypt" value="" type="hidden" />
	<input name="email" value="" type="hidden" />
	<input name="name" value="" type="hidden" />
	<input name="dept_code" value="" type="hidden" />
	<input name="role_code" value="" type="hidden" />
</form>
<iframe id="hidden" name="hiddenifr" src="" style="display: none;"></iframe>
<%-- 작업영역 --%>
<div id="PageFull">
<div style="margin: 70 0 0 20 ">
<table width="100%"  cellpadding="0" cellspacing="0">
	<tr>
		<td height="12">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="조직 및 인력 관리" />
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>	
	<tr>
		<td>
			<table cellSpacing="0" cellpadding="0" width="100%" >
				<tr>
					<td class="detailTableField_left" width="190"><input type="checkbox" value="Y" id="isAll" onclick="YAHOO.hynix.hynixOrgTree.init();">비활성화 조직 포함</td>
					<td width="4"></td>
					<td width="*">
						<table cellSpacing="0" cellpadding="0" width="100%" >
							<tr> 
								<td class="detailTableTitle_center" width="108px">검색조건</td>
								<td class="detailTableField_left" width="*">
									<select id="searchType" class="selectboxPopup" style="width:85px;"> 
										<OPTION value="name">이름</OPTION> 
										<OPTION value="uid">주민번호</OPTION>
										<OPTION value="ssn">고유식별번호</OPTION>
									</select>
									<input type="text" name="searchSsn" id="searchSsn" class="contentInput_left" style="width:250px;" 
									onkeypress="if($F('searchType')=='ssn'){searchFlag = 'ssn'}else{searchFlag = 'name'} if(event.keyCode == 13) LoadUserInfo(this.value);">
									<a href="#" onclick="if($F('searchType')=='ssn'){searchFlag = 'ssn'}else{searchFlag = 'name'}LoadUserInfo($('searchSsn').value);"><img src="/images/btn_search.jpg"></a>
								</td>
							</tr>
						</table>							
					</td>
				</tr>
			</table> 
		</td>
	</tr>
	<tr>
		<td height="3"></td>
	</tr>	 
	<tr>
		<td>
			<table cellSpacing="0" cellpadding="0" width="100%" style="table-layout: fixed;" border="0">
				<tr>
					<td width="200" valign="top" style="padding:0px;">
						<%@ include file="/system/orgmng/orgMngTree.jsp"%>
					</td>
					<td width="1"></td>
					<td width="*" valign="top" style="padding:0px; border-width:1; border-color:#c7c7c7; border-style:solid;">
						<div id="divExpert" style="overflow:auto; width:100%; height:450px;">
							<form name="frm" method="post">
								<input type="hidden" name="saveMode" id="saveMode" value="INSERT"/>
								<table width="99%" height="350" border="0" align="center">
									<tr>
										<td width="*" rowspan="7" align="center">
											<img id="MyPic" src="/images/im_noimage.jpg" width="110" border="0">
											<input type="hidden" name="photoId" id="photoId"/> 
										</td>
										<td class="detailTableTitle_center" width="75">이름</td>
										<td class="detailTableField_left" width="130"> 
											<input type="text" name="name" id="name" class="contentInput_left"/>
										</td>
										<td class="detailTableTitle_center" width="75">주민번호/<br>고유번호</td>
										<td class="detailTableField_left" width="130">
											<input type="text" name="uid" id="uid" class="contentInput_left" maxlength="13" onblur="checkSsn();" style="ime-mode:disabled;" onchange="Number_Only(this, -1, -1);">
											<input type="text" name="ssn" id="ssn" class="contentInput_left" maxlength="13" style="ime-mode:disabled;" readonly="readonly">
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">姓名</td>
										<td class="detailTableField_left">
											<input type="text" name="chnName" id="chnName" class="contentInput_left"/>
										</td>
										<td class="detailTableTitle_center">성별</td>
										<td class="detailTableField_left">
											<select name="gender" id="gender" class="selectboxPopup" style="width:100%"> 
												<option value="1">남자</option>
												<option value="0">여자</option> 
											</select>
										</td> 
									</tr>
									<tr>
										<td class="detailTableTitle_center">Name</td>
										<td class="detailTableField_left">
											<input type="text" name="engName" id="engName" style="ime-mode:inactive" class="contentInput_left"/>
										</td>
										<td class="detailTableTitle_center">국적</td>
										<td class="detailTableField_left">
											<code:codelist tableName="NATIONALITY" attribute=" name='nationality' id='nationality' class='selectboxPopup' style='width:100%;'" isSelectBox="Y" all="N" selectedInfo="KOR" />
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">자택전화</td>
										<td class="detailTableField_left">
											<input type="text" name="telNo" id="telNo" maxlength="13" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" class="contentInput_left"/>						
										</td>
										<td class="detailTableTitle_center">휴대전화</td>
										<td class="detailTableField_left">
											<input type="text" name="mobileNo" id="mobileNo" maxlength="13" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" class="contentInput_left"/>
										</td>
									</tr>
									<tr> 
										<td class="detailTableTitle_center">직업구분</td>
										<td class="detailTableField_left">
											<code:codelist tableName="EMP_WORKING_TYPE" attribute=" name='jobClass' id='jobClass' class='selectboxPopup' onchange='jobClass_Select(this);' style='width:100%;' " isSelectBox="Y" all="N" selectedInfo="" />
										</td>
										<td class="detailTableTitle_center">소속기관</td>
										<td class="detailTableField_left">
											<input type="text"		name="company"		id="company" value="KMAC" class="contentInput_left" style="width:67;" readOnly/>
											<input type="hidden"	name="companyId"	id="companyId" value=""/>
											<!-- <input type="button"	name="btnCompany"	value="검색" class="btnBox" onclick="search_Company();"> -->
											<img src="/images/btn_src.jpg" name="btnCompany" id="btnCompany" alt="검색" width="38" height="21" align="absmiddle" border="0" style="cursor:hand;" onClick="search_Company();"/>
										</td>
									</tr>
									<tr> 
										<td class="detailTableTitle_center">소속부서</td>
										<td class="detailTableField_left">
											<input type="hidden" name="deptCode" id="deptCode" value="" /> 
											<input type="text" name="deptName" id="deptName" value="" class="contentInput_left" style="display:none; width:100%;"/>
											<select name="selectDept" id="selectDept" class="selectboxPopup" onchange="companyDept_Set(this);" style="display:inline; width:100%;">
											</select> 
										</td>
										<td class="detailTableTitle_center">직위</td> 
										<td class="detailTableField_left">
											<input type="hidden" name="companyPosition" id="companyPosition" value="" class="contentInput_left">
											<input type="text" name="companyPositionName" id="companyPositionName" value="" class="contentInput_left" style="display:none">
											<select name="selectPosition" id="selectPosition" class="selectboxPopup" onchange="companyPosition_Set(this);" style="width:100%;" >
											</select>								
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">직장전화</td>
										<td class="detailTableField_left">
											<input type="text" name="companyTelNo" id="companyTelNo" maxlength="13" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" class="contentInput_left"/>
										</td>
										<td class="detailTableTitle_center">FAX</td>
										<td class="detailTableField_left">
											<input type="text" name="companyFaxNo" id="companyFaxNo" maxlength="13" onkeypress="PhoneNumber_Only();" style="ime-mode:disabled;" class="contentInput_left"/>
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">자택주소</td>
										<td class="detailTableField_left" colSpan="4">
											<input type="hidden"	name="zipCode"		id="zipCode">
											<input type="text"		name="zipCodeview"	id="zipCodeview" 		class="contentInput_left" style="width:70px;" />
											<a href="javascript:zipCodeWin_Open('callBackByZipWin1');"><img alt="우편번호찾기" src="/images/btn_code.jpg" border=0></a>											
											<input type="text"		name="address1"	id="address1" 		class="contentInput_left" style="width:268px;" ><br>
											<input type="text"		name="address2"	id="address2"		class="contentInput_left" style="width:100%">
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">직장주소</td>
										<td class="detailTableField_left" colSpan="4">
											<input type="hidden" name="companyZipcode" id="companyZipcode"/>
											<input type="text"	 name="companyZipcodeview" id="companyZipcodeview" class="contentInput_left" style="width:70px;" />
											<a href="javascript:zipCodeWin_Open('callBackByZipWin2');"><img alt="우편번호찾기" src="/images/btn_code.jpg" border=0></a>
											<input type="text"	 name="companyAddress1" id="companyAddress1"   class="contentInput_left" size="38" style="width:268px;" /><br>
											<input type="text"	 name="companyAddress2" id="companyAddress2"   class="contentInput_left" style="width:100%">
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">UserId</td>
										<td class="detailTableField_left" colspan="2">
											<input type="text" name="identify" id="identify" value="" class="contentInput_left" onblur="checkUserId();" style="width:100%;ime-mode:disabled;"> 
										</td>
										<td class="detailTableTitle_center">현 비밀번호</td>
										<td class="detailTableField_left">
											<input type="text" name="prepassword" id="prepassword" class="contentInput_left" style="width:100%">
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">
											새 비밀번호<div id="pwdReset" style="display:none;"><a href="#" onclick="passwordResetInfoShow();">[초기화]</a></div>
										</td>
										<td class="detailTableField_left" colspan="2">
											<input type="password" name="password" id="password" type="password" class="contentInput_left">
										</td>
										<td class="detailTableTitle_center">요율</td>
										<td class="detailTableField_left">
											<input type="text" name="rate" id="rate" class="contentInput_left" />
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">E-mail</td>
										<td class="detailTableField_left" colSpan="2">
											<input type="text" name="email" id="email" onblur="checkEmail();" class="contentInput_left" style="width:100%;ime-mode:disabled;">
										</td>
										<td class="detailTableTitle_center">메뉴권한</td>
										<td class="detailTableField_left">
											<input type="hidden" name="menuCode" id="menuCode" value="" class="contentInput_left">
											<input type="text" name="menuName" id="menuName" value="" class="contentInput_left" style="display:none">
											<select name="selectMenu" id="selectMenu" class="selectboxPopup" onchange="menu_Set(this);" style="width:100%;" >
											</select>
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">입사일/위촉일</td>
										<td class="detailTableField_left" colspan="2">
											<script>
												jQuery(function(){jQuery( "#acctBeginDate" ).datepicker({});});
											</script>
											<fmt:parseDate value="${acctBeginDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="dateVar"/>
											<fmt:formatDate value="${dateVar}" pattern="yyyy-MM-dd" var="beginDate"/>
											<input type="text" name="acctBeginDate" id="acctBeginDate" size="8" value="<c:out value="${beginDate}" />" />  
										</td>
										<td class="detailTableTitle_center">재직여부</td>
										<td class="detailTableField_left">
											<select name="enable" id="enable" class="selectboxPopup" style="width:100%">
												<option value="1">입사</option>
												<option value="0">퇴사</option>
											</select> 
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">퇴사일/해촉일</td>
										<td class="detailTableField_left" colspan="2">
											<script>
												jQuery(function(){jQuery( "#acctExpireDate" ).datepicker({});});
											</script>
											<fmt:parseDate value="${acctExpireDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="dateVar"/>
											<fmt:formatDate value="${dateVar}" pattern="yyyy-MM-dd" var="expireDate"/>
											<input type="text" name="acctExpireDate" id="acctExpireDate" size="8" value="<c:out value="${expireDate}" />" />  
										</td>
										<td class="detailTableTitle_center">다운로드 제한</td>
										<td class="detailTableField_left" colspan="2">
											<select name="blockDownload" id="blockDownload" class="selectboxPopup" style="width:100%">
												<option value="N">아니오</option>
												<option value="Y">예</option>
											</select> 
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">사번</td>
										<td class="detailTableField_left" colspan="2">
											<input type="text" name="emplNo" id="emplNo" class="contentInput_left">
										</td>
										<td class="detailTableTitle_center"><div id="expertField" style="display:none;">엑스퍼트 분류</div></td>
										<td class="detailTableField_left" colspan="2">
											<code:codelist tableName="EXPERT_FIELD" attribute=" name='rank' id='rank' style='display:none;' class='selectboxPopup' style='width:100%;' " isSelectBox="Y" all="Y" selectedInfo="" />
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">계정잠금</td>
										<td class="detailTableField_left" colspan="2">
											<select name="blockLogin" id="blockLogin" class="selectboxPopup" style="width:100%">
												<option value="1">아니오</option>
												<option value="0">예</option>
											</select> 
										</td>
										<td class="detailTableTitle_center"></td>
										<td class="detailTableField_left" colspan="2"></td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">비고</td>
										<td class="detailTableField_left" colspan="4">
											<input type="text" name="remark" id="remark" class="contentInput_left">
										</td>
									</tr>
								</table>
							</form>
						</div>
						<div id="divOrg" style='overflow:auto;width:100%;height:378; display:none;'>
							<form name="orgFrm" method="post">
							<input type="hidden" name="saveMode" value="INSERT">
								<table  cellSpacing="0" cellpadding="0" width="99%" align="center">
									<tr>
										<td class="detailTableTitle_center" width="110">*Group ID</td>
										<td class="detailTableField_left" width="200">
											<input type="text" name="id" value="" class="contentInput_left" style="width:100%">
										</td>
										<td class="detailTableTitle_center" width="110">*Group 명칭</td>
										<td class="detailTableField_left" width="200">
											<input type="text" name="name" value="" class="contentInput_left" style="width:100%">
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center" width="110">*Group 설명</td>
										<td class="detailTableField_left">
											<input type="text" name="description" value="" class="contentInput_left" style="width:100%">
										</td>
										<td class="detailTableTitle_center" width="110">*정렬순서</td>
										<td class="detailTableField_left">
											<input type="hidden" name="groupType" value="2000">
											<input type="text" name="seq" value="" class="contentInput_left" style="width:100%">
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center" width="110">*부모Group</td>
										<td class="detailTableField_left">
											<input type="hidden" name="parentId">											
											<input type="text" name="parentName" value="" class="contentInput_left" style="width:100%" readonly>
										</td>
										<td class="detailTableTitle_center" width="110">*path</td>
										<td class="detailTableField_left">
											<input type="text" name="path" value="" class="contentInput_left" style="width:100%">
										</td>
									</tr>
									<tr>
										<td class="detailTableTitle_center" width="110">*depth</td>
										<td class="detailTableField_left">
											<input type="text" name="depth" value="" class="contentInput_left" style="width:100%">
										</td>
										<td class="detailTableTitle_center" width="110">*사용여부</td>
										<td class="detailTableField_left">
											<select name="enable" class="selectboxPopup" style="width:100%">
												<option value="1">사    용</option>
												<option value="0">사용안함</option>
											</select>
										</td>
									</tr>
								</table>
							</form> 
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="right" height="30" valign="bottom">
			<div class="btNbox txtR">
				<a id="btnNewOrg" title="인력POOL" class="btN3fac0c txt2btn" href="#" onclick="saveListToExcel();">EXCEL 다운로드</a>
				<a id="btnNewOrg" title="신규조직" class="btN006bc6 txt2btn" href="#" onclick="viewExpertPoolWorkPeriodList_HR();">채용권한</a>
				<a id="btnNewOrg" title="신규조직" class="btN006bc6 txt2btn" href="#" onclick="clear_Form('GROUP');">신규조직</a>
				<a id="btnNewUser" title="신규유저" class="btN006bc6 txt2btn" href="#" onclick="clear_Form('USER');">신규유저</a>
				<a id="btnSave" title="신규조직" class="btNe006bc6 txt2btn" href="#" onclick="btnSave_click();">저장</a>
			</div>
		</td>
	</tr>	
</table>
</div>
<div id="searchResult" style="display: none; background-color: #E7FFE7; color: #666666; position: absolute; top: 130px; left: 413px; width: 303; border-style: solid; border-width: 1px; padding-left: 5px; padding-right: 5px; padding-top: 5px; padding-bottom: 5px; ">
</div>
<div id="passwordResetInfo" 
	style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 3px;">
	<table cellpadding="3" cellspacing="3" width="100%">
		<tr>
			<td><h4 class="subTitle">비밀번호 초기화</h4></td>
		</tr>
		<tr>
			<td>
				<table id="passwordResetInfoTable" width="100%">
					<tr>
						<td class="detailTableTitle_center" width="45%">새 비밀번호</td>
						<td class="searchField_center"><input type="password" class="textInput_left" id="newPwd" name="newPwd"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<div class="btNbox mt5 txtR">
					<a class="btNe006bc6 txt2btn" href="#" onclick="passwordReset();">저장</a>
					<a class="btNa0a0a0 txt2btn" href="#" onclick="passwordResetInfoHide();">닫기</a>
				</div>	
			</td>
		</tr>
		<tr>
			<td height="5px"></td>
		</tr>
	</table>
</div>
</div>
</body>
</html>