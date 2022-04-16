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
	if(businessTypeCode=='BTA' || (businessTypeCode=='BTE' && (processTemplateCode=='N1' || processTemplateCode=='N2' || processTemplateCode=='N4'))){
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
	var date_tmp = new Date()
	var tmp = (date_tmp.getMonth())+ (date_tmp.getMonth())+ (date_tmp.getDate())+ (date_tmp.getHours())+ (date_tmp.getMinutes())+ (date_tmp.getSeconds())+ (date_tmp.getMilliseconds()); 
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
	
	newCellArr[0].innerHTML = "<input type='hidden' name='memberTrainingYn' id='memberTrainingYn' value='Y'><input type='checkbox' name='projectMemberCheck' id='projectMemberCheck' />";
	newCellArr[1].innerHTML = "<select name='memberRole' id='memberRole' class='selectbox'><option>-선택-</option><option value='MB'>멤버</option><option value='PL'>PL</option><option value='PM'>PM</option></select>";
	newCellArr[2].innerHTML = "<input type='hidden' name='memberSsn' id='memberSsn'><input type='text' name='memberName' id='memberName' class='textInput_noLine_center' style='width: 100%' readonly='	readonly'>";
	newCellArr[3].innerHTML = "<input type='hidden' name='memberPosition' id='memberPosition'><input type='text' name='memberPositionName' id='memberPositionName' class='textInput_noLine_center' style='width: 100%' readonly='readonly'>";
	newCellArr[4].innerHTML = "<input type='hidden' name='memberJobClass' id='memberJobClass'><input type='text' name='memberResRate' id='memberResRate' class='textInput_center' style='width: 100%' value='1'>";
	newCellArr[5].innerHTML = "<input type='text' name='memberCost' id='memberCost' class='textInput_right' style='width: 100%' value='0'>";
	//newCellArr[6].innerHTML = "<input type='text' name='memberContributionCost' id='memberContributionCost' class='textInput_right' style='width: 100%' value='0'>"; 기여금액 주석처리
	newCellArr[6].innerHTML = "<input type='text' name='memberStartDate' id='memberStartDate__"+tmp+"' value='' style='width:73px;' /> ~ <input type='text'name='memberEndDate' id='memberEndDate__"+tmp+"' value='' style='width:73px;'/>"; 
	newCellArr[7].innerHTML = "<img alt='편집' src='/images/btn_glass.jpg'  style='cursor: hand;' onclick='openExpertPoolPopUpForProjectMaster(this);'>";

	newCellArr[0].className = "BGC";
	newCellArr[1].className = "BGC";
	newCellArr[2].className = "BGC";
	newCellArr[3].className = "BGL";
	newCellArr[4].className = "BGL";
	newCellArr[5].className = "BGC";
	newCellArr[6].className = "BGC";	
	newCellArr[7].className = "BGC";

	jQuery(function(){jQuery( "#memberStartDate__"+tmp ).datepicker({showOn: 'focus'});});
	jQuery(function(){jQuery( "#memberEndDate__"+tmp ).datepicker({showOn: 'focus'});});
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
function setProjectMember(expertPoolList) {
	var memberTrainingYnList = $$('input[name="memberTrainingYn"]');
	var memberSsnList = $$('input[name="memberSsn"]');
	var memberNameList = $$('input[name="memberName"]');
	var memberPositionList = $$('input[name="memberPosition"]');
	var memberPositionNameList = $$('input[name="memberPositionName"]'); 
	var memberJobClassList = $$('input[name="memberJobClass"]');
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
					memberJobClassList[i].value = expertPoolList[n].jobClass;
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
	var ActionURL = "/action/ProjectReportInfoAction.do?mode=searchProjectReportInfoList";
	var sFrm = document.forms["projectReportInfoForm"];
	
	var status = AjaxRequest.submit (
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.projectReportPlanList;		 
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
		    			newCellArr[2].innerHTML = "<span id='workSeq' style='display: none;'>"+preportInfo.workSeq+"</span><span id='workName'>"+preportInfo.workName+"</span>";
		    			var tempHtml = "";
		    			for(var i=0; preportInfo.ssnArray.length >i; i++){
			    			if(preportInfo.nameArray[i] != ''){
			    				tempHtml = "<span id='"+preportInfo.year+preportInfo.month+preportInfo.day+preportInfo.ssnArray[i]+"' year='"+preportInfo.year+"' month='"+preportInfo.month+"' day='"+preportInfo.day+"' ssn='"+preportInfo.ssnArray[i]+"'>"
												+preportInfo.nameArray[i]
												+"<img alt='삭제' src='/images/delete.gif' align='absmiddle' style='cursor: hand;' onclick=\"deleteProjectReportSchedule('"+preportInfo.year+preportInfo.month+preportInfo.day+preportInfo.ssnArray[i]+"')\">" 
												+ ",&nbsp;</span>" + tempHtml  ;
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
function openProjecReportScheduleRegistPopup(projectReportElmt) { 
	selectedElmt = $(projectReportElmt);
	var preportPop = 
	window.open("/action/ProjectReportInfoAction.do?mode=loadProjectSchedulePopup&projectCode="+$('projectCode').value,
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
						alert("저장할 수 없습니다.");
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
								alert("저장할 수 없습니다.");
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
	newCellArr[2].innerHTML = "<input type='text' name='amount' style='width: 100%' onchange='getNumber(this);' onkeyup='getNumber(this);' class='textInput_right' ><input type='hidden' name='seq' value='-1'>";
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
function openProjectProgressContent(projectCode, contentId, workSeq) {
	//alert(projectCode+":"+contentId+":"+workSeq);
	var url = "/action/ProjectProgressManagementAction.do?mode=loadProjectProgessContent&contentId="+contentId+"&projectCode="+projectCode+"&workSeq="+workSeq;
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
