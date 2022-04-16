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
	//프로젝트 버튼 이벤트 관련 ---------------------------------------------------시작//
	function storeProjectMasterInfo() {
		var businessTypeCode = $('businessTypeCode').value;
		var processTemplateCode = $('projectDetailCode').options[$('projectDetailCode').selectedIndex].processTemplateCode;
		
		//validation check 시작
		var memberSsnList = $$('input[name="memberSsn"]');
		var memberRoleList = $$('select[name="memberRole"]');
		var memberResRateList = $$('input[name="memberResRate"]');
		var memberCostList = $$('input[name="memberCost"]');
		var memberContributionCostList = $$('input[name="memberContributionCost"]'); 
	
		var flag=false;
		//PL이 있어야 하는 경우
		if(businessTypeCode=='BTA' || businessTypeCode=='BTJ'){
			var plFlag = false;
			memberRoleList.each( function(memberRole){
				if(memberRole.value == "PL"){flag=true;}
			});	if(flag==false){alert("PL을 설정하세요.");return;}
		}flag=false;
		
		if($('projectDetailCode').value == ""){alert("프로세스 유형을 선택하세요. "); return;};
		memberSsnList.each( function(memberSsn){
			if(memberSsn.value == ""){alert("프로젝트 인력정보가 없습니다. ");flag=true;}
		});	if(flag==true){return;}
		memberRoleList.each( function(memberRole){
			if(memberRole.value == ""){alert("프로젝트 인력정보의 역할정보가 없습니다. ");flag=true;}
		});	if(flag==true){return;}
		memberResRateList.each( function(memberResRate){
			if(memberResRate.value == ""){alert("프로젝트 인력정보의 요율정보가 없습니다. ");flag=true;}
		});	if(flag==true){return;}
		memberCostList.each( function(memberCost){
			if(memberCost.value == ""){alert("프로젝트 인력정보의 기준금액정보가 없습니다. ");flag=true;}
		});	if(flag==true){return;}
		memberContributionCostList.each( function(memberContributionCost){
			if(memberContributionCost.value == ""){alert("프로젝트 인력정보의 개인별수주단가/기여매출단가정보가 없습니다. ");flag=true;}
		});	if(flag==true){return;}
		//validation check 종료
		
		
		var ActionURL = "/action/ProjectMasterInfoAction.do?mode=createProjectMasterInfo";
		var sFrm = document.forms["projectMasterInfoForm"];
		
		var status = AjaxRequest.submit(
				sFrm,
				{	'url':ActionURL,
					'onSuccess':function(obj){ 
						alert('저장하였습니다.');
				},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;
	}
	
	function doProjectLaunchSanction(approvalType) {
		document.projectMasterInfoForm.target = "";
		document.projectMasterInfoForm.action = "/action/ProjectLaunchSanctionAction.do?mode=loadFormProjectLaunchSanction&approvalType="+approvalType+"&viewMode=lSanction";
		document.projectMasterInfoForm.submit();	
	}
	function completeProjectProcess() {
		var ActionURL = "/action/ProjectProgressManagementAction.do?mode=canDoProjectEval";
		var sFrm = document.forms["projectProgressForm"];
		
		var status = AjaxRequest.submit(
				sFrm,
				{	'url':ActionURL,
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						if(res.canFinish == true){
							if(confirm("일정을 모두 마쳤습니다. 평가를 진행하시겠습니까?")) {
								AjaxRequest.post (
										{	'url': "/action/ProjectEndProcessAction.do?mode=assignEndProcessTask&projectCode="+$('projectCode').value,
											'parameters' : { "projectCode": $("projectCode").value},
											'onSuccess':function(obj){
												var res = eval('(' + obj.responseText + ')');
												if(res.result == 'SUCCESS'){
													document.projectProgressForm.target = "";
													document.projectProgressForm.action = "/action/MyProjectAction.do?mode=getMyProjectList";
													document.projectProgressForm.submit();	
												}
											},
											'onLoading':function(obj){},
											'onError':function(obj){
												alert("저장할 수 없습니다.");
											}
										}
								); 
							}
						}else{
							alert('업무 프로세스를 모두 진행하셔야 프로젝트 평가를 하실 수 있습니다.');			
						}
				},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); 
	
	}
	function deleteProjectMasterInfo(){
		if(confirm("삭제 하시겠습니까?")) {
			var ActionURL = "/action/ProjectMasterInfoAction.do?mode=deleteProjectMasterInfo&projectCode="+$('projectCode').value;
			var status = AjaxRequest.post (
					{	'url':ActionURL,
						'parameters' : { "projectCode": $("projectCode").value},
						'onSuccess':function(obj){
							var res = eval('(' + obj.responseText + ')');
							if(res.result == 'SUCCESS'){
								document.projectMasterInfoForm.target = "";
								document.projectMasterInfoForm.action = "/action/RegisterProjectAction.do?mode=getRegisterProjectList";
								document.projectMasterInfoForm.submit();	
							}
						},
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("저장할 수 없습니다.");
						}
					}
			); status = null;		
		}
	} 
	//프로젝트 버튼 이벤트 관련 ---------------------------------------------------끝//
	
	
	//프로젝트 멤버 추가삭제 ---------------------------------------------------시작//
	function addRowProjectMember() {
		var tableObj = $('projectMemberTable');
		if($('projectMemberTableBody').childElements().size() > 0){
			if($('projectMemberTableBody').down('tr').childElements().size() == 1){
		   		$('projectMemberTableBody').down('tr').remove();   		
		   	}
		}
		var tdCount = tableObj.down('tr').down('td').nextSiblings().length + 1 ;
	    var newRow=tableObj.insertRow(-1);
		var newCellArr = new Array();
		for ( var i=0;i<tdCount;i++ ){
			newCellArr[i] = newRow.insertCell(-1);
		}
		new Effect.Highlight(newCellArr[0], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[1], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[2], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[3], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[4], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[5], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[6], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 	
		new Effect.Highlight(newCellArr[7], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 	
		
		newCellArr[0].innerHTML = "<input type='hidden' name='memberTrainingYn' value='Y'><input type='checkbox' name='projectMemberCheck' />";
		newCellArr[1].innerHTML = "<select name='memberRole' class='selectbox'><option>-선택-</option><option value='MB'>멤버</option><option value='PL'>PL</option><option value='PM'>PM</option><option value='AR'>COO</option></select>";
		newCellArr[2].innerHTML = "<input type='hidden' name='memberSsn'><input type='text' name='memberName' class='textInput_noLine_center' style='width: 100%' readonly='	readonly'>";
		newCellArr[3].innerHTML = "<input type='hidden' name='memberPosition'><input type='text' name='memberPositionName' class='textInput_noLine_center' style='width: 100%' readonly='readonly'>";
		newCellArr[4].innerHTML = "<input type='text' name='memberResRate' class='textInput_center' style='width: 100%' value='1'>";
		newCellArr[5].innerHTML = "<input type='text' name='memberCost' class='textInput_right' style='width: 100%' value='0'>";
		newCellArr[6].innerHTML = "<input type='text' name='memberContributionCost' class='textInput_right' style='width: 100%' value='0'>";
		newCellArr[7].innerHTML = "<img alt='편집' src='/images/btn_glass.jpg'  style='cursor: hand;' onclick='openExpertPoolPopUpForProjectMaster(this);'>";
	
		newCellArr[0].className = "BGC";
		newCellArr[1].className = "BGC";
		newCellArr[2].className = "BGC";
		newCellArr[3].className = "BGL";
		newCellArr[4].className = "BGL";
		newCellArr[5].className = "BGC";
		newCellArr[6].className = "BGC";	
		newCellArr[7].className = "BGC";	
	}
	function deleteRowProjectMember() {
		var tableObj = $('projectMemberTable');
		var deleteOverAllIds = "";
		var chkBoxEls = $$('input[name="projectMemberCheck"]');
		for ( var i=0;i<chkBoxEls.length;i++){ 
			if ( chkBoxEls[i].checked ) {
				$(chkBoxEls[i]).up().up().remove();
			}
		}
	}
	var selectedElmt;
	function openExpertPoolPopUpForProjectMaster(projectMemberElmt) {
		if(projectMemberElmt != 'all'){
			selectedElmt = $(projectMemberElmt);
		}
		orgFinder_Open('setProjectMember');	
	}
	function openPreportSchedule(projectCode, ssn) {
		var page="http://intranet.kmac.co.kr/kmac/projectexpert/IndividualSchedule.asp?ssn="+ssn+"&projectcode="+projectCode;
		open(page,'ProjectSchedule','scrollbars=no, width=920, height=400');			
	}
	function setProjectMember(expertPoolList) {
		var memberTrainingYnList = $$('input[name="memberTrainingYn"]');
		var memberSsnList = $$('input[name="memberSsn"]');
		var memberNameList = $$('input[name="memberName"]');
		var memberPositionList = $$('input[name="memberPosition"]');
		var memberPositionNameList = $$('input[name="memberPositionName"]'); 
		if(selectedElmt == null){
			var n=0;
			for (var i = 0; i < memberSsnList.length; ++i) {
				if(memberSsnList[i].value == ""){
					if(n < expertPoolList.length){
						memberTrainingYnList[i].value = 'Y';
						memberSsnList[i].value = expertPoolList[n].ssn;
						memberNameList[i].value = expertPoolList[n].name;
						memberPositionList[i].value = expertPoolList[n].companyPosition;
						memberPositionNameList[i].value = expertPoolList[n].companyPositionName; 
						n++;
					}
				}
			}
		}else{
			var i = selectedElmt.up().up().rowIndex -1;		
			memberSsnList[i].value = expertPoolList[0].ssn;
			memberNameList[i].value = expertPoolList[0].name;
			memberPositionList[i].value = expertPoolList[0].companyPosition;
			memberPositionNameList[i].value = expertPoolList[0].companyPositionName;
		}
	}
	//프로젝트 멤버 추가삭제 ---------------------------------------------------끝//
	
	
	//프로젝트 지도일정 ---------------------------------------------------시작//
	function createProjectReport(){
		var ActionURL = "/action/ProjectReportContentAction.do?mode=createProjectReportContent";
		var status = AjaxRequest.post (
				{	'url':ActionURL,
					'parameters' : { "projectCode": $("projectCode").value },
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						alert('지도일지를 생성했습니다.');
					}, 
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;	
	}
	function searchProjectReportInfo() {
		var ActionURL = "/action/ProjectReportInfoAction.do?mode=searchProjectReportInfoList&readonly=<c:out value="${subinfoReadonly}"/>";
		var sFrm = document.forms["projectReportInfoForm"];
		
		var status = AjaxRequest.submit (
				sFrm,
				{	'url':ActionURL,
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						var rsObj = res.projectReportPlanList;		 
						var project = res.project;	
						var table = $('projectReportInfoTable');
						var tbody = $('projectReportInfoTableBody').childElements();
			           	var tdCount = $('projectReportInfoTableHeader').down('tr').childElements().size();
	
			           	tbody.each( function(preportInfo){
			           		preportInfo.remove();
			    		});
			    		
	 		    		rsObj.each(function(preportInfo){
				    	    var newRow=table.insertRow(-1);
				    		var newCellArr = new Array();
				    		for ( var i=0;i<tdCount;i++ ){
				    			newCellArr[i] = newRow.insertCell(-1);
				    		}		

			    			newCellArr[0].innerHTML = "<span id='day'>"+preportInfo.day+"</span>";
			    			newCellArr[1].innerHTML = "<span id='week'>"+preportInfo.week+"</span>";
			    			newCellArr[2].innerHTML = "<img src='/images/btn_resanction.jpg' alt='지도일정등록' style='cursor:hand;' border='0' "
			    									+ " onclick='openProjecReportScheduleRegistPopup(\""+ preportInfo.year +"-"+ preportInfo.month +"-"+ preportInfo.day +"\")' >"
			    									+ "<span id='workName'>"+preportInfo.workName+"</span>";
			    			var tempHtml = "";
			    			for(var i=0; preportInfo.ssnArray.length >i; i++){
				    			if(preportInfo.nameArray[i] != ''){
					    			var bte="";
									if(project.businessTypeCode == 'BTE' || project.businessTypeCode == 'BTI'){
										 bte = "onclick=changeEduWorkTime('"+ preportInfo.year +"','"+ preportInfo.month +"','"+ preportInfo.day +"','"+
										 	preportInfo.ssnArray[i] +"') ";   
									}
									var delStr='';
									if('<%=request.getAttribute("viewMode")%>' == 'projectSearch' ||  res.readonly == true){
										delStr=', ';
									}else{
										delStr=" <img alt='삭제' src='/images/delete.gif' align='absmiddle' style='cursor: hand;' onclick=\"deleteProjectReportSchedule('"+preportInfo.year+preportInfo.month+preportInfo.day+preportInfo.ssnArray[i]+"')\">" 
									}
				    				tempHtml += "<span id='"+preportInfo.year+preportInfo.month+preportInfo.day+preportInfo.ssnArray[i]+"' year='"+preportInfo.year+"' month='"+preportInfo.month+"' day='"+preportInfo.day+"' ssn='"+preportInfo.ssnArray[i]+"'>"
				    								+ "<a href='#'"+  bte + ">"+ preportInfo.nameArray[i] +"</a>"
													+ delStr + "&nbsp;</span>";
				    			}
			    			}			    			
			    			newCellArr[3].innerHTML = tempHtml;
			    			newCellArr[4].innerHTML = "<span id='outputName'>"+preportInfo.outputName+"</span>";
	
			    			if(preportInfo.week == 'SAT' ){newCellArr[0].bgColor = "#D9ECFF";} 
			    			if(preportInfo.week == 'SUN'){newCellArr[0].bgColor = "#FFDFFF";} 
			    			if(preportInfo.week == 'SAT' ){newCellArr[1].bgColor = "#D9ECFF";} 
			    			if(preportInfo.week == 'SUN'){newCellArr[1].bgColor = "#FFDFFF";} 
			    			if(preportInfo.week == 'SAT' ){newCellArr[2].bgColor = "#D9ECFF";} 
			    			if(preportInfo.week == 'SUN'){newCellArr[2].bgColor = "#FFDFFF";} 
			    			if(preportInfo.week == 'SAT' ){newCellArr[3].bgColor = "#D9ECFF";} 
			    			if(preportInfo.week == 'SUN'){newCellArr[3].bgColor = "#FFDFFF";} 
			    			if(preportInfo.week == 'SAT' ){newCellArr[4].bgColor = "#D9ECFF";} 
			    			if(preportInfo.week == 'SUN'){newCellArr[4].bgColor = "#FFDFFF";} 
			    		});	
					}, 
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;			
	}
	//function openProjecReportScheduleRegistPopup(projectReportElmt) { 
		//selectedElmt = $(projectReportElmt);
		//var preportPop = 
		//window.open("/action/ProjectReportInfoAction.do?mode=loadProjectSchedulePopup&projectCode="+$('projectCode').value,
		//		"acct", "top=0,left=0,width=469,height=350,scrollbars=no");
	//}
	function openProjecReportScheduleRegistPopup(selectedDay) { 
		var preportPop = 
		window.open("/action/ProjectReportInfoAction.do"
				+ "?mode=loadProjectSchedulePopup"
				+ "&projectCode="+$('projectCode').value
				+ "&selectedDay=" + selectedDay,
				"acct", "top=0,left=0,width=469,height=350,scrollbars=no");
	}
	function deleteProjectReportSchedule(delKey){
		if(confirm("삭제 하시겠습니까?")) {
			var scheduleElement = $(delKey);
			var ActionURL = "/action/ProjectReportInfoAction.do?mode=deleteProjectReportInfo";
			var status = AjaxRequest.post (
					{	'url':ActionURL,
						'parameters' : { "projectCode": $("projectCode").value, "year": scheduleElement.year, "month": scheduleElement.month, "day": scheduleElement.day, "ssn":scheduleElement.ssn },
						'onSuccess':function(obj){
							var res = eval('(' + obj.responseText + ')');
							alert(res.message);
							if(res.canDelete == true){//담당자가 없을경우 업무명 산출물 삭제
								var siblingsCnt = scheduleElement.siblings().length;
								var targetTd = scheduleElement.up('td');
								scheduleElement.remove();	
								if(siblingsCnt==0){
									targetTd.previous(0).update();
									targetTd.next(0).update();
								}
							}
						},
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("삭제할 수 없습니다.");
						}
					}
			); status = null;
		}
	}
	
	//프로젝트 지도일정 ---------------------------------------------------끝//
	
	//협력사 팝업----------------------------------------------------------시작//
	function openRelationCompanyPopUp(){
		relationCompanyWin_Open('setRelationCompany')
	}
	function setRelationCompany(relationCompanyList){
		relationCompanyList.each(function(relationCompany){
			 $('orgCodes').value = ($('orgCodes').value=="" ? "" : $('orgCodes').value+", ") + relationCompany.OrgCODE;
			 $('orgNames').value = ($('orgNames').value=="" ? "" : $('orgNames').value+", ") + relationCompany.name;
		});
		
	}
	//협력사 팝업------------------------------------------------------------끝//
	
	
	//DMList 팝업----------------------------------------------------------시작//
	function openDMListPopUp(){
		dmlist_Open("setDM");
		//relationCompanyWin_Open('setDM');
	}
	function setDM(dm){
		 $('customerWorkPName').value = dm.name;
		 $('customerWorkPEmail').value = dm.email;
		 $('customerWorkPPhone').value = dm.tel;
	}
	//DMList 팝업------------------------------------------------------------끝//

	//교육과정 연계------------------------------------------------------------시작//
	function eduCourseRel() {
		if($('projectDetailCode').value == ''){
			alert('프로세스 유형을 선택하세요');
			return;
		}
		
		AjaxRequest.post(
				{	'url': "/action/ProcessCategoryAction.do?mode=getProcessCategoryObj",
					'parameters' : { "processCategoryCode": $('projectDetailCode').value },
					'async' : true,
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						window.open('', 'eduCourseRel', 'toolbar=no,location=no,status=yes,menubar=no,scrollbars=no,resizable=yes,width=810,height=530,left=0,top=0');
						eduCourseRelForm.action="http://intranet.kmac.co.kr/kmac/Emergency/courseprojectcodeSearch.asp";
						eduCourseRelForm.target = "eduCourseRel";
						eduCourseRelForm.p_code.value = $('projectCode').value;
						eduCourseRelForm.processCode.value = res.processCategory.processTemplateCode;
						eduCourseRelForm.bizCode.value = res.processCategory.businessTypeCode;
						eduCourseRelForm._id.value = "<%=session.getAttribute("userId")%>";
						eduCourseRelForm.submit();
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.9");
					}
				}
		);
	}
	//교육과정 연계------------------------------------------------------------끝//
	
	
	//CustomerInfo ------------------------------------------------------------시작//
	function openCustomerInfoPopUp(projectCode){
		var url = "/action/ProjectMasterInfoAction.do?mode=getProjectCustomerInfo&projectCode="+projectCode;
		window.open(url, 'CustomerInfo',
				'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=795,height=560,directories=no,menubar=no');
	}
	//CustomerInfo 팝업------------------------------------------------------------끝//
	
	//VOC 팝업------------------------------------------------------------끝//
	function vocValueChange(){
		if($('isVoc').value == 'Y'){
			$('vocType').show();
		}else{
			$('vocType').hide();
		}
	}
	function registProjectVocPopUp() {
		var win = new Window('modal_window', {
			className : "dialog",
			title : "VOC 일정 등록",
			top : 50,
			left : 50,
			width : 280,
			height : 280,
			zIndex : 150,
			opacity : 1, 
			resizable : true,
			showEffectOptions : {duration : 0.1},
			hideEffectOptions : {duration : 0.1},		
			url : "/action/ProjectVocAction.do?mode=registProjectVoc&projectCode="+$('projectCode').value
		});
		win.show(true);
		win.setDestroyOnClose();
	}
	function openVocContent(requestDate, projectCode){
		var url = 'http://intranet.kmac.co.kr/kmac/vocinfo/VocFeedbackView.asp?projectCode='+projectCode+'&RequestDate='+requestDate;
		 window.open(url,'VOC','top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=700,height=186,directories=no,menubar=no');
	}
	//VOC 팝업------------------------------------------------------------끝//
	
	//My Project------------------------------------------------------------끝//
	function searchMyProject(){
		document.projectMasterInfoForm.target = "";
		document.projectMasterInfoForm.action = "/action/MyProjectAction.do?mode=getMyProjectList";
		document.projectMasterInfoForm.submit();	
	}
	//My Project------------------------------------------------------------끝//
	
	//성과급  ------------------------------------------------------------끝//
	function deleteRowBUSalary(){
		var tableObj = $('buExpenseTable');
		var deleteOverAllIds = "";
		var chkBoxEls = $$('input[name="expenseCheck"]');
		for ( var i=0;i<chkBoxEls.length;i++){ 
			if ( chkBoxEls[i].checked ) {
				var ssn = chkBoxEls[i].ssn;
				var seq = chkBoxEls[i].seq;
				$(chkBoxEls[i]).up().up().remove();//성공 시 행삭제
				if(seq != -1){
					var status = AjaxRequest.post (
							{	'url':"/action/ProjectExpenseAction.do?mode=deleteProjectExpense",
								'parameters' : { "projectCode": $("projectCode").value, "year": $('year').value, "month": $('month').value, "ssn": ssn, "seq": seq },
								'onSuccess':function(obj){
									var res = eval('(' + obj.responseText + ')');
									alert("삭제하였습니다.");
								}, 
								'onLoading':function(obj){}, 
								'onError':function(obj){ 
									alert("삭제할 수 없습니다.");
								}
							}
					); 
				}
			}
		}
	}
	function addRowBUSalary(){
		var tableObj = $('buExpenseTable');
		if($('buExpenseTableBody').childElements().size() > 0){
			if($('buExpenseTableBody').down('tr').childElements().size() == 1){
		   		$('buExpenseTableBody').down('tr').remove();   		
		   	}
		}
	
		var tdCount = tableObj.down('tr').down('td').nextSiblings().length + 1 ;
	    var newRow=tableObj.insertRow(-1);
		var newCellArr = new Array();
		for ( var i=0;i<tdCount;i++ ){
			newCellArr[i] = newRow.insertCell(-1);
		}
		new Effect.Highlight(newCellArr[0], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[1], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[2], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		new Effect.Highlight(newCellArr[3], { startcolor: '#FFFF80', endcolor: '#ffffff', restorecolor: '#ffffff' }); 
		
		newCellArr[0].innerHTML = "<input type='checkbox' name='expenseCheck' />";
		newCellArr[1].innerHTML = $('buMemberValue').value;
		newCellArr[2].innerHTML = "<input type='text' name='amount' style='width: 100%' class='textInput_right' ><input type='hidden' name='seq' value='-1'>";
		newCellArr[3].innerHTML = "<select name='payYn' class='selectbox' style='width: 100%'><option value='N'>지급예정</option><option value='Y'>가지급됨</option>";
	
		newCellArr[0].className = "BGC";
		newCellArr[1].className = "BGC";
		newCellArr[2].className = "BGC";
		newCellArr[3].className = "BGL";
	}
	function storeBUSalary(){
		var ActionURL = "/action/ProjectExpenseAction.do?mode=insertProjectExpense";
		var sFrm = document.forms["projectExpenseForm"];
		
		var status = AjaxRequest.submit(
				sFrm,
				{	'url':ActionURL,
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						var rsObj = res.buSalary;		 
						var rsMember = res.buMember;
						var table = $('buExpenseTable');
						var tbody = $('buExpenseTableBody').childElements();
						var tdCount = $('buExpenseTableHeader').down('tr').childElements().size();
						tbody.each( function(buSalary){
							buSalary.remove();
						});
		
						rsObj.each(function(buSalary){
						    var newRow=table.insertRow(-1);
							var newCellArr = new Array();
							for ( var i=0;i<tdCount;i++ ){
								newCellArr[i] = newRow.insertCell(-1);
							}		
							
							newCellArr[0].innerHTML = "<input type='checkbox' name='expenseCheck' seq='"+buSalary.ssn+"'  ssn='"+buSalary.ssn+"'>";
							var temp = "";
							rsMember.each( function(mm){
								temp = temp + "<option value='"+mm.ssn+"' "+(mm.ssn == buSalary.ssn ?  "selected" : "")+" >"+buSalary.name+"</option>";
							});
							newCellArr[1].innerHTML = "<select name='ssn' class='selectbox' style='width: 100%' >"+temp+"</select>";
							newCellArr[2].innerHTML = "<input type='text' name='amount' style='width: 100%' class='textInput_right' value='"+buSalary.amount+"'><input type='hidden' name='seq' value='"+buSalary.seq+"'>";
							newCellArr[3].innerHTML = "<select name='payYn' class='selectbox' style='width: 100%'><option value='N' "+(buSalary.payYn == 'N' ?  "selected" : "")+">지급예정</option><option value='Y' "+(buSalary.payYn == 'Y' ?  "selected" : "")+">가지급됨</option>";
	
							
							
						});	
						alert('저장하였습니다.');
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;
	}
	//성과급  ------------------------------------------------------------끝//
	
	 
	//프로세스 ------------------------------------------------------------시작//
	function openProjectProgressContent(projectCode, contentId, workSeq, readOnly) {
		//alert(projectCode+":"+contentId+":"+workSeq+":"+readOnly);
		var url = "/action/ProjectProgressManagementAction.do?mode=loadProjectProgessContent&contentId="+contentId+"&projectCode="+projectCode+"&workSeq="+workSeq+"&readOnly="+readOnly;
		window.open(url, 'ProgressContent',
				'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=795,height=560,directories=no,menubar=no');
	}	
		
	function thisMovie(movieName) {
	    //if (navigator.appName.indexOf("Microsoft") != -1) {
	    //    return window[movieName];
	    //} else {
	        return document[movieName];
	    //}
	}
	
	function refreshGanttChart() {
		//alert('a');
		thisMovie("projectProgress").refreshGanttChart();
	}
	//프로세스 ------------------------------------------------------------끝//
	
	 
	function tempSaveWork(){
		var inputElements = $$('input[type!="radio"]');
		var selectElements = $$('select');
		
		if($("subject").value == ""){
		   alert('제목을 입력하세요.');
		   return;
		  }
		  
		var submitForm = $('projectLaunchSanctionForm');
		inputElements.each(function(item) {
			submitForm.appendChild(new Element('input', { name: item.name, value: item.value}));
		});
		selectElements.each(function(item) {
			submitForm.appendChild(new Element('input', { name: item.name, value: item.value}));
		});
		if($$('input[name="isApproved"]').length >0){
			if($$('input[name="isApproved"]')[0].checked){
				submitForm.appendChild(new Element('input', { name: "isApproved", value: "Y"}));
			}else if($$('input[name="isApproved"]')[1].checked){
				submitForm.appendChild(new Element('input', { name: "isApproved", value: "N"}));
			}
		}
		if(isIE){
			submitForm.appendChild(new Element('textarea', { name: 'content', value: $('content').value}));
			if($('state').value != 'SANCTION_STATE_DRAFT'){
				submitForm.appendChild(new Element('textarea', { name: 'rejectReason', value: $('rejectReason').value}));
				submitForm.appendChild(new Element('textarea', { name: 'rejectReasonView', value: $('rejectReasonView').value}));
			}
		}else{
			submitForm.appendChild(new Element('textarea', { name: 'content', id: 'content_n'}));
			$('content_n').value = $('content').value;
			if($('state').value != 'SANCTION_STATE_DRAFT'){
				submitForm.appendChild(new Element('textarea', { name: 'rejectReason', id: 'rejectReason_n'}));
				submitForm.appendChild(new Element('textarea', { name: 'rejectReasonView', id: 'rejectReasonView_n'}));
				$('rejectReason_n').value = $('rejectReason').value;
				$('rejectReasonView_n').value = $('rejectReasonView').value;
			}
		}

		var ActionURL = "/action/ProjectLaunchSanctionAction.do?mode=saveProjectLaunchSanction";
		var status = AjaxRequest.submit(
				submitForm,
				{	'url': "/action/ProjectLaunchSanctionAction.do?mode=saveProjectLaunchSanction",
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						if(res.result == 'SUCCESS'){
							alert("저장하였습니다.");
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
	
	function draftSave(){
		var ActionURL;
		if($("taskId").value == ""){
			ActionURL = "/action/ProjectLaunchSanctionAction.do?mode=draftProjectLaunchSanction";		
		} else {
			ActionURL = "/action/ProjectLaunchSanctionAction.do?mode=saveProjectLaunchSanction";		
		}
		
		var inputElements = $$('input[type!="radio"]');
		var selectElements = $$('select');
		var submitForm = document.forms["projectLaunchSanctionForm"];
		inputElements.each(function(item) {
			var elmt = new Element('input');
			$(elmt).writeAttribute("name", item.name );
			$(elmt).writeAttribute("value", item.value );
			submitForm.appendChild(elmt);
		});
		selectElements.each(function(item) {
			var elmt = new Element('input');
			$(elmt).writeAttribute("name", item.name );
			$(elmt).writeAttribute("value", item.value );
			submitForm.appendChild(elmt);
		});
    	var elmt = new Element('textarea');
		$(elmt).writeAttribute("name", "content" );
		$(elmt).writeAttribute("id", "content_n" );
		if((navigator.appVersion.indexOf("MSIE 10") > -1) || (navigator.userAgent.indexOf("Chrome") > -1) || (!!navigator.userAgent.match(/Trident.*rv[ :]*11\./))){
			submitForm.appendChild(elmt);
			$('content_n').innerHTML = $('content').value; 
		}else{
			if($('content') == null){
				alert("품의내용 Tab으로 이동 후 등록하여 주시기 바랍니다.");return;
			}
			$(elmt).writeAttribute("value", $('content').value ); 
			submitForm.appendChild(elmt);
		}
		if($('state').value != 'SANCTION_STATE_DRAFT'){
			var elmt1 = new Element('textarea');
			$(elmt1).writeAttribute("name", "rejectReason" );
			$(elmt1).writeAttribute("id", "rejectReason_n" );
			var elmt2 = new Element('textarea');
			$(elmt2).writeAttribute("name", "rejectReasonView" );
			$(elmt2).writeAttribute("id", "rejectReasonView_n" );
			if((navigator.appVersion.indexOf("MSIE 10") > -1) || (navigator.userAgent.indexOf("Chrome") > -1) || (!!navigator.userAgent.match(/Trident.*rv[ :]*11\./))){
				submitForm.appendChild(elmt1);
				submitForm.appendChild(elmt2);
				$('rejectReason_n').innerHTML = $('rejectReason').value; 
				$('rejectReasonView_n').innerHTML = $('rejectReasonView').value; 
			}else{
				$(elmt1).writeAttribute("value", $('rejectReason').value ); 
				$(elmt2).writeAttribute("value", $('rejectReasonView').value );
				submitForm.appendChild(elmt1);
				submitForm.appendChild(elmt2);
			}
		}
		var status = AjaxRequest.submit(
				submitForm,
				{	'url': ActionURL,
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						if(res.result == 'SUCCESS'){
							alert("저장하였습니다.");
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
	
	function deleteWork(){
		if(confirm("삭제 하시겠습니까?")) {
			var inputElements = $$('input[type="text"]');
			
			var submitForm = document.forms["projectLaunchSanctionForm"];
			inputElements.each(function(item) {
				//submitForm.appendChild(new Element('input', { name: item.name, value: item.value}));
				
				var elmt = new Element('input');
				$(elmt).writeAttribute("name", item.name );
				$(elmt).writeAttribute("value", item.value );
				submitForm.appendChild(elmt);
				
			});
			
			var status = AjaxRequest.submit(
					submitForm,
					{	'url': "/action/ProjectLaunchSanctionAction.do?mode=deleteProjectLaunchSanction",
						'onSuccess':function(obj){
							var res = eval('(' + obj.responseText + ')');
							if(res.result == 'SUCCESS'){alert("삭제하였습니다.");
								document.projectLaunchSanctionForm.target = "";
								document.projectLaunchSanctionForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
								document.projectLaunchSanctionForm.submit();								
							}		
						},
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("삭제할 수 없습니다.");
						}
					}
			);	 
		}
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
		
		var inputElements = $$('input[type!="radio"]');
		var radioElements = $$('input[name="isApproved"]');
		var selectElements = $$('select');

		var state = $('state').value;
		if(state == "SANCTION_STATE_DRAFT"){
			if($('registerSsn') == null || $('registerSsn').value == ''){
				alert("기안자 정보가 없습니다.");	return;
			}			
			if($('cioSsn') == null || $('cioSsn').value == ''){
				alert("승인자 정보가 없습니다.");	return;
			}
			$('isApproved').value= 'Y';
		}		
		if(state != "SANCTION_STATE_DRAFT" && state != 'SANCTION_STATE_REJECT_DRAFT'){
			if(radioElements[0].checked == false && radioElements[1].checked == false){
				alert('승인여부를 선택하세요');
				return;
			}	
		}
		if($("subject").value == ""){
		   alert('제목을 입력하세요.');
		   //document.projectLaunchSanctionForm.subject.focus();
		   return;
		  }
		if(doubleSubmitCheck()) return;
		//var submitForm = $('projectLaunchSanctionForm');
		var submitForm = document.forms["projectLaunchSanctionForm"];
		//alert(submitForm);
		inputElements.each(function(item) {
			var elmt = new Element('input');
			$(elmt).writeAttribute("name", item.name );
			$(elmt).writeAttribute("value", item.value );
			submitForm.appendChild(elmt);
		});
		selectElements.each(function(item) {
			var elmt = new Element('input');
			$(elmt).writeAttribute("name", item.name );
			$(elmt).writeAttribute("value", item.value );
			submitForm.appendChild(elmt);
		});
		if($$('input[name="isApproved"]').length >0){
			if($$('input[name="isApproved"]')[0].checked){
				var elmt = new Element('input');
				$(elmt).writeAttribute("name", "isApproved" );
				$(elmt).writeAttribute("value", "Y" );
				submitForm.appendChild(elmt);
			}else if($$('input[name="isApproved"]')[1].checked){
				var elmt = new Element('input');
				$(elmt).writeAttribute("name", "isApproved" );
				$(elmt).writeAttribute("value", "N" );
				submitForm.appendChild(elmt);
			}
		}
		if($$('input[name="isWholeApproval"]').length >0){
			if($$('input[name="isWholeApproval"]')[0].checked) {
				var elmt = new Element('input');
				$(elmt).writeAttribute("name", "isWholeApproval" );
				$(elmt).writeAttribute("value", "Y" );
				submitForm.appendChild(elmt);
			}else {
				var elmt = new Element('input');
				$(elmt).writeAttribute("name", "isWholeApproval" );
				$(elmt).writeAttribute("value", "N" );
				submitForm.appendChild(elmt);
			}
		}
		
		<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
			if($('content').value == ''){
				alert('품의 내용을 입력하세요.');
				return;
			}	
		</c:if>
    	var elmt = new Element('textarea');
		$(elmt).writeAttribute("name", "content" );
		$(elmt).writeAttribute("id", "content_n" );
		//if((navigator.appVersion.indexOf("MSIE 10") > -1) || (navigator.userAgent.indexOf("Chrome") > -1) || (!!navigator.userAgent.match(/Trident.*rv[ :]*11\./))){
			submitForm.appendChild(elmt);
			$('content_n').innerHTML = $('content').value; 
		//}else{
		//	$(elmt).writeAttribute("value", $('content').value ); 
		//	submitForm.appendChild(elmt);
		//}
		if($('state').value != 'SANCTION_STATE_DRAFT'){
			var elmt1 = new Element('textarea');
			$(elmt1).writeAttribute("name", "rejectReason" );
			$(elmt1).writeAttribute("id", "rejectReason_n" );
			var elmt2 = new Element('textarea');
			$(elmt2).writeAttribute("name", "rejectReasonView" );
			$(elmt2).writeAttribute("id", "rejectReasonView_n" );
			//if((navigator.appVersion.indexOf("MSIE 10") > -1) || (navigator.userAgent.indexOf("Chrome") > -1) || (!!navigator.userAgent.match(/Trident.*rv[ :]*11\./))){
				submitForm.appendChild(elmt1);
				submitForm.appendChild(elmt2);
				$('rejectReason_n').innerHTML = $('rejectReason').value; 
				$('rejectReasonView_n').innerHTML = $('rejectReasonView').value; 
			//}else{
			//	$(elmt1).writeAttribute("value", $('rejectReason').value ); 
			//	$(elmt2).writeAttribute("value", $('rejectReasonView').value );
			//	submitForm.appendChild(elmt1);
			//	submitForm.appendChild(elmt2);
			//}
		}
		var ActionURL = "";
		if($('seq').value == '0'){
			ActionURL = "/action/ProjectLaunchSanctionAction.do?mode=createProjectLaunchSanction";
		}else{
			ActionURL = "/action/ProjectLaunchSanctionAction.do?mode=executeProjectLaunchSanction";
		}

		AjaxRequest.submit(
				submitForm,
				{	'url':ActionURL,
					'onSuccess':function(obj){
						alert("등록하였습니다.");
						if($('state').value == "SANCTION_STATE_DRAFT"){
							document.location = "/action/RegisterProjectAction.do?mode=getRegisterProjectList";
						}else{
							document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
						}						
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		);
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

	function refTaskRequest(){
		var sFrm = document.forms["projectLaunchSanctionForm"];

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
	function viewBudget() {
		var url="/action/ProjectBudjetInfoAction.do?mode=goProjectBudgetInfo&projectCode=<c:out value="${projectCode}"/>&viewMode=lsanction";
		window.open(url,'budget','top=0,left=50,status=no,toolbar=no,scrollbars=yes,resizable=yes,width=850,height=700,directories=no,menubar=no');
	}
	<!-- By-Pass 추가 -->
	function viewBudget2() {
		var url="/action/ProjectBudjetInfoAction.do?mode=goProjectBudgetInfo2&projectCode=<c:out value="${projectCode}" />&viewMode=lsanction";
		window.open(url,'budget','top=0,left=50,status=no,toolbar=no,scrollbars=yes,resizable=yes,width=850,height=700,directories=no,menubar=no');
	}
	function checkCC() {
		<c:if test="${ccoTarget != NULL && ccoTarget != ''}">
			alert('해당 프로젝트의 담당 CCO인 <c:out value="${ccoTarget}" /> 를 참조자에 지정하십시오.');
			return;
		</c:if>
	}
	//첨부파일 다운로드
	function fileDownload1(uuid){
		fileDownload(uuid, '<c:out value="${param.log}"/>');
	}
	//첨부파일 삭제
	function deleteRowAttachFile(obj, fileId){	
		if(!confirm("파일을 삭제하시겠습니까?")) return;
		var deleteURL = "/action/RepositoryAction.do?mode=deleteFile"; //Upload URL
		var extraData ={"fileId":fileId}; //Extra Data.
		var jqXHR=j$.ajax({
			url: deleteURL,
			type: "GET",
			cache: false,
			data: extraData,
			success: function(data){
				var tr = j$(obj).parent().parent().parent();
				/* alert(j$(tr).attr("id")); */
				tr.remove();
				if(j$("#attachFileTableBody div").length == 0){					
					j$("#attachFileTableHeader").hide();
				}
			}
		});
	}
</script>
</head>

<body onload="checkCC()">
<div style="display: none">
	<form name="eduCourseRelForm" method="post">
		<input name="p_code" id="p_code" value="" type="hidden"> 
		<input name="processCode" id="processCode" value="" type="hidden"> 
		<input name="bizCode" id="bizCode" value="" type="hidden">
		<input name="_id" id="_id" value="" type="hidden">		
	</form>
	<form name="projectLaunchSanctionForm" method="post">
		<input name="chkSum" id="chkSum" value="" type="hidden">
	</form>
</div>

<div id="hiddneValue" style="display: none;">
	<input type="text" name="taskId" id="taskId" value="<c:out value="${sanctionDoc.taskId}" />" /> 
	<input type="text" name="approvalType" id="approvalType" value="<c:out value="${sanctionDoc.approvalType}" />" /> 
	<input type="text" name="projectCode" id="projectCode" value="<c:out value="${sanctionDoc.projectCode}" />" /> 
	<input type="text" name="seq" id="seq" value="<c:out value="${sanctionDoc.seq}" />" /> 
	<input type="text" name="state" id="state" value="<c:out value="${sanctionDoc.state}" />" />
	<input type="text" name="cco" id="cco" value="<c:out value="${ccoTarget}" />" />
	<!-- By-Pass 추가 -->
	<input type="text" name="func" id="func" value="<c:out value="${sanctionDoc.func}" />" />
	<input type="text" name="projectCodeBp" id="projectCodeBp" value="<c:out value="${sanctionDoc.projectCodeBp}" />" />
</div>


<!-- location -->
<div class="location">
	<p class="menu_title">프로젝트 실행품의</p>
	<ul>
		<li class="home">HOME</li>
		<li>PMS</li>
		<li>프로젝트 실행품의</li>
	</ul>
</div>
<!-- // location -->	

<div class="contents sub"><!-- 서브 페이지 .sub -->
	<div class="fixed_box">
		<div class="title">
			<div class="h1_area">
				<p class="h1"><i class="mdi mdi-file-document-outline"></i>
				<c:choose>
					<c:when test="${sanctionDoc.approvalType=='A'}">
						프로젝트 실행품의
					</c:when>
					<c:otherwise>
						프로젝트 기획품의
					</c:otherwise>
				</c:choose>
				</p>	
				<button type="button" class="btn line btn_black	" onclick="viewBudget();"><i class="mdi mdi-currency-krw"></i>예산보기</button>
			</div>
			<div class="btn_area">
				<!-- <button type="button" class="btn line btn_grey" onclick="location.href='javascript:;'"><i class="mdi mdi-printer"></i>인쇄</button> -->
				<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back();'"><i class="mdi mdi-backburger"></i>목록</button>
			</div>
		</div>	
		<div class="fixed_contents sc">
			<div class="sign_area">
				<div class="tab_menu_link_tit">
			   <!-- <p class="sign_title">21 농협 EPC 사업전략 수립 실행 품의의 건</p> -->
					<ul class="tab_menu_link">
						<li><a href="#" class="active" id="pane11" >품의내용</a></li>
						<li><a href="#" id="pane12">프로젝트정보</a></li>
					</ul>
				</div>
				<!-- sanctionLine area -->
				<%@include file="/sanction/common/sanctionLineInfo.jsp" %>	
				<!-- // sanctionLine area -->
			</div>
			
			<div id="Process_overlay1" class="overlay" style="display: none">
				<img alt="" src="/images/loading.gif" align="middle">
			</div>
			<div id="Process1" class="pane"></div>
		</div>
		<script type="text/javascript">
			new TabbedPane("Process1",
				{
					'pane11': '/action/ProjectLaunchSanctionAction.do?mode=getProjectLaunchSanctionContentData&projectCode=<c:out value="${sanctionDoc.projectCode}"/>&seq=<c:out value="${sanctionDoc.seq}"/>&workId=<c:out value="${sanctionDoc.taskId}"/>&approvalType=<c:out value="${sanctionDoc.approvalType}" />&viewMode=lsanction',
					'pane12': '/action/ProjectMasterInfoAction.do?mode=getProjectMasterInfo&projectCode=<c:out value="${projectCode}"/>&viewMode=lsanction&projectRole=PM',
				/* 'pane13': '/action/ProjectProgressInfoAction.do?mode=getProjectProgressInfo&projectCode=<c:out value="${projectCode}"/>&viewMode=lsanction&readonly=<c:out value="${subinfoReadonly}"/>' */
				/* <c:if test="${project.projectTypeCode != 'MM'}">
					,'pane14': '/action/ProjectReportInfoAction.do?mode=getProjectReportInfoList&projectCode=<c:out value="${projectCode}"/>&viewMode=lsanction&readonly=<c:out value="${subinfoReadonly}"/>'
				</c:if> */
				/* <c:if test="${project.businessTypeCode == 'BTA'}">
					,'pane16': '/action/ProjectVocAction.do?mode=selectVocInfoList&projectCode=<c:out value="${projectCode}"/>&viewMode=lsanction&readonly=<c:out value="${subinfoReadonly}"/>'
				</c:if>	 */
				},
				{
					onClick: function(e) {
						$('Process_overlay1').show();
					},
					onSuccess: function(e) {
						$('Process_overlay1').hide();
					}
				}
			);
		</script>		
	</div>
</div>
</body>
</html>