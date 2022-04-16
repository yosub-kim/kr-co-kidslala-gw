<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>조직도 검색</title>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<%-- ====================== YUI CSS include jsp ============= --%>
<link type="text/css" rel="stylesheet" href="/js/yui/treeview/assets/skins/sam/treeview.css" />

<meta name="viewport" content="width=1600">
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
var searchValue = "<%=request.getAttribute("searchValue")%>";

function ExpertPool(){
	ssn                 = "";
	uid					= "";
	name                = "";
	enable              = "";
	absence             = "";
	gender              = "";
	nationality         = "";
	telNo               = "";
	mobileNo            = "";
	zipCode             = "";
	address1            = "";
	address2            = "";
	company             = "";
	dept                = "";
	deptName            = "";
	companyPosition     = "";
	companyPositionName = "";
	jobClass            = "";
	companyZipcode      = "";
	companyAddress1     = "";
	companyAddress2     = "";
	companyTelNo        = "";
	companyFaxNo        = "";
	email               = "";
	indField            = "";
	funcField           = "";
	consultingMajor     = "";
	consultingMinor     = "";
	consultingDetail    = "";
	rate                = "";
	Role                = "";
	extRole             = "";
	resume              = "";
	companyId           = "";

	minAmt				= "";
	maxAmt				= "";
	minEdu				= "";
	maxEdu				= "";
	minMMAmt			= "";
	maxMMAmt			= "";

}
function doSearch(){
	var sURL = "/action/ExpertPoolManagerAction.do?mode=searchExpert&searchName=" + encodeURIComponent($("searchText").value);
	orgTab.changeTab('pane2', sURL);
}
function selectExpert(){
	var checkedExpertList = $$('input[name="chkExpertPool"]');
	
	var expertPoolList = new Array();
	
	for(var i = 0 ; i < checkedExpertList.length ; i++) {
		
		if(checkedExpertList[i].checked){
			
			var expertPool = expertPoolByCheckBox(checkedExpertList[i]);
			expertPoolList.push(expertPool);
		}
	}
	
	if(expertPoolList.length > 0)	
		selectedTable_addRow(expertPoolList);
}

function deleteExpert(){
	var expertPoolList = new Array();
	var selectedExpert = $$('input[name="chkSelectedExpert"]');
	if(selectedExpert.length > 0) {
		for(var i = 0; i < selectedExpert.length ; i++) {
			if(selectedExpert[i].checked) {
				var pNode = selectedExpert[i].parentNode.parentNode.parentNode.parentNode;
				pNode.removeChild(selectedExpert[i].parentNode.parentNode.parentNode);
			}
		}
	}
}

function selectedTable_addRow(expertPoolList) {
	var table     = document.getElementById("exprtListTable");
	for(var i = 0 ; i < expertPoolList.length ; i++) {
		var kk = $$('input[name="chkSelectedExpert"][exprt_ssn="'+ expertPoolList[i].ssn +'"]');
		
		if(kk.length == 0) {
			var tr        = table.insertRow(-1); 
			var tdCheck   = tr.insertCell(-1);
			tdCheck.align = "center";
			var chkBox = document.createElement("input");
			chkBox.type = "checkbox";
			chkBox.name = "chkSelectedExpert";
			chkBox.className="btn_check";
			chkBox.id=expertPoolList[i].ssn;
	
			expertPoolToCheckBox(expertPoolList[i],chkBox);
	
			var checkLabel= document.createElement("label");
			checkLabel.setAttribute("for", expertPoolList[i].ssn);
			var checkUl = document.createElement("ul");
			var checkLi = document.createElement("li");
			
			checkUl.appendChild(checkLi);
			checkUl.appendChild(chkBox);
			checkUl.appendChild(checkLabel);
			
			tdCheck.appendChild(checkUl);
			
			var tdName    = tr.insertCell(-1);
			tdName.align = "center";
			tdName.innerText = expertPoolList[i].name;
			
			var tdJobclass = tr.insertCell(-1);
			tdJobclass.align = "center";
			if(expertPoolList[i].jobClass == 'A' || expertPoolList[i].jobClass == 'B')
				tdJobclass.innerText = '상근';
			else if(expertPoolList[i].jobClass == 'J' )
				tdJobclass.innerText = '상임';
			else if(expertPoolList[i].jobClass == 'N')
				tdJobclass.innerText = 'RA';
			else if(expertPoolList[i].jobClass == 'C')
				tdJobclass.innerText = '엑스퍼트';
			else if(expertPoolList[i].jobClass == 'D')
				tdJobclass.innerText = '산업계강사';
			else if(expertPoolList[i].jobClass == 'E')
				tdJobclass.innerText = '대학교수';
			else
				tdJobclass.innerText = '기타';
			
			var tdCompany = tr.insertCell(-1);
			tdCompany.align = "center";
			tdCompany.innerText = expertPoolList[i].company;
			
			/* var tdBirth   = tr.insertCell(-1);
			tdBirth.align = "center";
			tdBirth.innerText = ssnToBirthday(expertPoolList[i].uid); */
			
			/* var tdEmail = tr.insertCell(-1);
			tdEmail.align = "left";
			tdEmail.innerText = expertPoolList[i].email; */
		}
	}
}

function expertPoolToCheckBox(expertPool, chkBox) {
	chkBox.exprt_ssn                 = expertPool.ssn;                  
	chkBox.exprt_uid                 = expertPool.uid;                  
	chkBox.exprt_name                = expertPool.name;                 
	chkBox.exprt_enable              = expertPool.enable;               
	chkBox.exprt_absence             = expertPool.absence;              
	chkBox.exprt_gender              = expertPool.gender;               
	chkBox.exprt_nationality         = expertPool.nationality;          
	chkBox.exprt_telNo               = expertPool.telNo;                
	chkBox.exprt_mobileNo            = expertPool.mobileNo;             
	chkBox.exprt_zipCode             = expertPool.zipCode;              
	chkBox.exprt_address1            = expertPool.address1;             
	chkBox.exprt_address2            = expertPool.address2;             
	chkBox.exprt_company             = expertPool.company;              
	chkBox.exprt_dept                = expertPool.dept;             
	chkBox.exprt_deptName            = expertPool.deptName;                 
	chkBox.exprt_companyPosition     = expertPool.companyPosition;      
	chkBox.exprt_companyPositionName = expertPool.companyPositionName;  
	chkBox.exprt_jobClass            = expertPool.jobClass;             
	chkBox.exprt_companyZipcode      = expertPool.companyZipcode;       
	chkBox.exprt_companyAddress1     = expertPool.companyAddress1;      
	chkBox.exprt_companyAddress2     = expertPool.companyAddress2;      
	chkBox.exprt_companyTelNo        = expertPool.companyTelNo;         
	chkBox.exprt_companyFaxNo        = expertPool.companyFaxNo;         
	chkBox.exprt_email               = expertPool.email;                
	chkBox.exprt_indField            = expertPool.indField;             
	chkBox.exprt_funcField           = expertPool.funcField;            
	chkBox.exprt_consultingMajor     = expertPool.consultingMajor;      
	chkBox.exprt_consultingMinor     = expertPool.consultingMinor;      
	chkBox.exprt_consultingDetail    = expertPool.consultingDetail;
	chkBox.exprt_rate                = expertPool.rate;                
	chkBox.exprt_role                = expertPool.role;                 
	chkBox.exprt_extRole             = expertPool.extRole;              
	chkBox.exprt_resume              = expertPool.resume;               
	chkBox.exprt_companyId           = expertPool.companyId;

	chkBox.exprt_minAmt				 =expertPool.minAmt;
	chkBox.exprt_maxAmt				 =expertPool.maxAmt;
	chkBox.exprt_minEdu				 =expertPool.minEdu;
	chkBox.exprt_maxEdu				 =expertPool.maxEdu;
	chkBox.exprt_minMMAmt			 =expertPool.minMMAmt;
	chkBox.exprt_maxMMAmt			 =expertPool.maxMMAmt;
}

function expertPoolByCheckBox(chkBox) {
	var expertPool = new ExpertPool();
	expertPool.ssn                 = chkBox.exprt_ssn != undefined ? chkBox.exprt_ssn : chkBox.getAttribute("exprt_ssn");
	expertPool.uid                 = chkBox.exprt_uid != undefined ? chkBox.exprt_uid : chkBox.getAttribute("exprt_uid");
	expertPool.name                = chkBox.exprt_name != undefined ? chkBox.exprt_name : chkBox.getAttribute("exprt_name");
	expertPool.enable              = chkBox.exprt_enable != undefined ? chkBox.exprt_enable : chkBox.getAttribute("exprt_enable");
	expertPool.absence             = chkBox.exprt_absence != undefined ? chkBox.exprt_absence : chkBox.getAttribute("exprt_absence");
	expertPool.gender              = chkBox.exprt_gender != undefined ? chkBox.exprt_gender : chkBox.getAttribute("exprt_gender");
	expertPool.nationality         = chkBox.exprt_nationality != undefined ? chkBox.exprt_nationality : chkBox.getAttribute("exprt_nationality");
	expertPool.telNo               = chkBox.exprt_telNo != undefined ? chkBox.exprt_telNo : chkBox.getAttribute("exprt_telNo");
	expertPool.mobileNo            = chkBox.exprt_mobileNo != undefined ? chkBox.exprt_mobileNo : chkBox.getAttribute("exprt_mobileNo");
	expertPool.zipCode             = chkBox.exprt_zipCode != undefined ? chkBox.exprt_zipCode : chkBox.getAttribute("exprt_zipCode");
	expertPool.address1            = chkBox.exprt_address1 != undefined ? chkBox.exprt_address1 : chkBox.getAttribute("exprt_address1");
	expertPool.address2            = chkBox.exprt_address2 != undefined ? chkBox.exprt_address2 : chkBox.getAttribute("exprt_address2");
	expertPool.company             = chkBox.exprt_company != undefined ? chkBox.exprt_company : chkBox.getAttribute("exprt_company");
	expertPool.dept                = chkBox.exprt_dept != undefined ? chkBox.exprt_dept : chkBox.getAttribute("exprt_dept");
	expertPool.deptName            = chkBox.exprt_deptName != undefined ? chkBox.exprt_deptName : chkBox.getAttribute("exprt_deptName");
	expertPool.companyPosition     = chkBox.exprt_companyPosition != undefined ? chkBox.exprt_companyPosition : chkBox.getAttribute("exprt_companyPosition");
	expertPool.companyPositionName = chkBox.exprt_companyPositionName != undefined ? chkBox.exprt_companyPositionName : chkBox.getAttribute("exprt_companyPositionName");
	expertPool.jobClass            = chkBox.exprt_jobClass != undefined ? chkBox.exprt_jobClass : chkBox.getAttribute("exprt_jobClass");
	expertPool.companyZipcode      = chkBox.exprt_companyZipcode != undefined ? chkBox.exprt_companyZipcode : chkBox.getAttribute("exprt_companyZipcode");
	expertPool.companyAddress1     = chkBox.exprt_companyAddress1 != undefined ? chkBox.exprt_companyAddress1 : chkBox.getAttribute("exprt_companyAddress1");
	expertPool.companyAddress2     = chkBox.exprt_companyAddress2 != undefined ? chkBox.exprt_companyAddress2 : chkBox.getAttribute("exprt_companyAddress2");
	expertPool.companyTelNo        = chkBox.exprt_companyTelNo != undefined ? chkBox.exprt_companyTelNo : chkBox.getAttribute("exprt_companyTelNo");
	expertPool.companyFaxNo        = chkBox.exprt_companyFaxNo != undefined ? chkBox.exprt_companyFaxNo : chkBox.getAttribute("exprt_companyFaxNo");
	expertPool.email               = chkBox.exprt_email != undefined ? chkBox.exprt_email : chkBox.getAttribute("exprt_email");
	expertPool.indField            = chkBox.exprt_indField != undefined ? chkBox.exprt_indField : chkBox.getAttribute("exprt_indField");
	expertPool.funcField           = chkBox.exprt_funcField != undefined ? chkBox.exprt_funcField : chkBox.getAttribute("exprt_funcField");
	expertPool.consultingMajor     = chkBox.exprt_consultingMajor != undefined ? chkBox.exprt_consultingMajor : chkBox.getAttribute("exprt_consultingMajor");
	expertPool.consultingMinor     = chkBox.exprt_consultingMinor != undefined ? chkBox.exprt_consultingMinor : chkBox.getAttribute("exprt_consultingMinor");
	expertPool.consultingDetail    = chkBox.exprt_consultingDetail != undefined ? chkBox.exprt_consultingDetail : chkBox.getAttribute("exprt_consultingDetail");
	expertPool.rate                = chkBox.exprt_rate != undefined ? chkBox.exprt_rate : chkBox.getAttribute("exprt_rate");
	expertPool.role                = chkBox.exprt_role != undefined ? chkBox.exprt_role : chkBox.getAttribute("exprt_role");
	expertPool.extRole             = chkBox.exprt_extRole != undefined ? chkBox.exprt_extRole : chkBox.getAttribute("exprt_extRole");
	expertPool.resume              = chkBox.exprt_resume != undefined ? chkBox.exprt_resume : chkBox.getAttribute("exprt_resume");
	expertPool.companyId           = chkBox.exprt_companyId != undefined ? chkBox.exprt_companyId : chkBox.getAttribute("exprt_companyId");
	expertPool.minAmt			   = chkBox.exprt_minAmt != undefined ? chkBox.exprt_minAmt : chkBox.getAttribute("exprt_minAmt");
	expertPool.maxAmt       	   = chkBox.exprt_maxAmt != undefined ? chkBox.exprt_maxAmt : chkBox.getAttribute("exprt_maxAmt");
	expertPool.minEdu              = chkBox.exprt_minEdu != undefined ? chkBox.exprt_minEdu : chkBox.getAttribute("exprt_minEdu");
	expertPool.maxEdu              = chkBox.exprt_maxEdu != undefined ? chkBox.exprt_maxEdu : chkBox.getAttribute("exprt_maxEdu");
	expertPool.minMMAmt			   = chkBox.exprt_minMMAmt != undefined ? chkBox.exprt_minMMAmt : chkBox.getAttribute("exprt_minMMAmt");
	expertPool.maxMMAmt       	   = chkBox.exprt_maxMMAmt != undefined ? chkBox.exprt_maxMMAmt : chkBox.getAttribute("exprt_maxMMAmt");

	return expertPool;
}                        

function ssnToBirthday(ssn) {
	if(ssn.length == 13){
		var k = ssn.substring(6,7);
		var birthYear = "";
		if((k=="1")||(k=="2"))
			birthYear = "19" + ssn.substring(0,2);
		else if((k=="3")||(k=="4"))
			birthYear = "20" + ssn.substring(0,2);
		else
			birthYear = "";
		if(birthYear != ""){
			birthYear = birthYear + "." + ssn.substring(2,4) + "." + ssn.substring(4,6);
		}
	}else{
		birthYear = "";
	}
	return birthYear;
}
function moveDataToRight(objNode) {
	//alert(objNode.USER_ID);
	//alert(objNode);
	var ActionURL = "/action/ExpertPoolManagerAction.do?mode=expertSelect";
	ActionURL += "&ssn=" + objNode.getAttribute("USER_ID");
	// alert("페이지 로드");
	var status = AjaxRequest.get(
		{
			'url':ActionURL
			, 'anotherParameter':'false'
			, 'onSuccess':function(obj){  // 
				// alert(obj.responseText);
	           	var res = eval('(' + obj.responseText + ')');
	           	var resList = new Array();
	           	resList.push(res.expertPool);
	           	selectedTable_addRow(resList);
          
			}// END  -- onSuccess
			, 'onLoading':function(obj){}
			, 'onError':function(obj){
						
				alert("데이터를 가져오지 못 했습니다.");
			}//END -- onError
		});// END -- status
	status = null;	
}
function find_Complete() {
	var expertPoolList = new Array();
	var selectedExpert = $$('input[name="chkSelectedExpert"]');
	if(selectedExpert.length > 0) {
		for(var i = 0; i < selectedExpert.length ; i++) {
			var expertPool = expertPoolByCheckBox(selectedExpert[i]);
			expertPoolList.push(expertPool);
		}
	
		opener.<c:out value="${callbackFunc }" />(expertPoolList);
	}
	window.close();
}
function Page_Load() {
	document.getElementById("searchText").focus();
	if(searchValue != ""){
		doSearch();
	}
	layer_open(this, 'pop_register');
}

</script>
</head>
 
<body onLoad="Page_Load();">
	<div id="pop_register" class="popup_layer pop_register">
		<div class="popup_inner tbl-sc" style="width:800px; ">
			<div class="a-both">
				<p class="h1">조직도 검색</p>
			</div>
			<div class="a-both">
				<div>
					<div class="sign_area">
						<div class="tab_menu_link_tit">
							<ul class="tab_menu_link">
								<li><a href="#" class="active" id="pane1" >조직도</a></li>
								<li><a href="#" id="pane2">검색결과</a></li>
							</ul>
						</div>
					</div>
					<div style="height:20px;"></div>
					<div id="Process_container" class="tabbed-container" style="width:360px">
						<div id="Process_overlay" class="overlay" style="display: none">
							<img alt="" src="/images/loading.gif" align="middle" >	 
						</div>
						<div id="Process" class="pane"  style='border-width:1; border-color: #c7c7c7; border-style: solid; border-collapse:collapse; margin: 0px;'></div>
					</div> 
					<script type="text/javascript">
						var orgTab = new TabbedPane('Process', 
							{
								'pane1': '/common/org/orgTree.jsp',
								'pane2': '/action/ExpertPoolManagerAction.do?mode=searchExpert'
							},
							{
								onClick: function(e) {
									$('Process_overlay').show();
								},
								
								onSuccess: function(e) {
									$('Process_overlay').hide();
								}
							}); 
					</script>
				</div>
				<td width="*" valign="middle" align="center"> 
					&nbsp&nbsp
					<table>
						<tr><td><a href="javascript:selectExpert();" class="btn_arrow"><i class="mdi mdi-chevron-right-box"></i></a></td></tr>
						<tr><td><br></td></tr>
						<tr><td><a href="javascript:deleteExpert();" class="btn_arrow"><i class="mdi mdi-chevron-left-box"></i></a></td></tr>
					</table>
					&nbsp&nbsp	
				</td> 
				<div>
					<br><br>
					<div class="board_contents">
						<table class="tbl-edit td-c pd-none" border="0" width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse; ">
							<colgroup> 
								<col width="20%">
								<col width="*"> 
								<col width="20%"> 
							</colgroup>
							<tr> 
								<th>성명</th>
								<td><input type="text" name="searchText"  id="searchText" 
									onkeypress="if(event.keyCode == 13) doSearch();" value="<c:out value="${searchValue}" />"></td>
								<td><button type="button" class="btn btn_blue" onclick="location.href='javascript:doSearch();'">검색</button></td>
							</tr>
						</table>
					</div>
					<div style="height:10px;"></div>
							<div class="outLine1">
								<div style='overflow:auto;width:100%;height:320; vertical-align:top; border-width:1; border-color: #c7c7c7; border-style: solid; border-collapse:collapse; margin: 0px;'>
									<div style="margin:15px 15px 0 15px;">
									<table class="tbl-edit td-c pd-none" width="100%"> 
										<thead>
											<colgroup> 	
												<col width="10%">
												<col width="30%">
												<col width="30%"> 
												<col width="*"> 
											</colgroup>
											<tr>
												<th>&nbsp;</th>
												<th>이름</th>
												<th>구분</th>
												<th>소속</th>
											</tr>
										</thead>
										<tbody id="exprtListTable">													
											
										</tbody>
									</table>
									</div>
								</div>
							</div>
						</div>
			</div>
				<div class="btn_area">
					<button type="button" class="btn btn_blue" onclick="find_Complete()">등록</button>
					<button type="button" class="btn btn_d_blue" onclick="window.close();">닫기</button>
				</div>
		</div>
	</div>
</body>

</html>					