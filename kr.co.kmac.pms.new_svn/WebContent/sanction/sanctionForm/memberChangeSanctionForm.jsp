<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">

<script type="text/javascript">
	function projectMemberValidation() {
		var addMemberSsnList = $$('input[name="addMemberSsn"]');
		var runningMemberSsnList = $$('input[name="runningMemberSsn"]');

		var addMemberRoleList = $$('select[name="addMemberRole"]');
		var runningMemberRoleList = $$('select[name="runningMemberRole"]');
		
		var runningMemberTrainingYnList = $$('select[name="runningMemberTrainingYn"]');
		var addMemberJobClassList = $$('input[name="addMemberJobClass"]');
		
		var budgetMemberSsnList = $$('input[name="budgetMemberSsn"]');

		var roleArray = new Array();
		addMemberRoleList.each(function(addMemberRole){roleArray.push(addMemberRole.value);});
		runningMemberRoleList.each(function(runningMemberRole){roleArray.push(runningMemberRole.value);});
		var ssnArray = new Array();
		addMemberSsnList.each(function(addMemberSsn){ssnArray.push(addMemberSsn.value);});
		runningMemberSsnList.each(function(runningMemberSsn){ssnArray.push(runningMemberSsn.value);});
		var trainingYnArray = new Array();
		addMemberSsnList.each(function(addMemberSsn){trainingYnArray.push("Y");});
		runningMemberTrainingYnList.each(function(runningMemberTrainingYn){trainingYnArray.push(runningMemberTrainingYn.value);});
		
		var ar=false, pm=false, pl=false; plSsn="";
		for(var i=0; i<roleArray.length; i++){
			if(roleArray[i]=='AR' && trainingYnArray[i]=="Y"){if(!ar){ar=true;}else{alert('추가할 인력과 현재 투입인력에\nCOO가 중복되어 지정되었습니다.');return false;}}
		}
		for(var i=0; i<roleArray.length; i++){
			if(roleArray[i]=='PM' && trainingYnArray[i]=="Y"){if(!pm){pm=true;}else{alert('추가할 인력과 현재 투입인력에\nPM이 중복되어 지정되었습니다.');return false;}}
		}
		for(var i=0; i<roleArray.length; i++){
			if(roleArray[i]=='PL' && trainingYnArray[i]=="Y"){plSsn=ssnArray[i];if(!pl){pl=true;}else{alert('추가할 인력과 현재 투입인력에\nPL이 중복되어 지정되었습니다.');return false;}}
		}
		if(!ar){alert("COO를 선택해 주세요."); return false;}
		if(!pm){alert("PM을 선택해 주세요."); return false;}
		if($('businessTypeCode').value == 'BTA' || $('businessTypeCode').value == 'BTJ'){ 
			if(!pl){alert("PL을 선택해 주세요."); return false;}
			for(var i=0; i<ssnArray.length; i++){
				if(ssnArray[i]==plSsn && roleArray[i]=="MB"){
					alert("PL을 멤버로 중복 지정할 수 없습니다. ");
					return false;
				}
			}
		}
		

		var resValue=true;	
		$$('input[name="runningMemberSsn"]').each(function(runningMemberSsn){
			addMemberSsnList.each(function(addMemberSsn){
				if(runningMemberSsn.value == addMemberSsn.value){
					alert("추가할 인력과 현재 투입인력에 중복된 인력이 있습니다. ");
					resValue = false;return false;
				}
			});
		});
		
		var entno = $F('budgetEntNo');
		var addMemberPositionList = document.getElementsByName("addMemberPosition");
		for (var pos = 0; pos < addMemberJobClassList.length; pos++) {
			if(addMemberJobClassList[pos].value == 'C' && addMemberRoleList[pos].value == 'PL') {
				alert("PL에 엑스퍼트를 지정할 수 없습니다.\n상근 또는 상임을 지정하십시오.");
				resValue = false;return false;
			}
			if(addMemberJobClassList[pos].value == 'C' && addMemberPositionList[pos].value == '') {
				alert("엑스퍼트의 등급을 선택하세요.");
				resValue = false;return false;
			}
			// (대기)인력 프로젝트 투입 제안
			/* if(addMemberJobClassList[pos].value == 'F' || addMemberJobClassList[pos].value == 'L' || addMemberJobClassList[pos].value == 'M') {
				alert("(대기)인력은 인력변경 품의를 진행할 수 없습니다.");
				resValue = false;return false;
			} */
		}

		$$('input[name="addMemberSsn"]').each(function(addMemberSsn){
			if(addMemberSsn.value == ""){
				alert("추가할 인력이 없습니다.\n(인력추가가 없는 경우 빈 행을 삭제하세요)");  
				resValue = false;
				return false;
			}
		});
		if(!resValue){return false;}
		
		$$('input[name="addMemberCost"]').each(function(addMemberCost){
			if(addMemberCost.value == ""){
				alert("총 성과급을 입력하세요.");
				//alert("기준금액을 입력하세요. \n\n※ 입력안내 \n\n 1) 컨설팅/리서치/진단평가/국제사업 \n     - 상근/상임/엑스퍼트: MD금액 또는 MM금액 입력 \n     - 그 외: 0 입력\n\n 2) 컨설팅/리서치/진단평가/해외연수 외 \n     - 상근: 0 입력 \n     - 상임/엑스퍼트: 시간당 금액 입력 \n     - 그 외: 0 입력");
				resValue = false;
				return false;
			}
		});
		if(!resValue){return false;}
		
		$$('input[name="addMemberCost"]').each(function(addMemberCost){
			if(addMemberCost.value == "0"){
				alert("총 성과급 0은 불가능합니다.");
				//alert("기준금액을 입력하세요. \n\n※ 입력안내 \n\n 1) 컨설팅/리서치/진단평가/국제사업 \n     - 상근/상임/엑스퍼트: MD금액 또는 MM금액 입력 \n     - 그 외: 0 입력\n\n 2) 컨설팅/리서치/진단평가/해외연수 외 \n     - 상근: 0 입력 \n     - 상임/엑스퍼트: 시간당 금액 입력 \n     - 그 외: 0 입력");
				resValue = false;
				return false;
			}
		});
		if(!resValue){return false;}
		
		/* if($("approvalType").value == "M"){
			//상근,전문가,RA
			//실수주단가, 기여매출단가 주석
			$$('input[name="addMemberContributionCost"]').each(function(addMemberContributionCost){
				if(addMemberContributionCost.value == ""){
					var type;
					if ($("businessTypeCode").value == 'BTA' || $("businessTypeCode").value == 'BTD' || $("processTypeCode").value == 'N4') {
						type = "실수주단가";
					} else {
						type = "기여매출단가";
					}
					alert(type + "를 입력하세요. \n\n※ 입력안내 \n\n 1) 입력대상: 컨설팅 프로젝트의 상임\n\n 2) 그 외: 0 입력 ");
					resValue = false;return false;
				}
			});
			if(!resValue){return false;}
		} */ 
		
		/* if($("approvalType").value == "ME" || $("approvalType").value == "MF" || $("approvalType").value == "MG"){//엑스퍼트,강사,교수
			var addMemberStartDate = $$('input[name="addMemberStartDate"]');
			var addMemberEndDate = $$('input[name="addMemberEndDate"]');
			for(var i=0; i < addMemberStartDate.length; i++){
				if(addMemberStartDate[i].value == ""){
					alert("엑스퍼트/산업계강사/대학교수 의 실제 투입기간은 필수 입력 사항 입니다. \n인력정보 "+(i+1)+"번째의 투입 시작 일자를 입력 하세요");	return false; 
				}
				if(addMemberEndDate[i].value == ""){
					alert("엑스퍼트/산업계강사/대학교수 의 실제 투입기간은 필수 입력 사항 입니다. \n인력정보 "+(i+1)+"번째의 투입  종료 일자를 입력 하세요");	return false;
				}
				if(addMemberStartDate[i].value > addMemberEndDate[i].value ){
					alert("인력정보 "+(i+1)+"번째의 투입 인력의 시작일과 종요일을 확인하세요.");	return false;
				}
			}
		} */

		var addMemberCostList = $$('input[name="addMemberCost"]');
		//실수주단가 주석
		/* var addMemberContributionCostList = $$('input[name="addMemberContributionCost"]');
		for(var i=0; i<addMemberCostList.length; i++){
			if(addMemberCostList[i].jobClass == 'J' && 
					(addMemberContributionCostList[i].value == '' 
							|| addMemberContributionCostList[i].value < 1 
							|| addMemberContributionCostList[i].value > 2000000 )){
				if ($("businessTypeCode").value == 'BTA') {
					alert("상임 인력의 올바른 실수주단가를 입력하세요.");
					return false;
				}				
			}
		}	 */
		
		if(!resValue){return false;}
		return true;
	}
	
	 var doubleSubmitFlag = false;
	
	 function doubleSubmitCheck(){
		if(doubleSubmitFlag){
			return doubleSubmitFlag;
		}else{
			doubleSubmitFlag = true;
			return false;
	 	}
	}
	 
	function draftRequest(){
		
		if(!projectMemberValidation()){return;}
		
		var ActionURL;
		if($("subject").value == ""){
			alert('제목을 입력하세요.');
			document.memberChangeSanctionForm.subject.focus();
			return;
		}
		if($("content").value == ""){
			alert('품의 내용을 입력하세요.');
			document.memberChangeSanctionForm.content.focus();
			return;
		}
		if($F('cioSsn') == ""){
			alert('승인자를 지정하세요.');return;
		}		
		
		if($('seq').value == '0'){
			ActionURL = "/action/MemberChangeSanctionAction.do?mode=createMemberChangeSanction";
		}else{
			ActionURL = "/action/MemberChangeSanctionAction.do?mode=executeMemberChangeSanction";
			<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT' && sanctionDoc.state != 'SANCTION_STATE_REJECT_DRAFT' }">
				if(memberChangeSanctionForm.isApproved[0].checked == false && memberChangeSanctionForm.isApproved[1].checked == false){
					alert('승인여부를 선택하세요.');return;
				}  
			</c:if>
		}
		
		if(doubleSubmitCheck()) return;
		
		var sFrm = document.forms["memberChangeSanctionForm"];
		
		var status = AjaxRequest.submit(
				sFrm,
				{	'url':ActionURL,
					'onSuccess':function(obj){
						alert('등록하였습니다.');
						if($('seq').value == '0'){
							document.location = "/action/MyProjectAction.do?mode=getMyProjectList";
						}else{
							document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
						}
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;		
	}
	
	function tempSaveWork(){
		var sFrm = document.forms["memberChangeSanctionForm"];
		var status = AjaxRequest.submit(
				sFrm,
				{	'url': "/action/MemberChangeSanctionAction.do?mode=saveMemberChangeSanction",
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						if(res.result == 'SUCCESS'){alert('저장하였습니다.');}						
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;				
	}
	
	function deleteWork(){
		if(confirm("삭제 하시겠습니까?")) {
			var sFrm = document.forms["memberChangeSanctionForm"];
			var status = AjaxRequest.submit(
					sFrm,
					{	'url': "/action/MemberChangeSanctionAction.do?mode=deleteMemberChangeSanction",
						'onSuccess':function(obj){
							var res = eval('(' + obj.responseText + ')');
							if(res.result == 'SUCCESS'){alert('삭제하였습니다.');
								document.memberChangeSanctionForm.target = "";
								document.memberChangeSanctionForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
								document.memberChangeSanctionForm.submit();								
							}		
						},
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("저장할 수 없습니다.");
						}
					}
			);	 
		}
	}
	
	function viewBudgetInfo(){
		var budgetUrl = "https://newbudget.kmac.co.kr:8080/DWPM3/DWPM30100_U_LINK.jsp"
			+ "?empno=<authz:authentication operation="username" />"
			+ "&cmd=MOD&&srch_workgb=1";
			
		if($F('budgetEntNo') != ''){
			budgetUrl  += ("&gubun=N&entno="+$F('budgetEntNo'));
			var sFeatures = "top=100,left=100,width=800,height=600,resizable=yes,scrollbars=yes";
			var detailWin = window.open(budgetUrl,"budgetMemberChange", sFeatures);
			detailWin.focus();
		}else{
			alert('인력변경용 예산 정보가 없습니다.');
		}
	}

	var levelInfo;
	function openExpertPoolPopUp(_levelInfo) {
		levelInfo = _levelInfo;
		orgFinder_Open('setSanctionLevelInfo');	
	}
	
	function setSanctionLevelInfo(expertPoolList){
		if(levelInfo == "refMember"){
			expertPoolList.each(function(expertPool) {
				$('refMemberSsn').value = $('refMemberSsn').value + ($('refMemberSsn').value.length > 0 ? ", ": "") + expertPool.ssn;
				$('refMemberName').value = $('refMemberName').value + ($('refMemberName').value.length > 0 ? ", ": "") +  expertPool.name; 	
			});
		}else if(levelInfo == "entrust"){
			expertPoolList.each(function(expertPool) {
				$('entrustUserSsn').value = expertPool.ssn;
				$('entrustUserName').value = expertPool.name; 	
			});
		}else{
			var n=0;
			var snactionNameList = $$('input[sanctionInfo="name"]');
			var snactionSsnList = $$('input[sanctionInfo="ssn"]');
			for (var i = 0; i < snactionNameList.length; ++i) {
				try{
					if(levelInfo <= snactionNameList[i].getAttribute("seq")){
						snactionNameList[i].value = expertPoolList[n].name;
						snactionSsnList[i].value = expertPoolList[n].ssn;
						n++;
					}
				}catch(e){}
			}
		}
	}

	function openExpertPoolPopUpForProjectMember() {
		//orgFinder_OpenForProjectMember($('projectCode').value, 'setProjectMember');
		orgFinder_Open('setProjectMember');		
	}
	function setProjectMember(expertPoolList) {
		var approvalType = $('approvalType').value;
		var memberSsnList = $$('input[name="addMemberSsn"]');
		var memberNameList = $$('input[name="addMemberName"]');
		//var memberPositionList = $$('input[name="addMemberPosition"]');
		var memberPositionList = document.getElementsByName("addMemberPosition");
		var memberPositionNameList = $$('input[name="addMemberPositionName"]'); 
		var memberCostList = $$('input[name="addMemberCost"]');
		var memberRole = $$('select[name="addMemberRole"]');
		var businessTypeCode = $('businessTypeCode').value;
		var projectTypeCode = $('projectTypeCode').value;
		var memberJobClassList = $$('input[name="addMemberJobClass"]');
		
		var expertPositionList = document.getElementsByName("expertPosition");
			
		var n=0;
		for (var i = 0; i < memberSsnList.length; ++i) {
			if(memberSsnList[i].value == ""){
				/* if(approvalType == "ME" && (expertPoolList[n].jobClass != "C")){
					alert(" 해당 인력 변경 품의 양식에서는 엑스퍼트 만 가능합니다.");
					break;
				}
				if(approvalType == "MF" && (expertPoolList[n].jobClass != "D")){
					alert(" 해당 인력 변경 품의 양식에서는 산업계강사 만 가능합니다.");
					break;
				}
				if(approvalType == "MG" && (expertPoolList[n].jobClass != "E")){
					alert(" 해당 인력 변경 품의 양식에서는 대학교수 만 가능합니다.");
					break;
				} */
				if(expertPoolList[n].jobClass == 'C'
					&& memberRole[i].options[memberRole[i].selectedIndex].value == 'PL') {
					alert("PL에 엑스퍼트를 지정할 수 없습니다.\n상근 또는 상임을 지정하십시오.");
					break;
				}else if(expertPoolList[n].jobClass == 'H' 
					&& (expertPoolList[n].companyPosition != "61DT" 
						&& expertPoolList[n].companyPosition != "62ET" 
						&& expertPoolList[n].companyPosition != "63FT" 
						&& expertPoolList[n].companyPosition != "64GT")){
					alert("(구)RA는 선택할 수 없습니다. ");
					break;
				}else{
					memberSsnList[i].value = expertPoolList[n].ssn;
					memberNameList[i].value = expertPoolList[n].name;
					memberCostList[i].maxAmt = expertPoolList[n].maxAmt;
					memberCostList[i].minAmt = expertPoolList[n].minAmt;
					memberCostList[i].minEdu = expertPoolList[n].minEdu;
					memberCostList[i].maxEdu = expertPoolList[n].maxEdu;
					memberCostList[i].minMMAmt = expertPoolList[n].minMMAmt;
					memberCostList[i].maxMMAmt = expertPoolList[n].maxMMAmt;
					memberCostList[i].jobClass = expertPoolList[n].jobClass;
					memberJobClassList[i].value = expertPoolList[n].jobClass;
					memberCostList[i].businessTypeCode = businessTypeCode;
					memberCostList[i].projectTypeCode = projectTypeCode;
					
					if (expertPoolList[n].companyPosition == "") {
						expertPoolList[n].companyPosition = expertPoolList[n].companyPositionName;
					}
					
					var resStr = "";
					expertPositionList[i].innerHTML = "";
					if(expertPoolList[n].jobClass == 'C'){
						resStr = "<select name='addMemberPosition' id='addMemberPosition' class='selectbox'>";
						resStr += "	<option value=''>--등급선택--</option>";
						resStr += "	<option value='41EF' minAmt='432000' maxAmt='480000' minEdu='81000' maxEdu='151200' minMMAmt='8640000' maxMMAmt='9600000'>엑스퍼트Ⅰ</option>";												
						resStr += "	<option value='42EG' minAmt='372000' maxAmt='420000' minEdu='70800' maxEdu='132300' minMMAmt='7440000' maxMMAmt='8400000'>엑스퍼트Ⅱ</option>";
						resStr += "	<option value='43EH' minAmt='276000' maxAmt='324000' minEdu='52000' maxEdu='972000' minMMAmt='5520000' maxMMAmt='6480000'>엑스퍼트Ⅲ</option>";
						resStr += "	<option value='44EI' minAmt='180000' maxAmt='240000' minEdu='39300' maxEdu='73400' minMMAmt='3600000' maxMMAmt='4800000'>엑스퍼트Ⅳ</option>";
						resStr += "</select>";
					} else {
						resStr =  "<input type='text' name='addMemberPositionName' class='textInput_noLine_center' style='width: 100%' value='"+expertPoolList[n].companyPositionName+"' readonly='readonly'>";
						resStr += "<input type='hidden' name='addMemberPosition' id='addMemberPosition' value='"+expertPoolList[n].companyPosition+"' />";
					}
					expertPositionList[i].innerHTML = resStr;
				}					
				n++;
			}
		}
	}
	
	function openCostInputGuide() {
		var infomWin;
		var surl = "/sanction/sanctionForm/costInputSample.jsp";
		var sfeature = "top=0,left=50,width=670,height=640,scrollbars=no";
		infomWin = window.open(surl,"infomWin",sfeature);
		infomWin.focus();
	}
	
	function addRowProjectMember() {
		var date_tmp = new Date()
		tmp = (date_tmp.getMonth())+ (date_tmp.getMonth())+ (date_tmp.getDate())+ (date_tmp.getHours())+ (date_tmp.getMinutes())+ (date_tmp.getSeconds())+ (date_tmp.getMilliseconds()); 
		
		var tableObj = $('addProjectMemberTable');
		var tdCount = tableObj.down('tr').down('th').nextSiblings().length + 1 ;
	    var newRow=tableObj.insertRow(-1);
		var newCellArr = new Array();
		for ( var i=0;i<tdCount;i++ ){
			newCellArr[i] = newRow.insertCell(-1);
		}
		
		var date_tmp = new Date()
		tmp =
			(date_tmp.getMonth())+
			(date_tmp.getMonth())+
			(date_tmp.getDate())+
			(date_tmp.getHours())+
			(date_tmp.getMinutes())+
			(date_tmp.getSeconds())+
			(date_tmp.getMilliseconds()); 
		
		newCellArr[0].innerHTML = "<ul><li><input type='checkbox' name='projectMemberCheck' id='"+tmp+"' class='btn_check' value='C' /><label for='"+tmp+"'></label></li></ul>";
		newCellArr[1].innerHTML = "<select name='addMemberRole' class='selectbox'>"
											+"<option>-선택-</option>"
											+"<option value='MB' selected>멤버</option>"
										<c:if test="${project.businessTypeCode eq 'BTA' || project.businessTypeCode eq 'BTJ'}">
											+"<option value='PL'>PL</option>"
										</c:if>
											+"<option value='PM'>PM</option>"
											+"<option value='QM'>QM</option>"
											+"<option value='AR'>COO</option></select>";
		newCellArr[2].innerHTML = "<input type='hidden' name='addMemberSsn'><input type='text' name='addMemberName' class='textInput_noLine_center' readonly='readonly'>";
		newCellArr[3].innerHTML = "<span id='expertPosition' name='expertPosition' />";
		newCellArr[4].innerHTML = "<input type='text' name='addMemberCost' class='textInput_right' jobClass='' minAmt='' maxAmt='' minEdu='' maxEdu='' minMMAmt='' maxMMAmt='' businessTypeCode='<c:out value="${project.businessTypeCode }"/>' projectTypeCode='<c:out value="${project.projectTypeCode }"/>' value=''>"
		/* newCellArr[4].innerHTML = "<input type='text' name='addMemberCost' class='textInput_right' jobClass='' minAmt='' maxAmt='' minEdu='' maxEdu='' minMMAmt='' maxMMAmt='' businessTypeCode='<c:out value="${project.businessTypeCode }"/>' projectTypeCode='<c:out value="${project.projectTypeCode }"/>' onchange='Money_Only(this, 120000000, -1);Check_MoneyAmt(this);' style='width: 100%' value=''>" */
		
		<c:choose>
			<c:when test="${sanctionDoc.approvalType == 'M' || sanctionDoc.approvalType == 'MI'}">
										 +"<input type='hidden' name='addMemberJobClass' id='addMemberJobClass'><input type='hidden' name='addMemberResRate' class='textInput_center' style='width: 100%' value='1'>"
										 +"<input type='hidden' name='addMemberContributionCost' class='textInput_right' style='width: 100%; ime-mode:disabled;' onchange='Money_Only(this, -1, -1);' value='1'>"
										 +"<input type='hidden' name='addMemberStartDate' />"
				                         +"<input type='hidden' name='addMemberEndDate' />";
			</c:when>
			<c:when test="${sanctionDoc.approvalType == 'ME' || sanctionDoc.approvalType == 'MF' || sanctionDoc.approvalType == 'MG'}">
				newCellArr[5].innerHTML = "<input type='text' name='addMemberStartDate' id='addMemberStartDate_"+tmp+"' value='' style='width: 73px;' readonly='readonly' class='textInput_center' /> ~ "
				                         +"<input type='text' name='addMemberEndDate' id='addMemberEndDate_"+tmp+"' value='' style='width: 73px;' readonly='readonly' class='textInput_center' />"
				                         +"<input type='hidden' name='addMemberJobClass' ><input type='hidden' name='addMemberResRate' value='1'>"
				         				 +"<input type='hidden' name='addMemberContributionCost'value=''>";	
				         				
				jQuery(function(){jQuery( "#addMemberStartDate_"+tmp ).datepicker({showOn: 'focus'});});
				jQuery(function(){jQuery( "#addMemberEndDate_"+tmp ).datepicker({showOn: 'focus'});});			
			</c:when>
		</c:choose>
	}
	
	function deleteRowProjectMember() {
		var tableObj = $('addProjectMemberTable');
		var deleteOverAllIds = "";
		var chkBoxEls = $$('input[name="projectMemberCheck"]');
		var checkCount = 0;

		for ( var i=0;i<chkBoxEls.length;i++){ 
			if ( chkBoxEls[i].checked ) {
				checkCount++;
				$(chkBoxEls[i]).up().up().up().up().remove();
			}
		}
		if (checkCount < 1)
			alert("삭제할 행을 선택하세요.");
	}

	function erpMemberDataImport(){
		var ActionURL = "/action/MemberChangeSanctionAction.do?mode=getErpMemberChangeList";
		var status = AjaxRequest.post (
				{	'url':ActionURL,
					'parameters' : { "projectCode": $('projectCode').value },
					'onSuccess':function(obj){
			           	var res = eval('(' + obj.responseText + ')');
			           	var addMemberList = res.projectMemberList;
		        		var tableObj = $('addProjectMemberTable');
			           	addMemberList.each(function(addMember){
			           		var checkAddMemberSsnList = $$('input[name="addMemberSsn"]');
							var flag = true;
							checkAddMemberSsnList.each(function(checkAddMemberSsn){
			           			if(checkAddMemberSsn.value == addMember.ssn){
									flag = false;
			           			}
			           		});
							if(flag){
								$('wholeApproval').style.display = 'inline';
				           		var tdCount = tableObj.down('tr').down('td').nextSiblings().length + 1 ;
				        	    var newRow=tableObj.insertRow(-1);
				        		var newCellArr = new Array();
				        		for ( var i=0;i<tdCount;i++ ){
				        			newCellArr[i] = newRow.insertCell(-1);
				        		}
				        		newCellArr[0].innerHTML = "<input type='checkbox' name='projectMemberCheck' disable='disable'/>";
				        		newCellArr[1].innerHTML = "<select name='addMemberRole' class='selectbox'>"
				        											+"<option value='MB' "+(addMember.role=='MB' ? 'selected' : '')+" >멤버</option>"
				        											+"<option value='PL' "+(addMember.role=='PL' ? 'selected' : '')+" >PL</option>"
				        											+"<option value='PM' "+(addMember.role=='PM' ? 'selected' : '')+" >PM</option>"
					        										+"<option value='AG' "+(addMember.role=='AG' ? 'selected' : '')+" >그룹장</option>"
					        										+"<option value='AA' "+(addMember.role=='AA' ? 'selected' : '')+" >PU장</option></select>"
					        										+"<option value='AR' "+(addMember.role=='AR' ? 'selected' : '')+" >COO</option></select>";
				        		newCellArr[2].innerHTML = "<input type='hidden' name='addMemberSsn' value='"+addMember.ssn+"' >"
				        											+"<input type='text' name='addMemberName' class='textInput_noLine_center' style='width: 100%' readonly='readonly' value='"+addMember.name+"'>";
				        		newCellArr[3].innerHTML = "<input type='text' name='addMemberCost' class='textInput_right' style='width: 100%' jobClass='' minAmt='' maxAmt='' minEdu='' maxEdu='' minMMAmt='' maxMMAmt='' businessTypeCode='<c:out value="${project.businessTypeCode }"/>' projectTypeCode='<c:out value="${project.projectTypeCode }"/>' value='"+addMember.cost+"'>";
				        		/* newCellArr[3].innerHTML = "<input type='text' name='addMemberCost' class='textInput_right' style='width: 100%' jobClass='' minAmt='' maxAmt='' minEdu='' maxEdu='' minMMAmt='' maxMMAmt='' businessTypeCode='<c:out value="${project.businessTypeCode }"/>' projectTypeCode='<c:out value="${project.projectTypeCode }"/>' onchange='Money_Only(this, 120000000, -1);Check_MoneyAmt(this);' value='"+addMember.cost+"'>"; */
				        		newCellArr[4].innerHTML = "<input type='text' name='addMemberResRate' class='textInput_center' style='width: 100%' value='"+addMember.resRate+"'>";
				        		newCellArr[5].innerHTML = "<input type='text' name='addMemberContributionCost' class='textInput_right' style='width: 100%' value='"+addMember.contributionCost+"'>";
	
							}else{
								alert(addMember.name +"님은 이미 등록되어 있습니다. ");
							}
			           	});
					},
					'onLoading':function(obj){},
					'onError':function(obj){ 
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;	
	}

	
	function refTaskRequest(){
		var sFrm = document.forms["memberChangeSanctionForm"];

		var status = AjaxRequest.submit(
				sFrm,
				{	'url': "/action/GeneralSanctionAction.do?mode=executeRefWork",
					'onSuccess':function(obj){
						alert('등록하였습니다.');
						document.location = "/action/WorkCabinetAction.do?mode=getMyRefWorkList";
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;		
	}	

	function openExpertReqPopup(){
		AjaxRequest.post (
				{	'url': "/action/ProjectBudjetInfoAction.do?mode=goProjectEntNos",
					'parameters' : { "projectCode": $("projectCode").value}, 
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						var entNo = res.entNoOld;
						if(entNo != "" && entNo != 'undefined'){
							if(res.entNoNew != "" && res.entNoNew != null)
								entNo = res.entNoNew;	// 변경 예산이 있는 경우 변경 예산서 번호로 설정함
							window.open(
								"https://newbudget.kmac.co.kr:8080/DWPM3/DWPM30140_L_PMS.jsp"
									+"?programId=pms"
									+"&entno="+entNo
									+"&empno=<authz:authentication operation="username" />"
								,"mm_amt_reg"
								,"left=100, top=100, width=804, height=550, toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, copyhistory=no, resizable=no");
						}else{
							alert('프로젝트 예산이 존재하지 않으므로 엑스퍼트를 신청할 수 없습니다.');
						}
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		);		
	}	
</script>
</head>

<body>
<div style="overflow:auto;">
	<form name="memberChangeSanctionForm" method="post">
		<div id="hiddneValue" style="display: none;">
			<input type="text" name="taskId" id="taskId"
				value="<c:out value="${sanctionDoc.taskId}" />" /> <input
				type="text" name="approvalType" id="approvalType"
				value="<c:out value="${sanctionDoc.approvalType}" />" /> <input
				type="text" name="projectCode" id="projectCode"
				value="<c:out value="${sanctionDoc.projectCode}" />" /> <input
				type="text" name="businessTypeCode" id="businessTypeCode"
				value="<c:out value="${project.businessTypeCode}" />" /> <input
				type="text" name="processTypeCode" id="processTypeCode"
				value="<c:out value="${project.processTypeCode}" />" /> <input
				type="text" name="projectTypeCode" id="projectTypeCode"
				value="<c:out value="${project.projectTypeCode}" />" /> <input
				type="text" name="seq" id="seq"
				value="<c:out value="${sanctionDoc.seq}" />" /> <input type="text"
				name="memberChangeSeq" id="memberChangeSeq"
				value="<c:out value="${sanctionDoc.seq}" />" /> <input type="text"
				name="state" id="state"
				value="<c:out value="${sanctionDoc.state}" />" /> <input
				type="text" name="budgetEntNo" id="budgetEntNo"
				value="<c:out value="${budgetEntNo}" />" />
		</div>
		<c:forEach var="rs" items="${projectMemberListFromEntNo}">
			<input type="hidden" name="budgetMemberSsn" id="budgetMemberSsn"
				value="<c:out value="${rs.ssn}" />" />
		</c:forEach>
		
		<!-- location -->
		<div class="location">
				<p class="menu_title">인력변경품의</p>
				<ul>
					<li class="home">HOME</li>
					<li>MY PROJECT</li>
					<li>인력변경품의</li>
			</ul>
		</div>
		<!-- // location -->
		
		<div class="contents sub"><!-- 서브 페이지 .sub -->
			<div class="fixed_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1"><i class="mdi mdi-file-document-outline"></i>내용 작성</p>	
					</div>
					<div class="btn_area">
						<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back();'"><i class="mdi mdi-backburger"></i>목록</button>
					</div>
				</div>	
				<div class="fixed_contents sc">
					<!-- sanctionLine area -->
					<%@include file="/sanction/common/sanctionLineInfo.jsp" %>	
					<!-- // sanctionLine area -->
					
					<!-- 기안 내용 -->
					<div class="board_box">
						<div class="title">
							<div class="h1_area">
								<p class="h1">기본 내용</p>
							</div>
						</div>
						<div class="board_contents">
							<table class="tbl-edit">
								<colgroup>
									<col style="width: 13%"/>
									<col style="width: 37%" />
									<col style="width: 13%" />
									<col style="width: 37%" />
								</colgroup>
								<tbody>
									<tr>
										<th>소속</th>
										<td><c:out value="${userDept }" /></td>
										<th>성명 / 직위</th>
										<td><c:out value="${userName }" /> / <c:out value="${companyPositionName }" /></td>
									</tr>
									<tr>
										<th>결재유형</th>
										<td><c:out value="${sanctionTemplate.approvalName}" /></td>
										<th>제목</th>
										<td><input type="text" id="subject" name="subject" class="textInput_left" style="width: 100%"  <c:if test="${readonly}">readonly</c:if> value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${project.projectName}"/> 인력변경품의 </c:when><c:otherwise><c:out value="${sanctionDoc.subject}"/></c:otherwise></c:choose>" ></td>
									</tr>
									<tr>
										<th>프로젝트 정보</th>
										<td colspan="3">[<c:out value="${project.projectCode}"/>] <c:out value="${project.projectName}"/></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class="board_box">
						<div class="title">
							<div class="h1_area">
								<p class="h1">상세 내용</p>
							</div>
							<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' }">
							<div class="text-R">
								<c:if test="${!readonly}">
								<button type="button" class="btn line btn_blue" onclick="draftRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
								</c:if>
								<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
									<!-- <a class="btNa0a0a0 txt4btn" href="#" onclick="showEntrustInfo();">업무위임</a> -->
								</c:if>
								<c:if test="${sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
									<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>업무 삭제</button>
									<button type="button" class="btn line btn_grey" onclick="tempSaveWork()"><i class="mdi mdi-square-edit-outline"></i>임시 저장</button>
								</c:if>
								<c:if test="${readonly && isRefWork}"> 
									<button type="button" class="btn line btn_blue" onclick="refTaskRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
								</c:if>
							</div>
							</c:when><c:otherwise> </c:otherwise></c:choose>
						</div>
						
						<div class="board_contents">
							<c:choose>
							<c:when test="${empty sanctionDoc.taskId && sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
								<textarea name="content" id="content" class="textArea" style="width: 100%; height: 30%;" <c:if test="${readonly}">readonly</c:if>><c:out value="${sanctionTemplate.templateText}" /></textarea>
							</c:when>
							<c:otherwise><textarea name="content" id="content" class="textArea" style="width: 100%; height: 30%;" <c:if test="${readonly}">readonly</c:if>><c:out value="${sanctionDoc.content}" /></textarea></c:otherwise>
							</c:choose>
							</textarea>
							<c:if test="${sanctionTemplate.hasAttach}">
							</br>
							<table class="tbl-edit">
								<colgroup>
									<col style="width: 13%"/>
									<col style="width: 37%" />
									<col style="width: 13%" />
									<col style="width: 37%" />
								</colgroup>
								</br>
								<tr class="file">
									<th><label for="file">첨부파일</label></th>
									<td colspan="3" style="padding: 10 0 0 0 ">
										<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log"  /></jsp:include>
										<br>
									</td>
								</tr>
							</table>
							</c:if>
						</div>
					</div>
					
					<div class="board_box">
						<div class="title">
							<div class="h1_area">
								<p class="h1">변경 인력 정보</p>
							</div>
						</div>
						<div class="a-both stretch">
						<div>
							<div class="board_contents pop_tbl sc">
							<table id="addProjectMemberTable" class="tbl-edit td-c pd-none"  >
								<thead id="addProjectMemberTableHead">
								<colgroup>
									<col style="width: 12%"/>
									<col style="width: 22%" />
									<col style="width: 22%" />
									<col style="width: 22%" />
									<col style="width: 22%" />
								</colgroup>
								<tr>
									<th>선택</th>
									<th>역할</th>
									<th>이름</th>
									<th>직위/직책</th>
									<th>성과급</th>
								</tr>
								</thead>
								<tbody id="addProjectMemberTableBody">
									<c:if test="${!empty addMemberList}">
											<c:forEach var="rs" items="${addMemberList}">
												<tr>
													<td><input type="checkbox" name="projectMemberCheck" id="<c:out value="${rs.addMemberSsn}"/>" class="btn_check" <c:if test="${readonly}">disabled</c:if>><label for="<c:out value="${rs.addMemberSsn}"/>"></label></td>
													<td><select name='addMemberRole' class='selectbox'
														<c:if test="${readonly}">disabled</c:if>>
															<option value='MB'
																<c:if test="${rs.addMemberRole== 'MB'}">selected</c:if>>멤버</option>
															<option value='AR' <c:if test="${rs.addMemberRole== 'AR'}">selected</c:if>>COO</option>
															<option value='QM' <c:if test="${rs.addMemberRole== 'QM'}">selected</c:if>>QM</option>
															<option value='PM' <c:if test="${rs.addMemberRole== 'PM'}">selected</c:if>>PM</option>
															<c:if test="${project.businessTypeCode eq 'BTA' || project.businessTypeCode eq 'BTJ'}">
																<option value='PL' <c:if test="${rs.addMemberRole== 'PL'}">selected</c:if>>PL</option>
															</c:if>
													</select></td>
													<td><input type="hidden" name="addMemberSsn"
														value="<c:out value="${rs.addMemberSsn}"/>"> <input
														type="text"  name="addMemberName"
														value="<c:out value="${rs.addMemberName}"/>"
														class="textInput_noLine_center"
														readonly="readonly"></td>
													<td><code:code tableName="POSITION_KIND"
															code="${rs.addMemberPosition}" /><input type="hidden"
														name="addMemberPosition" id="addMemberPosition"
														value="<c:out value="${rs.addMemberPosition}"/>" class="textInput_noLine_center" /></td>
													<td><input type="text" name="addMemberCost" style="text-align: right;"
														value="<fmt:formatNumber value="${rs.addMemberCost}" type="number" var="restAmount" /><c:out value="${restAmount}" />"
														class="textInput_right" jobClass=""
														minAmt="" maxAmt="" minEdu="" maxEdu="" minMMAmt=""
														maxMMAmt=""
														businessTypeCode="<c:out value="${project.businessTypeCode }"/>"
														projectTypeCode="<c:out value="${project.projectTypeCode }"/>"
														Check_MoneyAmt(this);" 
														<c:if test="${readonly}">readonly</c:if>></td>
													<c:choose>
														<c:when test="${sanctionDoc.approvalType == 'M' || sanctionDoc.approvalType == 'MI' }">
															<input type="hidden" name="addMemberResRate"
																value="<c:out value="${rs.addMemberResRate}" default="1" />"
																class="textInput_center"
																<c:if test="${readonly}">readonly</c:if>>
															<input type="hidden"
																name="addMemberContributionCost"
																value="<c:out value="${rs.addMemberContributionCost}"/>"
																class="textInput_right"
																ime-mode: disabled;"
																onchange="Money_Only(this, -1, -1);"
																<c:if test="${readonly}">readonly</c:if>>
															<input type="hidden" name="addMemberStartDate" value="" />
															<input type="hidden" name="addMemberEndDate" value="" />
														</c:when>
														<c:when test="${sanctionDoc.approvalType == 'ME' || sanctionDoc.approvalType == 'MF' || sanctionDoc.approvalType == 'MG'}">
															<td><script>
																	j$(function(){j$( "#addMemberStartDate<c:out value="${status.index}"/>" ).datepicker({showOn: 'focus', dateFormat: 'yy.mm.dd'});});
																	j$(function(){j$( "#addMemberEndDate<c:out value="${status.index}"/>" ).datepicker({showOn: 'focus', dateFormat: 'yy.mm.dd'});});
																</script> <input type="text" name="addMemberStartDate"
																id="addMemberStartDate<c:out value="${status.index}"/>"
																value="<c:out value="${rs.addMemberStartDate}"/>"
																class="textInput_center" style="width: 73px;" readonly>
																~ <input type="text" name="addMemberEndDate"
																id="addMemberEndDate<c:out value="${status.index}"/>"
																value="<c:out value="${rs.addMemberEndDate}"/>"
																class="textInput_center" style="width: 73px;" readonly>
																<input type="hidden" name="addMemberResRate" value="" />
																<input type="hidden" name="addMemberContributionCost"
																value="" /></td>
														</c:when>
													</c:choose>
												</tr>
											</c:forEach>
										</c:if>
										<c:if
											test="${empty addMemberList && sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
											<tr>
												<td><ul><li><input type="checkbox" name="projectMemberCheck" id="temp" class="btn_check" value="C"><label for="temp"></label></li></ul></td>
												<td><select name='addMemberRole' class='selectbox'>
														<option value='MB'>멤버</option>
														<c:if
															test="${project.businessTypeCode eq 'BTA' || project.businessTypeCode eq 'BTJ'}">
															<option value='PL'>PL</option>
														</c:if>
														<option value='PM'>PM</option>
														<option value="AR">COO</option>
												</select></td>
												<td><input type="hidden" name="addMemberSsn"><input
													type="text" name="addMemberName"
													class="textInput_noLine_center"
													readonly="readonly"></td>
												<td><span id="expertPosition" name="expertPosition" />
												</td>
												<td><input type="text" name="addMemberCost"
													class="textInput_right" jobClass="" minAmt="" maxAmt=""
													minEdu="" maxEdu="" minMMAmt="" maxMMAmt="" style="text-align: right;"  onchange="getNumber(this);" onkeyup="getNumber(this);"
													businessTypeCode="<c:out value="${project.businessTypeCode }"/>"
													projectTypeCode="<c:out value="${project.projectTypeCode }"/>"
													Check_MoneyAmt(this);' <c:if test="${readonly}">readonly</c:if>></td>

												<c:choose>
													 <c:when test="${sanctionDoc.approvalType == 'M' || sanctionDoc.approvalType == 'MI' }">
														<input type="hidden" name="addMemberJobClass"
															id="addMemberJobClass"><input type="hidden"
															name="addMemberResRate" class="textInput_center"
															<c:if test="${readonly}">readonly</c:if> value="1">
														<input type="hidden"
															name="addMemberContributionCost" class="textInput_right"
															ime-mode: disabled;"
															onchange="Money_Only(this, -1, -1);"
															<c:if test="${readonly}">readonly</c:if> value="1">
														<input type="hidden" name="addMemberStartDate" value="" />
														<input type="hidden" name="addMemberEndDate" value="" />
													</c:when>
													<c:when test="${sanctionDoc.approvalType == 'ME' || sanctionDoc.approvalType == 'MF' || sanctionDoc.approvalType == 'MG' }">
														<td><script>
																jQuery(function(){jQuery( "#addMemberStartDate000" ).datepicker({showOn: 'focus', dateFormat: 'yy.mm.dd'});});
																jQuery(function(){jQuery( "#addMemberEndDate000"   ).datepicker({showOn: 'focus', dateFormat: 'yy.mm.dd'});});
															</script> <input type="text" name="addMemberStartDate"
															id="addMemberStartDate000" value=""
															class="textInput_center" style="width: 73px;" readonly>
															~ <input type="text" name="addMemberEndDate"
															id="addMemberEndDate000" value=""
															class="textInput_center" style="width: 73px;" readonly>
															<input type="hidden" name="addMemberJobClass" /> <input
															type="hidden" name="addMemberResRate" value="" /> <input
															type="hidden" name="addMemberContributionCost" value="" />
														</td>
													</c:when>
												</c:choose>
											</tr>
										</c:if>
								</tbody>
								</table>
							</div>
							<div class="btn_area">
								<c:if test="${readonly}">
									<c:set var="entno" value="$F('budgetEntNo')" />
								</c:if>
								<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
									<button type="button" class="btn btn_blue" onclick="addRowProjectMember()">행추가</button>
									<button type="button" class="btn btn_pink" onclick="deleteRowProjectMember()">행삭제</button>
									<button type="button" class="btn btn_w_grey" onclick="openExpertPoolPopUpForProjectMember()">인력추가</button>
								</c:if>
							</div>
							<br>
						</div>
						<td width="*" valign="middle" align="center"> 
							<table>
								<tr><td><a class="btn_arrow"><i class="mdi mdi-chevron-right-box"></i></a></td></tr>
							</table>
						</td> 
						<div>
							<div class="board_contents pop_tbl sc">
							<table id="runningProjectMemberTable" class="tbl-edit td-c pd-none">
								<thead>
								<colgroup>
									<col style="width: 30%"/>
									<col style="width: 30%" />
									<col style="width: 40%" />
								</colgroup>
								<tr>
									<th>상태</th>
									<th>역할</th>
									<th>이름</th>
								</tr>
								</thead>
								<tbody>
										<c:choose>
											<c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
												<c:forEach var="rs" items="${projectMemberList}">
													<tr>
														<td><select name="runningMemberTrainingYn"
															class="selectbox"
															<c:if test="${readonly}">disabled</c:if>>
																<option value="Y"
																	<c:if test="${rs.trainingYn== 'Y'}">selected</c:if>>진행</option>
																<option value="N"
																	<c:if test="${rs.trainingYn== 'N'}">selected</c:if>>제외</option>
														</select></td>
														<td><select name="runningMemberRole"
															trainingYn="<c:out value="${rs.trainingYn}"/>"
															class='selectbox'
															<c:if test="${readonly}">disabled</c:if>>
																<option value='AR'
																	<c:if test="${rs.role== 'AR'}">selected</c:if>>COO</option>
																<option value='QM'
																	<c:if test="${rs.role== 'QM'}">selected</c:if>>QM</option>
																<option value='PM'
																	<c:if test="${rs.role== 'PM'}">selected</c:if>>PM</option>
																<option value='PL'
																	<c:if test="${rs.role== 'PL'}">selected</c:if>>PL</option>
																<option value='MB'
																	<c:if test="${rs.role== 'MB'}">selected</c:if>>멤버</option>
																<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
																	<option value='AG'
																		<c:if test="${rs.role== 'AG'}">selected</c:if>>그룹장</option>
																	<option value='AA'
																		<c:if test="${rs.role== 'AA'}">selected</c:if>>PU장</option>
																</c:if>
														</select></td>
														<td><input type="text" name="runningMemberName"
															value="<c:out value="${rs.name}"/>"
															class="textInput_noLine_center"
															readonly> <input type="hidden"
															name="runningMemberSsn"
															value="<c:out value="${rs.ssn}"/>"> <input
															type="hidden" name="runningMemberCost"
															value="<c:out value="${rs.cost}"/>"> <input
															type="hidden" name="runningMemberContributionCost"
															value="<c:out value="${rs.contributionCost}"/>">
															<input type="hidden" name="runningMemberTeachingDay"
															value="<c:out value="${rs.teachingDay}"/>"> <input
															type="hidden" name="runningMemberPosition"
															value="<c:out value="${rs.position}"/>"> <input
															type="hidden" name="runningMemberResRate"
															value="<c:out value="${rs.resRate}"/>"></td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:forEach var="rs" items="${runningMemberList}">
													<tr>
														<td><select name="runningMemberTrainingYn"
															class="selectbox" disabled>
																<option value="Y"
																	<c:if test="${rs.runningMemberTrainingYn== 'Y'}">selected</c:if>>진행</option>
																<option value="N"
																	<c:if test="${rs.runningMemberTrainingYn== 'N'}">selected</c:if>>제외</option>
														</select></td>
														<td><select name="runningMemberRole"
															trainingYn="<c:out value="${rs.runningMemberTrainingYn}"/>"
															class='selectbox' disabled>
																<option value='AR'
																	<c:if test="${rs.runningMemberRole== 'AR'}">selected</c:if>>COO</option>
																<option value='PL'
																	<c:if test="${rs.runningMemberRole== 'PL'}">selected</c:if>>PL</option>
																<option value='PM'
																	<c:if test="${rs.runningMemberRole== 'PM'}">selected</c:if>>PM</option>
																<option value='MB'
																	<c:if test="${rs.runningMemberRole== 'MB'}">selected</c:if>>멤버</option>
																<option value='AG'
																	<c:if test="${rs.runningMemberRole== 'AG'}">selected</c:if>>그룹장</option>
																<option value='AA'
																	<c:if test="${rs.runningMemberRole== 'AA'}">selected</c:if>>PU장</option>
																<option value='QM'
																	<c:if test="${rs.runningMemberRole== 'QM'}">selected</c:if>>QM</option>
														</select></td>
														<td><input type="text" name="runningMemberName"
															value="<c:out value="${rs.runningMemberName}"/>"
															class="textInput_noLine_center"
															readonly> <input type="hidden"
															name="runningMemberSsn"
															value="<c:out value="${rs.runningMemberSsn}"/>">
															<input type="hidden" name="runningMemberCost"
															value="<c:out value="${rs.runningMemberCost}"/>">
															<input type="hidden" name="runningMemberContributionCost"
															value="<c:out value="${rs.runningMemberContributionCost}"/>">
															<input type="hidden" name="runningMemberTeachingDay"
															value="<c:out value="${rs.runningMemberTeachingDay}"/>">
															<input type="hidden" name="runningMemberPosition"
															value="<c:out value="${rs.runningMemberPosition}"/>">
															<input type="hidden" name="runningMemberResRate"
															value="<c:out value="${rs.runningMemberResRate}"/>">
														</td>
													</tr>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' }">
						<div style="display:none">
							<div class="board_box">
								<div class="title_both">
									<div class="h1_area">
										<c:choose>
										<c:when test="${!readonly }">
											<p class="h1">결재 선택/의견 작성</p>
										</c:when>
										<c:otherwise>
											<p class="h1">승인/반려 내역</p>
										</c:otherwise>
										</c:choose>
									</div>
									<div class="text-R">
										<c:if test="${!readonly}">
										<button type="button" class="btn line btn_blue" onclick="draftRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
										</c:if>
										<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
											<!-- <a class="btNa0a0a0 txt4btn" href="#" onclick="showEntrustInfo();">업무위임</a> -->
										</c:if>
										<c:if test="${sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
											<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>업무 삭제</button>
											<button type="button" class="btn line btn_grey" onclick="tempSaveWork()"><i class="mdi mdi-square-edit-outline"></i>임시 저장</button>
										</c:if>
									</div>
								</div>
								<div class="board_contents">
									<!-- sanctionApprove -->
									<%@include file="/sanction/common/sanctionApproveInfo.jsp" %>
									<!-- // sanctionApprove -->
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="board_box">
								<div class="title_both">
									<div class="h1_area">
										<c:choose>
										<c:when test="${!readonly }">
											<p class="h1">결재 선택/의견 작성</p>
										</c:when>
										<c:otherwise>
											<p class="h1">승인/반려 내역</p>
										</c:otherwise>
										</c:choose>
									</div>
									<div class="text-R">
										<c:if test="${!readonly}">
										<button type="button" class="btn line btn_blue" onclick="draftRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
										</c:if>
										<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
											<!-- <a class="btNa0a0a0 txt4btn" href="#" onclick="showEntrustInfo();">업무위임</a> -->
										</c:if>
										<c:if test="${sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
											<button type="button" class="btn line btn_pink" onclick="deleteWork()"><i class="mdi mdi-trash-can-outline"></i>업무 삭제</button>
											<button type="button" class="btn line btn_grey" onclick="tempSaveWork()"><i class="mdi mdi-square-edit-outline"></i>임시 저장</button>
										</c:if>
										<c:if test="${readonly && isRefWork}"> 
											<button type="button" class="btn line btn_blue" onclick="refTaskRequest()"><i class="mdi mdi-book-check-outline"></i>등록 요청</button>
										</c:if>
										<%-- <c:if test="${readonly && sanctionTemplate.approvalType == 'draft2' && sanctionDoc.state == 'SANCTION_STATE_COMPLETE'}"> 
											<button type="button" class="btn line btn_grey" onclick="print()"><i class="mdi mdi-file-excel-outline"></i>인쇄하기</button>
										</c:if> --%>
									</div>
								</div>
								<div class="board_contents">
									<!-- sanctionApprove -->
									<%@include file="/sanction/common/sanctionApproveInfo.jsp" %>
									<!-- // sanctionApprove -->
								</div>
							</div>
					</c:otherwise></c:choose>
			</div>
		</div>
	</div>
		<script type="text/javascript">
		function showEntrustInfo(){
			$('entrustInfo').style.top = document.body.scrollHeight-120;
			$('entrustInfo').style.left = 457;
			$('entrustInfo').show();
		}
		
		function entrustRequest(){
			if($F('entrustUserName') ==""){alert("위임자를 지정하시기 바랍니다.");return;}
			var sFrm = document.forms["memberChangeSanctionForm"];
			var status = AjaxRequest.submit(
					sFrm, 
					{	'url': "/action/GeneralSanctionAction.do?mode=entrustGeneralSanction",
						'onSuccess':function(obj){
							alert('업무를 위임하였습니다.');
							document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
						},
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("execution Fail");
						}
					}
			); status = null;			
		}	
		</script>
		<div id="entrustInfo"
			style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 3px;">
			<table style="" cellpadding="3" cellspacing="3">
				<tr>
					<td><h4 style="">업무위임 요청</h4></td>
				</tr>
				<tr>
					<td><table id="delayInfoTable" width="290px">
							<thead id="delayInfoTableHeader">
								<tr>
									<td class="detailTableTitle_center" width="27%">위임자</td>
									<td class="detailTableField_left" width="65%"><input
										type="hidden" id="entrustUserSsn" name="entrustUserSsn">
										<input type="Text" id="entrustUserName" name="entrustUserName"
										class="textInput_left" readonly></td>
									<td class="detailTableField_left" width="8%"><img
										alt="위임자 선택" src="/images/btn_glass.jpg" style="cursor: hand;"
										onclick="openExpertPoolPopUp('entrust')"></td>
								</tr>
							</thead>
							<tbody id="delayInfoTableBody">
							</tbody>
						</table></td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="btNbox txtR">
							<a class="btNe006bc6 txt2btn" href="javascript: entrustRequest()">위임
								실행</a> <a class="btNa0a0a0 txt2btn" href="#"
								onclick="$('entrustInfo').hide()">취소</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
	</div>
</body>
</html>
